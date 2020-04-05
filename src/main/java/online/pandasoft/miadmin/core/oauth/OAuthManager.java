/*
 * Copyright 2020 PandaSoft.Dev Social Networks.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package online.pandasoft.miadmin.core.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import online.pandasoft.miadmin.api.http.parameters.AppCreateParameter;
import online.pandasoft.miadmin.api.http.parameters.AuthSessionGenerateParameter;
import online.pandasoft.miadmin.api.http.parameters.AuthSessionUserkeyParameter;
import online.pandasoft.miadmin.api.http.parameters.RequestParameters;
import online.pandasoft.miadmin.api.http.responses.AppCreateResponse;
import online.pandasoft.miadmin.api.http.responses.AuthSessionGenerateResponse;
import online.pandasoft.miadmin.api.http.responses.AuthSessionUserkeyResponse;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OAuthManager {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();

    private final String instanceUrl;

    public OAuthManager(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getAppSecret() {
        AppCreateParameter appCreate = (AppCreateParameter) RequestParameters.APP_CREATE;
        appCreate.setName("MiAdmin");
        appCreate.setDescription("A multi-functional tool to support Misskey's operations.");
        appCreate.setPermission(getAllPermissionList());
        appCreate.setCallbackURL("http://localhost:8080/");

        try {
            String requestParameter = mapper.writeValueAsString(appCreate);
            RequestBody requestBody = RequestBody.create(requestParameter, JSON);
            Request request = new Request.Builder()
                    .url("https://" + instanceUrl + "/api/" + appCreate.getEndpoint())
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 200) {
                    AppCreateResponse appCreateResponse = mapper.readValue(response.body().string(), AppCreateResponse.class);
                    return appCreateResponse.getSecret();
                }
            } catch (IOException e) {
                log.error("An error occurred during a secret key request.", e);
            }
        } catch (JsonProcessingException e) {
            log.error("An error occurred while reading Json.", e);
        }
        return null;
    }

    public void openAuthenticationPage(String appSecret) {
        AuthSessionGenerateParameter generateParameter = (AuthSessionGenerateParameter) RequestParameters.AUTH_SESSION_GENERATE;
        generateParameter.setAppSecret(appSecret);

        String url = null;
        try {
            String requestParameter = mapper.writeValueAsString(generateParameter);
            RequestBody requestBody = RequestBody.create(requestParameter, JSON);
            Request request = new Request.Builder()
                    .url("https://" + instanceUrl + "/api/" + generateParameter.getEndpoint())
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 200) {
                    AuthSessionGenerateResponse generateResponse = mapper.readValue(response.body().string(), AuthSessionGenerateResponse.class);
                    url = generateResponse.getUrl();
                }
            } catch (IOException e) {
                log.error("An error occurred during the authentication URL request.", e);
            }
        } catch (JsonProcessingException e) {
            log.error("An error occurred while reading Json.", e);
        }

        if (url == null)
            return;

        try {
            URI uri = new URI(url);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            log.error("The specified string does not conform to the URL format.");
        } catch (IOException e) {
            log.error("The oAuth page could not be opened. To authenticate, please visit this page.\n{}", url);
        }
    }

    public String waitForTokenResponse() {
        String token = null;
        log.debug("Start the standby server on port 8080.");
        try (ServerSocket ss = new ServerSocket(8080)) {
            do {
                try (Socket s = ss.accept();
                     OutputStream out = s.getOutputStream();
                     InputStream in = s.getInputStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = reader.readLine();
                    out.write("HTTP/1.0 200 OK\n".getBytes());
                    s.close();
                    log.debug("A request has been received on the standby server:\n{}", line);
                    token = line.split(" ")[1].substring(8);
                } catch (IOException e) {
                    log.error("There was a problem receiving the request.", e);
                }
            } while (token == null);
        } catch (IOException e) {
            log.error("An error occurred while preparing the standby server.", e);
        }
        log.debug("The standby server has been stopped.");
        return token;
    }

    public String getAccessToken(String appSecret, String token) {
        AuthSessionUserkeyParameter userkeyParameter = (AuthSessionUserkeyParameter) RequestParameters.AUTH_SESSION_USERKEY;
        userkeyParameter.setAppSecret(appSecret);
        userkeyParameter.setToken(token);

        try {
            String requestParameter = mapper.writeValueAsString(userkeyParameter);
            RequestBody requestBody = RequestBody.create(requestParameter, JSON);
            Request request = new Request.Builder()
                    .url("https://" + instanceUrl + "/api/" + userkeyParameter.getEndpoint())
                    .post(requestBody)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 200) {
                    AuthSessionUserkeyResponse userkeyResponse = mapper.readValue(response.body().string(), AuthSessionUserkeyResponse.class);
                    String applicationToken = DigestUtils.sha256Hex(userkeyResponse.getAccessToken() + appSecret);
                    return applicationToken;
                }
            } catch (IOException e) {
                log.error("An error occurred during the request for an access token.", e);
            }
        } catch (JsonProcessingException e) {
            log.error("An error occurred while reading Json.", e);
        }
        return null;
    }

    public List<String> getAllPermissionList() {
        List<String> permissions = new ArrayList<>();
        permissions.add("read:account");
        permissions.add("write:account");
        permissions.add("read:blocks");
        permissions.add("write:blocks");
        permissions.add("read:drive");
        permissions.add("write:drive");
        permissions.add("read:favorites™");
        permissions.add("write:favorites");
        permissions.add("read:following");
        permissions.add("write:following");
        permissions.add("read:messaging");
        permissions.add("write:messaging");
        permissions.add("read:mutes");
        permissions.add("write:mutes");
        permissions.add("write:notes");
        permissions.add("read:notifications");
        permissions.add("write:notifications");
        permissions.add("read:reactions");
        permissions.add("write:reactions");
        permissions.add("write:votes");
        permissions.add("read:pages");
        permissions.add("write:pages");
        permissions.add("write:page-likes");
        permissions.add("read:user-groups");
        permissions.add("write:user-groups");
        return permissions;
    }
}

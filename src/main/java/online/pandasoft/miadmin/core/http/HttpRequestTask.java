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

package online.pandasoft.miadmin.core.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;
import online.pandasoft.miadmin.core.task.MiTask;
import online.pandasoft.miadmin.core.task.MiTaskResultHandler;

@Slf4j
public class HttpRequestTask implements MiTask {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();
    private static final MiAdminLauncher launcher = Main.getLauncher();
    private final RequestParameter parameter;
    private final MiTaskResultHandler handler;

    public HttpRequestTask(RequestParameter parameter, MiTaskResultHandler handler) {
        this.parameter = parameter;
        this.handler = handler;
    }

    @Override
    public MiTaskResultHandler getHandler() {
        return handler;
    }

    @Override
    public Object call() throws Exception {
        parameter.setToken(launcher.getToken());
        String requestParameter = mapper.writeValueAsString(parameter);
        RequestBody requestBody = RequestBody.create(requestParameter, JSON);
        log.debug("Send Request: {}\n{}\n", parameter.getEndpoint(), requestParameter);
        Request request = new Request.Builder()
                .url("https://" + launcher.getMiConfig().getAuth().getInstanceUrl() + "/api/" + parameter.getEndpoint())
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseRaw = response.body().string();
            Object responseClass = null;
            log.debug("Response Received: Code {}\n{}\n", response.code(), responseRaw);
            if (response.code() == parameter.getSuccessCode() && parameter.getResponseClass() != null)
                responseClass = mapper.readValue(responseRaw, parameter.getResponseClass());
            return new HttpRequestResult(response.code(), requestParameter, responseClass, responseRaw);
        }
    }
}

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

package online.pandasoft.miadmin.core.websocket;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketClient {
    private static final MiAdminLauncher launcher = Main.getLauncher();
    private final OkHttpClient client = new OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS).build();
    private WebSocket webSocket;

    public void connect() {
        try {
            URL url = new URL("wss://" + launcher.getMiConfig().getAuth().getInstanceUrl() + "streaming?i=" + launcher.getToken());
            Request request = new Request.Builder().url(url).build();
            webSocket = client.newWebSocket(request, new MiSocketListener());
        } catch (MalformedURLException e) {
            log.error("The URL format is not correct.", e);
        }
    }

    public void sendMessage(String message) {
        if (webSocket != null)
            webSocket.send(message);
        else
            throw new IllegalStateException("Make sure to establish a websocket connection before running it.");
    }

    public void close() {
        client.dispatcher().executorService().shutdownNow();
        try {
            client.dispatcher().executorService().awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.debug("An interruption occurred while waiting for the end.");
        }
    }
}

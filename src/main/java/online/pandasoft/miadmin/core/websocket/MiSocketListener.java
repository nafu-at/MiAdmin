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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import online.pandasoft.miadmin.api.elements.message.Message;
import online.pandasoft.miadmin.api.elements.note.Note;
import online.pandasoft.miadmin.api.websocket.event.MessageReceivedEvent;
import online.pandasoft.miadmin.api.websocket.event.NoteReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MiSocketListener extends WebSocketListener {
    private static final Gson gson = new Gson();
    private static final ObjectMapper mapper = new ObjectMapper();
    private List<WebSocketEventListener> listeners = new ArrayList<>();

    public void registerListener(WebSocketEventListener eventListener) {
        listeners.add(eventListener);
    }

    private void fireEvent(WebSocketEvent event) {
        for (WebSocketEventListener listener : listeners) {
            try {
                listener.onEvent(event);
            } catch (Throwable e) {
                log.warn("There was an uncaught exception within the event listener.", e);
            }
        }
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        log.debug("WebSocket Message Received: {}", text);
        JsonObject jsonObject = gson.fromJson(text, JsonObject.class);
        String eventType = jsonObject.get("type").getAsString();
        JsonObject eventBody = jsonObject.get("body").getAsJsonObject();
        String connectionId = eventBody.get("id").getAsString();
        String messageType = eventBody.get("type").getAsString();
        String messageBody = eventBody.get("body").toString();

        WebSocketEvent webSocketEvent = new WebSocketEvent(connectionId, eventType, eventBody.toString(), messageType, messageBody);
        fireEvent(webSocketEvent);

        if (eventType == "channel") {
            try {
                switch (messageType) {
                    case "note":
                        fireEvent(new NoteReceivedEvent(webSocketEvent, mapper.readValue(messageBody, Note.class)));
                        break;

                    case "messagingMessage":
                        fireEvent(new MessageReceivedEvent(webSocketEvent, mapper.readValue(messageBody, Message.class)));
                        break;

                    default:
                        break;
                }
            } catch (JsonProcessingException e) {
                log.error("Failed to read the message I received.", e);
            }
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
    }
}

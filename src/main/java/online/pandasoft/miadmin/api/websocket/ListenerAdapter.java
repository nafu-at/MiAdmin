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

package online.pandasoft.miadmin.api.websocket;

import online.pandasoft.miadmin.api.websocket.event.MessageReceivedEvent;
import online.pandasoft.miadmin.api.websocket.event.NoteReceivedEvent;
import online.pandasoft.miadmin.core.websocket.IWebSocketEvent;
import online.pandasoft.miadmin.core.websocket.WebSocketEventListener;
import org.jetbrains.annotations.NotNull;

public abstract class ListenerAdapter implements WebSocketEventListener {

    @Override
    public final void onEvent(@NotNull IWebSocketEvent event) {
        // TODO: 2020/03/20 そのうちバリエーション増やす
        onWebSocketEvent(event);

        if (event instanceof NoteReceivedEvent)
            onNoteReceivedEvent((NoteReceivedEvent) event);
        if (event instanceof MessageReceivedEvent)
            onMessageReceivedEvent((MessageReceivedEvent) event);
    }

    public void onWebSocketEvent(IWebSocketEvent event) {
    }

    public void onNoteReceivedEvent(NoteReceivedEvent event) {
    }

    public void onMessageReceivedEvent(MessageReceivedEvent event) {
    }
}

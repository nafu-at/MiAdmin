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

package online.pandasoft.miadmin.api.websocket.event;

import online.pandasoft.miadmin.api.elements.file.File;
import online.pandasoft.miadmin.api.elements.note.Note;
import online.pandasoft.miadmin.api.elements.user.User;
import online.pandasoft.miadmin.core.websocket.WebSocketEvent;

import java.util.List;

public class NoteReceivedEvent extends WebSocketEvent {
    private final Note note;

    public NoteReceivedEvent(WebSocketEvent event, Note note) {
        super(event.getConnectionId(), event.getEventType(), event.getEventBody(), event.getMessageType(), event.getMessageBody());
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public User getAuthor() {
        return note.getUser();
    }

    public List<File> getFiles() {
        return note.getFiles();
    }
}

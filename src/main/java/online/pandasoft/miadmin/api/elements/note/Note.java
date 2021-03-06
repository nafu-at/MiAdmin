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

package online.pandasoft.miadmin.api.elements.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import online.pandasoft.miadmin.api.elements.file.File;
import online.pandasoft.miadmin.api.elements.general.Emoji;
import online.pandasoft.miadmin.api.elements.user.User;

import java.util.List;
import java.util.Map;

public class Note {
    @JsonProperty("id")
    private String id;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("userId")
    private String userID;
    @JsonProperty("user")
    private User user;
    @JsonProperty("text")
    private String text;
    @JsonProperty("cw")
    private String cw;
    @JsonProperty("visibility")
    private String visibility;
    @JsonProperty("localOnly")
    private boolean localOnly;
    @JsonProperty("renoteCount")
    private long renoteCount;
    @JsonProperty("repliesCount")
    private long repliesCount;
    @JsonProperty("reactions")
    private Map<String, Integer> reactions;
    @JsonProperty("emojis")
    private List<Emoji> emojis;
    @JsonProperty("fileIds")
    private List<String> fileIDS;
    @JsonProperty("files")
    private List<File> files;
    @JsonProperty("replyId")
    private String replyID;
    @JsonProperty("renoteId")
    private String renoteID;
    @JsonProperty("visibleUserIDS")
    private List<String> visibleUserIDS;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("reply")
    private Note reply;
    @JsonProperty("renote")
    private Note renote;
    @JsonProperty("viaMobile")
    private boolean viaMobile;
    @JsonProperty("isHidden")
    private boolean isHidden;
    @JsonProperty("mentions")
    private List<String> mentions;
    @JsonProperty("tags")
    private List<String> tags;
    @JsonProperty("poll")
    private Object poll;

    public String getID() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUserID() {
        return userID;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getCw() {
        return cw;
    }

    public String getVisibility() {
        return visibility;
    }

    public boolean isLocalOnly() {
        return localOnly;
    }

    public long getRenoteCount() {
        return renoteCount;
    }

    public long getRepliesCount() {
        return repliesCount;
    }

    public Map<String, Integer> getReactions() {
        return reactions;
    }

    public List<Emoji> getEmojis() {
        return emojis;
    }

    public List<String> getFileIDS() {
        return fileIDS;
    }

    public List<File> getFiles() {
        return files;
    }

    public Object getReplyID() {
        return replyID;
    }

    public String getRenoteID() {
        return renoteID;
    }

    public boolean isViaMobile() {
        return viaMobile;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public List<String> getVisibleUserIDS() {
        return visibleUserIDS;
    }

    public String getURI() {
        return uri;
    }

    public Note getReply() {
        return reply;
    }

    public Note getRenote() {
        return renote;
    }

    public List<String> getTags() {
        return tags;
    }

    public Object getPoll() {
        return poll;
    }
}

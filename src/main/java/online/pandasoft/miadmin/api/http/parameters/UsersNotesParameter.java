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

package online.pandasoft.miadmin.api.http.parameters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import online.pandasoft.miadmin.api.elements.note.Note;
import online.pandasoft.miadmin.core.http.RequestParameter;

import java.util.List;

public class UsersNotesParameter extends RequestParameter {
    @JsonProperty("userId")
    private String userID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("includeReplies")
    private boolean includeReplies;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("limit")
    private long limit = 10;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sinceId")
    private String sinceID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("untilId")
    private String untilID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sinceDate")
    private long sinceDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("untilDate")
    private long untilDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("includeMyRenotes")
    private boolean includeMyRenotes;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("withFiles")
    private boolean withFiles;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("fileType")
    private List<String> fileType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("excludeNsfw")
    private boolean excludeNsfw;

    public UsersNotesParameter(String endpoint) {
        super(endpoint);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setIncludeReplies(boolean includeReplies) {
        this.includeReplies = includeReplies;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public void setSinceID(String sinceID) {
        this.sinceID = sinceID;
    }

    public void setUntilID(String untilID) {
        this.untilID = untilID;
    }

    public void setSinceDate(long sinceDate) {
        this.sinceDate = sinceDate;
    }

    public void setUntilDate(long untilDate) {
        this.untilDate = untilDate;
    }

    public void setIncludeMyRenotes(boolean includeMyRenotes) {
        this.includeMyRenotes = includeMyRenotes;
    }

    public void setWithFiles(boolean withFiles) {
        this.withFiles = withFiles;
    }

    public void setFileType(List<String> fileType) {
        this.fileType = fileType;
    }

    public void setExcludeNsfw(boolean excludeNsfw) {
        this.excludeNsfw = excludeNsfw;
    }

    @Override
    public int getSuccessCode() {
        return 200;
    }

    @Override
    public Class getResponseClass() {
        return Note[].class;
    }
}

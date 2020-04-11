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
import online.pandasoft.miadmin.api.elements.user.User;
import online.pandasoft.miadmin.core.http.RequestParameter;

import java.util.List;

public class UsersShowParameter extends RequestParameter {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("userId")
    private String userID;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("userIds")
    private List<String> userIDS;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("username")
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("host")
    private String host;

    public UsersShowParameter(String endpoint) {
        super(endpoint);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserIDS(List<String> userIDS) {
        this.userIDS = userIDS;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public int getSuccessCode() {
        return 200;
    }

    @Override
    public Class getResponseClass() {
        return User.class;
    }
}

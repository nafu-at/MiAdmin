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

package online.pandasoft.miadmin.api.http.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AppCreateResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("callbackUrl")
    private String callbackURL;
    @JsonProperty("permission")
    private List<String> permission;
    @JsonProperty("secret")
    private String secret;

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public List<String> getPermission() {
        return permission;
    }

    public String getSecret() {
        return secret;
    }
}

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

import com.fasterxml.jackson.annotation.JsonProperty;
import online.pandasoft.miadmin.api.http.responses.AppCreateResponse;
import online.pandasoft.miadmin.core.http.RequestParameter;

import java.util.List;

public class AppCreateParameter extends RequestParameter {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("permission")
    private List<String> permission;
    @JsonProperty("callbackUrl")
    private String callbackURL;

    protected AppCreateParameter(String endpoint) {
        super(endpoint);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    @Override
    public int getSuccessCode() {
        return 200;
    }

    @Override
    public Class getResponseClass() {
        return AppCreateResponse.class;
    }
}

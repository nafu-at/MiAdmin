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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestParameter implements IRequestParameter {
    @JsonIgnore
    private final String endpoint;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("i")
    private String token;

    public RequestParameter(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @JsonIgnore
    @Override
    public int getSuccessCode() {
        return 204;
    }

    @JsonIgnore
    @Override
    public Class getResponseClass() {
        return null;
    }

    protected void setToken(String token) {
        this.token = token;
    }
}

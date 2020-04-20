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
import online.pandasoft.miadmin.api.http.responses.AdminResetPasswordResponse;
import online.pandasoft.miadmin.core.http.RequestParameter;

public class AdminResetPasswordParameter extends RequestParameter {
    @JsonProperty("userId")
    private String userId;

    public AdminResetPasswordParameter(String endpoint) {
        super(endpoint);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int getSuccessCode() {
        return 200;
    }

    @Override
    public Class getResponseClass() {
        return AdminResetPasswordResponse.class;
    }
}

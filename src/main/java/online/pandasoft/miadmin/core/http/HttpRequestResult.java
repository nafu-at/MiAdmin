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

import online.pandasoft.miadmin.core.task.MiTaskResult;

public class HttpRequestResult implements MiTaskResult {
    private final int code;
    private final String requestParameter;
    private final Object requestResponse;
    private final String rawMessage;

    protected HttpRequestResult(int code, String requestParameter, Object requestResponse, String rawMessage) {
        this.code = code;
        this.requestParameter = requestParameter;
        this.requestResponse = requestResponse;
        this.rawMessage = rawMessage;
    }

    /**
     * Returns the status code of the response.
     *
     * @return Status code of the response
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the parameters used for the request.
     *
     * @return Parameters used for the request
     */
    public String getRequestParameter() {
        return requestParameter;
    }

    /**
     * The response obtained by the request is returned with the specified type.
     *
     * @return Response obtained by the request
     */
    public Object getRequestResponse() {
        return requestResponse;
    }

    /**
     * Returns the raw message of the response obtained by the request.
     *
     * @return Raw message of the response obtained by the request
     */
    public String getRawMessage() {
        return rawMessage;
    }
}

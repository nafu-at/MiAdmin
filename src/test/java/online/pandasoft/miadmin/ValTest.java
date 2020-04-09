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

package online.pandasoft.miadmin;

import lombok.extern.slf4j.Slf4j;
import online.pandasoft.miadmin.api.http.parameters.AppCreateParameter;
import online.pandasoft.miadmin.api.http.parameters.RequestParameters;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ValTest {

    // Dead code.
    public static void main(String[] args) {
        List<String> permissions = new ArrayList<>();
        permissions.add("read:account");
        permissions.add("write:account");

        AppCreateParameter requestParameter1 = (AppCreateParameter) RequestParameters.newInstance(RequestParameters.APP_CREATE);
        AppCreateParameter requestParameter2 = (AppCreateParameter) RequestParameters.newInstance(RequestParameters.APP_CREATE);

        requestParameter1.setName("MiAdmin");
        requestParameter1.setDescription("A multi-functional tool to support Misskey's operations.");
        requestParameter1.setPermission(permissions);
        requestParameter1.setCallbackURL("http://127.0.0.1:8080/");

        log.info("Unchanged value1: {}", requestParameter1.toString());
        log.info("Unchanged value2: {}", requestParameter2.toString());

        requestParameter2.setName("MiAdmin2");
        requestParameter2.setDescription("A multi-functional tool to support Misskey's operations.");
        requestParameter2.setPermission(permissions);
        requestParameter2.setCallbackURL("http://127.0.0.2:8080/");

        log.info("Changed value1: {}", requestParameter1.toString());
        log.info("Changed value2: {}", requestParameter2.toString());
    }
}

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
import online.pandasoft.miadmin.core.oauth.OAuthManager;

@Slf4j
public class OAuthTest {

    public static void main(String[] args) {
        log.info("Test Start.");
        OAuthManager authManager = new OAuthManager("nijimiss.moe");
        String appSecret = authManager.getAppSecret();
        authManager.openAuthenticationPage(appSecret);
        String token = authManager.waitForTokenResponse();
        String accessToken = authManager.getAccessToken(appSecret, token);
        log.info("AccessToken: {}", accessToken);
        return;
    }
}

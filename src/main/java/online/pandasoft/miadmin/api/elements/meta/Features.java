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

package online.pandasoft.miadmin.api.elements.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Features {
    @JsonProperty("registration")
    private boolean registration;
    @JsonProperty("localTimeLine")
    private boolean localTimeLine;
    @JsonProperty("globalTimeLine")
    private boolean globalTimeLine;
    @JsonProperty("elasticsearch")
    private boolean elasticsearch;
    @JsonProperty("recaptcha")
    private boolean recaptcha;
    @JsonProperty("objectStorage")
    private boolean objectStorage;
    @JsonProperty("twitter")
    private boolean twitter;
    @JsonProperty("github")
    private boolean github;
    @JsonProperty("discord")
    private boolean discord;
    @JsonProperty("serviceWorker")
    private boolean serviceWorker;
    @JsonProperty("miauth")
    private boolean miauth;

    public boolean getRegistration() {
        return registration;
    }

    public boolean getLocalTimeLine() {
        return localTimeLine;
    }

    public boolean getGlobalTimeLine() {
        return globalTimeLine;
    }

    public boolean getElasticsearch() {
        return elasticsearch;
    }

    public boolean getRecaptcha() {
        return recaptcha;
    }

    public boolean getObjectStorage() {
        return objectStorage;
    }

    public boolean getTwitter() {
        return twitter;
    }

    public boolean getGithub() {
        return github;
    }

    public boolean getDiscord() {
        return discord;
    }

    public boolean getServiceWorker() {
        return serviceWorker;
    }

    public boolean getMiauth() {
        return miauth;
    }
}

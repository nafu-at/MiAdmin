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

package online.pandasoft.miadmin.core.launcher;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MiConfig {
    @JsonProperty("auth")
    private AuthConfigSection auth;
    @JsonProperty("database")
    private DatabaseConfigSection database;
    @JsonProperty("modules")
    private ModulesConfigSection modules;

    public AuthConfigSection getAuth() {
        return auth;
    }

    public DatabaseConfigSection getDatabase() {
        return database;
    }

    public ModulesConfigSection getModules() {
        return modules;
    }

    public static class AuthConfigSection {
        @JsonProperty("authType")
        private AuthType authType;
        @JsonProperty("saveToken")
        private boolean saveToken;
        @JsonProperty("fixedToken")
        private String fixedToken;
        @JsonProperty("instanceUrl")
        private String instanceUrl;

        public AuthType getAuthType() {
            return authType;
        }

        public boolean isSaveToken() {
            return saveToken;
        }

        public String getFixedToken() {
            return fixedToken;
        }

        public String getInstanceUrl() {
            return instanceUrl;
        }
    }

    public static class DatabaseConfigSection {
        @JsonProperty("tablePrefix")
        private String tablePrefix;
        @JsonProperty("address")
        private String address;
        @JsonProperty("database")
        private String database;
        @JsonProperty("username")
        private String username;
        @JsonProperty("password")
        private String password;

        public String getTablePrefix() {
            return tablePrefix;
        }

        public String getAddress() {
            return address;
        }

        public String getDatabase() {
            return database;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class ModulesConfigSection {
        @JsonProperty("enableModules")
        private List<String> enableModules;
        @JsonProperty("dynamicModule")
        private boolean dynamicModule;
        @JsonProperty("enableInternalCommand")
        private boolean enableInternalCommand;

        public List<String> getEnableModules() {
            return enableModules;
        }

        public boolean isDynamicModule() {
            return dynamicModule;
        }

        public boolean isEnableInternalCommand() {
            return enableInternalCommand;
        }
    }

    public enum AuthType {
        OAUTH, TOKEN
    }
}

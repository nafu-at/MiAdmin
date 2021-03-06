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

import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import online.pandasoft.miadmin.core.command.CommandExecutor;
import online.pandasoft.miadmin.core.command.CommandManager;
import online.pandasoft.miadmin.core.command.ConsoleReader;
import online.pandasoft.miadmin.core.command.executor.InviteCommand;
import online.pandasoft.miadmin.core.command.executor.PasswordResetCommand;
import online.pandasoft.miadmin.core.command.executor.SilenceCommand;
import online.pandasoft.miadmin.core.command.executor.SoftIceCommand;
import online.pandasoft.miadmin.core.command.executor.system.HelpCommand;
import online.pandasoft.miadmin.core.command.executor.system.ModuleCommand;
import online.pandasoft.miadmin.core.command.executor.system.StopCommand;
import online.pandasoft.miadmin.core.database.DatabaseConnector;
import online.pandasoft.miadmin.core.database.tables.SystemParametersTable;
import online.pandasoft.miadmin.core.module.ModuleManager;
import online.pandasoft.miadmin.core.oauth.OAuthManager;
import online.pandasoft.miadmin.core.task.MiTaskManager;
import online.pandasoft.miadmin.core.websocket.WebSocketClient;

import java.sql.SQLException;

@Slf4j
public class Launcher implements MiAdminLauncher {
    private ConfigManager configManager;
    private MiConfig config;

    private String accessToken;
    private DatabaseConnector connector;
    private SystemParametersTable parametersTable;
    private MiTaskManager taskManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;

    private WebSocketClient webSocketClient;

    @Override
    public String getToken() {
        return accessToken;
    }

    @Override
    public void launch() {
        log.info("Load the configuration file.");
        configManager = new ConfigManager();
        if (configManager.existsConfig(true)) {
            configManager.reloadConfig();
            config = configManager.getConfig();
            log.info("The configuration file has been successfully loaded.");
        } else {
            log.error("The configuration file could not be loaded successfully.");
            return;
        }

        log.info("Start a connection to the database.");
        connector = new DatabaseConnector(
                config.getDatabase().getAddress(),
                config.getDatabase().getDatabase(),
                config.getDatabase().getUsername(),
                config.getDatabase().getPassword());
        parametersTable = new SystemParametersTable(config.getDatabase().getTablePrefix(), connector);
        try {
            parametersTable.createTable();
        } catch (SQLException e) {
            log.error("An error occurred while initializing the table.", e);
            return;
        }

        if (config.getAuth().getAuthType().equals(MiConfig.AuthType.OAUTH)) {
            try {
                accessToken = parametersTable.getProperty("accessToken");
            } catch (SQLException e) {
                log.error("An error occurred while getting a property.", e);
            }

            if (accessToken == null) {
                log.info("Start oAuth.");
                OAuthManager authManager = new OAuthManager(config.getAuth().getInstanceUrl());
                String appSecret = authManager.getAppSecret();
                authManager.openAuthenticationPage(appSecret);
                String token = authManager.waitForTokenResponse();
                accessToken = authManager.getAccessToken(appSecret, token);
                if (config.getAuth().isSaveToken()) {
                    try {
                        parametersTable.setProperty("accessToken", accessToken);
                    } catch (SQLException e) {
                        log.error("An error occurred while saving a property.", e);
                    }
                }
            }
        } else {
            if (StringUtils.isNullOrEmpty(config.getAuth().getFixedToken())) {
                log.error("If \"authType\" is set to \"TOKEN\", please enter \"fixedToken\".");
                return;
            } else {
                accessToken = config.getAuth().getFixedToken();
            }
        }

        taskManager = new MiTaskManager(500);
        taskManager.setName("MiTaskManagerThread");
        taskManager.start();

        moduleManager = new ModuleManager("modules");
        moduleManager.loadAllModules();

        try {
            commandManager = new CommandManager(connector);
        } catch (SQLException e) {
            log.error("An error occurred while initializing the table.", e);
            return;
        }

        initCommand();

        ConsoleReader consoleReader = new ConsoleReader();
        consoleReader.setName("ConsoleReaderThread");
        consoleReader.setDaemon(true);
        consoleReader.start();

        moduleManager.enableAllModules();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down the system...");
            if (webSocketClient != null)
                webSocketClient.close();
            moduleManager.disableAllModules();
            taskManager.shutdown();
            connector.close();
            log.info("See you again!");
        }));
    }

    private void initCommand() {
        CommandExecutor helpCommand = new HelpCommand("help", "h");
        commandManager.registerCommand(helpCommand, null);
        commandManager.setIgnore(helpCommand);
        CommandExecutor stopCommand = new StopCommand("stop", "exit", "shutdown");
        commandManager.registerCommand(stopCommand, null);
        commandManager.setIgnore(stopCommand);
        commandManager.registerCommand(new ModuleCommand("modules", "module"), null);

        commandManager.registerCommand(new InviteCommand("invite"), null);
        commandManager.registerCommand(new PasswordResetCommand("reset"), null);
        commandManager.registerCommand(new SoftIceCommand("freeze", "softfreeze"), null);
        commandManager.registerCommand(new SilenceCommand("silence"), null);
    }

    @Override
    public MiConfig getMiConfig() {
        return config;
    }

    @Override
    public DatabaseConnector getConnector() {
        return connector;
    }

    @Override
    public MiTaskManager getTaskManager() {
        return taskManager;
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    @Override
    public WebSocketClient getWebSocketClient() {
        if (webSocketClient == null) {
            webSocketClient = new WebSocketClient();
            webSocketClient.connect();
        }
        return webSocketClient;
    }
}

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

package online.pandasoft.miadmin.core.command;

import lombok.extern.slf4j.Slf4j;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.database.DatabaseConnector;
import online.pandasoft.miadmin.core.database.tables.OperationLogTable;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;
import online.pandasoft.miadmin.core.module.MiModule;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Slf4j
public class CommandManager {
    private static final MiAdminLauncher launcher = Main.getLauncher();
    private final CommandRegistry commandRegistry = new CommandRegistry();
    private final OperationLogTable operationLogTable;

    public CommandManager(DatabaseConnector connector) throws SQLException {
        operationLogTable = new OperationLogTable(launcher.getMiConfig().getDatabase().getTablePrefix(), connector);
        operationLogTable.createTable();
    }

    public void registerCommand(CommandExecutor executor, MiModule module) {
        commandRegistry.registerCommand(executor, module);
    }

    public void removeCommand(String name, MiModule module) {
        commandRegistry.removeCommand(name, module);
    }

    public void removeCommand(CommandExecutor executor, MiModule module) {
        commandRegistry.removeCommand(executor, module);
    }

    public void removeCommands(MiModule module) {
        commandRegistry.removeCommands(module);
    }

    public List<CommandExecutor> getCommands() {
        return commandRegistry.getCommands();
    }

    public CommandExecutor getExecutor(String name) {
        return commandRegistry.getExecutor(name);
    }

    public void executeCommand(CommandContext context) {
        CommandExecutor command = context.getCommand();
        if (command == null)
            throw new IllegalArgumentException("\"CommandExecutor\" is null.");

        try {
            command.onInvoke(context);
            OperationLogTable.OperationLog operationLog =
                    new OperationLogTable.OperationLog(new Date(), context.getCustomInvokerName(), command.getClass().getName(), context.getCustomLoggerMessage());
            operationLogTable.saveOperationLog(operationLog);
        } catch (SQLException e) {
            log.error("An error occurred while recording the operation log.", e);
        } catch (Throwable e) {
            log.warn("An uncaught exception was raised while executing the command.", e);
        }
    }
}

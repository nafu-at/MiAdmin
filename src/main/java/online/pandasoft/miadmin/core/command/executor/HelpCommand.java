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

package online.pandasoft.miadmin.core.command.executor;

import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.command.CommandContext;
import online.pandasoft.miadmin.core.command.CommandExecutor;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;

import java.util.logging.Level;

public class HelpCommand extends CommandExecutor {
    private static final MiAdminLauncher launcher = Main.getLauncher();

    public HelpCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(CommandContext context) {
        if (context.getArgs().length == 0) {
            StringBuilder builder = new StringBuilder("These are common MiAdmin commands that are used in a variety of situations.:\n\n");
            for (CommandExecutor executor : launcher.getCommandManager().getCommands()) {
                builder.append(executor.getName() + ": " + executor.getDescription() + "\n");
            }
            context.printMessage(Level.INFO, builder.toString());
        } else {
            CommandExecutor executor = launcher.getCommandManager().getExecutor(context.getArgs()[0]);
            if (executor != null) {
                StringBuilder builder = new StringBuilder();
                builder.append(executor.getName() + ": " + executor.getDescription() + "\n");
                builder.append(executor.getDescription());
                context.printMessage(Level.INFO, builder.toString());
            } else {
                context.printMessage(Level.INFO, "No such command is registered.\n" +
                        "You can see the information about the command by executing \"help\".");
            }
        }
    }

    @Override
    public String getDescription() {
        return "Displays help for commands.";
    }

    @Override
    public String getHelp() {
        return getName() + " [options]\n----\n" +
                "<CommandName>: Displays detailed usage of the command.";
    }
}

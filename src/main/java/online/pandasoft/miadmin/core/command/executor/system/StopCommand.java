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

package online.pandasoft.miadmin.core.command.executor.system;

import online.pandasoft.miadmin.core.command.CommandCache;
import online.pandasoft.miadmin.core.command.CommandContext;
import online.pandasoft.miadmin.core.command.CommandExecutor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.logging.Level;

public class StopCommand extends CommandExecutor {

    public StopCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(CommandContext context) {
        if (context.getArgs().length == 0) {
            String pass = RandomStringUtils.randomAlphanumeric(6);
            CommandCache.registerCache(null, "exitcode", pass);
            context.printMessage(Level.INFO, "An execution key has been generated to prevent misoperation.\n" +
                    "Enter a execution key in the argument and run it again.: " + pass);
        } else {
            if (context.getArgs()[0].equals(CommandCache.getCache(null, "exitcode")))
                Runtime.getRuntime().exit(0);
            else
                context.printMessage(Level.INFO, "Incorrect execution key.");
        }
    }

    @Override
    public String getDescription() {
        return "Exit the system.";
    }

    @Override
    public String getHelp() {
        return getName() + " <ExecutionKey>\n----\n" +
                "<ExecutionKey>: Enter the execution key that you received to exit the system.\n";
    }
}

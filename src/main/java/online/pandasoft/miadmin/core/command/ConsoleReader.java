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
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;

import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class ConsoleReader extends Thread {
    private static final MiAdminLauncher launcher = Main.getLauncher();

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String raw = scanner.nextLine();
            String input = raw;

            // コマンドオプションを分割
            String[] args = input.split("\\p{javaSpaceChar}+");
            if (args.length == 0)
                continue;
            String commandTrigger = args[0];

            // コマンドクラスの取得
            CommandExecutor command = launcher.getCommandManager().getExecutor(commandTrigger.toLowerCase());
            if (command == null) {
                log.info("No such command is registered.\n" +
                        "You can see the information about the command by executing \"help\".");
            } else {
                try {
                    launcher.getCommandManager().executeCommand(new CommandContext(commandTrigger,
                            Arrays.copyOfRange(args, 1, args.length), command));
                } catch (Throwable e) {
                    log.warn("An uncaught exception was raised while executing the command.", e);
                }
            }
        }
    }
}

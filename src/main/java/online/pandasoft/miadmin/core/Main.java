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

package online.pandasoft.miadmin.core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import online.pandasoft.miadmin.core.launcher.Launcher;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;
import org.slf4j.LoggerFactory;

@Slf4j
public class Main {
    private static boolean debugMode;
    private static MiAdminLauncher launcher;

    public static void main(String[] args) {
        log.info("\n" +
                " __  __ _    _       _           _       \n" +
                "|  \\/  (_)  / \\   __| |_ __ ___ (_)_ __  \n" +
                "| |\\/| | | / _ \\ / _` | '_ ` _ \\| | '_ \\ \n" +
                "| |  | | |/ ___ \\ (_| | | | | | | | | | |\n" +
                "|_|  |_|_/_/   \\_\\__,_|_| |_| |_|_|_| |_|\n" +
                "                                         \n");
        log.info("Starting MiAdmin. The currently running version is {}.",
                Main.class.getPackage().getImplementationVersion());

        for (String prop : args) {
            switch (prop.toLowerCase()) {
                case "debug":
                    debugMode = true;
                    break;
            }
        }

        if (debugMode) {
            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            Logger jdaLogger = (Logger) LoggerFactory.getLogger("net.dv8tion");
            Logger cpLogger = (Logger) LoggerFactory.getLogger("com.zaxxer.hikari");
            root.setLevel(Level.DEBUG);
            jdaLogger.setLevel(Level.DEBUG);
            cpLogger.setLevel(Level.DEBUG);
        }

        launcher = new Launcher();
        launcher.launch();
    }

    public static MiAdminLauncher getLauncher() {
        return launcher;
    }
}

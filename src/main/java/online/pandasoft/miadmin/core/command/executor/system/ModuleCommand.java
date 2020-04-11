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

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.command.CommandContext;
import online.pandasoft.miadmin.core.command.CommandExecutor;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;
import online.pandasoft.miadmin.core.module.MiModule;
import online.pandasoft.miadmin.core.module.ModuleDescription;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class ModuleCommand extends CommandExecutor {
    private static final MiAdminLauncher launcher = Main.getLauncher();

    public ModuleCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(CommandContext context) {
        context.setCustomLoggerMessage("Operation: " + (context.getArgs().length == 0 ? "list" : context.getArgs()[0]) +
                " Args: " + (context.getArgs().length == 0 ? "null" :
                Arrays.copyOfRange(context.getArgs(), 1, context.getArgs().length).toString()));
        if (context.getArgs().length == 0) {
            List<MiModule> modules = launcher.getModuleManager().getModules();
            AsciiTable table = new AsciiTable();
            table.addRule();
            table.addRow("ModuleName", null, "Description", "Version").setTextAlignment(TextAlignment.CENTER);
            for (MiModule module : modules) {
                table.addLightRule();
                table.addRow(module.getDescription().getName(), null, module.getDescription().getDescription(), module.getDescription().getVersion());
            }
            context.printMessage(Level.INFO, "\n" + table.render());
        } else if (context.getArgs().length <= 1) {
            MiModule module = launcher.getModuleManager().getModule(context.getArgs()[0]);
            if (module == null) {
                context.printMessage(Level.WARNING, "The specified module is not registered.");
            } else {
                ModuleDescription description = module.getDescription();
                AsciiTable table = new AsciiTable();
                table.addRule();
                table.addRow("Name", null, null, description.getName());
                table.addRule();
                table.addRow("Description", null, null, description.getDescription());
                table.addRule();
                table.addRow("Version", null, null, description.getVersion());
                table.addRule();
                table.addRow("Authors", null, null, description.getAuthors());
                table.addRule();
                table.addRow("WebSite", null, null, description.getWebsite());
                context.printMessage(Level.INFO, "\n" + table.render());
            }
        } else switch (context.getArgs()[0].toLowerCase()) {
            case "load":
                if (launcher.getModuleManager().loadModule(new File(context.getArgs()[1])))
                    context.printMessage(Level.INFO, "The module has been successfully loaded.");
                else
                    context.printMessage(Level.WARNING, "Failed to load the module.");
                break;

            case "unload":
                switch (context.getArgs()[1].toLowerCase()) {
                    case "all":
                        launcher.getModuleManager().unloadAllModules();
                        break;

                    default:
                        try {
                            launcher.getModuleManager().unloadModule(context.getArgs()[1]);
                        } catch (IllegalArgumentException e) {
                            context.printMessage(Level.WARNING, "The specified module is not registered.");
                        }
                        break;
                }
                break;

            case "enable":
                switch (context.getArgs()[1].toLowerCase()) {
                    case "all":
                        launcher.getModuleManager().enableAllModules();
                        break;

                    default:
                        try {
                            launcher.getModuleManager().enableModule(context.getArgs()[1]);
                        } catch (IllegalArgumentException e) {
                            context.printMessage(Level.WARNING, "The specified module is not registered.");
                        }
                        break;
                }
                break;

            case "disable":
                switch (context.getArgs()[1].toLowerCase()) {
                    case "all":
                        launcher.getModuleManager().disableAllModules();
                        break;

                    default:
                        try {
                            launcher.getModuleManager().disableModule(context.getArgs()[1]);
                        } catch (IllegalArgumentException e) {
                            context.printMessage(Level.WARNING, "The specified module is not registered.");
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public String getDescription() {
        return "Module management.";
    }

    @Override
    public String getHelp() {
        return "If no argument is specified, a list of all loaded modules is displayed.\n\n" +
                getName() + " [options] <args>\n----\n" +
                "[<ModuleName>]: Displays detailed information about the specified module.\n" +
                "[load]: Load the specified file as a module.\n" +
                "[unload]: Unloads the specified module.\n" +
                "[enable]: Enables the specified module.\n" +
                "[enable] <all>: Enables all modules.\n" +
                "[disable]: Disables the specified module.\n" +
                "[disable] <all>: Disables all modules.\n";
    }
}

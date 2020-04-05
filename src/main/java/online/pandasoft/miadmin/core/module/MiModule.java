/*
 * Copyright 2019 くまねこそふと.
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

package online.pandasoft.miadmin.core.module;

import online.pandasoft.miadmin.api.MiAdmin;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.command.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public abstract class MiModule implements Module {
    private final MiAdmin launcher = Main.getLauncher();
    private final ClassLoader classLoader;
    private ModuleDescription description;
    private File dataFolder;
    private Logger logger;
    private boolean isEnabled;

    public MiModule() {
        classLoader = this.getClass().getClassLoader();
        if (!(classLoader instanceof ModuleClassLoader))
            throw new IllegalStateException("The module must be loaded with a \"ModuleClassLoader\".");
        ((ModuleClassLoader) classLoader).initialize(this);
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean isEnable() {
        return isEnabled;
    }

    protected boolean setEnable(boolean enabled) {
        if (isEnabled != enabled) {
            isEnabled = enabled;
            if (isEnabled) {
                try {
                    onEnable();
                    getModuleLogger().info("Module is now enabled.: {}", getDescription().getName());
                    return true;
                } catch (Throwable e) {
                    getModuleLogger().error("An uncaught exception has been raised with \"onEnable\".: {}\n", getDescription().getName(), e);
                }
            } else {
                try {
                    onDisable();
                    getModuleLogger().info("Module is now disabled.: {}", getDescription().getName());
                    return true;
                } catch (Throwable e) {
                    getModuleLogger().error("An uncaught exception has been raised with \"onDisable\".: {}\n", getDescription().getName(), e);
                }
            }
        }
        return false;
    }

    @Override
    public void registerCommand(CommandExecutor executor) {
        launcher.getCommandManager().registerCommand(executor, this);
    }

    @Override
    public void registerCommands(List<CommandExecutor> executors) {
        executors.forEach(this::registerCommand);
    }

    @Override
    public void removeCommand(CommandExecutor executor) {
        launcher.getCommandManager().removeCommand(executor, this);
    }

    @Override
    public void removeCommands() {
        launcher.getCommandManager().removeCommands(this);
    }

    @Override
    public ModuleDescription getDescription() {
        return description;
    }

    @Override
    public MiAdmin getMiAdmin() {
        return launcher;
    }

    @Override
    public InputStream getResources(String filename) {
        return classLoader.getResourceAsStream(filename);
    }

    @Override
    public File getDataFolder() {
        if (!dataFolder.exists())
            dataFolder.mkdirs();
        return dataFolder;
    }

    @Override
    public ModuleClassLoader getClassLoder() {
        return (ModuleClassLoader) classLoader;
    }

    @Override
    public Logger getModuleLogger() {
        return logger;
    }

    final void init(ModuleDescription description) {
        this.description = description;
        dataFolder = new File("modules/", description.getName());
        logger = LoggerFactory.getLogger(description.getName());
    }
}

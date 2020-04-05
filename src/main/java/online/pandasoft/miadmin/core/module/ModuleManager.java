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

package online.pandasoft.miadmin.core.module;

import lombok.extern.slf4j.Slf4j;
import online.pandasoft.miadmin.core.module.exception.InvalidModuleException;
import online.pandasoft.miadmin.core.module.exception.ModuleDuplicateException;
import online.pandasoft.miadmin.core.module.exception.UnknownDependencyException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ModuleManager {
    private final ModuleRegistry moduleRegistry;
    private final ModuleLoader moduleLoader;
    private List<File> files;

    public ModuleManager(String moduleDir) {
        this.moduleRegistry = new ModuleRegistry();
        moduleLoader = new ModuleLoader(moduleRegistry, moduleDir);
        files = moduleLoader.searchModules();
    }

    /**
     * Get a module.
     *
     * @param name Name of the module to get.
     * @return module
     */
    public MiModule getModule(String name) {
        return moduleRegistry.getModule(name);
    }

    /**
     * Load all the modules found.
     */
    public void loadAllModules() {
        List<File> waitingModule = new ArrayList<>();
        while (!files.isEmpty()) {
            Iterator<File> iterator = files.iterator();
            iterator:
            while (iterator.hasNext()) {
                File file = iterator.next();
                ModuleDescription description;
                try {
                    description = moduleLoader.loadModuleDescription(file);
                } catch (InvalidModuleException e) {
                    log.warn("Failed to load the module information.: {}", file.getName(), e);
                    iterator.remove();
                    continue iterator;
                }

                if (!CollectionUtils.isEmpty(description.getLoadBefore())) {
                    if (waitingModule.size() != IterableUtils.size(Collections.singleton(iterator))) {
                        for (String module : description.getLoadBefore()) {
                            if (moduleRegistry.getModule(module) == null) {
                                waitingModule.add(file);
                                continue iterator;
                            }
                            waitingModule.remove(file);
                        }
                    }
                }

                loadModule(file);
                iterator.remove();
            }
        }
    }

    /**
     * Load the module.
     *
     * @param file Jar file of the module.
     * @return Returns true if the module is successfully loaded.
     */
    public boolean loadModule(File file) {
        MiModule module = null;
        try {
            module = moduleLoader.loadModule(file);
        } catch (UnknownDependencyException e) {
            log.error("It couldn't be load because the dependency couldn't be resolved.", e);
        } catch (InvalidModuleException e) {
            log.error("The module could not be loaded due to an incorrect format.", e);
        }

        if (module == null)
            return false;

        try {
            moduleRegistry.registerModule(module);
        } catch (ModuleDuplicateException e) {
            log.warn("The same module is already loaded.: {}", module.getDescription().getName());
            return false;
        }

        try {
            module.onLoad();
            log.info("Module loaded.: {}", module.getDescription().getName());
        } catch (Throwable e) {
            log.warn("An uncaught exception has been raised in \"onLoad\".", e);
            unloadModule(module);
            return false;
        }
        return true;
    }

    /**
     * Enable all loaded modules.
     */
    public void enableAllModules() {
        for (MiModule module : moduleRegistry.getModules()) {
            enableModule(module);
        }
    }

    /**
     * Enables the module.
     *
     * @param name Module name to enable.
     * @return Returns true if the module was successfully enabled.
     */
    public boolean enableModule(String name) {
        MiModule module = moduleRegistry.getModule(name);
        return enableModule(module);
    }

    /**
     * Enables the module.
     *
     * @param module The module to enable.
     * @return Returns true if the module was successfully enabled.
     */
    public boolean enableModule(MiModule module) {
        return module.setEnable(true);
    }

    /**
     * Disable all loaded modules.
     */
    public void disableAllModules() {
        for (MiModule module : moduleRegistry.getModules()) {
            disableModule(module);
        }
    }

    /**
     * Disables the module.
     *
     * @param name Module name to disable.
     * @return Returns true if the module has been successfully disabled.
     */
    public boolean disableModule(String name) {
        MiModule module = moduleRegistry.getModule(name);
        return disableModule(module);
    }

    /**
     * Disables the module.
     *
     * @param module Modules to disable.
     * @return Returns true if the module has been successfully disabled.
     */
    public boolean disableModule(MiModule module) {
        return module.setEnable(false);

    }

    /**
     * Unload all loaded modules.
     */
    public void unloadAllModules() {
        for (MiModule module : moduleRegistry.getModules()) {
            unloadModule(module);
        }
    }

    /**
     * Unloads the module.
     *
     * @param name Module name to unload.
     */
    public void unloadModule(String name) {
        MiModule module = moduleRegistry.getModule(name);
        unloadModule(module);
    }

    /**
     * Unloads the module.
     *
     * @param module Modules to unload
     */
    public void unloadModule(MiModule module) {
        if (module.isEnable()) {
            log.warn("Unable to unload because the module is in a valid state.: {}", module.getDescription().getName());
        } else {
            try {
                moduleRegistry.deleteModule(module);
                ModuleClassLoader classLoader = module.getClassLoder();
                classLoader.close();
                log.info("Module unloaded.: {}", module.getDescription().getName());
            } catch (IOException e) {
                log.error("An error occurred during the unloading of the module.", e);
            }
        }
    }
}

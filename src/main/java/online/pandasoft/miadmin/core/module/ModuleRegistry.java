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

import online.pandasoft.miadmin.core.module.exception.ModuleDuplicateException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModuleRegistry {
    private final Map<String, MiModule> modules = new LinkedHashMap<>();

    /**
     * Register a module.
     *
     * @param module Modules to register.
     * @throws ModuleDuplicateException Thrown if the module you are trying to register already exists.
     */
    public synchronized void registerModule(MiModule module) throws ModuleDuplicateException {
        if (modules.containsKey(module.getDescription().getName()))
            throw new ModuleDuplicateException(module.getDescription().getName());
        else
            modules.put(module.getDescription().getName(), module);
    }

    /**
     * Deletes a registered module.
     *
     * @param module Module to be removed.
     */
    public synchronized void deleteModule(MiModule module) {
        modules.remove(module);
    }

    /**
     * Returns a list of all registered modules.
     *
     * @return List of all registered modules
     */
    public synchronized List<MiModule> getModules() {
        return new ArrayList<>(modules.values());
    }

    /**
     * Get the matching module from the name.
     *
     * @param name Name of the module to get.
     * @return The appropriate module
     */
    public synchronized MiModule getModule(String name) {
        return modules.get(name);
    }
}

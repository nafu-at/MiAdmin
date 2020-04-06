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

import java.util.logging.Level;

@Slf4j
public class CommandContext {
    private final String trigger;
    private final String[] args;
    private final CommandExecutor command;

    private String customInvokerName = "SystemConsole";
    private String customLoggerMessage;

    public CommandContext(String trigger, String[] args, CommandExecutor command) {
        this.trigger = trigger;
        this.args = args;
        this.command = command;
    }

    /**
     * @return actual invocation word
     */
    public String getTrigger() {
        return trigger;
    }

    public String[] getArgs() {
        return args;
    }

    public CommandExecutor getCommand() {
        return command;
    }

    public void printMessage(Level level, String message, Object... args) {
        if (Level.INFO.equals(level)) {
            log.info(message, args);
        } else if (Level.WARNING.equals(level)) {
            log.warn(message, args);
        } else if (Level.SEVERE.equals(level)) {
            log.error(message, args);
        } else if (Level.FINE.equals(level) || Level.FINER.equals(level) || Level.FINEST.equals(level)) {
            log.debug(message, args);
        }
    }

    public void setCustomInvokerName(String invokerName) {
        customInvokerName = invokerName;
    }

    public void setCustomLoggerMessage(String customLoggerMessage) {
        this.customLoggerMessage = customLoggerMessage;
    }

    protected String getCustomInvokerName() {
        return customInvokerName;
    }

    protected String getCustomLoggerMessage() {
        return customLoggerMessage;
    }
}

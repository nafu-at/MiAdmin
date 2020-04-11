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

import online.pandasoft.miadmin.api.elements.user.User;
import online.pandasoft.miadmin.api.http.parameters.AdminSilenceSuspendParameter;
import online.pandasoft.miadmin.api.http.parameters.RequestParameters;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.command.CommandContext;
import online.pandasoft.miadmin.core.command.CommandExecutor;
import online.pandasoft.miadmin.core.http.HttpRequestResult;
import online.pandasoft.miadmin.core.http.HttpRequestTask;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;
import online.pandasoft.miadmin.core.task.MiTask;
import online.pandasoft.miadmin.core.task.MiTaskResult;
import online.pandasoft.miadmin.core.task.MiTaskResultHandler;
import online.pandasoft.miadmin.core.util.UserSearcher;

import java.util.Arrays;
import java.util.logging.Level;

public class SoftIceCommand extends CommandExecutor {
    private static final MiAdminLauncher launcher = Main.getLauncher();

    public SoftIceCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(CommandContext context) {
        context.setCustomLoggerMessage(Arrays.toString(context.getArgs()));
        if (context.getArgs().length >= 1) {
            for (String username : context.getArgs()) {
                User user = UserSearcher.searchUser(username);
                if (user == null) {
                    context.printMessage(Level.INFO, "The specified user does not exist.");
                } else if (user.isSuspended()) {
                    context.printMessage(Level.INFO, "{} is already frozen.", user.getUsername());
                } else {
                    AdminSilenceSuspendParameter suspendParameter = (AdminSilenceSuspendParameter) RequestParameters.newInstance(RequestParameters.ADMIN_SUSPEND_USER);
                    suspendParameter.setUserId(user.getID());
                    HttpRequestTask requestTask = new HttpRequestTask(suspendParameter, new MiTaskResultHandler() {
                        @Override
                        public void onTaskSuccess(MiTask executedTask, MiTaskResult result) {
                            HttpRequestResult requestResult = (HttpRequestResult) result;
                            if (requestResult.getCode() == suspendParameter.getSuccessCode()) {
                                context.printMessage(Level.INFO, "{} has been frozen.", user.getUsername());
                            }
                        }

                        @Override
                        public void onTaskFailed(MiTask executedTask, Throwable throwable) {
                            context.printMessage(Level.WARNING, "Request failed.", throwable);
                        }
                    });
                    launcher.getTaskManager().reservationTask(requestTask);
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Freeze the user.";
    }

    @Override
    public String getHelp() {
        return getName() + " <username>\n----\n" +
                "<username>: Freeze the specified user.\n";
    }
}

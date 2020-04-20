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
import online.pandasoft.miadmin.api.http.parameters.RequestParameters;
import online.pandasoft.miadmin.api.http.responses.AdminResetPasswordResponse;
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

import java.util.logging.Level;

public class PasswordResetCommand extends CommandExecutor {
    private static final MiAdminLauncher launcher = Main.getLauncher();

    public PasswordResetCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(CommandContext context) {
        context.setCustomLoggerMessage(context.getArgs()[0]);
        if (context.getArgs().length >= 1) {
            User user = UserSearcher.searchUser(context.getArgs()[0]);
            if (user == null) {
                context.printMessage(Level.INFO, "The specified user does not exist.");
            } else {
                HttpRequestTask requestTask = new HttpRequestTask(RequestParameters.newInstance(RequestParameters.ADMIN_RESET_PASSWORD), new MiTaskResultHandler() {
                    @Override
                    public void onTaskSuccess(MiTask executedTask, MiTaskResult result) {
                        if (result instanceof HttpRequestResult) {
                            HttpRequestResult requestResult = (HttpRequestResult) result;
                            if (requestResult.getCode() == 200) {
                                AdminResetPasswordResponse response = (AdminResetPasswordResponse) requestResult.getRequestResponse();
                                context.printMessage(Level.INFO, "New password has been issued.: " + response.getPassword());
                            }
                        }
                    }

                    @Override
                    public void onTaskFailed(MiTask executedTask, Throwable throwable) {
                        context.printMessage(Level.WARNING, "An error has occurred while issuing a new password.", throwable);
                    }
                });
                launcher.getTaskManager().reservationTask(requestTask);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Reset the user's password.";
    }

    @Override
    public String getHelp() {
        return null;
    }
}

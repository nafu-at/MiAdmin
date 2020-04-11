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

import online.pandasoft.miadmin.api.http.parameters.RequestParameters;
import online.pandasoft.miadmin.api.http.responses.AdminInviteResponse;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.command.CommandContext;
import online.pandasoft.miadmin.core.command.CommandExecutor;
import online.pandasoft.miadmin.core.http.HttpRequestResult;
import online.pandasoft.miadmin.core.http.HttpRequestTask;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;
import online.pandasoft.miadmin.core.task.MiTask;
import online.pandasoft.miadmin.core.task.MiTaskResult;
import online.pandasoft.miadmin.core.task.MiTaskResultHandler;

import java.util.logging.Level;

public class InviteCommand extends CommandExecutor {
    private static final MiAdminLauncher launcher = Main.getLauncher();

    public InviteCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void onInvoke(CommandContext context) {
        HttpRequestTask requestTask = new HttpRequestTask(RequestParameters.newInstance(RequestParameters.ADMIN_INVITE), new MiTaskResultHandler() {
            @Override
            public void onTaskSuccess(MiTask executedTask, MiTaskResult result) {
                if (result instanceof HttpRequestResult) {
                    HttpRequestResult requestResult = (HttpRequestResult) result;
                    if (requestResult.getCode() == 200) {
                        AdminInviteResponse response = (AdminInviteResponse) requestResult.getRequestResponse();
                        context.printMessage(Level.INFO, "An invitation code was issued.: " + response.getCode());
                    }
                }
            }

            @Override
            public void onTaskFailed(MiTask executedTask, Throwable throwable) {
                context.printMessage(Level.WARNING, "An error occurred while issuing the invitation code.", throwable);
            }
        });
        launcher.getTaskManager().reservationTask(requestTask);
    }

    @Override
    public String getDescription() {
        return "An invitation code will be issued.";
    }

    @Override
    public String getHelp() {
        return null;
    }
}

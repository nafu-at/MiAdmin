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

package online.pandasoft.miadmin.core.util;

import online.pandasoft.miadmin.api.elements.user.User;
import online.pandasoft.miadmin.api.http.parameters.RequestParameters;
import online.pandasoft.miadmin.api.http.parameters.UsersShowParameter;
import online.pandasoft.miadmin.core.Main;
import online.pandasoft.miadmin.core.http.HttpRequestResult;
import online.pandasoft.miadmin.core.http.HttpRequestTask;
import online.pandasoft.miadmin.core.http.RequestParameter;
import online.pandasoft.miadmin.core.launcher.MiAdminLauncher;
import online.pandasoft.miadmin.core.task.MiTask;
import online.pandasoft.miadmin.core.task.MiTaskResult;
import online.pandasoft.miadmin.core.task.MiTaskResultHandler;

import static java.lang.Thread.sleep;

public final class UserSearcher {
    private static final MiAdminLauncher launcher = Main.getLauncher();

    public static User searchUser(String keyword) {
        // Search priority.
        // Username -> userId
        UsersShowParameter parameter = (UsersShowParameter) RequestParameters.newInstance(RequestParameters.USERS_SHOW);
        String[] username = (keyword.startsWith("@") ? keyword.substring(1) : keyword).split("@");
        parameter.setUsername(username[0]);
        if (username.length == 2)
            parameter.setHost(username[1]);
        User user = request(parameter);
        if (user.getID() == null)
            return getUser(keyword);
        return user;
    }

    public static User getUser(String id) {
        UsersShowParameter parameter = (UsersShowParameter) RequestParameters.newInstance(RequestParameters.USERS_SHOW);
        parameter.setUserID(id);
        User user = request(parameter);
        return user.getID() == null ? null : user;
    }

    private static User request(RequestParameter parameter) {
        try {
            final User[] var = new User[1];
            HttpRequestTask task = new HttpRequestTask(parameter, new MiTaskResultHandler() {
                @Override
                public void onTaskSuccess(MiTask executedTask, MiTaskResult result) {
                    if (result instanceof HttpRequestResult) {
                        HttpRequestResult requestResult = (HttpRequestResult) result;
                        if (requestResult.getCode() == parameter.getSuccessCode()) {
                            var[0] = (User) requestResult.getRequestResponse();
                        } else {
                            var[0] = new User();
                        }
                    }
                }

                @Override
                public void onTaskFailed(MiTask executedTask, Throwable throwable) {
                    var[0] = new User();
                }
            });
            launcher.getTaskManager().reservationTask(task);

            while (var[0] == null)
                sleep(20);

            return var[0];
        } catch (InterruptedException e) {
            // Nothing...
        }
        return null;
    }
}

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

package online.pandasoft.miadmin.core.task;

/**
 * The handler receives the results of the execution of the task.
 */
public interface MiTaskResultHandler {

    /**
     * It will be called if the reserved task is successfully executed.
     *
     * @param executedTask Executed task
     * @param result       Execution result
     */
    void onTaskSuccess(MiTask executedTask, MiTaskResult result);

    /**
     * It will be called if the reserved task is not completed successfully.
     *
     * @param executedTask Executed task
     * @param throwable    Exceptions that have occurred
     */
    void onTaskFailed(MiTask executedTask, Throwable throwable);
}

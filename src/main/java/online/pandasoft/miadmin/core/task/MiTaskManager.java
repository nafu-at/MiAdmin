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

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.*;

@Slf4j
public class MiTaskManager extends Thread {
    private final int executeInterval;
    private final Queue<MiTask> waitingQueue = new LinkedBlockingDeque();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private boolean isShutdown;

    public MiTaskManager(int executeInterval) {
        this.executeInterval = executeInterval;
    }

    /**
     * Reserve a task.
     *
     * @param task Task to be reserved
     */
    public void reservationTask(MiTask task) {
        waitingQueue.offer(task);
    }

    /**
     * Returns the number of tasks that are currently waiting to be executed.
     *
     * @return Number of tasks waiting
     */
    public int waitingTasks() {
        return waitingQueue.size();
    }

    public void shutdown() {
        isShutdown = true;
        waitingQueue.clear();
        executor.shutdownNow();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.debug("An interruption occurred while waiting for the end.");
        }
    }

    @Override
    public void run() {
        try {
            while (!isShutdown) {
                MiTask task = waitingQueue.poll();
                if (task != null) {
                    try {
                        log.debug("Execute task: {}", task.toString());
                        MiTaskResult result = (MiTaskResult) executor.submit(task).get();
                        task.getHandler().onTaskSuccess(task, result);
                    } catch (ExecutionException e) {
                        task.getHandler().onTaskFailed(task, e);
                    } catch (Throwable e) {
                        log.error("Uncaught exceptions in handlers", e);
                    }
                }
                sleep(executeInterval);
            }
        } catch (InterruptedException e) {
            log.debug("The thread has been manually resumed from a standby state.");
            run();
        }
    }
}
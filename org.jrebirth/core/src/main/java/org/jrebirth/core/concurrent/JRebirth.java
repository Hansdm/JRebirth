/**
 * Copyright JRebirth.org © 2011-2012 
 * Contact : sebastien.bordes@jrebirth.org
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
package org.jrebirth.core.concurrent;

import javafx.application.Platform;

/**
 * The class <strong>JRebirth</strong>.
 * 
 * @author Sébastien Bordes
 * 
 */
public final class JRebirth {

    /**
     * Private Constructor.
     */
    private JRebirth() {
        super();
    }

    /**
     * Run the task into the appropriated thread.
     * 
     * @param runInto the targeted thread
     * @param runnable the task to run
     */
    public static void run(final RunIntoType runInto, final Runnable runnable) {
        switch (runInto) {
            case JAT:
                runIntoJAT(runnable);
                break;
            case JIT:
                runIntoJIT(runnable);
                break;
            case POOL:
                runIntoThreadPool(runnable);
                break;
            default:
        }
    }

    /**
     * Run the task into the Java Application Thread.
     * 
     * @param runnable the task to run
     */
    public static void runIntoJAT(final Runnable runnable) {
        Platform.runLater(runnable);

    }

    /**
     * Run into the JRebirth Internal Thread.
     * 
     * @param runnable the task to run
     */
    public static void runIntoJIT(final Runnable runnable) {
        JRebirthThread.runLater(runnable);

    }

    /**
     * Run into the internal thread pool.
     * 
     * @param runnable the task to run
     */
    public static void runIntoThreadPool(final Runnable runnable) {
        // TODO add the runnable into the thread pool
        // Manage different pools (1 and 4)
        JRebirthThread.runLater(runnable); // FIXME
    }
}

/**
 * Get more info at : www.jrebirth.org .
 * Copyright JRebirth.org © 2011-2013
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
package org.jrebirth.af.core.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

import org.jrebirth.af.api.command.Command;
import org.jrebirth.af.api.concurrent.JRebirthRunnable;
import org.jrebirth.af.api.concurrent.Priority;
import org.jrebirth.af.api.concurrent.RunnablePriority;
import org.jrebirth.af.api.exception.CoreException;
import org.jrebirth.af.api.log.JRLogger;
import org.jrebirth.af.api.service.Service;
import org.jrebirth.af.api.service.ServiceTask;
import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.api.wave.Wave.Status;
import org.jrebirth.af.api.wave.WaveGroup;
import org.jrebirth.af.api.wave.contract.WaveType;
import org.jrebirth.af.core.log.JRLoggerFactory;
import org.jrebirth.af.core.wave.Builders;
import org.jrebirth.af.core.wave.WaveItemBase;

/**
 * The class <strong>ServiceTask</strong>.
 *
 * @author Sébastien Bordes
 *
 * @param <T> the current Service Task type
 */
public final class ServiceTaskBase<T> extends Task<T> implements JRebirthRunnable, ServiceTask<T>, ServiceMessages {

    /** The class logger. */
    private static final JRLogger LOGGER = JRLoggerFactory.getLogger(ServiceTaskBase.class);

    /**
     * The <code>parameterValues</code>.
     */
    private final Object[] parameterValues;

    /**
     * The <code>method</code>.
     */
    private final Method method;

    /**
     * The <code>localService</code>.
     */
    private final Service service;

    /**
     * The <code>sourceWave</code>.
     */
    private final Wave wave;

    /**
     * The workdone copied property stored locally to allow access outside JAT.<br/>
     * It implies to synchronize each access/modification
     */
    private double localWorkDone;

    /** The runnable priority. */
    private final RunnablePriority priority;

    /** The creation timestamp in milliseconds. */
    private final Instant creationTime;

    /**
     * Default Constructor only visible by service package.
     *
     * @param parameterValues the list of function parameter
     * @param method the method to call
     * @param service the service object
     * @param wave the wave to process
     */
    ServiceTaskBase(final Service service, final Method method, final Object[] parameterValues, final Wave wave) {
        super();

        this.creationTime = Instant.now();

        this.service = service;
        this.method = method;
        this.parameterValues = parameterValues.clone();
        this.wave = wave;

        final Priority priorityA = method.getAnnotation(Priority.class);
        this.priority = priorityA == null ? RunnablePriority.Low : priorityA.value();

    }

    /**
     * Return the full service handler name.
     *
     * ServiceName + method + ( parameters types )
     *
     * @return the full service handler name
     */
    @Override
    public String getServiceHandlerName() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.service.getClass().getSimpleName()).append(".");
        sb.append(this.method.getName()).append("(");
        for (final Class<?> parameterType : this.method.getParameterTypes()) {
            sb.append(parameterType.getSimpleName()).append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @throws CoreException if return WaveType has bad API
     */
    @SuppressWarnings("unchecked")
    @Override
    protected T call() throws CoreException {
        T res = null;
        try {

            // Build parameter list of the searched method
            final List<Object> params = new ArrayList<>();
            for (final Object o : this.parameterValues) {
                params.add(o);
            }
            // Add the current wave to process
            params.add(this.wave);

            // Call this method with right parameters
            res = (T) this.method.invoke(this.service, params.toArray());

            if (Void.TYPE.equals(this.method.getReturnType())) {
                // No return wave required because the service method will return nothing (VOID)
                LOGGER.log(NO_RETURN_WAVE_CONSUMED, this.service.getClass().getSimpleName(), this.wave.toString());
                this.wave.status(Status.Consumed);

                // Otherwise prepare the return wave
            } else {

                // Send the result into a wave
                sendReturnWave(res);

            }

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.log(SERVICE_TASK_ERROR, e, getServiceHandlerName());
            this.wave.status(Status.Failed);
        }
        return res;
    }

    /**
     * Send a wave that will carry the service result.
     *
     * 2 Kinds of wave can be sent according to service configuration
     *
     * @param res the service result
     *
     * @throws CoreException if the wave generation has failed
     */
    @SuppressWarnings("unchecked")
    private void sendReturnWave(final T res) throws CoreException {

        Wave returnWave = null;

        // Try to retrieve the return Wave type, could be null
        final WaveType responseWaveType = this.wave.waveType().returnWaveType();

        final Class<? extends Command> responseCommandClass = this.wave.waveType().returnCommandClass();

        if (responseWaveType != null) {

            // No service result type defined into a WaveItem
            if (responseWaveType.items().isEmpty()) {
                LOGGER.log(NO_RETURNED_WAVE_ITEM);
                throw new CoreException(NO_RETURNED_WAVE_ITEM);
            }

            // Get the first (and unique) WaveItem used to define the service result type
            final WaveItemBase<T> resultWaveItem = (WaveItemBase<T>) responseWaveType.items().get(0);

            // Try to retrieve the command class, could be null
            // final Class<? extends Command> responseCommandClass = this.service.getReturnCommand(this.wave.waveType());

            if (responseCommandClass != null) {

                // If a Command Class is provided, call it with the right WaveItem to get the real result type
                returnWave = Builders.wave()
                                     .waveGroup(WaveGroup.CALL_COMMAND)
                                     .fromClass(this.service.getClass())
                                     .componentClass(responseCommandClass)
                                     .addDatas(Builders.waveData(resultWaveItem, res));
            } else {

                // Otherwise send a generic wave that can be handled by any component
                returnWave = Builders.wave()
                                     .waveType(responseWaveType)
                                     .fromClass(this.service.getClass())
                                     .addDatas(Builders.waveData(resultWaveItem, res));
            }

            returnWave.relatedWave(this.wave);
            returnWave.addWaveListener(new ServiceTaskReturnWaveListener());

            // Send the return wave to interested components
            this.service.sendWave(returnWave);

        } else {
            // No service return wave Type defined
            LOGGER.log(NO_RETURNED_WAVE_TYPE_DEFINED, this.wave.waveType());
            throw new CoreException(NO_RETURNED_WAVE_ITEM, this.wave.waveType());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProgress(final long workDone, final long totalWork) {
        synchronized (this) {
            this.localWorkDone = workDone;
        }
        super.updateProgress(workDone, totalWork);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProgress(final double workDone, final double totalWork) {
        synchronized (this) {
            this.localWorkDone = workDone;
        }
        super.updateProgress(workDone, totalWork);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMessage(final String message) { // NOSONAR Override method visibility
        super.updateMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTitle(final String title) { // NOSONAR Override method visibility
        super.updateTitle(title);
    }

    /**
     * The task has been terminated because the source wave was consumed or has failed.
     *
     * Remove the task from the service pending list
     */
    @Override
    public void taskAchieved() {
        // We can now remove the pending task (even if the return wave isn't processed TO CHECK)
        this.service.removePendingTask(this.wave.getWUID());
    }

    /**
     * Check if the task has enough progressed according to the given threshold.
     *
     * This method can be called outside the JAT, it's useful to filter useless call to JAT
     *
     * @param newWorkDone the amount of work done
     * @param totalWork the total amount of work
     * @param amountThreshold the minimum threshold amount to return true; range is [0.0 - 100.0] (typically 1.0 for 1%)
     *
     * @return true if the threshold is reached
     */
    @Override
    public boolean checkProgressRatio(final double newWorkDone, final double totalWork, final double amountThreshold) {

        double currentRatio;
        synchronized (this) {
            // Compute the actual progression
            currentRatio = this.localWorkDone >= 0 ? 100 * this.localWorkDone / totalWork : 0.0;
        }

        // Compute the future progression
        final double newRatio = 100 * newWorkDone / totalWork;

        // return true if the task has progressed of at least block increment value
        return newRatio - currentRatio > amountThreshold;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RunnablePriority getPriority() {
        return this.priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant getCreationTime() {
        return this.creationTime;
    }

}

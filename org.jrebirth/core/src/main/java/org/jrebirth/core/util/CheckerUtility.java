package org.jrebirth.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jrebirth.core.exception.CoreRuntimeException;
import org.jrebirth.core.facade.WaveReady;
import org.jrebirth.core.log.JRLogger;
import org.jrebirth.core.log.JRLoggerFactory;
import org.jrebirth.core.resource.provided.JRebirthParameters;
import org.jrebirth.core.wave.Wave;
import org.jrebirth.core.wave.WaveItem;
import org.jrebirth.core.wave.WaveType;
import org.jrebirth.core.wave.WaveTypeBase;

/**
 * The class <strong>CheckerUtility</strong>.
 * 
 * Some Useful method utilities to check if API is respected.
 * 
 * @author Sébastien Bordes
 */
public final class CheckerUtility implements UtilMessages {

    /** The class logger. */
    private static final JRLogger LOGGER = JRLoggerFactory.getLogger(CheckerUtility.class);

    /**
     * Private Constructor.
     */
    private CheckerUtility() {
        // Nothing to do
    }

    /**
     * Check if wave Type contract is respected for the the given {@link WaveReady} class.
     * 
     * Throws a Runtime exception is Wave Contract is broken.
     * 
     * @param waveReadyClass the {@link WaveReady} class to check
     * @param waveTypes the contract to respect (could be several WaveType)
     */
    public static void checkWaveTypeContract(final Class<? extends WaveReady> waveReadyClass, final WaveType... waveTypes) {

        // Perform the check only if Developer Mode is activated
        if (JRebirthParameters.DEVELOPER_MODE.get()) {

            for (final WaveType waveType : waveTypes) {

                final String methodName = ClassUtility.underscoreToCamelCase(waveType.toString());
                final List<Method> methods = ClassUtility.retrieveMethodList(waveReadyClass, waveType.toString());

                if (methods.size() < 1) {
                    LOGGER.log(BROKEN_API_NO_METHOD, waveReadyClass.getSimpleName(), methodName);
                    LOGGER.log(WAVE_HANDLER_METHOD_REQUIRED, waveReadyClass.getSimpleName(), methodName, ((WaveTypeBase) waveType).getItems());
                }

                // Check parameter only for a WaveTypeBase
                if (waveType instanceof WaveTypeBase) {

                    int methodParameters = 0;
                    boolean hasCompliantMethod = false;

                    final List<WaveItem<?>> wParams = ((WaveTypeBase) waveType).getWaveItemList();

                    for (int j = 0; j < methods.size() && !hasCompliantMethod; j++) {
                        hasCompliantMethod = checkMethodSignature(methods.get(j), wParams);
                        if (!hasCompliantMethod) {
                            methodParameters = methods.get(j).getParameterTypes().length - 1; // Remove the wave parameters
                        }
                    }
                    if (!hasCompliantMethod) {
                        LOGGER.log(BROKEN_API_WRONG_PARAMETERS, waveReadyClass.getSimpleName(), methodName,
                                ((WaveTypeBase) waveType).getWaveItemList().size(), methodParameters);

                        LOGGER.log(WAVE_HANDLER_METHOD_REQUIRED, waveReadyClass.getSimpleName(),
                                methodName, ((WaveTypeBase) waveType).getItems());

                        throw new CoreRuntimeException(BROKEN_API_WRONG_PARAMETERS.getText(waveReadyClass.getSimpleName(),
                                methodName,
                                ((WaveTypeBase) waveType).getWaveItemList().size(),
                                methodParameters));
                    }
                }
            }
        }
    }

    /**
     * Compare method parameters with wave parameters.
     * 
     * @param method the method to check
     * @param wParams the wave parameters taht define the contract
     * 
     * @return true if the method has the right signature
     */
    private static boolean checkMethodSignature(final Method method, final List<WaveItem<?>> wParams) {
        boolean isCompliant = false;

        final Type[] mParams = method.getGenericParameterTypes();

        if (wParams.isEmpty() && Wave.class.isAssignableFrom(ClassUtility.getClassFromType(mParams[0]))) {
            isCompliant = true;
        } else if (mParams.length - 1 == wParams.size()) {

            // Flag used to skip a method not compliant
            boolean skipMethod = false;
            // Check each parameter
            for (int i = 0; !skipMethod && i < mParams.length - 1 && !isCompliant; i++) {
                if (!ClassUtility.getClassFromType(mParams[i]).isAssignableFrom(ClassUtility.getClassFromType(wParams.get(i).getItemType()))) {
                    // This method has not the right parameters
                    skipMethod = true;
                }
                if (i == mParams.length - 2
                        && Wave.class.isAssignableFrom(ClassUtility.getClassFromType(mParams[i + 1]))) {
                    // This method is compliant with wave type
                    isCompliant = true;
                }
            }
        }
        return isCompliant;
    }

    /**
     * Check that wave has all data required by the WaveType contract (if any).
     * 
     * Data can be stored into WaveData wrapper or WaveBean
     * 
     * @param wave the wave to check
     * 
     */
    public static void checkWave(final Wave wave) {
        if (JRebirthParameters.DEVELOPER_MODE.get()) {
            if (wave.getWaveType() != null && wave.getWaveType() instanceof WaveTypeBase) {

                if (((WaveTypeBase) wave.getWaveType()).getWaveItemList().size() > wave.getWaveItems().size()) {
                    // not enough Wave Data;
                }

                // List missing wave items not held by WaveData wrapper
                final List<WaveItem<?>> missingWaveItems = new ArrayList<>();
                for (final WaveItem<?> item : ((WaveTypeBase) wave.getWaveType()).getWaveItemList()) {

                    if (!wave.contains(item)) {
                        missingWaveItems.add(item);
                    }

                }

                if (wave.getWaveBean() != null) {
                    // Check that WaveItem not present into WaveData wrapper are available into WaveBean
                    for (final WaveItem<?> missing : missingWaveItems) {
                        if (missing.getName() != null && !missing.getName().isEmpty()) {
                            for (final Field wbField : ClassUtility.retrievePropertyList(wave.getWaveBean().getClass())) {

                            }
                        }
                    }
                }

                if (missingWaveItems.size() > 0) {
                    LOGGER.log(BROKEN_WAVE_SENT, wave.toString());
                    final StringBuilder sb = new StringBuilder();
                    for (final WaveItem<?> missing : missingWaveItems) {
                        sb.append(missing.toString());
                    }
                    LOGGER.log(BROKEN_WAVE_BAD_ITEM_LIST, sb.toString());

                    throw new CoreRuntimeException(BROKEN_WAVE_BAD_ITEM_LIST.getText(sb.toString()));
                }
            }
        }
    }
}

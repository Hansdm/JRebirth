package org.jrebirth.af.core.log;

import org.jrebirth.af.core.exception.CoreRuntimeException;
import org.jrebirth.af.core.resource.i18n.MessageItem;
import org.jrebirth.af.core.resource.provided.JRebirthParameters;

/**
 * The class <strong>AbstractLogAdapter</strong> shares common code used by {@link LogbackAdapter} and {@link Slf4jAdapter}.
 * 
 * @author Sébastien Bordes
 */
public abstract class AbstractLogAdapter implements JRLogger {

    /**
     * Throw a Runtime Exception.
     * When an exception is logged
     * and when an error is logged and we are running in Developer Mode
     * 
     * @param messageItem the message to display for the exception thrown
     * @param t the throwable source (could be null)
     * @param parameters the message parameters
     */
    protected void throwError(final MessageItem messageItem, final Throwable t, final Object... parameters) {
        if ( messageItem.getLevel() == JRLevel.Exception || 
             messageItem.getLevel() == JRLevel.Error && JRebirthParameters.DEVELOPER_MODE.get() ) {
            throw new CoreRuntimeException(messageItem, t, parameters);
        }
    }

}
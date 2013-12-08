package org.jrebirth.core.log;

import org.jrebirth.core.exception.CoreRuntimeException;
import org.jrebirth.core.resource.i18n.MessageItem;
import org.jrebirth.core.resource.provided.JRebirthParameters;

/**
 * The class <strong>AbstractAdapter</strong> shares common code used by {@link LogbackAdapter} and {@link Slf4jAdapter}.
 * 
 * @author Sébastien Bordes
 */
public class AbstractAdapter {

    /**
     * If an error is logged when running in Developer Mode, Throw a Runtime Exception.
     * 
     * @param messageItemthe message to display for the exception thrown
     * @param t the throwable source (could be null)
     * @param parameters the message parameters
     */
    protected void throwError(final MessageItem messageItem, final Throwable t, final Object... parameters) {
        if (JRebirthParameters.DEVELOPER_MODE.get()) {
            throw new CoreRuntimeException(messageItem, t, parameters);
        }
    }

}
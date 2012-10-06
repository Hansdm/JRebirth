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
package org.jrebirth.core.exception.handler;

import org.jrebirth.core.facade.GlobalFacade;

/**
 * The class <strong>DefaultUncaughtExceptionHandler</strong>.
 * 
 * @author Sébastien Bordes
 */
public class DefaultUncaughtExceptionHandler extends AbstractJrbUncaughtExceptionHandler {

    /**
     * Instantiates a new Default uncaught exception handler.
     * 
     * @param globalFacade the global facade
     */
    public DefaultUncaughtExceptionHandler(final GlobalFacade globalFacade) {
        super(globalFacade, UncaughtExceptionHandlerType.DefaultHandler);
    }

}

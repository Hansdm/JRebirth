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
package org.jrebirth.core.facade;

import org.jrebirth.core.service.Service;

/**
 * 
 * The interface <strong>ServiceReady</strong>.
 * 
 * @author Sébastien Bordes
 */
public interface ServiceReady {

    /**
     * Return the service singleton or part of multiton.
     * 
     * @param clazz the service class to find
     * @param keyPart the unique key (in option)
     * 
     * @param <S> a sub class of service
     * 
     * @return a service instance
     */
    <S extends Service> S getService(final Class<S> clazz, final Object... keyPart);

}

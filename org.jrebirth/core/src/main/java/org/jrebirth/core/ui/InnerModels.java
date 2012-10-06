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
package org.jrebirth.core.ui;

import org.jrebirth.core.facade.UniqueKey;

/**
 * The interface <strong>InnerModels</strong>.
 * 
 * You may create an enumeration that implements this interface to manage inner models inside a root model.
 * 
 * @author Sébastien Bordes
 * 
 */
public interface InnerModels {

    /**
     * Return the class model.
     * 
     * @return the class of the model
     */
    Class<? extends Model> getModelClass();

    /**
     * Return the key of the model. Can return null if the model is unique
     * 
     * @return the multiton key if any, or null
     */
    UniqueKey getKey();

}

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
package org.jrebirth.core.ui.fxml;

import javafx.fxml.Initializable;

import org.jrebirth.core.ui.View;

/**
 * The interface <strong>FXMLController</strong>.
 * 
 * @author Sébastien Bordes
 */
public interface FXMLController extends Initializable {

    /**
     * Set the view that load this FXML component.
     * 
     * @param view the linked view
     */
    void setView(View<?, ?, ?> view);

    /**
     * Return the linked view that load this component.
     * 
     * @return the linked view
     */
    View<?, ?, ?> getView();

}

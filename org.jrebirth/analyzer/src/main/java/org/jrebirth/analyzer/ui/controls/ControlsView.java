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
package org.jrebirth.analyzer.ui.controls;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.jrebirth.core.exception.CoreException;
import org.jrebirth.core.ui.DefaultView;

/**
 * 
 * The class <strong>ControlsView</strong>.
 * 
 * The view used to control the analyzer animation.
 * 
 * @author Sébastien Bordes
 */
public final class ControlsView extends DefaultView<ControlsModel, HBox, ControlsController> {

    /** The open button. */
    private Button openButton;

    /** The playPause button. */
    private Button playPauseButton;

    /** The backward button. */
    private Button backwardButton;

    /** The forward button. */
    private Button forwardButton;

    /** The stop button. */
    private Button stopButton;

    /**
     * Default Constructor.
     * 
     * @param model the controls view model
     * 
     * @throws CoreException if build fails
     */
    public ControlsView(final ControlsModel model) throws CoreException {
        super(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customInitializeComponents() {

        this.openButton = new Button("Open");
        HBox.setHgrow(this.openButton, Priority.ALWAYS);
        this.openButton.setMaxWidth(Double.MAX_VALUE);

        this.playPauseButton = new Button("Play");
        HBox.setHgrow(this.playPauseButton, Priority.ALWAYS);
        this.playPauseButton.setMaxWidth(Double.MAX_VALUE);

        this.backwardButton = new Button("<<");
        HBox.setHgrow(this.backwardButton, Priority.ALWAYS);
        this.backwardButton.setMaxWidth(Double.MAX_VALUE);

        this.forwardButton = new Button(">>");
        HBox.setHgrow(this.forwardButton, Priority.ALWAYS);
        this.forwardButton.setMaxWidth(Double.MAX_VALUE);

        this.stopButton = new Button("Stop");
        HBox.setHgrow(this.stopButton, Priority.ALWAYS);
        this.stopButton.setMaxWidth(Double.MAX_VALUE);

        getRootNode().getChildren().addAll(this.openButton, this.playPauseButton, this.backwardButton, this.forwardButton, this.stopButton);
    }

    /**
     * @return Returns the openButton.
     */
    Button getOpenButton() {
        return this.openButton;
    }

    /**
     * @return Returns the playPauseButton.
     */
    Button getPlayPauseButton() {
        return this.playPauseButton;
    }

    /**
     * @return Returns the backwardButton.
     */
    Button getBackwardButton() {
        return this.backwardButton;
    }

    /**
     * @return Returns the forwardButton.
     */
    Button getForwardButton() {
        return this.forwardButton;
    }

    /**
     * @return Returns the stopButton.
     */
    Button getStopButton() {
        return this.stopButton;
    }

}

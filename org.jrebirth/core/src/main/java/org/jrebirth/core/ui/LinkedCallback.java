package org.jrebirth.core.ui;

import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * The class <strong>LinkedCallback</strong>.
 * 
 * @author Sébastien Bordes
 */
public final class LinkedCallback {

    /**
     * Private Constructor.
     */
    private LinkedCallback() {
        // Nothing to do
    }

    /** The mouse double click checker. */
    public static final Callback<MouseEvent, Boolean> CHECK_MOUSE_DOUBLE_CLICK = new Callback<MouseEvent, Boolean>() {

        /**
         * Check if the mouse event is a double click.
         * 
         * @param event the mouse event triggered
         * 
         * @return true for double click
         */
        @Override
        public Boolean call(final MouseEvent event) {
            return event.getClickCount() == 2;
        }
    };
}

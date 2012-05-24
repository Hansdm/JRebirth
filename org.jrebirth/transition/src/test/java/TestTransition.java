import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.jrebirth.core.application.AbstractApplication;
import org.jrebirth.core.ui.Model;

import ui.TransitionModel;

/**
 * The class <strong>JRebirthAnalyzer</strong>.
 * 
 * The application that demonstrate how to use JRebirth Framework. It also allow to show all JRebirth events.
 * 
 * @author Sébastien Bordes
 * 
 */
public final class TestTransition extends AbstractApplication<StackPane> {

    /**
     * Application launcher.
     * 
     * @param args the command line arguments
     */
    public static void main(final String... args) {
        Application.launch(TestTransition.class, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Model> getFirstModelClass() {
        return TransitionModel.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getApplicationTitle() {
        return "Test Transition";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeStage(final Stage stage) {
        stage.setFullScreen(false);
        stage.setWidth(800);
        stage.setHeight(600);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeScene(Scene scene) {
        // Nothing to do yet

    }
}

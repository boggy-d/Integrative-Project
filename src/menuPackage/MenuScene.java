package menuPackage;

import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import utilities.Constants;

public class MenuScene extends GeneralLayout implements Constants {

    private StackPane pane;
    private Label welcome;
    private MainMenu mainMenu;

    public MenuScene(MainMenu menu, double width, double height) {
        super(menu, width, height);
        mainMenu = menu;
        welcome = new Label(WELCOME_MESSAGE);
        welcome.setTranslateY(-THREE * ONE_HUNDRED);
        pane = new StackPane(pepeWelcome());
        mainPane.setCenter(pane);
        pane.getChildren().add(welcome);
    }

    @Override
    public Button start() {
        return null;
    }

    @Override
    public Button done() {
        return null;
    }

    @Override
    public Button pause() {
        return null;
    }

    @Override
    public Button reset() {
        return null;
    }

    @Override
    public Button help() {
        return null;
    }

    @Override
    public Button continueButton() {
        return null;
    }

    @Override
    public void createSliders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ImageView pepeWelcome() {
        Image pepe = new Image(new File(RARE).toURI().toString());
        ImageView pepeSwag = new ImageView();
        pepeSwag.setImage(pepe);
        pepeSwag.setFitWidth(STAGE_SIZE);
        pepeSwag.setPreserveRatio(TRUE);
        pepeSwag.setSmooth(TRUE);
        pepeSwag.setCache(TRUE);

        return pepeSwag;
    }

    @Override
    public void createGraph() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

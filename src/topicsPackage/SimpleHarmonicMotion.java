package topicsPackage;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import menuPackage.MainMenu;
import menuPackage.GeneralLayout;
import static utilities.Constants.FIFTY;
import static utilities.Constants.ONE_HUNDRED;
import static utilities.Constants.ZERO;
import utilities.Graph;

public class SimpleHarmonicMotion extends GeneralLayout {

    private final Rectangle load = LOAD;
    private final Image springImage = new Image(new File(SPRING_FILE).toURI().toString());
    private final Canvas animationCanvas = new Canvas(TWO_HUNDRED, THREE_HUNDRED);

    private final ButtonListener listener = new ButtonListener();
    private final AnimationTimer runner = new Refresh();

    private final ChangeListener sliderWatch = new ChangeListener() {
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            updateFormula();
        }
    };

    private MainMenu mainMenu;
    private final Slider[] sliderArray;
    private final Graph[] graphArray;

    public SimpleHarmonicMotion(MainMenu menu, double width, double height) {
        super(menu, width, height);
        mainMenu = menu;
        Button[] buttonArray = {start(), done(), pause(), reset(), help(), continueButton()};
        buttonPane(buttonArray);

        sliderArray = new Slider[TWO];
        createSliders();

        graphArray = new Graph[ONE];
        createGraph();

        grid.add(animationCanvas, ZERO, ONE);
        runner.start();
    }

    private void updateFormula() {
        if (!graphArray[ZERO].isRunning()) {
            graphArray[ZERO].setParameters(
                    sliderArray[ZERO].getValue(),
                    sliderArray[ONE].getValue() * TWO * Math.PI,
                    ZERO, ZERO);
        }
    }

    @Override
    public Button start() {
        BUTTONS[ZERO].setOnAction(listener);
        return BUTTONS[ZERO];
    }

    @Override
    public Button done() {
        BUTTONS[ONE].setOnAction(listener);
        return BUTTONS[ONE];
    }

    @Override
    public Button pause() {
        BUTTONS[TWO].setOnAction(listener);
        return BUTTONS[TWO];
    }

    @Override
    public Button reset() {
        BUTTONS[THREE].setOnAction(listener);
        return BUTTONS[THREE];
    }

    @Override
    public Button help() {
        BUTTONS[FOUR].setOnAction(listener);
        return BUTTONS[FOUR];
    }

    @Override
    public Button continueButton() {
        BUTTONS[FIVE].setOnAction(listener);
        return BUTTONS[FIVE];
    }

    @Override
    public void createSliders() {
        sliderArray[ZERO] = new Slider(ZERO, TEN, STARTING_AMPLITUDE);
        sliderArray[ZERO].valueProperty().addListener(sliderWatch);
        sliderArray[ONE] = new Slider(ZERO, TEN, STARTING_FREQUENCY);
        sliderArray[ONE].valueProperty().addListener(sliderWatch);
        Label[] sliderTitles = {new Label(SLIDER_VARIABLES[SIX]), new Label(SLIDER_VARIABLES[SIX + SIX])};
        sliderPane(sliderArray, sliderTitles);
        sliders.setSpacing(SIX);
        sliderArray[ZERO].setMajorTickUnit(ONE);
        sliderArray[ONE].setMajorTickUnit(ONE);
    }

    @Override
    public void createGraph() {
        graphArray[ZERO] = new Graph(ZERO, TEN, TWO, -TEN, TEN, ONE);
        graphArray[ZERO].setTitle(A_VS_T_TITLE);
        grid.add(graphArray[ZERO], ONE, ONE);
        graphArray[ZERO].setFunctionType(Graph.SIN);
        updateFormula();
    }

    private class Refresh extends AnimationTimer {

        GraphicsContext gc = animationCanvas.getGraphicsContext2D();

        @Override
        public void handle(long now) {
            double displacement;

            if (graphArray[ZERO].getLastPoint() == null) {
                displacement = ZERO;
            } else {
                displacement = (Double) graphArray[ZERO].getLastPoint().getYValue() * TEN;
            }

            gc.clearRect(ZERO, ZERO, animationCanvas.getWidth(), animationCanvas.getHeight());

            gc.drawImage(springImage, FIFTY, ZERO, ONE_HUNDRED, ONE_HUNDRED + FIFTY - displacement);
            gc.setFill(Color.GREY);
            gc.fillRect(FIFTY, ONE_HUNDRED + FIFTY - displacement, ONE_HUNDRED, ONE_HUNDRED);
        }

    }

    private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Button source = new Button();
            if (event.getSource() instanceof Button) {
                source = (Button) event.getSource();
            }
            switch (source.getText()) {
                case "Start":
                    if (!graphArray[ZERO].isRunning()) {
                        updateFormula();
                        graphArray[ZERO].start();
                    }
                    break;
                case "Done":
                    mainMenu.launchMenuScene();  //Click this button to terminate the animation, initialize variables with the default values, clear the screen and display the main menu again.
                    break;
                case "Pause":   //pause
                    graphArray[ZERO].pause();
                    break;
                case "Reset":   //reset
                    graphArray[ZERO].reset();
                    graphArray[ZERO].addPoint(ZERO, ZERO);
                    break;
                case "Help":    //dialog box
                    helpDialog(SHM_HELP);
                    break;
                case "Continue":   //resume from pause
                    if (!graphArray[ZERO].isRunning() && !graphArray[ZERO].isReseted()) {
                        updateFormula();
                        graphArray[ZERO].start();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

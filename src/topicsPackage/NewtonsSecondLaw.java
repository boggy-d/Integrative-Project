package topicsPackage;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import menuPackage.GeneralLayout;
import menuPackage.MainMenu;
import static utilities.Constants.FALSE;
import utilities.Graph;

public class NewtonsSecondLaw extends GeneralLayout {

    private final MainMenu mainMenu;
    private final Slider[] sliderArray;
    private final Graph[] graphArray;
    private ImageView chariot;
    private final Group chariotAnimation;
    private AnimationTimer animationTime;
    private final ButtonListener buttonListener;
    private final StackPane container = new StackPane();
    private final Canvas animationCanvas = new Canvas();

    private double force, mass, acceleration, time, velocity, position;

    public NewtonsSecondLaw(MainMenu menu, double width, double height) {
        super(menu, width, height);
        mainMenu = menu;
        buttonListener = new ButtonListener();
        chariotAnimation = new Group();
        Button[] buttonArray = {start(), done(), pause(), reset(), help(), continueButton()};
        time = ZERO;

        buttonPane(buttonArray);

        sliderArray = new Slider[TWO];
        createSliders();

        graphArray = new Graph[TWO];
        createGraph();

        Image background = new Image(new File(COLISEUM).toURI().toString());
        animationCanvas.setHeight(grid.getScene().getHeight() / TWO);
        animationCanvas.setWidth(grid.getScene().getWidth() / TWO + TEN + TEN + FIVE);
        GraphicsContext gc = animationCanvas.getGraphicsContext2D();
        gc.drawImage(background, ZERO, ZERO, animationCanvas.getWidth(), animationCanvas.getHeight());
        container.getChildren().add(animationCanvas);

        createCart();

        animationPane(container);

        animateCart();

    }

    public void setMass(double givenMass) {
        this.mass = givenMass;
    }

    public void setForce(double givenForce) {
        this.force = givenForce;
    }

    public void setAcceleration(double givenAcceleration) {
        this.acceleration = givenAcceleration;
    }

    public double getMass() {
        return this.mass;
    }

    public double getForce() {
        return this.force;
    }

    public double getAcceleration() {
        return this.acceleration;
    }

    @Override
    public Button start() {
        BUTTONS[ZERO].setOnAction(buttonListener);
        return BUTTONS[ZERO];
    }

    @Override
    public Button done() {
        BUTTONS[ONE].setOnAction(buttonListener);
        return BUTTONS[ONE];
    }

    @Override
    public Button pause() {
        BUTTONS[TWO].setOnAction(buttonListener);
        return BUTTONS[TWO];
    }

    @Override
    public Button reset() {
        BUTTONS[THREE].setOnAction(buttonListener);
        return BUTTONS[THREE];
    }

    @Override
    public Button help() {
        BUTTONS[FOUR].setOnAction(buttonListener);
        return BUTTONS[FOUR];
    }

    @Override
    public Button continueButton() {
        BUTTONS[FIVE].setOnAction(buttonListener);
        return BUTTONS[FIVE];
    }

    @Override
    public void createSliders() {
        sliderArray[ZERO] = new Slider(ZERO, ONE_HUNDRED * TWO, ZERO);
        sliderArray[ONE] = new Slider(ZERO, ONE_HUNDRED * TWO, ZERO);
        Label[] sliderTitles = {new Label(SLIDER_VARIABLES[ZERO]), new Label(SLIDER_VARIABLES[ONE])};
        sliderPane(sliderArray, sliderTitles);
        sliderArray[ZERO].setMajorTickUnit(TEN);
    }

    @Override
    public void createGraph() {
        graphArray[ZERO] = new Graph(ZERO, TEN, ONE, ZERO, (int) getAcceleration() / ONE + ONE, (int) getAcceleration() / FIVE);
        graphArray[ZERO].setFunctionType(LINEAR);
        graphArray[ZERO].setTitle(ACCEL_TITLE);

        graphArray[ONE] = new Graph(ZERO, TEN, ONE, ZERO, (int) getAcceleration() * TEN + ONE, (int) getAcceleration() / FIVE);
        graphArray[ONE].setFunctionType(LINEAR);
        graphArray[ONE].setTitle(VEL_TITLE);
        graphPane(graphArray);
    }

    public void calculateAcceleration() {
        setAcceleration(getForce() / getMass());
    }

    public void createCart() {
        chariot = new ImageView();
        chariot.setImage(new Image(new File(CHARIOT).toURI().toString()));
        chariot.setFitHeight(ONE_HUNDRED - TEN * TWO);
        chariot.setPreserveRatio(TRUE);
        chariot.setSmooth(TRUE);
        chariotAnimation.getChildren().add(chariot);
        container.getChildren().add(chariotAnimation);
        chariotAnimation.setTranslateX(STARTING_POINT);
        chariotAnimation.setTranslateY(ONE_HUNDRED);
    }

    public void animateCart() {
        animationTime = new AnimationTimer() {
            @Override
            public void handle(long now) {
                velocity = getAcceleration() * time;
                position = velocity * time;
                System.out.println(time + " " + now + " Vel: " + velocity + " Pos: " + position);
                chariotAnimation.setTranslateX(STARTING_POINT + position);
                graphArray[ONE].setParameters(getAcceleration(), ONE, ZERO, ZERO);
                time += TIMEFRAME;

                if (position > grid.getWidth() / TWO) {
                    chariot.setVisible(FALSE);
                } else {
                    chariot.setVisible(TRUE);
                }
            }
        };
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
                    setForce(sliderArray[ZERO].getValue());
                    setMass(sliderArray[ONE].getValue());
                    calculateAcceleration();
                    System.out.println(force + " " + mass + " " + acceleration);
                    animationTime.start();
                    createGraph();
                    graphArray[ZERO].setParameters(ZERO, ZERO, ZERO, acceleration);
                    graphArray[ZERO].start();
                    graphArray[ONE].start();
                    break;
                case "Done":
                    animationTime.stop();
                    time = ZERO;
                    mainMenu.launchMenuScene();  //Click this button to terminate the chariotAnimation, initialize variables with the default values, clear the screen and display the main menu again.
                    break;
                case "Pause":   //pause
                    animationTime.stop();
                    graphArray[ZERO].pause();
                    graphArray[ONE].pause();
                    break;
                case "Reset":   //reset
                    animationTime.stop();
                    time = ZERO;
                    chariotAnimation.setTranslateX(STARTING_POINT);
                    chariot.setVisible(TRUE);
                    graphArray[ZERO].reset();
                    graphArray[ONE].reset();
                    break;
                case "Help":    //dialog box
                    helpDialog(NSL_HELP);
                    break;
                case "Continue":   //resume from pause
                    animationTime.start();
                    graphArray[ZERO].start();
                    graphArray[ONE].start();
                    break;
                default:
                    break;
            }
        }

    }
}

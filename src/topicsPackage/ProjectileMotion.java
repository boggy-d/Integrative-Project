package topicsPackage;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import menuPackage.MainMenu;
import menuPackage.GeneralLayout;
import static utilities.Constants.ONE;
import static utilities.Constants.P_TO_M_CONSTANT;
import static utilities.Constants.ZERO;
import utilities.Graph;

public class ProjectileMotion extends GeneralLayout {

    private final MainMenu mainMenu;
    private final Slider[] sliderArray;
    private final Graph[] graphArray;

    private final ButtonListener listener = new ButtonListener();

    private final StackPane animationGroup = new StackPane();
    private final Canvas animationCanvas = new Canvas();
    private final GraphicsContext gc;

    private final Circle ball = new Circle(TEN);
    private AnimationTimer runner;

    private boolean shotLanded = TRUE;

    private final Group canonGroup = new Group();
    private Rotate canonRotation = new Rotate();

    public ProjectileMotion(MainMenu menu, double width, double height) {
        super(menu, width, height);
        mainMenu = menu;
        Button[] buttonArray = {start(), done(), pause(), reset(), help(), continueButton()};
        buttonPane(buttonArray);

        sliderArray = new Slider[TWO];
        createSliders();

        graphArray = new Graph[TWO];
        createGraph();

        grid.add(animationGroup, ZERO, ONE);
        animationGroup.getChildren().add(animationCanvas);
        animationCanvas.setHeight(animationGroup.getScene().getHeight() / TWO);
        animationCanvas.setWidth(animationGroup.getScene().getWidth() / TWO + FIVE);
        Image background = new Image(new File(SEA_IMAGE).toURI().toString());
        gc = animationCanvas.getGraphicsContext2D();
        gc.drawImage(background, ZERO, ZERO, animationCanvas.getWidth(), animationCanvas.getHeight());

        animationGroup.setAlignment(Pos.TOP_LEFT);
        animationGroup.getChildren().add(ball);
        ball.setVisible(FALSE);

        ball.setTranslateX(STARTING_X);
        ball.setTranslateY(STARTING_Y);

        // Creating the canon
        Circle canonBase = new Circle(CANON_BASE);
        Polygon canonNozzle = new Polygon();
        canonNozzle.getPoints().addAll(CANON_POINTS);

        canonGroup.getChildren().addAll(canonBase, canonNozzle);
        animationGroup.getChildren().add(canonGroup);
        canonBase.setTranslateY(CANON_BASE);
        canonGroup.setTranslateX(STARTING_X + TEN - CANON_BASE);
        canonGroup.setTranslateY(STARTING_Y + TEN - CANON_BASE);
        canonGroup.getTransforms().add(canonRotation);
        canonRotation.setPivotY(CANON_BASE);
        canonRotation.setAngle(-START_ANGLE);

        sliderArray[ONE].valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue angle, Object old, Object recent) {
                double data = ((ObservableDoubleValue) angle).get();
                canonRotation.setAngle(-data);
            }
        });
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
        sliderArray[ZERO] = new Slider(ZERO, VEL_MAX, START_VEL);
        sliderArray[ONE] = new Slider(ZERO, ANGLE_MAX, START_ANGLE);
        Label[] sliderTitles = {new Label(SLIDER_VARIABLES[TWO]), new Label(SLIDER_VARIABLES[THREE])};
        sliderPane(sliderArray, sliderTitles);
        sliderArray[ZERO].setMajorTickUnit(TEN);
        sliderArray[ONE].setMajorTickUnit(TEN);
    }

    @Override
    public void createGraph() {
        graphArray[ZERO] = new Graph(ZERO, TEN, ONE, ZERO, FIFTY, FIVE);
        graphArray[ZERO].setTitle(ALT_TITLE);
        graphArray[ONE] = new Graph(ZERO, TEN, ONE, -TEN, TEN, TWO);
        graphArray[ONE].setTitle(VER_VEL_TITLE);
        graphPane(graphArray);
    }

    private void fire() {
        shotLanded = FALSE;

        double velocity = sliderArray[ZERO].getValue();
        double angle = sliderArray[ONE].getValue();

        double verticalVel = velocity * Math.sin((angle / TRHEE_SIXTY) * TWO * Math.PI);
        double horizontalVel = velocity * Math.cos((angle / TRHEE_SIXTY) * TWO * Math.PI);

        double time = TWO * verticalVel / GRAVITY;

        double maxHeight = verticalVel * time / FOUR;

        double range = time * horizontalVel;

        // Setting up the graph
        Graph hGraph = graphArray[ZERO];
        hGraph = new Graph(ZERO, (int) time / ONE + ONE, ONE, ZERO, maxHeight - maxHeight % TEN + TEN, TEN);
        hGraph.setFunctionType(QUADRATIC);
        hGraph.setParameters(-maxHeight / (time * time / FOUR), ONE, time / TWO, maxHeight);
        hGraph.setTitle(ALT_TITLE);
        graphArray[ZERO] = hGraph;

        Graph vGraph = graphArray[ONE];
        vGraph = new Graph(ZERO, (int) time / ONE + ONE, ONE, -verticalVel + verticalVel % FIVE - FIVE,
                verticalVel - verticalVel % FIVE + FIVE, FIVE);
        vGraph.setFunctionType(LINEAR);
        vGraph.setParameters(-GRAVITY, ONE, ZERO, verticalVel);
        vGraph.setTitle(VER_VEL_TITLE);
        graphArray[ONE] = vGraph;

        graphPane(graphArray);
        grid.setVisible(FALSE);
        grid.setVisible(TRUE);

        hGraph.start();
        vGraph.start();

        // Setting up the actual animation
        ball.setVisible(TRUE);

        runner = new AnimationTimer() {
            @Override
            public void handle(long now) {
                XYChart.Data point = graphArray[ZERO].getLastPoint();

                if (point == null) {
                    return;
                }

                ball.setTranslateX(STARTING_X
                        + ((Double) point.getXValue()) * P_TO_M_CONSTANT * horizontalVel);

                ball.setTranslateY(STARTING_Y - ((Double) point.getYValue()) * P_TO_M_CONSTANT);

                if ((Double) point.getXValue() * horizontalVel >= range) {
                    landShot(range);
                }
            }
        };

        runner.start();
    }

    private void landShot(double distance) {
        shotLanded = TRUE;

        runner.stop();
        runner = null;

        graphArray[ZERO].pause();
        graphArray[ONE].pause();

        ball.setVisible(FALSE);
        ball.setTranslateX(STARTING_X);
        ball.setTranslateY(STARTING_Y);

        if (distance < BOAT_LOWER_EDGE) {
            System.out.println(SHORT_TEXT);
        } else if (distance < BOAT_HIGHER_EDGE) {
            System.out.println(HIT_TEXT);
        } else if (distance < SCREEN_EDGE) {
            System.out.println(FAR_TEXT);
        } else {
            System.out.println(DOPE_TEXT);
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
                        fire();
                    }
                    break;
                case "Done":
                    mainMenu.launchMenuScene();  //Click this button to terminate the animation, initialize variables with the default values, clear the screen and display the main menu again.
                    break;
                case "Pause":   //pause
                    if (graphArray[ZERO].isRunning()) {
                        graphArray[ZERO].pause();
                        graphArray[ONE].pause();
                        runner.stop();
                    }
                    break;
                case "Reset":   //reset
                    graphArray[ZERO].reset();
                    graphArray[ONE].reset();
                    ball.setVisible(FALSE);
                    ball.setTranslateX(STARTING_X);
                    ball.setTranslateY(STARTING_Y);
                    shotLanded = TRUE;
                    break;
                case "Help":    //dialog box
                    helpDialog(PROJECTTILE_HELP);
                    break;
                case "Continue":   //resume from pause
                    if (!graphArray[ZERO].isRunning() && !shotLanded) {
                        graphArray[ZERO].start();
                        graphArray[ONE].start();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

package topicsPackage;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import menuPackage.GeneralLayout;
import menuPackage.MainMenu;
import static utilities.Constants.CHARIOT;
import static utilities.Constants.COLISEUM;
import static utilities.Constants.FIFTY;
import static utilities.Constants.FIVE;
import static utilities.Constants.TEN;
import static utilities.Constants.TRUE;
import static utilities.Constants.TWO;
import static utilities.Constants.ZERO;
import utilities.Graph;

public class InfiniteGeometricSeries extends GeneralLayout {

    private MainMenu mainMenu;
    private final Slider[] sliderArray;
    private final Graph[] graphArray;
    private int firstTerm, n;
    private double seriesResult, commonRatio;
    private Group baseballAnimation, throwerAnimation, hitterAnimation, loseMessage, winMessage;
    private ImageView hitter, thrower, baseball;
    private TranslateTransition ballThrow, ballFly;
    private final InfiniteGeometricSeries.ButtonListener buttonListener;
    private Timeline timeline;
    private Canvas animationCanvas = new Canvas();
    private StackPane container = new StackPane();

    public InfiniteGeometricSeries(MainMenu menu, double width, double height) {
        super(menu, width, height);
        mainMenu = menu;
        buttonListener = new ButtonListener();
        Button[] buttonArray = {start(), done(), pause(), reset(), help(), continueButton()};
        buttonPane(buttonArray);

        sliderArray = new Slider[TWO];
        createSliders();

        graphArray = new Graph[ONE];
        createGraph();

        createScenery();

        createBaseBall();

        animationPane(container);

    }

    public void setFirstTerm(int givenFirstTerm) {
        this.firstTerm = givenFirstTerm;
    }

    public void setCommonRatio(int givenCommonRatio) {
        this.commonRatio = givenCommonRatio;
    }

    public void setSeriesResult(double givenSeriesResult) {
        this.seriesResult = givenSeriesResult;
    }

    public int getFirstTerm() {
        return this.firstTerm;
    }

    public double getCommonRatio() {
        return this.commonRatio;
    }

    public double getSeriesResult() {
        return this.seriesResult;
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
        sliderArray[ZERO] = new Slider(-TEN, TEN, ZERO);
        sliderArray[ONE] = new Slider(-TEN, TEN, ZERO);
        Label[] sliderTitles = {new Label(SLIDER_VARIABLES[NINE]), new Label(SLIDER_VARIABLES[TEN])};
        sliderPane(sliderArray, sliderTitles);
        sliderArray[ZERO].setMajorTickUnit(TEN);
        sliderArray[ONE].setMajorTickUnit(TEN);
        sliderArray[ZERO].setSnapToTicks(TRUE);
        sliderArray[ONE].setSnapToTicks(TRUE);
    }

    @Override
    public void createGraph() {
        if (getCommonRatio() < ZERO) {
            graphArray[ZERO] = new Graph(ZERO, TEN, ONE, ZERO, getFirstTerm(), ONE);
        } else {
            graphArray[ZERO] = new Graph(ZERO, TEN, ONE, ZERO, getFirstTerm() / (ONE - commonRatio), ONE);
        }
        graphArray[ZERO].setTitle(IGS_TITLE);
        graphPane(graphArray);
    }

    public void createBaseBall() {
        baseball.setImage(new Image(new File(BASEBALL).toURI().toString()));
        baseball.setFitWidth(TEN + TEN);
        baseball.setPreserveRatio(TRUE);
        baseball.setSmooth(TRUE);
        baseballAnimation = new Group(baseball);
        baseballAnimation.setTranslateX(PITCHER_DIST_X);
        baseballAnimation.setTranslateY(PITCHER_DIST_Y);
        container.getChildren().add(baseballAnimation);

        baseballAnimation();
    }

    public void createScenery() {
        hitter = new ImageView(new Image(new File(HITTING_INI).toURI().toString()));
        thrower = new ImageView(new Image(new File(THROWING_INI).toURI().toString()));
        baseball = new ImageView();

        Image background = new Image(new File(BASEBALL_FIELD).toURI().toString());
        animationCanvas.setHeight(grid.getScene().getHeight() / TWO);
        animationCanvas.setWidth(grid.getScene().getWidth() / TWO + TEN + TEN + FIVE);
        GraphicsContext gc = animationCanvas.getGraphicsContext2D();
        gc.drawImage(background, ZERO, ZERO, animationCanvas.getWidth(), animationCanvas.getHeight());
        container.getChildren().add(animationCanvas);

        hitter.setFitHeight(ONE_HUNDRED + FIFTY);
        hitter.setPreserveRatio(TRUE);
        hitter.setSmooth(TRUE);
        hitterAnimation = new Group(hitter);
        hitterAnimation.setTranslateX(HITTER_DIST);
        hitterAnimation.setTranslateY(PITCHER_DIST_Y);
        container.getChildren().add(hitterAnimation);

        thrower.setFitWidth(ONE_HUNDRED);
        thrower.setPreserveRatio(TRUE);
        thrower.setSmooth(TRUE);
        throwerAnimation = new Group(thrower);
        throwerAnimation.setTranslateX(PITCHER_DIST_X);
        throwerAnimation.setTranslateY(PITCHER_DIST_Y);
        container.getChildren().add(throwerAnimation);
    }

    public void baseballAnimation() {
        timeline = new Timeline();
        ballThrow = new TranslateTransition(Duration.millis(ONE_SECOND), baseballAnimation);
        ballThrow.setByX(BALL_THROW);
        ballThrow.setCycleCount(ZERO);

        ballFly = new TranslateTransition(Duration.millis(ONE_SECOND / FOUR), baseballAnimation);
    }

    public void calculateSum() {
        seriesResult += getFirstTerm() * Math.pow(getCommonRatio(), n);
    }

    private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Button source = new Button();
            if (event.getSource() instanceof Button) {
                source = (Button) event.getSource();
            }

            Timeline ballHit = new Timeline(new KeyFrame(Duration.millis(ONE_SECOND), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    thrower.setImage(new Image(new File(CATCHING).toURI().toString()));
                    thrower.setFitWidth(FIFTY + TEN);
                    hitter.setImage(new Image(new File(HITTING_FIN).toURI().toString()));
                }
            }));
            ballHit.setCycleCount(ZERO);
            switch (source.getText()) {
                case "Start":
                    thrower.setImage(new Image(new File(THROWING_FIN).toURI().toString()));
                    ballThrow.play();
                    ballHit.play();
                    setFirstTerm((int) sliderArray[ZERO].getValue());
                    commonRatio = ONE / sliderArray[ONE].getValue();
                    setSeriesResult(ZERO);
                    n = ZERO;

                    timeline = new Timeline(new KeyFrame(Duration.millis(ONE_SECOND / TWO), new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("STARTED!");
                            double totalResult = getFirstTerm() / (ONE - getCommonRatio());
                            double prevResult = getSeriesResult();
                            calculateSum();
                            graphArray[ZERO].addPoint(n, getSeriesResult());
                            ballFly.setByX(n * TEN);
                            if (n > ZERO) {
                                if (baseballAnimation.getTranslateX() >= PITCHER_DIST_X) {
                                    if (baseballAnimation.getTranslateY() >= thrower.getFitHeight()) {
                                        timeline.stop();
                                        baseballAnimation.setTranslateX(PITCHER_DIST_X - n * TEN + FIVE);
                                        baseballAnimation.setTranslateY(FIFTY);
                                        loseMessage = new Group(new Label(OUT));
                                        loseMessage.setTranslateY(-ONE_HUNDRED * TWO);
                                        container.getChildren().add(loseMessage);
                                    } else {
                                        timeline.stop();
                                        winMessage = new Group(new Label(HOMERUN));
                                        winMessage.setTranslateY(-ONE_HUNDRED * TWO);
                                        container.getChildren().add(winMessage);
                                    }
                                } else {
                                    ballFly.setByY(-(getSeriesResult() - prevResult) * THREE * ONE_HUNDRED);
                                }
                            }
                            ballFly.play();
                            if (Math.abs(totalResult - getSeriesResult()) <= RESULT_PRECISION) {
                                System.out.println("STOP! " + getSeriesResult() + " " + n);
                            } else {
                                System.out.println(getSeriesResult() + " " + n);
                            }
                            n++;
                        }
                    }));
                    timeline.setCycleCount(Timeline.INDEFINITE);

                    createGraph();
                    graphArray[ZERO].start();
                    timeline.play();
                    break;
                case "Done":
                    mainMenu.launchMenuScene();  //Click this button to terminate the baseballAnimation, initialize variables with the default values, clear the screen and display the main menu again.
                    break;
                case "Pause":   //pause
                    timeline.pause();
                    ballFly.pause();
                    graphArray[ZERO].pause();
                    break;
                case "Reset":   //reset
                    container.getChildren().removeAll(loseMessage, winMessage);
                    timeline.stop();
                    ballFly.stop();
                    graphArray[ZERO].reset();
                    baseballAnimation.setTranslateX(PITCHER_DIST_X);
                    baseballAnimation.setTranslateY(PITCHER_DIST_Y);
                    thrower.setImage(new Image(new File(THROWING_INI).toURI().toString()));
                    thrower.setFitWidth(ONE_HUNDRED);
                    hitter.setImage(new Image(new File(HITTING_INI).toURI().toString()));
                    break;
                case "Help":    //dialog box
                    helpDialog(IGS_HELP);
                    break;
                case "Continue":   //resume from pause
                    timeline.play();
                    ballFly.play();
                    graphArray[ZERO].start();

                    break;
                default:
                    break;
            }
        }
    }
}

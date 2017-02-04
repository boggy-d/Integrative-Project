package topicsPackage;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import menuPackage.MainMenu;
import menuPackage.GeneralLayout;
import static utilities.Constants.FALSE;
import static utilities.Constants.FIVE;
import static utilities.Constants.MAX_PROFIT;
import static utilities.Constants.TEN;
import static utilities.Constants.THREE;
import static utilities.Constants.TRUE;
import static utilities.Constants.TWENTY;
import static utilities.Constants.ZERO;
import utilities.Graph;

public class NewSportsBike extends GeneralLayout {

    private final ButtonListener listener = new ButtonListener();
    private MainMenu mainMenu;
    private final Slider[] sliderArray;
    private Graph[] graphArray = new Graph[ONE];

    private XYChart.Series graphLine = new XYChart.Series();
    private XYChart.Data firstPoint;
    private XYChart.Data secondPoint;

    private double lastPrice = ZERO;

    private AnimationTimer runner;

    private final StackPane animationGroup = new StackPane();
    private final Text profitText = new Text();
    private final Group store = new Group();

    private final Group[] bikes = new Group[TEN];
    private final Group[] bills = new Group[TWO_HUNDRED];

    ImageView lexi;

    public NewSportsBike(MainMenu menu, double width, double height) {
        super(menu, width, height);
        mainMenu = menu;
        Button[] buttonArray = {start(), done(), pause(), reset(), help(), continueButton()};
        buttonPane(buttonArray);

        sliderArray = new Slider[ONE];
        createSliders();

        createGraph();

        firstPoint = new XYChart.Data(ZERO, LOWEST_PROFIT);
        secondPoint = new XYChart.Data(ZERO, TOP_PROFIT);
        graphLine.getData().addAll(firstPoint, secondPoint);

        grid.add(animationGroup, ZERO, ONE);
        animationGroup.setVisible(FALSE);
        Rectangle facade = new Rectangle(TWO_HUNDRED, TWO_HUNDRED - FIFTY);
        facade.setFill(Color.BROWN);
        Rectangle door = new Rectangle(FIFTY, ONE_HUNDRED);
        door.setTranslateY(FIFTY);
        door.setTranslateX(ONE_HUNDRED + TWENTY);
        door.setFill(Color.BLACK);
        Rectangle window = new Rectangle(FIFTY + TWENTY, FIFTY);
        window.setFill(Color.DARKBLUE);
        window.setTranslateX(THREE_HUNDRED / TEN);
        window.setTranslateY(FIFTY + TEN);
        Polygon roof = new Polygon(ZERO, ZERO, TWO_HUNDRED, ZERO, TWO_HUNDRED + TWENTY, FIFTY, -TWENTY, FIFTY);
        roof.setFill(Color.DARKGREEN);
        roof.setTranslateY(-TWENTY);
        Text storeSign = new Text(STORE_STRING);
        storeSign.setFill(Color.BURLYWOOD);
        storeSign.setFont(Font.font(SIGN_FONT, TWENTY));
        storeSign.setTranslateX(TEN);
        storeSign.setTranslateY(FIVE);
        store.getChildren().addAll(facade, door, window, roof, storeSign);
        Image lexiImg = new Image(new File(LEXI_FILE).toURI().toString());
        lexi = new ImageView(lexiImg);
        lexi.setFitHeight(LEXI_SIZE);
        lexi.setFitWidth(LEXI_SIZE);
        lexi.setSmooth(TRUE);
        animationGroup.getChildren().addAll(profitText, store, lexi);

        Random rn = new Random();

        for (int i = ZERO; i < bills.length; ++i) {
            bills[i] = new DollarBill();
            RotateTransition rt = new RotateTransition(Duration.seconds(TWO + rn.nextInt(FOUR)), bills[i]);
            rt.setToAngle(TRHEE_SIXTY);
            rt.setFromAngle(ZERO);
            rt.setAutoReverse(TRUE);
            rt.setCycleCount(Animation.INDEFINITE);
            rt.play();
            bills[i].setTranslateX(-ONE_HUNDRED - rn.nextInt(ONE_HUNDRED));

            TranslateTransition tt = new TranslateTransition(Duration.seconds(FIVE + rn.nextDouble() * FIVE), bills[i]);
            tt.setFromY(-ONE_HUNDRED);
            tt.setToY(ONE_HUNDRED);
            tt.setCycleCount(Animation.INDEFINITE);
            tt.play();
            animationGroup.getChildren().add(bills[i]);
            bills[i].setVisible(FALSE);
        }

        for (int i = ZERO; i < bikes.length; ++i) {
            bikes[i] = new Bike();
            TranslateTransition tt = new TranslateTransition(Duration.seconds(ONE + rn.nextDouble() * TWO), bikes[i]);
            tt.setByX(ONE_HUNDRED * THREE);
            tt.setCycleCount(Animation.INDEFINITE);
            tt.play();
            animationGroup.getChildren().add(bikes[i]);
            bikes[i].setVisible(FALSE);
        }

        runner = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (graphArray[ZERO].getLastPoint() != null) {
                    if ((Double) graphArray[ZERO].getLastPoint().getXValue() > MAX_PRICE && graphArray[ZERO].isRunning()) {
                        graphArray[ZERO].pause();
                    }
                }

                double price = sliderArray[ZERO].getValue();
                double profit = graphArray[ZERO].calculate(price);

                if (lastPrice != price) {
                    firstPoint.setXValue(price);
                    secondPoint.setXValue(price);
                    lastPrice = price;
                }

                profitText.setText(PROFIT_TEXT + new DecimalFormat(DECIMAL_FORMAT).format(profit));
                if (profit < ZERO) {
                    profitText.setFill(Color.RED);
                } else {
                    profitText.setFill(Color.GREEN);
                }

                profitText.setTranslateY(-grid.getHeight() / FOUR + TWENTY);

                int nDollas = (int) ((profit / MAX_PROFIT) * bills.length);
                int nBikes = (int) ((profit / MAX_PROFIT) * bikes.length);

                for (int i = ZERO; i < bills.length; ++i) {
                    if (i <= nDollas) {
                        bills[i].setVisible(TRUE);
                    } else {
                        bills[i].setVisible(FALSE);
                    }
                }

                for (int i = ZERO; i < bikes.length; ++i) {
                    if (i <= nBikes && bikes[i].getTranslateX() < grid.getWidth() / FOUR - FIFTY) {
                        bikes[i].setVisible(TRUE);
                    } else {
                        bikes[i].setVisible(FALSE);
                    }
                }

                if (profit >= LEXI_HIGH_SCORE) {
                    lexi.setVisible(TRUE);
                } else {
                    lexi.setVisible(FALSE);
                }
            }
        };
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
        sliderArray[ZERO] = new Slider(ZERO, MAX_PRICE, ZERO);
        Label[] sliderTitles = {new Label(SLIDER_NAME)};
        sliderPane(sliderArray, sliderTitles);
    }

    @Override
    public void createGraph() {
        graphArray[ZERO] = new Graph(ZERO, MAX_PRICE, FIFTY, LOWEST_PROFIT, TOP_PROFIT, PROFIT_TICK);
        graphArray[ZERO].setTitle(PROFIT_TITLE);
        graphArray[ZERO].setFunctionType(Graph.QUADRATIC);
        graphArray[ZERO].setParameters(-TWO_HUNDRED, ONE, TWO_HUNDRED + THREE * TEN, MAX_PROFIT);
        graphArray[ZERO].setSpeed(THREE_HUNDRED);
        graphArray[ZERO].setPrecision(ONE);
        grid.add(graphArray[ZERO], ONE, ONE);
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
                    if (!animationGroup.isVisible()) {
                        graphArray[ZERO].start();
                    }
                    graphArray[ZERO].getData().add(graphLine);
                    runner.start();
                    animationGroup.setVisible(TRUE);
                    break;
                case "Done":
                    mainMenu.launchMenuScene();  //Click this button to terminate the animation, initialize variables with the default values, clear the screen and display the main menu again.
                    break;
                case "Pause":   //pause
                    if (animationGroup.isVisible()) {
                        graphArray[ZERO].reset();
                    }
                    runner.stop();
                    animationGroup.setVisible(FALSE);
                    break;
                case "Reset":   //reset
                    if (animationGroup.isVisible()) {
                        graphArray[ZERO].reset();
                    }
                    runner.stop();
                    animationGroup.setVisible(FALSE);
                    break;
                case "Help":    //dialog box
                    helpDialog(BIKE_MESSAGE);
                    break;
                case "Continue":   //resume from pause
                    if (!animationGroup.isVisible()) {
                        graphArray[ZERO].start();
                    }
                    graphArray[ZERO].getData().add(graphLine);
                    runner.start();
                    animationGroup.setVisible(TRUE);
                    break;
                default:
                    break;
            }
        }
    }

    private class DollarBill extends Group {

        public DollarBill() {
            Rectangle paper = new Rectangle(FIVE, TEN + FIVE);
            paper.setFill(Color.GREEN);

            this.getChildren().addAll(paper);
        }
    }

    private class Bike extends Group {

        public Bike() {
            Circle wheel1 = new Circle(TWENTY);
            wheel1.setFill(Color.TRANSPARENT);
            wheel1.setStroke(Color.BLACK);
            wheel1.setStrokeWidth(THREE);
            Circle wheel2 = new Circle(TWENTY);
            wheel2.setFill(Color.TRANSPARENT);
            wheel2.setStroke(Color.BLACK);
            wheel2.setStrokeWidth(THREE);
            wheel2.setTranslateX(FIFTY + TWENTY);

            Polygon frame = new Polygon(ZERO, ZERO, TWENTY + TEN, ZERO, TWENTY, -TWENTY, ZERO, ZERO, TWENTY, -TWENTY, TWENTY * THREE, -TWENTY - TEN, TWENTY + FIFTY, ZERO, TWENTY * THREE, -TWENTY - TEN, TWENTY + TEN, ZERO);
            frame.setFill(Color.TRANSPARENT);
            frame.setStroke(Color.PINK);
            frame.setStrokeWidth(FIVE);
            this.setTranslateY(FIFTY + TEN);
            this.setTranslateX(FIFTY);
            this.getChildren().addAll(wheel1, wheel2, frame);
        }
    }
}

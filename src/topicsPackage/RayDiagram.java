package topicsPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Line;
import javafx.util.StringConverter;
import menuPackage.MainMenu;
import menuPackage.GeneralLayout;
import utilities.Graph;

public class RayDiagram extends GeneralLayout {

    private MainMenu mainMenu;
    private final Slider[] sliderArray;
    private final Graph[] graphArray;
    private int object_distance, focal_length, object_height;
    private double image_distance, image_height;
    private final RayDiagram.ButtonListener buttonListener;
    private Line objArrow1, objArrow2, objLine, imgArrow1, imgArrow2, imgLine, lensLine, lensArrow1, lensArrow2, lensArrow3, lensArrow4;
    private Group object, image, lens;
    private XYChart.Series straightLine, constantStraight, straightConstant;

    public RayDiagram(MainMenu menu, double width, double height) {
        super(menu, width, height);
        buttonListener = new ButtonListener();
        mainMenu = menu;
        Button[] buttonArray = {start(), done(), pause(), reset(), help(), continueButton()};
        buttonPane(buttonArray);

        sliderArray = new Slider[FOUR];
        createSliders();

        graphArray = new Graph[ONE];
        createGraph();

        objArrow1 = new Line(ZERO, ZERO, -TEN, TEN);
        objArrow2 = new Line(ZERO, ZERO, TEN, TEN);
        objLine = new Line(ZERO, ZERO, ZERO, ZERO);
        object = new Group(objArrow1, objArrow2, objLine);
        object.setTranslateX(OBJ_START);
        object.setTranslateY(FOUR);
        animationPane(object);

        imgArrow1 = new Line(ZERO, ZERO, -TEN, -TEN);
        imgArrow2 = new Line(ZERO, ZERO, TEN, -TEN);
        imgLine = new Line(ZERO, ZERO, ZERO, ZERO);
        image = new Group(imgArrow1, imgArrow2, imgLine);
        image.setTranslateX(OBJ_START);
        image.setTranslateY(-FOUR);

        lensLine = new Line(ZERO, -LENS, ZERO, LENS);
        lensArrow1 = new Line(ZERO, -LENS, -TEN, (TEN - LENS));
        lensArrow2 = new Line(ZERO, -LENS, TEN, (TEN - LENS));
        lensArrow3 = new Line(ZERO, LENS, -TEN, (LENS - TEN));
        lensArrow4 = new Line(ZERO, LENS, TEN, (LENS - TEN));
        lens = new Group(lensLine, lensArrow1, lensArrow2, lensArrow3, lensArrow4);
        lens.setTranslateX(OBJ_START);
        animationPane(lens);
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
        sliderArray[ZERO] = new Slider(ZERO, TEN, ZERO);
        sliderArray[ONE] = new Slider(-TEN, TEN, ZERO);
        sliderArray[TWO] = new Slider(-TEN, TEN, ZERO);
        sliderArray[THREE] = new Slider(ZERO, ONE, ZERO);
        Label[] sliderTitles = {new Label(SLIDER_VARIABLES[FOUR]), new Label(SLIDER_VARIABLES[FIVE]), new Label(SLIDER_VARIABLES[TEN + THREE]), new Label(SLIDER_VARIABLES[TEN + FOUR])};
        sliderPane(sliderArray, sliderTitles);
        sliderArray[ZERO].setMajorTickUnit(TEN);
        sliderArray[ONE].setMajorTickUnit(TEN);
        sliderArray[TWO].setMajorTickUnit(TEN);
        sliderArray[THREE].setMajorTickUnit(ONE);
        sliderArray[THREE].setMinorTickCount(ZERO);

        sliderArray[ZERO].setSnapToTicks(TRUE);
        sliderArray[ONE].setSnapToTicks(TRUE);
        sliderArray[TWO].setSnapToTicks(TRUE);
        sliderArray[THREE].setSnapToTicks(TRUE);

        sliderArray[ZERO].valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                double data = ((ObservableDoubleValue) observable).get();
                object.setTranslateX(OBJ_START - (data * GROWY) * GROWX);
            }
        });
        sliderArray[TWO].valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                double data = ((ObservableDoubleValue) observable).get();
                object.setTranslateY(FOUR - (data + ((double) EIGHT / TEN)) * GROWY / TWO);
                objLine.setStartY(-(FOUR - (data) * GROWY));
                System.out.println(data + " " + objLine.getEndY() + " " + object.getTranslateY());
            }
        });

        sliderArray[THREE].valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int data = (int) ((ObservableDoubleValue) observable).get();
                if (data == ONE) {
                    lensArrow1.setEndY(-LENS - TEN);
                    lensArrow2.setEndY(-LENS - TEN);
                    lensArrow3.setEndY(LENS + TEN);
                    lensArrow4.setEndY(LENS + TEN);
                } else {
                    lensArrow1.setEndY(TEN - LENS);
                    lensArrow2.setEndY(TEN - LENS);
                    lensArrow3.setEndY(LENS - TEN);
                    lensArrow4.setEndY(LENS - TEN);
                }
            }
        });

        sliderArray[THREE].setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < (double) ONE / TWO) {
                    return "Converging";
                }
                return "Diverging";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Converging":
                        return 0d;
                    default:
                        return 1d;
                }
            }
        });
        sliders.setAlignment(Pos.TOP_CENTER);
        sliders.setSpacing(ONE);
    }

    @Override
    public void createGraph() {
        graphArray[ZERO] = new Graph(-TEN, TEN, ONE, -TEN, TEN, ONE);
        graphArray[ZERO].setTitle(RD_TITLE);
        graphPane(graphArray);
    }

    public void drawLightRays() {
        constantStraight = new XYChart.Series();
        straightConstant = new XYChart.Series();

        if (chooseLens()) {
            constantStraight.getData().add(new XYChart.Data(-object_distance, object_height));
            constantStraight.getData().add(new XYChart.Data(ZERO, object_height));
            XYChart.Series constantStraight2 = new XYChart.Series();
            constantStraight2.getData().add(new XYChart.Data(ZERO, object_height));
            constantStraight2.getData().add(new XYChart.Data(image_distance, -image_height));

            straightConstant.getData().add(new XYChart.Data(-object_distance, object_height));
            straightConstant.getData().add(new XYChart.Data(ZERO, -image_height));
            XYChart.Series straightConstant2 = new XYChart.Series();
            straightConstant2.getData().add(new XYChart.Data(ZERO, -image_height));
            straightConstant2.getData().add(new XYChart.Data(image_distance, -image_height));

            graphArray[ZERO].getData().addAll(constantStraight2, straightConstant2);

            ((Node) (constantStraight2.nodeProperty().get())).setStyle(BLUE);
            ((Node) (straightConstant2.nodeProperty().get())).setStyle(ORANGE);

        } else {
            constantStraight.getData().add(new XYChart.Data(-object_distance, object_height));
            constantStraight.getData().add(new XYChart.Data(ZERO, object_height));
            constantStraight.getData().add(new XYChart.Data(image_distance, -image_height));

            straightConstant.getData().add(new XYChart.Data(-object_distance, object_height));
            straightConstant.getData().add(new XYChart.Data(ZERO, -image_height));
            straightConstant.getData().add(new XYChart.Data(image_distance, -image_height));
        }
        straightLine = new XYChart.Series();
        straightLine.getData().add(new XYChart.Data(-object_distance, object_height));
        straightLine.getData().add(new XYChart.Data(image_distance, -image_height));

        graphArray[ZERO].getData().addAll(straightLine, constantStraight, straightConstant);
        ((Node) (straightLine.nodeProperty().get())).setStyle(GREEN);
        ((Node) (constantStraight.nodeProperty().get())).setStyle(BLUE);
        ((Node) (straightConstant.nodeProperty().get())).setStyle(ORANGE);
    }

    public double calculateImageDistance(int objDist, int focalLength) {
        return (double) ONE / (((double) ONE / focal_length) - ((double) ONE / object_distance));
    }

    public double calculateImageHeight(int objDist, int objHeight, double imgDist) {
        return object_height * image_distance / object_distance;
    }

    public boolean chooseLens() {
        if ((int) sliderArray[THREE].getValue() == ONE) {
            return TRUE;
        } else {
            return FALSE;
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
                    object_distance = (int) sliderArray[ZERO].getValue();
                    focal_length = (int) sliderArray[ONE].getValue();
                    object_height = (int) sliderArray[TWO].getValue();
                    if (chooseLens()) {
                        focal_length = -focal_length;
                    }
                    image_distance = calculateImageDistance(object_distance, focal_length);
                    if (image_distance < ZERO) {
                        imgArrow1.setEndY(TEN);
                        imgArrow2.setEndY(TEN);
                    } else {
                        imgArrow1.setEndY(-TEN);
                        imgArrow2.setEndY(-TEN);
                    }
                    image_height = calculateImageHeight(object_distance, object_height, image_distance);
                    System.out.println("OBJD: " + object_distance + " OBJH: " + object_height + " IMGD: " + image_distance + " IMGH: " + image_height + " FOCL: " + focal_length);
                    animationPane(image);
                    image.setTranslateX(OBJ_START - (-image_distance * GROWY) * GROWX);
                    image.setTranslateY(FOUR - (-image_height + ((double) EIGHT / TEN)) * GROWY / TWO);
                    imgLine.setStartY(-(FOUR - (-image_height) * GROWY));
                    drawLightRays();
                    break;
                case "Done":
                    mainMenu.launchMenuScene();  //Click this button to terminate the animation, initialize variables with the default values, clear the screen and display the main menu again.
                    break;
                case "Pause":   //pause
                    break;
                case "Reset":   //reset
                    sliderArray[ZERO].setValue(ZERO);
                    sliderArray[ONE].setValue(ZERO);
                    sliderArray[TWO].setValue(ZERO);
                    grid.getChildren().remove(image);
                    graphArray[ZERO].getData().removeAll(straightLine, constantStraight, straightConstant);
                    break;
                case "Help":    //dialog box
                    helpDialog(RD_HELP);
                    break;
                case "Continue":   //resume from pause
                    break;
                default:
                    break;
            }
        }

    }
}


package menuPackage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import utilities.Constants;
import utilities.Graph;

public abstract class GeneralLayout extends Scene implements Constants {

    BorderPane mainPane;
    protected GridPane grid;

    private Menu exit;
    private MenuItem yes, no;
    private MenuBar menuBar;
    private int menu_counter;
    private ChoiceListener choice;

    private MainMenu mainMenu;
    protected VBox buttons, sliders;
    private GridPane graphs;

    public GeneralLayout(MainMenu menu, double width, double height) {
        super(new BorderPane(), width, height);

        mainMenu = menu;
        menuBar = new MenuBar();

        exit = new Menu(EXIT_MENU[ZERO]);

        yes = new MenuItem(EXIT_MENU[ONE]);
        no = new MenuItem(EXIT_MENU[TWO]);

        mainPane = (BorderPane) this.getRoot();

        choice = new ChoiceListener();

        for (int i = ZERO; i < TOPICS.length; i++) {
            Menu topic = new Menu(TOPICS[i]);
            menuBar.getMenus().addAll(topic);
            for (int j = menu_counter; j < (menu_counter + TWO); j++) {
                MenuItem sub_topic = new MenuItem(SUBTOPICS[j + ONE]);
                sub_topic.setOnAction(choice);
                topic.getItems().add(sub_topic);
            }
            menu_counter += TWO;
        }

        yes.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(ZERO);
            }
        });
        exit.getItems().addAll(yes, no);
        menuBar.getMenus().addAll(exit);

        createGrid();

        mainPane.setTop(menuBar);
        mainPane.setCenter(grid);

    }

    private void createGrid() {
        grid = new GridPane();

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(FIFTY);
        column.setHalignment(HPos.CENTER);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(FIFTY);
        row.setValignment(VPos.CENTER);

        for (int i = ZERO; i < TWO; ++i) {
            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }

        grid.setGridLinesVisible(true);
    }

    public VBox buttonPane(Button[] controlButtons) {
        buttons = new VBox(TEN + THREE);
        buttons.setAlignment(Pos.CENTER);

        for (Button controlButton : controlButtons) {
            controlButton.setPrefWidth(ONE_HUNDRED);
            buttons.getChildren().add(controlButton);
        }
        grid.add(buttons, ZERO, ZERO);
        return null;
    }

    public VBox sliderPane(Slider[] variableSliders, Label[] variableTitles) {
        sliders = new VBox(TEN);
        sliders.setAlignment(Pos.CENTER);
        NumberFormat intFormat = new DecimalFormat("#0");

        for (int i = ZERO; i < variableSliders.length; i++) {
            Label value = new Label(intFormat.format(variableSliders[i].getValue()));
            variableSliders[i].setShowTickMarks(true);
            variableSliders[i].setShowTickLabels(true);
            variableSliders[i].setMajorTickUnit(FIFTY);
            variableSliders[i].setMinorTickCount(NINE);

            variableSliders[i].valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    value.setText(intFormat.format((double) newValue));
                }
            });
            sliders.getChildren().addAll(variableTitles[i], value, variableSliders[i]);
        }
        grid.add(sliders, ONE, ZERO);
        grid.setMargin(sliders, new Insets(TEN, TEN, TEN, TEN));
        return null;
    }

    public void graphPane(Graph[] variableGraphs) {
        grid.getChildren().remove(graphs);
        graphs = new GridPane();
        graphs.setGridLinesVisible(true);

        for (int i = ZERO; i < variableGraphs.length; i++) {
            graphs.add(variableGraphs[i], i, ZERO);
        }
        if (variableGraphs[ZERO].getTitle().equals(RD_TITLE)) {
            grid.add(variableGraphs[ZERO], ZERO, ONE, TWO, ONE);
            grid.setGridLinesVisible(FALSE);
        } else {
            grid.add(graphs, ONE, ONE);
        }
    }

    public void animationPane(Node animation) {
        grid.add(animation, ZERO, ONE);
    }

    public void helpDialog(String helpMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(helpMessage);

        alert.showAndWait();
    }
    public abstract Button start();

    public abstract Button done();

    public abstract Button pause();

    public abstract Button reset();

    public abstract Button help();

    public abstract Button continueButton();

    public abstract void createSliders();

    public abstract void createGraph();

    private class ChoiceListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            MenuItem source = new MenuItem();
            if (event.getSource() instanceof MenuItem) {
                source = (MenuItem) event.getSource();
            }
            System.out.println(source.getText());
            switch (source.getText()) {
                case "Newton's Second Law":
                    mainMenu.launchNewtonsSecondLawScene();
                    break;
                case "Projectile Motion":
                    mainMenu.launchProjectileMotionScene();
                    break;
                case "Ray Diagram":
                    mainMenu.launchRayDiagramScene();
                    break;
                case "Simple Harmonic Motion":
                    mainMenu.launchSimpleHarmonicMotionScene();
                    break;
                case "Infinite Geometric Series":
                    mainMenu.launchInfiniteGeometricSeriesScene();
                    break;
                case "New Sports Bike":
                    mainMenu.launchNewSportsBikeScene();
                    break;
                default:
                    break;
            }
        }
    }
}

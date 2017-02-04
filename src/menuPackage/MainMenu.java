package menuPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import topicsPackage.InfiniteGeometricSeries;
import topicsPackage.NewSportsBike;
import topicsPackage.NewtonsSecondLaw;
import topicsPackage.ProjectileMotion;
import topicsPackage.RayDiagram;
import topicsPackage.SimpleHarmonicMotion;
import utilities.Constants;

public class MainMenu extends Application implements Constants {

    Stage copy_Stage;
    Scene menuScene;

    Menu topics, exit;

    MenuItem yes, no;

    MenuBar menu;

    int menu_counter;

    @Override
    public void start(Stage primaryStage) {
        menuScene = new MenuScene(this, STAGE_SIZE, STAGE_SIZE);
        copy_Stage = primaryStage;

        primaryStage.setTitle(SUBTOPICS[ZERO]);
        launchMenuScene();
        primaryStage.show();
    }

    public void launchMenuScene() {
        copy_Stage.setScene(new MenuScene(this, STAGE_SIZE, STAGE_SIZE));
        copy_Stage.setResizable(TRUE);
        copy_Stage.setTitle(SUBTOPICS[ZERO]);
    }

    public void launchNewtonsSecondLawScene() {
        copy_Stage.setScene(new NewtonsSecondLaw(this, STAGE_SIZE, STAGE_SIZE));
        copy_Stage.setTitle(SUBTOPICS[ONE]);
    }

    public void launchProjectileMotionScene() {
        copy_Stage.setScene(new ProjectileMotion(this, LARGE_SIZE, STAGE_SIZE));
        copy_Stage.setTitle(SUBTOPICS[TWO]);
    }

    public void launchRayDiagramScene() {
        copy_Stage.setScene(new RayDiagram(this, STAGE_SIZE, STAGE_SIZE));
        copy_Stage.setTitle(SUBTOPICS[THREE]);
    }

    public void launchSimpleHarmonicMotionScene() {
        copy_Stage.setScene(new SimpleHarmonicMotion(this, STAGE_SIZE, STAGE_SIZE));
        copy_Stage.setTitle(SUBTOPICS[FOUR]);
    }

    public void launchInfiniteGeometricSeriesScene() {
        copy_Stage.setScene(new InfiniteGeometricSeries(this, STAGE_SIZE, STAGE_SIZE));
        copy_Stage.setTitle(SUBTOPICS[FIVE]);
    }

    public void launchNewSportsBikeScene() {
        copy_Stage.setScene(new NewSportsBike(this, STAGE_SIZE, STAGE_SIZE));
        copy_Stage.setTitle(SUBTOPICS[SIX]);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

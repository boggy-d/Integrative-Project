package utilities;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public interface Constants {

    //Numerical Constants

    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int TWENTY = 20;
    public static final int FIFTY = 50;
    public static final int ONE_HUNDRED = 100;
    public static final int TWO_HUNDRED = 200;
    public static final int THREE_HUNDRED = 300;
    public static final double TRHEE_SIXTY = 360;
    public static final double STAGE_SIZE = 750;

    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    public static final String[] TOPICS = {"Mechanics", "Waves & Optics", "Calculus"};
    public static final String[] SUBTOPICS = {"Integrative Project", "Newton's Second Law", "Projectile Motion", "Ray Diagram",
        "Simple Harmonic Motion", "Infinite Geometric Series", "New Sports Bike"};
    public static final String[] EXIT_MENU = {"Exit", "Yes", "No"};
    public static final String[] SLIDER_VARIABLES = {"Force: ", "Mass: ", "Initial Velocity: ", "Angle: ", "Object Distance: ",
        "Focal Length: ", "Amplitude: ", "Period: ", "Phase Constant: ", "First Term: ",
        "Common Ratio (1/r): ", "Desired Units Sold (x10): ", "Frequency: ", "Object Height: ", "Type of Lens: "};

    public static final Button[] BUTTONS = {new Button("Start"), new Button("Done"), new Button("Pause"), new Button("Reset"),
        new Button("Help"), new Button("Continue")};

    //MenuScene Constants
    public static final String WELCOME_MESSAGE = "Welcome to the Integrative Project!";
    public static final String RARE = "res//edgymeme2.png";

    // Codes for the types of function for Graph class
    public static final byte NO_FUNCTION = ZERO;
    public static final byte LINEAR = ONE;
    public static final byte QUADRATIC = TWO;
    public static final byte SIN = THREE;
    public static final double DEF_PRECISION = 0.01;
    public static final double DEF_SPEED = 1.0;
    public static final double DEF_START = ZERO;
    public static final String GRAPH_STYLE = "-fx-stroke-width: 1px";
    public static final String GRAPH_ERROR = "The type of function selected is undefined";

    //Newton's Second Law
    public static final double TIMEFRAME = 0.016;
    public static final double STARTING_POINT = -STAGE_SIZE / FOUR + 63;
    public static final String COLISEUM = "res//coliseum.jpg";
    public static final String CHARIOT = "res//NSL.jpg";
    public static final String ACCEL_TITLE = "Acceleration vs. Time";
    public static final String VEL_TITLE = "Velocity vs. Time";
    public static final String NSL_HELP = " The Newton's Second Law application allow the use to simulate"
            + " the equation F= ma. By providing a force and a mass to the chariot, the object will start speeding up"
            + " and.  The velocity vs. time and acceleration vs. time graphs show the relation between the two.";

    // Projectile motion constants:
    public static final int LARGE_SIZE = 1500;
    public static final String ALT_TITLE = "Altitude vs. Time";
    public static final String VER_VEL_TITLE = "Vertical velocity vs. Time";
    public static final double VEL_MAX = FIFTY;
    public static final double ANGLE_MAX = 90;
    public static final String SEA_IMAGE = "res//sea.jpg";
    public static final double STARTING_X = 40;
    public static final double STARTING_Y = 310;
    public static final double GRAVITY = 9.81;
    public static final double START_VEL = 25;
    public static final double START_ANGLE = 45;
    public static final double P_TO_M_CONSTANT = 7.848;
    public static final double BOAT_LOWER_EDGE = 57.33;
    public static final double BOAT_HIGHER_EDGE = 65.08;
    public static final double SCREEN_EDGE = 90;
    public static final double CANON_BASE = 14;
    public static final String SHORT_TEXT = "too short";
    public static final String HIT_TEXT = "hit";
    public static final String FAR_TEXT = "too far";
    public static final String DOPE_TEXT = "wow there";
    public static final String PROJECTTILE_HELP = "This animation simulates a projectile motion.\nIt shoots stuff.";
    public static final Double[] CANON_POINTS = new Double[]{
        0.0, 0.0,
        60.0, 3.0,
        60.0, 25.0,
        0.0, 28.0};

    //Infinite Geometric Series Constants
    public static final String IGS_TITLE = "Series Result vs. Number of Iterations";
    public static final String BASEBALL_FIELD = "res//baseball_field.jpg";
    public static final double RESULT_PRECISION = 0.000000000000002;
    public static final int ONE_SECOND = 1000;
    public static final double PITCHER_DIST_X = STAGE_SIZE / FOUR - 55;
    public static final double PITCHER_DIST_Y = STAGE_SIZE / FOUR - 80;
    public static final double HITTER_DIST = -(STAGE_SIZE / FOUR) + 20;
    public static final double BALL_THROW = HITTER_DIST - PITCHER_DIST_X;
    public static final String CATCHING = "res//catching.png";
    public static final String THROWING_INI = "res//throwing_1.png";
    public static final String THROWING_FIN = "res//throwing_2.png";
    public static final String HITTING_INI = "res//hitting_1.png";
    public static final String HITTING_FIN = "res//hitting_2.png";
    public static final String BASEBALL = "res//baseball.png";
    public static final String OUT = "YOU'RE OUT!!!";
    public static final String HOMERUN = "HOMERUN!!!!";
    public static final String IGS_HELP = "The Infinite Geometric Series Application is a game where the"
            + " user chooses the first term and the common ratio for an infinite geometric series of the form"
            + " sum(firstTerm *(1/commonRatio)) from n= 0 to n=infinity. This function is then transformed into a baseball game"
            + " where the ball's trajectory follows the graph of the series. The user wins if he manages to get the ball over the catcher.";

    // Constants for simple harmonic motion
    public static final double STARTING_AMPLITUDE = FIVE;
    public static final double STARTING_FREQUENCY = ONE;
    public static final String A_VS_T_TITLE = "Distance vs. Time:";
    public static final String SHM_HELP = "This pendulum demonstates a simple harmonic motion\nEnjoy!";
    public static final String SPRING_FILE = "res//spring.png";
    public static final Rectangle LOAD = new Rectangle(FIFTY, FIFTY, Color.GREY);

    //Ray Diagram Constants:
    public static final String RD_TITLE = "Ray Diagram";
    public static final double OBJ_START = 201.5;
    public static final double GROWY = 14.05;
    public static final double GROWX = 2.475;
    public static final double LENS = 140;
    public static final String GREEN = "-fx-stroke: #228B22";
    public static final String BLUE = "-fx-stroke: #6495ED";
    public static final String ORANGE = "-fx-stroke: #FF8C00";
    public static final String RD_HELP = "The Ray Diagram application allows the user to simulate an object"
            + " being refracted through a converging or diverging lens. By providing the object distance and height and focal length,"
            + " the application will draw the three light rays necessary to finding the image along with the image itself using the formulas"
            + " image distance = 1/((1/focal length)-(1/object distance)) and image height = image distance * object height/object distance";

    // New Sportts Bike Constants
    public static final String PROFIT_TITLE = "Profit vs. Sale Price";
    public static final String SLIDER_NAME = "Price of bike";
    public static final int MAX_PRICE = 450;
    public static final double TOP_PROFIT = 3E6;
    public static final double MAX_PROFIT = 2180000;
    public static final double LOWEST_PROFIT = -1E6;
    public static final double PROFIT_TICK = 5E5;
    public static final String PROFIT_TEXT = "Current Profit = $ ";
    public static final String DECIMAL_FORMAT = "###,###,###.00";
    public static final String STORE_STRING = "BIKES! NEW! BIKES!";
    public static final String OPEN_STRING = "OPEN!";
    public static final String SIGN_FONT = "Britannic bold";
    public static final String LEXI_FILE = "res//lexi.gif";
    public static final double LEXI_HIGH_SCORE = 2100000;
    public static final double LEXI_SIZE = 200;
    public static final String BIKE_MESSAGE = "Hello, in this experiment you will find the\nthe profit that can be made if you\nset the right price";
}

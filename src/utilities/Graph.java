package utilities;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;



public class Graph extends LineChart<Number,Number> implements Constants
{
    // Number of steps per graph
    private double precision = DEF_PRECISION;
    
    private double speed = DEF_SPEED;
    
    private double startValue = DEF_START;    
    
    //Properties of the function to draw
    private double a = ZERO, b = ZERO, h = ZERO, k = ZERO;
    
    private byte functionType = (byte)ZERO;
    /*
    0 = no function
    1 = linear
    2 = quadratic
    3 = sin
    */
    
    // This object will keep track of the time
    private Timeline refresher;
    
    private XYChart.Series dataPoints = new XYChart.Series();
    
    
    // Last posistion of the next y to compute
    private double xCursor = startValue;
    
    private boolean isReseted = TRUE;
    private boolean isRunning = FALSE;
    
    
    public Graph(Axis<Number> xAxis, Axis<Number> yAxis) 
    {
        super(xAxis, yAxis);
        this.setCreateSymbols(FALSE);
        setLegendVisible(FALSE);
        getData().add(dataPoints);
        ((Node)(dataPoints.nodeProperty().get())).setStyle(GRAPH_STYLE);
    }
    
    
    public Graph(double xLower, double xUpper, double xTick, double yLower, double yUpper, double yTick)
    {
      this(new NumberAxis(xLower, xUpper, xTick), new NumberAxis(yLower, yUpper, yTick));
    }
    
    
    
    /**
     * This function will start the animation of the graph.
     * @return true if it is now running true if something went wrong or
     * the function to draw was nothing
     */
    public boolean start()
    {
        if (functionType == NO_FUNCTION)
            return false;
        
        if(isReseted)
        {
            xCursor = startValue;
            dataPoints = new XYChart.Series();
            getData().add(dataPoints);
            ((Node)(dataPoints.nodeProperty().get())).setStyle(GRAPH_STYLE);
        }
        
        isReseted = false;
        
        refresher = new Timeline(
                new KeyFrame(Duration.seconds(precision / speed),
                        new EventHandler<ActionEvent>()
                        {
                            @Override
                            public void handle(ActionEvent event) 
                            {
                                // Computation of the points 
                                double y = calculate(xCursor);
                                
                                dataPoints.getData()
                                        .add(new XYChart.Data(xCursor, y));
                                
                                xCursor += precision;                                
                            }   
                        },
                        new KeyValue(new SimpleBooleanProperty(TRUE), FALSE)));
        
        refresher.setCycleCount(Timeline.INDEFINITE);
        refresher.play();
        isRunning = TRUE;
        return TRUE;
    }
    
    /**
     * Can stop the drawing of the points on the graph.
     * Changes can than be made to the functions without harming the data.
     * The Animation can be directly restarted and is not reseted.
     * @return true if the drawing was running
     */
    public boolean pause()
    {
        if (!isRunning)
            return FALSE;
        
        refresher.stop();
        refresher = null;
        
        isRunning = FALSE;
        return TRUE;
    }
    
    /**
     * Deletes all data and puts cursor back at the starting point
     */
    public void reset()
    {
        pause();
        getData().clear();
        xCursor = startValue;
        isReseted = TRUE;
    }
    
    
    
    public double calculate(double x)
    {
        switch(functionType)
        {
            case LINEAR:
                return a*(b*(x-h))+k;
            case QUADRATIC:
                return a*Math.pow(b*(x-h), TWO) + k;
            case SIN:
                return a*Math.sin(b*(x-h)) + k;
            default:
                throw new IllegalStateException(GRAPH_ERROR);
        }
    }
    
    
    
    public double getPrecision()
    {
        return precision;
    }
    
    public void setPrecision(double nStepPerUnit)
    {
        precision = nStepPerUnit;
    }
    
    public double getStart()
    {
        return startValue;
    }
    
    public void setStart(double begining)
    {
        startValue = begining;
    }
    
    
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }
    
    public double getSpeed()
    {
        return speed;
    }
    
    /**
     * This method is used to change the type of the function.<br>
     * Here is a list of the different codes:<br>
     * <ul>
     * <li>0 = no function</li>
     * <li>1 = linear</li>
     * <li>2 = quadratic</li>
     * <li>3 = sin</li>
     * </ul>
     * @param type One of the specified integer
     */
    public void setFunctionType(int type)
    {
        if(type < ZERO || type > SIN)
            throw new IllegalArgumentException("You must enter a valid type");
        functionType = (byte)type;
    }
    
    public byte getFunctionType()
    {
        return functionType;
    }
    
    public void setParameters (double a, double b, double h, double k)
    {
        this.a = a;
        this.b = b;
        this.h = h;
        this.k = k;
    }
    
    public double[] getParameters()
    {
        double[] array = new double[FOUR];
        array[0] = a;
        array[1] = b;
        array[2] = h;
        array[3] = k;
        return array;
    }
    
    /**
     * Will fail if set is empty
     * @return the last data point that got calculated if any
     */
    public XYChart.Data getLastPoint()
    {
        if(dataPoints.getData().size() > ZERO)
            return (XYChart.Data)dataPoints.getData().get((dataPoints.getData().size())-ONE);
        return null;
    }
    
    // Tells if the graph is currently drawing points
    public boolean isRunning()
    {
        return isRunning;
    }
    
    public boolean isReseted()
    {
        return isReseted;
    }
    
    
    public void addPoint(double x, double y)
    {
        dataPoints.getData().add(new XYChart.Data(x, y));
    }
}

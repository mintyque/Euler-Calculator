package App;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import Equation.*;
import Euler.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

/**
 * Controller class of the app
 */
public class GUIController {

    /*
     * Various elements of JavaFX Scene that need to be controlled
     */
    public LineChart<Number, Number> chart;
    public LineChart<Number, Number> localErrorChart;
    public LineChart<Number, Number> globalErrorChart;

    public CheckBox eulerButton;
    public CheckBox heunButton;
    public CheckBox rungeButton;

    public TextField stepText;
    public TextField maxXText;
    public TextField initialXText;
    public TextField initialYText;
    public TextField initialNText;
    public TextField maxNText;
    public TextArea errorReport;

    /**
     * Method for adding data to a chart
     * @param dataList - ObservableList of XYChart.Series elements
     * @param data - ArrayList of Points that need to be added to a chart
     */
    private void addData(ObservableList dataList, ArrayList<Point> data){
        for (Point pointToAdd : data) {
            dataList.add(new XYChart.Data(pointToAdd.getX(), pointToAdd.getY()));
        }
    }

    /**
     * Method for checking correctness of input
     * Checks all fields to make them contain numbers, not chars;
     * Provides security from negative step value;
     * Sets boundaries for numerical values
     * @return false = input is correct, true = input is incorrect
     * @throws Exception - mostly NumberFormatException that has to be intercepted
     */
    private boolean check() throws Exception{
        boolean checker = false;
        String errors = new String();
        try{
            if(Double.parseDouble(stepText.getText()) <= 0){
                errors += "Step can't be negative or zero\n";
                checker = true;
            }
        } catch(Exception exception){
            errors += "Step has to be a number\n";
            checker = true;
        }
        try{
            if(Double.parseDouble(maxXText.getText()) < -100 || Double.parseDouble(maxXText.getText()) > 100) {
                errors += "Maximum X = (-100; 100)\n";
                checker = true;
            }
        } catch(Exception exception){
            errors += "Maximum X has to be a number\n";
            checker = true;
        }
        try{
            if(Double.parseDouble(initialXText.getText()) < -100 || Double.parseDouble(initialXText.getText()) > 100){
                errors += "Initial X = (-100;100)\n";
                checker = true;
            }
        } catch(Exception exception){
            errors += "Initial X has to be a number\n";
            checker = true;
        }
        try{
            if(Double.parseDouble(initialYText.getText()) < -100 || Double.parseDouble(initialYText.getText()) > 100){
                errors += ("Initial Y = (-100;100)\n");
                checker = true;
            }
        } catch(Exception exception){
            errors += "Initial Y has to be a number";
            checker = true;
        }
        if(checker){
            errorReport.setText(errors);
        }
        return checker;
    }

    /**
     * Additional checker for plotting global error, as globalErrorChart uses some fields
     * which are not used in other graphs
     * @return false = input is correct, true = input is incorrect
     * @throws Exception NumberFormatException
     */
    private boolean check2() throws Exception{
        boolean checker = false;
        String msg = new String();
        try{
            if(Double.parseDouble(initialNText.getText()) > Double.parseDouble(maxNText.getText())){
                checker = true;
                msg += "Minimum N should be less than maximum N\n";
            }
        } catch (Exception exception){
            checker = true;
            msg += "Minimum and maximum N should be numbers\n";
        }
        errorReport.setText(errorReport.getText() + msg);
        return checker;
    }

    /**
     * Simple method that is invoked when the button is pressed
     * @param actionEvent
     * @throws Exception
     */
    public void execute(ActionEvent actionEvent) throws Exception{
        if(!check()){
            plot();
        }
    }

    /**
     * Calculates local error and puts all data in a graph
     * @param exact - set of points of exact solution
     * @param approx - set of points of approximate solution
     * @param error - ObservableList of points of a graph
     */
    private void calculateError(ArrayList<Point> exact, ArrayList<Point> approx, ObservableList error){
        for(int i = 0; i < exact.size(); i++){
            double errorValue = Math.abs(exact.get(i).getY() - approx.get(i).getY());
            error.add(new XYChart.Data(exact.get(i).getX(), Math.abs(errorValue)));
        }
    }

    /**
     * Calculates local error and puts each value into an ArrayList
     * @param exact - set of points of exact solution
     * @param approx - set of points of approximate solution
     * @return ArrayList of Point with coordinates (x, local error value)
     */
    private ArrayList<Point> calculateError(ArrayList<Point> exact, ArrayList<Point> approx){
        ArrayList<Point> errorValues = new ArrayList<>();
        for(int i = 0; i < exact.size(); i++){
            double errorValue = Math.abs(exact.get(i).getY() - approx.get(i).getY());
            errorValues.add(new Point(exact.get(i).getX(), Math.abs(errorValue)));
        }
        return errorValues;
    }

    /**
     * Find the value of a maximum local error from provided ArrayList of Points of local error graph
     * @param errorValues
     * @return BigDecimal maximum error
     */
    private double findMaxError(ArrayList<Point> errorValues){
        double maxError = -1;
        for(Point point : errorValues){
            if(maxError < point.getY()){
                maxError = point.getY();
            }
        }
        return maxError;
    }

    /**
     * Method for plotting the graphs
     */
    private void plot() {
        //Clear all previous data
        chart.getData().clear();
        localErrorChart.getData().clear();

        //Get all constants from text fields
        double step = Double.parseDouble(stepText.getText());
        double maxX = Double.parseDouble(maxXText.getText());
        double initialX = Double.parseDouble(initialXText.getText());
        double initialY = Double.parseDouble(initialYText.getText());

        //Setting up the equation
        EquationInterfaceBD equation = new EquationBD();
        EulerCalculatorBD euler = new EulerCalculatorBD(initialX, initialY);
        euler.setStep(step);

        //defining a series for Euler method
        XYChart.Series exactGraph = new XYChart.Series();
        exactGraph.setName("Exact solution");

        //create a graph of exact solution
        addData(exactGraph.getData(), euler.exact(equation, maxX));
        chart.getData().add(exactGraph);

        //plot graph of Euler method and its error if it's selected
        if(eulerButton.isSelected()){
            XYChart.Series eulerGraph = new XYChart.Series();
            eulerGraph.setName("Euler method");
            addData(eulerGraph.getData(), euler.euler(equation, maxX));
            chart.getData().add(eulerGraph);

            XYChart.Series eulerError = new XYChart.Series();
            eulerError.setName("Euler method");
            calculateError(euler.exact(equation, maxX), euler.euler(equation, maxX), eulerError.getData());
            localErrorChart.getData().add(eulerError);
        }

        //plot graph of Heun method and its error if it's selected
        if(heunButton.isSelected()){
            XYChart.Series heunGraph = new XYChart.Series();
            heunGraph.setName("Heun method");
            addData(heunGraph.getData(), euler.heun(equation, maxX));
            chart.getData().add(heunGraph);

            XYChart.Series heunError = new XYChart.Series();
            heunError.setName("Heun method");
            calculateError(euler.exact(equation, maxX), euler.heun(equation, maxX), heunError.getData());
            localErrorChart.getData().add(heunError);
        }

        //plot graph of Runge Kutta method and its error if it's selected
        if(rungeButton.isSelected()){
            XYChart.Series rungeGraph = new XYChart.Series();
            rungeGraph.setName("Runge Kutta");
            addData(rungeGraph.getData(), euler.rungeKutta(equation, maxX));
            chart.getData().add(rungeGraph);

            XYChart.Series rungeError = new XYChart.Series();
            rungeError.setName("Runge Kutta");
            calculateError(euler.exact(equation, maxX), euler.rungeKutta(equation, maxX), rungeError.getData());
            localErrorChart.getData().add(rungeError);
        }
    }

    /**
     * Method that is invoked when user wishes to plot a graph of total error
     * @throws Exception NumberFormatException from check() and check2()
     */
    public void calculateGlobalError() throws Exception{
        if(!check() && !check2()){
            graphGlobalError();
        }
    }

    /**
     * Method for plotting global error graph
     */
    private void graphGlobalError(){
        //Clear all previous data
        globalErrorChart.getData().clear();

        //Get all constants from text fields
        double maxX = Double.parseDouble(maxXText.getText());
        double initialX = Double.parseDouble(initialXText.getText());
        double initialY = Double.parseDouble(initialYText.getText());
        int initialN = Integer.parseInt(initialNText.getText());
        int maxN = Integer.parseInt(maxNText.getText());

        //Setting up the equation
        EquationInterfaceBD equation = new EquationBD();
        EulerCalculatorBD euler = new EulerCalculatorBD(initialX, initialY);

        if(eulerButton.isSelected()){
            XYChart.Series eulerError = new XYChart.Series();
            eulerError.setName("Euler method");
            for(int step = initialN; step < maxN; step++){
                double newStep = (maxX - initialX) / step;
                euler.setStep(newStep);
                ArrayList<Point> exact = euler.exact(equation, maxX);
                ArrayList<Point> eulerMethod = euler.euler(equation, maxX);
                ArrayList<Point> errors = calculateError(exact, eulerMethod);
                eulerError.getData().add(new XYChart.Data(step, findMaxError(errors)));
            }
            globalErrorChart.getData().add(eulerError);
        }
        if(heunButton.isSelected()){
            XYChart.Series heunError = new XYChart.Series();
            heunError.setName("Heun method");
            for(int step = initialN; step < maxN; step++){
                double newStep = (maxX - initialX) / step;
                euler.setStep(newStep);
                ArrayList<Point> exact = euler.exact(equation, maxX);
                ArrayList<Point> heunMethod = euler.heun(equation, maxX);
                ArrayList<Point> errors = calculateError(exact, heunMethod);
                heunError.getData().add(new XYChart.Data(step, findMaxError(errors)));
            }
            globalErrorChart.getData().add(heunError);
        }
        if(rungeButton.isSelected()){
            XYChart.Series rungeError = new XYChart.Series();
            rungeError.setName("Runge Kutta");
            for(int step = initialN; step < maxN; step++){
                double newStep = (maxX - initialX) / step;
                euler.setStep(newStep);
                ArrayList<Point> exact = euler.exact(equation, maxX);
                ArrayList<Point> rungeMethod = euler.rungeKutta(equation, maxX);
                ArrayList<Point> errors = calculateError(exact, rungeMethod);
                rungeError.getData().add(new XYChart.Data(step, findMaxError(errors)));
            }
            globalErrorChart.getData().add(rungeError);
        }
    }
}

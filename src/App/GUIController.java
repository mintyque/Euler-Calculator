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

public class GUIController {
    /**
     * Controller class of the app
     */

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
            if(new BigDecimal(stepText.getText()).compareTo(new BigDecimal(0)) < 1){
                errors += "Step can't be negative\n";
                checker = true;
            }
        } catch(Exception exception){
            errors += "Step has to be a number\n";
            checker = true;
        }
        try{
            if(new BigDecimal(maxXText.getText()).compareTo(new BigDecimal("100")) > -1 || new BigDecimal(maxXText.getText()).compareTo(new BigDecimal("-100")) < 1) {
                errors += "Maximum X = (-100; 100)\n";
                checker = true;
            }
        } catch(Exception exception){
            errors += "Maximum X has to be a number\n";
            checker = true;
        }
        try{
            if(new BigDecimal(initialXText.getText()).compareTo(new BigDecimal("100")) > -1 || new BigDecimal(initialXText.getText()).compareTo(new BigDecimal("-100")) < 1){
                errors += "Initial X = (-100;100)\n";
                checker = true;
            }
        } catch(Exception exception){
            errors += "Initial X has to be a number\n";
            checker = true;
        }
        try{
            if(new BigDecimal(initialYText.getText()).compareTo(new BigDecimal("100")) > -1 || new BigDecimal(initialYText.getText()).compareTo(new BigDecimal("-100")) < 1){
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
            double errorValue = Math.abs(exact.get(i).getY().subtract(approx.get(i).getY()).doubleValue());
            error.add(new XYChart.Data(exact.get(i).getX(), Math.abs(errorValue)));
        }
    }

    /**
     * Method for plotting the graphs
     */
    private void plot() {
        //Clear all previous data
        chart.getData().clear();
        localErrorChart.getData().clear();

        MathContext prec = new MathContext(10);

        //Get all constants from text fields
        BigDecimal step = new BigDecimal(stepText.getText());
        BigDecimal maxX = new BigDecimal(maxXText.getText(), prec);
        BigDecimal initialX = new BigDecimal(initialXText.getText(), prec);
        BigDecimal initialY = new BigDecimal(initialYText.getText(), prec);

        //Setting up the equation
        EquationInterfaceBD equation = new EquationBD();
        EulerCalculatorBD euler = new EulerCalculatorBD(initialX, initialY, prec);
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
}

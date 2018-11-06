package App;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import Equation.*;
import Euler.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class GUIController {
    public LineChart<Number, Number> chart;

    public RadioButton eulerButton;
    public RadioButton heunButton;
    public RadioButton rungeButton;

    public TextField stepText;
    public TextField maxXText;
    public TextField initialXText;
    public TextField initialYText;

    private void addData(ObservableList dataList, ArrayList<Point> data){
        for (Point pointToAdd : data) {
            dataList.add(new XYChart.Data(pointToAdd.getX(), pointToAdd.getY()));
        }
    }

    private boolean check() throws Exception{
        boolean checker = false;
        try{
            if(new BigDecimal(stepText.getText()).compareTo(new BigDecimal(0)) < 1){
                stepText.setText("More, pls");
                checker = true;
            }
        } catch(Exception exception){
            stepText.setText("Number, pls");
            checker = true;
        }
        try{
            if(new BigDecimal(maxXText.getText()).compareTo(new BigDecimal("100")) > -1){
                maxXText.setText("Less, pls");
                checker = true;
            }
            else if(new BigDecimal(maxXText.getText()).compareTo(new BigDecimal("-100")) < 1){
                maxXText.setText("More, pls");
            }
        } catch(Exception exception){
            maxXText.setText("Number, pls");
            checker = true;
        }
        try{
            if(new BigDecimal(initialXText.getText()).compareTo(new BigDecimal("100")) > -1){
                initialXText.setText("Less, pls");
                checker = true;
            }
            else if(new BigDecimal(initialXText.getText()).compareTo(new BigDecimal("-100")) < 1){
                initialXText.setText("More, pls");
            }
        } catch(Exception exception){
            initialXText.setText("Number, pls");
            checker = true;
        }
        try{
            if(new BigDecimal(initialYText.getText()).compareTo(new BigDecimal("100")) > -1){
                initialYText.setText("Less, pls");
                checker = true;
            }
            else if(new BigDecimal(initialYText.getText()).compareTo(new BigDecimal("-100")) < 1){
                initialYText.setText("More, pls");
            }
        } catch(Exception exception){
            initialYText.setText("Number, pls");
            checker = true;
        }
        return checker;
    }

    public void execute(ActionEvent actionEvent) throws Exception{
        if(!check()){
            plot();
        }
    }

    private void plot() {
        //Clear all previous data
        chart.getData().clear();

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

        XYChart.Series eulerGraph = new XYChart.Series();
        eulerGraph.setName("Euler method");

        XYChart.Series heunGraph = new XYChart.Series();
        heunGraph.setName("Heun method");

        XYChart.Series rungeGraph = new XYChart.Series();
        rungeGraph.setName("Runge Kutta");


        addData(exactGraph.getData(), euler.exact(equation, maxX));
        chart.getData().add(exactGraph);

        if(eulerButton.isSelected()){
            addData(eulerGraph.getData(), euler.euler(equation, maxX));
            chart.getData().add(eulerGraph);
        }

        if(heunButton.isSelected()){
            addData(heunGraph.getData(), euler.heun(equation, maxX));
            chart.getData().add(heunGraph);
        }

        if(rungeButton.isSelected()){
            addData(rungeGraph.getData(), euler.rungeKutta(equation, maxX));
            chart.getData().add(rungeGraph);
        }
    }
}

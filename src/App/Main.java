package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("euler.fxml"));
        primaryStage.setTitle("DE Approximation");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    /*
    private void addData(ObservableList dataList, ArrayList<Point> data){
        for (Point pointToAdd : data) {
            dataList.add(new XYChart.Data(pointToAdd.getX(), pointToAdd.getY()));
        }
    }

    @Override public void start(Stage stage) {
        //Setting up the equation
        EquationInterfaceBD equation = new EquationBD();
        MathContext prec = new MathContext(10);
        EulerCalculatorBD euler = new EulerCalculatorBD(new BigDecimal(0, prec), new BigDecimal(1, prec), prec);

        stage.setTitle("Approximating a DE");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Your equation");

        //defining a series for Euler method
        XYChart.Series eulerGraph = new XYChart.Series();
        eulerGraph.setName("Euler method");

        XYChart.Series heunGraph = new XYChart.Series();
        heunGraph.setName("Heun method");

        XYChart.Series rungeGraph = new XYChart.Series();
        rungeGraph.setName("Runge Kutta");

        //populating the series with data
        addData(eulerGraph.getData(), euler.euler(equation, new BigDecimal("9.300000000")));
        addData(heunGraph.getData(), euler.heun(equation, new BigDecimal("9.300000000")));
        addData(rungeGraph.getData(), euler.rungeKutta(equation, new BigDecimal("9.300000000")));

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(eulerGraph);
        lineChart.getData().add(heunGraph);
        lineChart.getData().add(rungeGraph);
        scene.getStylesheets().add(getClass().getResource("linechart.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void main(String[] args) {
        EquationInterfaceBD equation = new EquationBD();
        MathContext prec = new MathContext(10);
        EulerCalculatorBD euler = new EulerCalculatorBD(new BigDecimal(0), new BigDecimal(1), prec);
        //euler.setStep(1.0);
        //euler.setStep(new BigDecimal("1", prec));
        System.out.println("Euler's method:");
        euler.euler(equation, new BigDecimal("9.000000000"));
        System.out.println("Improved Euler's method:");
        euler.heun(equation, new BigDecimal("9.000000000"));
        System.out.println("Runge Kutta:");
        euler.rungeKutta(equation, new BigDecimal("9.000000000"));
    }
    */
}

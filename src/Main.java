import Equation.*;
import Euler.*;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        EquationInterface equation = new Equation();
        EulerCalculator euler = new EulerCalculator(0.0, 1.0);
        //euler.setStep(1.0);
        System.out.println("Euler's method:");
        euler.euler(equation, 9.0);
        System.out.println("Improved Euler's method:");
        euler.heun(equation, 9.0);
        System.out.println("Runge Kutta:");
        euler.rungeKutta(equation, 9.0);
    }
}

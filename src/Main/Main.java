package Main;

import Equation.*;
import Euler.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;

public class Main {

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
}

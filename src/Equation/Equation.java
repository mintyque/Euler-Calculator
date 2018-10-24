package Equation;

import java.math.BigDecimal;

/**
 * Equation given in task
 */
public class Equation implements EquationInterface {

    /**
     * Method for computing an equation
     * @param x - x0
     * @param y - y0
     * @return evaluating e^(-sinx) - y * cosx
     */
    public double compute(double x, double y){
        return (Math.exp(-Math.sin(x)) - y * Math.cos(x));
    }
}

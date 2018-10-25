package Equation;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Equation given in task. Separate class for testing BigDecimal consistency
 */
public class EquationBD implements EquationInterfaceBD {

    private MathContext prec = new MathContext(5);

    /**
     * Method for computing an equation
     * @param x - x0
     * @param y - y0
     * @return evaluating e^(-sinx) - y * cosx
     */
    public BigDecimal compute(BigDecimal x, BigDecimal y){
        double x1 = x.doubleValue();
        double y1 = x.doubleValue();
        return new BigDecimal(Math.exp(-Math.sin(x1)) - y1 * Math.cos(x1), prec);
    }
}
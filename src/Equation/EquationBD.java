package Equation;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Equation given in task. Separate class for testing BigDecimal consistency
 */
public class EquationBD implements EquationInterfaceBD {

    private MathContext prec = new MathContext(10);

    private BigDecimal constant;

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

    public void setC(BigDecimal x, BigDecimal y){
        double x1 = x.doubleValue();
        double y1 = y.doubleValue();
        constant = new BigDecimal(y1/Math.exp(-Math.sin(x1)) - x1);
    }

    public BigDecimal exact(BigDecimal x) {
        double x1 = x.doubleValue();
        return new BigDecimal((x1 + constant.doubleValue()) * Math.exp(-Math.sin(x1)));
    }
}
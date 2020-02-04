package Equation;


/**
 * Equation given in task
 */
public class EquationBD implements EquationInterfaceBD {

    private double constant;

    /**
     * Method for computing an equation
     * @param x - x0
     * @param y - y0
     * @return evaluating e^(-sinx) - y * cosx
     */
    public double compute(double x, double y){
        return Math.exp(-Math.sin(x)) - y * Math.cos(x);
    }

    /**
     * Method for computing a coefficient to provide an exact solution of an IVP
     * @param x - x0
     * @param y - y0
     */
    public void setC(double x, double y){
        constant = y/Math.exp(-Math.sin(x)) - x;
    }

    /**
     * Method for computing an exact solution of an IVP
     * @param x - value of X
     * @return BigDecimal representation of Y coordinate of a point
     */
    public double exact(double x) {
        return (x + constant) * Math.exp(-Math.sin(x));
    }
}
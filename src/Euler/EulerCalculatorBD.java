package Euler;

import Equation.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

/**
 * Calculator implementing Euler, Heun and Runge Kutta methods of approximation of a DE solution
 */
public class EulerCalculatorBD {

    // Step for Euler's method. 0.1 by default
    private BigDecimal step = new BigDecimal(0.1);

    /**
     * Simple setter for step that will be used for computing.
     *
     * @param step - new step value
     */
    public void setStep(BigDecimal step) {
        this.step = step;
    }


    // Initial values for Euler's method
    private BigDecimal initialX;
    private BigDecimal initialY;

    private BigDecimal two = new BigDecimal(2);
    private BigDecimal six = new BigDecimal(6);
    private MathContext prec;

    /**
     * Constructor for EulerCalculatorBD with initial values
     *
     * @param x - initial x
     * @param y - initial y
     */
    public EulerCalculatorBD(BigDecimal x, BigDecimal y, MathContext prec) {
        this.initialX = x;
        this.initialY = y;
        this.prec = prec;
    }

    /**
     * Provide an exact solution to DE
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     * @return ArrayList of Points of the graph
     */
    public ArrayList<Point> exact(EquationInterfaceBD equation, BigDecimal finalX){
        BigDecimal x = this.initialX;
        BigDecimal y = this.initialY;
        ArrayList<Point> toReturn = new ArrayList<>();
        equation.setC(x, y);

        while(x.compareTo(finalX) < 1){
            toReturn.add(new Point(x, equation.exact(x)));
            x = x.add(step, prec);
        }

        return toReturn;
    }

    /**
     * Approximate the solution of a first order DE on range [x0, finalX] with Euler's method as follows:
     * y1 = y0 + step * f(x0, y0)
     *
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     * @return ArrayList of Points of the graph
     */
    public ArrayList<Point> euler(EquationInterfaceBD equation, BigDecimal finalX){
        BigDecimal x = this.initialX;
        BigDecimal y = this.initialY;

        ArrayList<Point> toReturn = new ArrayList<>();

        while(x.compareTo(finalX) < 1){
            toReturn.add(new Point(x, y));
            y = y.add(step.multiply(equation.compute(x, y)), prec);
            x = x.add(step, prec);
        }

        return toReturn;
    }

    /**
     * Approximate the solution of a first order DE on range [x0, finalX] with improved Euler's method (Heun's method),
     * using the following formula:
     * intY = y0 + step * f(x0, y0)
     * y1 = y0 + (f(x0, y0) + f(x0, intY))/2
     *
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     * @return ArrayList of Points of the graph
     */
    public ArrayList<Point> heun(EquationInterfaceBD equation, BigDecimal finalX){
        BigDecimal x = this.initialX;
        BigDecimal y = this.initialY;

        ArrayList<Point> toReturn = new ArrayList<>();

        while(x.compareTo(finalX) < 1){
            toReturn.add(new Point(x, y));
            //yI = y + step * equation(x, y)
            //y = y + (equation(x, y) + equation(x + step, yI))*step/2
            BigDecimal intermediateY = y.add(step.multiply(equation.compute(x, y)), prec);
            y = y.add(equation.compute(x, y).add(equation.compute(x.add(step, prec), intermediateY)).divide(two, prec).multiply(step, prec), prec);
            x = x.add(step, prec);

        }

        return toReturn;
    }

    /**
     * Approximate the solution of a first order DE on range [x0, finalX] with Runge Kutta method.
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     * @return ArrayList of Points of the graph
     */
    public ArrayList<Point> rungeKutta(EquationInterfaceBD equation, BigDecimal finalX){
        BigDecimal x = this.initialX;
        BigDecimal y = this.initialY;

        // Intermediate steps of algorithm
        BigDecimal k1, k2, k3, k4;

        ArrayList<Point> toReturn = new ArrayList<>();

        while(x.compareTo(finalX) < 1){
            toReturn.add(new Point(x, y));
            k1 = step.multiply(equation.compute(x, y), prec);
            k2 = step.multiply(equation.compute(x.add(step.divide(two, prec), prec), y.add(k1.divide(two, prec), prec)), prec);
            k3 = step.multiply(equation.compute(x.add(step.divide(two, prec)), y.add(k2.divide(two, prec), prec)), prec);
            k4 = step.multiply(equation.compute(x.add(step, prec), y.add(k3, prec)), prec);
            y = y.add(k1.add(k2.add(k3, prec).multiply(two, prec)).add(k4, prec).divide(six, prec), prec);
            x = x.add(step, prec);
        }
        return toReturn;
    }
}

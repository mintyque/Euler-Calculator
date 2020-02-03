package Euler;

import Equation.*;

import java.util.ArrayList;

/**
 * Calculator implementing Euler, Heun and Runge Kutta methods of approximation of a DE solution
 */
public class EulerCalculatorBD {

    // Step for Euler's method. 0.1 by default
    private double step = 0.1;

    /**
     * Simple setter for step that will be used for computing.
     *
     * @param step - new step value
     */
    public void setStep(double step) {
        this.step = step;
    }


    // Initial values for Euler's method
    private double initialX;
    private double initialY;

    /**
     * Constructor for EulerCalculatorBD with initial values
     *
     * @param x - initial x
     * @param y - initial y
     */
    public EulerCalculatorBD(double x, double y) {
        this.initialX = x;
        this.initialY = y;
    }

    /**
     * Provide an exact solution to DE
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     * @return ArrayList of Points of the graph
     */
    public ArrayList<Point> exact(EquationInterfaceBD equation, double finalX){
        double x = this.initialX;
        double y = this.initialY;
        ArrayList<Point> toReturn = new ArrayList<>();
        equation.setC(x, y);

        while(x <= finalX){
            toReturn.add(new Point(x, equation.exact(x)));
            x += step;
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
    public ArrayList<Point> euler(EquationInterfaceBD equation, double finalX){
        double x = this.initialX;
        double y = this.initialY;

        ArrayList<Point> toReturn = new ArrayList<>();

        while(x <= finalX){
            toReturn.add(new Point(x, y));
            y = y + step * equation.compute(x, y);
            x += step;
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
    public ArrayList<Point> heun(EquationInterfaceBD equation, double finalX){
        double x = this.initialX;
        double y = this.initialY;

        ArrayList<Point> toReturn = new ArrayList<>();

        while(x <= finalX){
            toReturn.add(new Point(x, y));
            //yI = y + step * equation(x, y)
            //y = y + (equation(x, y) + equation(x + step, yI))*step/2
            double intermediateY = y + step * equation.compute(x, y);
            y = y + ((equation.compute(x, y)) + equation.compute(x + step, intermediateY)) * step / 2;
            x += step;
        }

        return toReturn;
    }

    /**
     * Approximate the solution of a first order DE on range [x0, finalX] with Runge Kutta method.
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     * @return ArrayList of Points of the graph
     */
    public ArrayList<Point> rungeKutta(EquationInterfaceBD equation, double finalX){
        double x = this.initialX;
        double y = this.initialY;

        // Intermediate steps of algorithm
        double k1, k2, k3, k4;

        ArrayList<Point> toReturn = new ArrayList<>();

        while(x <= finalX){
            toReturn.add(new Point(x, y));
            k1 = step * equation.compute(x, y);
            k2 = step * equation.compute(x + step/2, y + k1/2);
            k3 = step * equation.compute(x + step/2, y + k2/2);
            k4 = step * equation.compute(x + step, y + k3/2);
            y = y + (k1 + 2*k2 + 2*k3 + k4)/6;
            x += step;
        }
        return toReturn;
    }
}

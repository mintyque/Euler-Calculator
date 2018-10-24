package Euler;

import Equation.*;

public class EulerCalculator {

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
     * Constructor for EulerCalculator with initial values
     *
     * @param x - initial x
     * @param y - initial y
     */
    public EulerCalculator(double x, double y) {
        this.initialX = x;
        this.initialY = y;
    }

    /**
     * Approximate the solution of a first order DE on range [x0, finalX] with Euler's method as follows:
     * y1 = y0 + step * f(x0, y0)
     *
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     */
    public void euler(EquationInterface equation, double finalX){
        double x = this.initialX;
        double y = this.initialY;

        while(x <= finalX){
            System.out.println("" + x + " " + y);
            y += step * equation.compute(x, y);
            x += step;
        }
    }

    /**
     * Approximate the solution of a first order DE on range [x0, finalX] with improved Euler's method (Heun's method),
     * using the following formula:
     * intY = y0 + step * f(x0, y0)
     * y1 = y0 + (f(x0, y0) + f(x0, intY))/2
     *
     * @param equation - equation we need to solve
     * @param finalX - closes the range of X
     */
    public void heun(EquationInterface equation, double finalX){
        double x = this.initialX;
        double y = this.initialY;
        while(x <= finalX){
            System.out.println("" + x + " " + y);
            double intermediateY = y + step * equation.compute(x, y);
            y = y + (equation.compute(x,y) + equation.compute(x, intermediateY))/2;
            x += step;
        }
    }

    /**
     * Approximate the solution of a first order DE on range [x0, finalX] with Runge Kutta method.
     *
     */
    public void rungeKutta(EquationInterface equation, double finalX){
        double x = this.initialX;
        double y = this.initialY;

        // Intermediate steps of algorithm
        double k1, k2, k3, k4;

        while(x <= finalX){
            System.out.println("" + x + " " + y);
            k1 = step * equation.compute(x, y);
            k2 = step * equation.compute(x + step/2.0, y + k1/2);
            k3 = step * equation.compute(x + step/2.0, y + k2/2);
            k4 = step * equation.compute(x + step, y + k3);
            y = y + (k1 + 2.0 * (k2 + k3) + k4)/6.0;
            x += step;
        }
    }
}

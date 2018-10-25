package Equation;

/**
 * Interface for creating equations to plug into Euler's method using double precision floating point args
 */
public interface EquationInterface {
    double compute(double x, double y);
}

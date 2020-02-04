package Equation;

import java.math.BigDecimal;

/**
 * Interface for creating equations to plug into Euler's method using BigDecimal args
 */
public interface EquationInterfaceBD {
    double compute(double x, double y);

    double exact(double x);

    void setC(double x, double y);
}

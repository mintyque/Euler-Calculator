package Equation;

import java.math.BigDecimal;

/**
 * Interface for creating equations to plug into Euler's method using BigDecimal args
 */
public interface EquationInterfaceBD {
    BigDecimal compute(BigDecimal x, BigDecimal y);

    BigDecimal exact(BigDecimal x);

    void setC(BigDecimal x, BigDecimal y);
}

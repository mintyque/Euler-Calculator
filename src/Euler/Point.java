package Euler;

import java.math.BigDecimal;

/**
 * Simple class to hold coordinates of a point for the purpose of data encapsulation.
 */
public class Point {
    private BigDecimal x;
    private BigDecimal y;

    public Point(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }
}

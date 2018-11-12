package Euler;

import java.math.BigDecimal;

public class Point {

    /**
     * Simple class to hold coordinates of a point for the purpose of data encapsulation.
     */
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

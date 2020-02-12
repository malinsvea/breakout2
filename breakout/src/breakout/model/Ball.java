package breakout.model;

import java.util.Random;

/*
 *    A Ball for the Breakout game
 */

public class Ball extends MovableRectangle {

    public static final double radius = 5;

    public Ball(double x, double y) {
        super(x, y);
    }

    @Override
    public double getWidth() {
        return radius*2;
    }

    @Override
    public double getHeight() {
        return radius*2;
    }

}

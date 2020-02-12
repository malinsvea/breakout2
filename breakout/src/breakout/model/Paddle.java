package breakout.model;

/*
 * A Paddle for the Breakout game
 *
 */
public class Paddle extends MovableRectangle {

    public static final double PADDLE_WIDTH = 75;
    public static final double PADDLE_HEIGHT = 20;
    public static final double PADDLE_SPEED = 5;

    public Paddle(double x, double y) {
        super(x, y);
    }

    @Override
    public double getWidth() {
        return PADDLE_WIDTH;
    }

    @Override
    public double getHeight() {
        return PADDLE_HEIGHT;
    }
}

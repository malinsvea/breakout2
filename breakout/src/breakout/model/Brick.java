package breakout.model;

/*
 *   A brick for the rows of bricks
 */

public class Brick extends Rectangle {

    public static final double BRICK_WIDTH = 50;
    public static final double BRICK_HEIGHT = 20;
    private int points;

    public Brick(double x, double y, int points) {
        super(x, y);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public double getWidth() {
        return BRICK_WIDTH;
    }

    @Override
    public double getHeight() {
        return BRICK_HEIGHT;
    }
}


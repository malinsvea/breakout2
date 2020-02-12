package breakout.model;

import org.w3c.dom.css.Rect;
import static breakout.model.Breakout.GAME_HEIGHT;

abstract class Rectangle implements IPositionable {

    protected double x;
    protected double y;

    public Rectangle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    public boolean hit(Rectangle rect) {
        double x1 = rect.getX();
        double y1 = rect.getY();
        double w1 = rect.getWidth();
        double h1 = rect.getHeight();

        double x2 = this.getX();
        double y2 = this.getY();
        double w2 = this.getWidth();
        double h2 = this.getHeight();

        boolean far_right = x2 > x1 + w1;
        boolean far_left = x2 + w2 < x1;
        boolean too_high = y2 + h2 < y1;
        boolean too_low = y2 > y1 + h1;

        if (far_left || far_right || too_high || too_low) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLeft(Rectangle rectangle) {
        return this.getX() < rectangle.getX();
    }

    public boolean isRight(Rectangle rectangle) {
        return this.getX() + this.getWidth() > rectangle.getX();
    }

    public boolean isAbove(Rectangle rectangle) {
        return this.getY() < rectangle.getY();
    }

    public boolean isUnder(Rectangle rectangle) {
        return this.getY() + this.getHeight() > GAME_HEIGHT;
    }
}

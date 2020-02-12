package breakout.model;
import org.w3c.dom.css.Rect;

import java.util.Random;
import static breakout.model.Breakout.BALL_SPEED_FACTOR;

abstract class MovableRectangle extends Rectangle {

    private enum HitDirection {
        FROM_BELOW, FROM_ABOVE, FROM_LEFT, FROM_RIGHT;
    }

    private double velocity;
    private double dx;
    private double dy;

    public MovableRectangle(double x, double y) {
        super(x, y);
        this.velocity = BALL_SPEED_FACTOR;

        Random rnd = new Random();
        dx = (rnd.nextDouble() - 0.5);
        dy = - Math.abs(rnd.nextDouble() - 0.5);

        dy = Math.min(dy, -0.5);
        double norm = Math.sqrt( Math.pow(this.dx, 2) + Math.pow(this.dy, 2) );
        this.dx = (dx/norm)*velocity;
        this.dy = (dy/norm)*velocity;
    }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

    public double getDx() {
        return this.dx;
    }

    public double getDy() {
        return this.dy;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public void move() {
        double x = this.getX();
        double y = this.getY();
        double dx = this.getDx();
        double dy = this.getDy();
        double v = this.getVelocity();

        this.x = x + (dx * v);
        this.y = y + (dy * v);
    }

    public void changeDirection(Rectangle hittedItem) {
        HitDirection hit_direction = this.getHitDirection(hittedItem);
        if ( (hit_direction == HitDirection.FROM_BELOW) || (hit_direction == HitDirection.FROM_ABOVE) ) {
            this.dy = - this.dy;
        } else {
            this.dx = - this.dx;
        }
    }

    private HitDirection getHitDirection(Rectangle hittedItem) {

        boolean moving_right = this.dx > 0;
        boolean moving_down = this.dy > 0;

        if ( (this.getX() + this.getWidth() == hittedItem.getX()) && (moving_right) ) {
            return HitDirection.FROM_LEFT;
        } else if ( (this.getX() == hittedItem.getX() + hittedItem.getWidth()) && (!moving_right) ) {
            return HitDirection.FROM_RIGHT;
        } else if ( (this.getY() + this.getHeight() == hittedItem.getY()) && (moving_down) ) {
            return HitDirection.FROM_ABOVE;
        } else {
            return HitDirection.FROM_BELOW;
        }
    }

    public void adjustIllegalPosition(Paddle paddle) {
        if (this.getY() < paddle.getY()) {
            // from above
            this.setY(paddle.getY() - this.getHeight());
        } else if (this.getX() < paddle.getX()) {
            // from left
            this.setX(paddle.getX() - this.getWidth());
        } else if (this.getX() + this.getWidth() > paddle.getX() + paddle.getWidth()){
            this.setX(paddle.getX() + paddle.getWidth());
        } else if (this.getY() < paddle.getY() + paddle.getHeight()) {
            this.setY(paddle.getY());
        }
    }
}

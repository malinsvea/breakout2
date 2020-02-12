package breakout.model;
import breakout.event.ModelEvent;
import breakout.event.EventBus;
import breakout.view.BreakoutGUI;
import javafx.beans.binding.StringBinding;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static breakout.model.Ball.radius;
import static breakout.model.Brick.BRICK_WIDTH;
import static breakout.model.Brick.BRICK_HEIGHT;
import static breakout.model.Brick.BRICK_HEIGHT;
import static breakout.model.Paddle.*;

/*
 *  Overall all logic for the Breakout Game
 *  Model class representing the full game
 *  This class should use other objects delegate work.
 *
 *  NOTE: Nothing visual here
 *
 */
public class Breakout {

    public static final double GAME_WIDTH = 400;
    public static final double GAME_HEIGHT = 400;
    public static final double BALL_SPEED_FACTOR = 1.8; // Increase ball speed
    public static final long SEC = 1_000_000_000;  // Nano seconds used by JavaFX

    private int ballsLeft = 5;
    int points;

    private Ball ball;
    private Paddle paddle;
    private List<Wall> walls;
    private List<Brick> bricks;
    private Direction paddleDirection;

    public enum Direction{
        LEFT, RIGHT, NONE
    }

    public void setPaddleDirection(Direction paddleDirection) {
        this.paddleDirection = paddleDirection;
    }

    public Breakout(List<Wall> walls, List<Brick> bricks) {

        // Starts ball in middle of gamle
        double x_ball = GAME_WIDTH/2 - radius;
        double y_ball = GAME_HEIGHT/2 - radius;
        this.ball = new Ball(x_ball, y_ball);

        double x_paddle = GAME_WIDTH/2 - PADDLE_WIDTH/2;
        double y_paddle = GAME_HEIGHT - PADDLE_HEIGHT - 20;
        this.paddle = new Paddle(x_paddle, y_paddle);
        this.paddleDirection = Direction.NONE;

        this.walls = walls;
        this.bricks = bricks;

    }

    // --------  Game Logic -------------

    private long timeForLastHit;         // To avoid multiple collisions

    public void update(long now) {

        // TODO  Main game loop, start functional decomposition from here

            ball.move();
            checkIllegalPosition(ball);
            checkBallTooLow(ball);
            checkOnBrick(ball);

            Rectangle hitted_item = checkIfBallHitBrickAndRemoveIt();

            if (hitted_item instanceof Paddle) {
                ball.adjustIllegalPosition(paddle);
            }
            if (hitted_item != null) {
                ball.changeDirection(hitted_item);
            }
            movePaddle();

        }

    /*private void checkOnPaddle(Ball ball) {
        Paddle paddle = this.paddle;
        if (ball.hit(paddle)){
            ball.adjustIllegalPosition(paddle);
        }
    }*/

    private void checkBallTooLow (Ball ball){
            if (ball.getY() > GAME_HEIGHT) {
                this.ballsLeft = this.ballsLeft - 1;
                double x_ball = this.GAME_WIDTH / 2 - radius;
                double y_ball = this.GAME_HEIGHT / 2 - radius;
                this.ball = new Ball(x_ball, y_ball);
            }
        }

        private Rectangle checkIfBallHitBrickAndRemoveIt () {

            for (Brick brick : bricks) {
                if (ball.hit(brick)) {
                    EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_BRICK));  // Send to all registered
                    if (!hasLowerPointBricks(bricks, brick)){
                        bricks.remove(brick);
                        points = points + brick.getPoints();
                    }
                    return brick;
                }
            }
            for (Wall wall : walls) {
                if (ball.hit(wall)) {
                    return wall;
                }
            }
            if (ball.hit(paddle)) {
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BALL_HIT_PADDLE));  // Send to all registered
                return paddle;
            }

            return null;
        }

    private boolean hasLowerPointBricks(List<Brick> bricks, Brick brick) {
        for (Brick otherBrick : bricks){
            if (otherBrick.getPoints() < brick.getPoints()){
                return true;
            }
        }
        return false;
    }

    // ----- Helper methods--------------

        // Used for functional decomposition

        public void checkIllegalPosition (Ball ball){
            this.checkWithinWalls(ball);
        }

        public void checkOnBrick (Ball ball){
            for (Brick brick : this.bricks) {
                if (ball.hit(brick)) {
                    ball.setY(brick.getY() + brick.getHeight());
                }
            }
        }


        public void checkWithinWalls (Ball ball){
            Wall left_wall = this.walls.get(0);
            Wall top_wall = this.walls.get(1);
            Wall right_wall = this.walls.get(2);

            if (ball.isLeft(left_wall)) {
                ball.setX(left_wall.getX());
            } else if (ball.isRight(right_wall)) {
                ball.setX(right_wall.getX() - ball.getWidth());
            } else if (ball.isAbove(top_wall)) {
                ball.setY(top_wall.getY());
            }
        }

        // --- Used by GUI  ------------------------

        public List<IPositionable> getPositionables () {
            List<IPositionable> positionables = new ArrayList<IPositionable>(2 + this.bricks.size() + this.walls.size());
            positionables.add(this.ball);
            positionables.add(this.paddle);
            positionables.addAll(this.bricks);
            return positionables;
        }

        public int getPoints () {
            return points;
        }

        public int getBallsLeft () {
                return ballsLeft;
        }

        public void movePaddle(){
            double paddle_speed = PADDLE_SPEED;
            double x = this.paddle.getX();
            if (this.paddleDirection == Direction.LEFT) {
                x = Math.max(0, x - paddle_speed);
                this.paddle.setX(x);
            } else if (this.paddleDirection == Direction.RIGHT) {
                double w = this.paddle.getWidth();
                x = Math.min(GAME_WIDTH - w, x + paddle_speed);
                this.paddle.setX(x);
            }
        }

}

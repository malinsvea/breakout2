package breakout;

import breakout.model.Ball;
import breakout.model.Brick;
import breakout.model.Paddle;
import breakout.model.Wall;

import static breakout.model.Ball.radius;
import static breakout.model.Paddle.PADDLE_WIDTH;
import static java.lang.System.out;
import static breakout.model.Breakout.GAME_HEIGHT;
import static breakout.model.Breakout.GAME_WIDTH;
import breakout.model.Wall.Dir;
import static breakout.model.Brick.BRICK_WIDTH;

/**
 * Here you should write your tests
 *
 * Right click and run ...
 */
public class Test {

    public static void main(String[] args) {
        new Test().test();
    }

    void test() {
        testBallHitBrick();
        testPaddle();
        testBallHitPaddleCrash();
        testBallHitPaddleNonCrash();
        testWall();
        testBallHitLeftWall();
        testBallNotHitWall();

    }

    private void testBallHitBrick() {

        out.println("Tests ball hitting brick");
        // puts ball ontop of brick
        Ball b = new Ball(4,5 ); // Create object (just an example)
        Brick br = new Brick(4, 5, 1 );
        boolean hit = b.hit(br);
        out.println(hit == true);

        // puts ball to the right
        Ball b2 = new Ball(BRICK_WIDTH + 1,0 ); // Create object (just an example)
        Brick br2 = new Brick(0, 0, 2 );
        boolean hit2 = b2.hit(br2);
        out.println(hit2 == false);

        Ball b3 = new Ball(20,30 ); // Create object (just an example)
        Brick br3 = new Brick(0, 0, 3 );
        boolean hit3 = b3.hit(br3);
        out.println(hit3 == false);

        Ball b4 = new Ball(0,10 ); // Create object (just an example)
        Brick br4 = new Brick(0, 0 , 4);
        boolean hit4 = b4.hit(br4);
        out.println(hit4 == true);
    }

    private void testPaddle() {
        out.println("Testing paddle");
        Paddle p = new Paddle(6, 7);
        out.println(p.getX() == 6);
    }

    private void testBallHitPaddleCrash() {
        double x1 = 0;
        double y1 = 0;

        double w1 = PADDLE_WIDTH;
        double w2 = radius*2;

        double h2 = radius*2;

        double x2 = x1 + w1 - w2;
        double y2 = y1 - h2;

        Paddle paddle = new Paddle(x1, y1);
        Ball ball = new Ball(x2, y2);

        out.println("Ball should hit paddle");
        out.println(ball.hit(paddle) == true);

    }

    private void testBallHitPaddleNonCrash() {
        double x1 = 0;
        double y1 = 0;

        double w1 = PADDLE_WIDTH;
        double w2 = radius*2;

        double h2 = radius*2;

        double x2 = x1 + w1 +1;
        double y2 = y1;

        Paddle paddle = new Paddle(x1, y1);
        Ball ball = new Ball(x2, y2);

        out.println("Ball should not hit paddle");
        out.println(ball.hit(paddle) == false);

    }

    private void testBallHitLeftWall() {
        double x1 = 0;
        double y1 = 0;

        Wall wall = new Wall(x1, y1, Dir.VERTICAL);
        double w1 = wall.getWidth();

        double x2 = x1 + w1;
        double y2 = y1;

        Ball ball = new Ball(x2, y2);

        out.println("Test if ball hits left wall");
        out.println(ball.hit(wall) == true);
    }

    public void testBallNotHitWall() {

        double x1 = 0;
        double y1 = 0;

        Wall wall = new Wall(x1, y1, Dir.HORIZONTAL);
        double w1 = wall.getWidth();

        double x2 = x1 + w1 + 1;
        double y2 = y1;

        Ball ball = new Ball(x2, y2);

        out.println("Test if ball not hits left wall");
        out.println(ball.hit(wall) == false);

    }

    private void testWall () {
        out.println("Test wall");
        Wall wall = new Wall(0, 0, Dir.VERTICAL);
        out.println(wall.getWidth() == 0);
        out.println(wall.getHeight() == GAME_HEIGHT);

        Wall wall2 = new Wall(0, 0, Dir.HORIZONTAL);
        out.println(wall2.getWidth() == GAME_WIDTH);
        out.println(wall2.getHeight() == 0);
    }


}

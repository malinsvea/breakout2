package breakout.model;

import static breakout.model.Breakout.GAME_WIDTH;
import static breakout.model.Breakout.GAME_HEIGHT;

public class Wall extends Rectangle {

    public enum Dir {
        HORIZONTAL, VERTICAL;
    }
    private Dir direction;


    public Wall(double x, double y, Dir direction) {
        super(x, y);
        this.direction = direction;
    }

    @Override
    public double getWidth() {
        if (direction == Dir.VERTICAL) {
            return 0;
        } else if (direction == Dir.HORIZONTAL) {
            return GAME_WIDTH;
        } else {
            throw new IllegalStateException("Wall has undefined direction");
        }
    }

    @Override
    public double getHeight() {
        if (direction == Dir.VERTICAL) {
            return GAME_HEIGHT;
        } else if (direction == Dir.HORIZONTAL) {
            return 0;
        } else {
            throw new IllegalStateException("Wall has undefined direction");
        }
    }

    /*public enum Dir {
        HORIZONTAL, VERTICAL;
    }*/

}

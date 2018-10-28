package tnr.fbsg_android.Generator;

import android.graphics.Color;

public class Knot
{
    public enum KnotDirection
    {
        LEFT,
        RIGHT,
        LEFT_ANGLE,
        RIGHT_ANGLE,
        LEFT_EMPTY,
        RIGHT_EMPTY
    }

    private KnotDirection direction;
    private int colour;

    Knot()
    {
        this.direction = KnotDirection.LEFT;
    }

    Knot( KnotDirection direction)
    {
        this.direction = direction;
    }

    public void changeDirection()
    {
        switch (direction)
        {
            case LEFT: direction = KnotDirection.RIGHT;
                break;
            case RIGHT: direction = KnotDirection.LEFT_ANGLE;
                break;
            case LEFT_ANGLE: direction = KnotDirection.RIGHT_ANGLE;
                break;
            case RIGHT_ANGLE: direction = KnotDirection.LEFT;
                break;
            default: break;
        }
    }

    public KnotDirection getDirection() {
        return direction;
    }

    public void setDirection(KnotDirection direction) {
        this.direction = direction;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }
}

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
    private int firstUp;
    private int secondUp;
    private int firstDown;
    private int secondDown;

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

    public int getFirstUp() {
        return firstUp;
    }

    public void setFirstUp(int firstUp) {
        this.firstUp = firstUp;
    }

    public int getSecondUp() {
        return secondUp;
    }

    public void setSecondUp(int secondUp) {
        this.secondUp = secondUp;
    }

    public int getFirstDown() {
        return firstDown;
    }

    public void setFirstDown(int firstDown) {
        this.firstDown = firstDown;
    }

    public int getSecondDown() {
        return secondDown;
    }

    public void setSecondDown(int secondDown) {
        this.secondDown = secondDown;
    }
}

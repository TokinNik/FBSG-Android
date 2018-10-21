package tnr.fbsg_android.Generator;

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
    private Colour colour;

    Knot()
    {
        this.direction = KnotDirection.LEFT;
    }

    Knot( KnotDirection direction)
    {
        this.direction = direction;
    }

    public KnotDirection getDirection() {
        return direction;
    }

    public void setDirection(KnotDirection direction) {
        this.direction = direction;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
}

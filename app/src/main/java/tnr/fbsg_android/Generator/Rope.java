package tnr.fbsg_android.Generator;

public class Rope
{
    private final int id;
    private Colour colour;

    Rope (int id)
    {
        this.id = id;
        this.colour = Colour.WHITE;
    }
    Rope (int id, Colour colour)
    {
        this.id = id;
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour)
    {
        this.colour = colour;
    }

    public int getId() {
        return id;
    }
}

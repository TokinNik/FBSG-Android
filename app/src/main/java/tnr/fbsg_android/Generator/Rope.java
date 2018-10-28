package tnr.fbsg_android.Generator;

import android.graphics.Color;

public class Rope
{
    private final int id;
    private int colour;

    Rope (int id)
    {
        this.id = id;
        this.colour = Color.rgb(255, 255, 255);
    }
    Rope (int id, int colour)
    {
        this.id = id;
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour)
    {
        this.colour = colour;
    }

    public int getId() {
        return id;
    }
}

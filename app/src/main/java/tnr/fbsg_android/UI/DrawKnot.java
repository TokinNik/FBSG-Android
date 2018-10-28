package tnr.fbsg_android.UI;


import android.graphics.Color;

import tnr.fbsg_android.Generator.Knot;

public class DrawKnot
{
    private Knot knot;
    private int rowId;
    private int knotId;
    private float x;
    private float y;


    DrawKnot (Knot knot, int rowId, int knotId)
    {
        this.knot = knot;
        this.rowId = rowId;
        this.knotId = knotId;
    }


    public Knot getKnot() {
        return knot;
    }

    public void setKnot(Knot knot) {
        this.knot = knot;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getKnotId() {
        return knotId;
    }

    public void setKnotId(int knotId) {
        this.knotId = knotId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

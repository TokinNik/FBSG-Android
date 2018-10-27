package tnr.fbsg_android.UI;

import android.graphics.Bitmap;

import tnr.fbsg_android.Generator.Knot;

public class ImageKnot
{
    private Knot knot;
    private Bitmap image;
    private int rowId;
    private int knotId;

    ImageKnot(Knot knot, Bitmap image, int rowId, int knotId)
    {
        this.knot = knot;
        this.image = image;
        this.rowId = rowId;
        this.knotId = knotId;
    }


    public Knot getKnot() {
        return knot;
    }

    public void setKnot(Knot knot) {
        this.knot = knot;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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

}

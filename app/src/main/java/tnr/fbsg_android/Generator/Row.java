package tnr.fbsg_android.Generator;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class Row
{
    public enum RowType
    {
        FULL,
        NOT_FULL,
        OPEN_LEFT,
        OPEN_RIGHT
    }

    private final String TAG = "Row";
    private RowType type;
    private ArrayList<Knot> knots = new ArrayList<>();
    private ArrayList<Integer> ropesUp = new ArrayList<>();
    private ArrayList<Integer> ropesDown = new ArrayList<>();
    private int id;
    private final Scheme currentScheme;

    Row(int id, ArrayList<Integer> ropesUp, ArrayList<Knot> knots, ArrayList<Integer> ropesDown, Scheme scheme, RowType type)
    {
        this.id = id;
        this.knots = knots;
        this.ropesUp = ropesUp;
        this.ropesDown = ropesDown;
        this.type = type;
        this.currentScheme = scheme;
    }

    Row(int id, ArrayList<Integer> ropesUp, ArrayList<Knot> knots,  RowType type, Scheme scheme)
{
    this.id = id;
    this.knots = knots;
    this.ropesUp = ropesUp;
    this.ropesDown.addAll(ropesUp);
    this.type = type;
    this.currentScheme = scheme;
    makeRow(currentScheme);
}

    public void makeRow(@Nullable Scheme scheme )
    {
        int i = 0;
        for (Knot k:knots)
        {
            if (k.getDirection() == Knot.KnotDirection.LEFT_EMPTY)
            {
                ropesDown.set(i,ropesUp.get(i));
                k.setColour(currentScheme.getRopeUp().get(ropesUp.get(i)).getColour());
                k.setFirstUp(k.getColour());
                k.setSecondUp(k.getColour());
                k.setFirstDown(k.getColour());
                k.setSecondDown(k.getColour());
                i++;
                continue;
            }
            if (i + 1 == ropesUp.size() || k.getDirection() == Knot.KnotDirection.RIGHT_EMPTY)
            {
                ropesDown.set(i,ropesUp.get(i));
                k.setColour(currentScheme.getRopeUp().get(ropesUp.get(i)).getColour());
                k.setFirstUp(k.getColour());
                k.setSecondUp(k.getColour());
                k.setFirstDown(k.getColour());
                k.setSecondDown(k.getColour());
                break;
            }
            if (k.getDirection() == Knot.KnotDirection.RIGHT || k.getDirection() == Knot.KnotDirection.LEFT)
            {
                if (k.getDirection() == Knot.KnotDirection.LEFT)
                {
                    k.setColour(currentScheme.getRopeUp().get(ropesUp.get(i+1)).getColour());
                    k.setFirstUp(currentScheme.getRopeUp().get(ropesUp.get(i)).getColour());
                    k.setSecondUp(k.getColour());
                    k.setFirstDown(k.getColour());
                    k.setSecondDown(k.getFirstUp());
                }
                else
                {
                    k.setColour(currentScheme.getRopeUp().get(ropesUp.get(i)).getColour());
                    k.setFirstUp(k.getColour());
                    k.setSecondUp((currentScheme.getRopeUp().get(ropesUp.get(i+1)).getColour()));
                    k.setFirstDown(k.getSecondUp());
                    k.setSecondDown(k.getColour());
                }

                int buf = ropesUp.get(i);
                ropesDown.set(i, ropesUp.get(i+1));
                ropesDown.set(i+1, buf);
            }
            else
            {
                if (k.getDirection() == Knot.KnotDirection.LEFT_ANGLE)
                {
                    k.setColour(currentScheme.getRopeUp().get(ropesUp.get(i)).getColour());
                    k.setFirstUp(k.getColour());
                    k.setSecondUp(currentScheme.getRopeUp().get(ropesUp.get(i+1)).getColour());
                    k.setFirstDown(k.getColour());
                    k.setSecondDown(k.getSecondUp());
                }
                else if (k.getDirection() == Knot.KnotDirection.RIGHT_ANGLE)
                {
                    k.setColour(currentScheme.getRopeUp().get(ropesUp.get(i+1)).getColour());
                    k.setFirstUp(currentScheme.getRopeUp().get(ropesUp.get(i)).getColour());
                    k.setSecondUp(k.getColour());
                    k.setFirstDown(k.getFirstUp());
                    k.setSecondDown(k.getColour());
                }

                ropesDown.set(i,ropesUp.get(i));
                ropesDown.set(i+1,ropesUp.get(i+1));
            }
            if (i + 2 < ropesUp.size())
                i += 2;
        }
    }

    public RowType getType() {
        return type;
    }

    public void setType(RowType type) {
        this.type = type;
    }

    public ArrayList<Knot> getKnots() {
        return knots;
    }

    public void setKnots(ArrayList<Knot> knots) {
        this.knots = knots;
    }

    public ArrayList<Integer> getRopesUp() {
        return ropesUp;
    }

    public void setRopesUp(ArrayList<Integer> ropesUp) {
        this.ropesUp.clear();
        this.ropesUp.addAll(ropesUp);
    }

    public ArrayList<Integer> getRopesDown() {
        return ropesDown;
    }

    public void setRopesDown(ArrayList<Integer> ropesDown) {
        this.ropesDown.clear();
        this.ropesDown.addAll(ropesDown);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

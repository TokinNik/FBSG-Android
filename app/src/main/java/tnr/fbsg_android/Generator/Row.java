package tnr.fbsg_android.Generator;

import java.util.ArrayList;

public class Row
{
    static enum RowType
    {
        FULL,
        NOT_FULL,
        OPEN_LEFT,
        OPEN_RIGHT
    }

    private RowType type;
    private ArrayList<Knot> knots = new ArrayList<>();
    private ArrayList<Integer> ropesUp = new ArrayList<>();
    private ArrayList<Integer> ropesDown = new ArrayList<>();
    private int id;

    Row(int id, ArrayList<Integer> ropesUp, ArrayList<Knot> knots, ArrayList<Integer> ropesDown,  RowType type)
    {
        this.id = id;
        this.knots = knots;
        this.ropesUp = ropesUp;
        this.ropesDown = ropesDown;
        this.type = type;
    }

    Row(int id, ArrayList<Integer> ropesUp, ArrayList<Knot> knots,  RowType type)
{
    this.id = id;
    this.knots = knots;
    this.ropesUp = ropesUp;
    this.ropesDown.addAll(ropesUp);
    this.type = type;
    makeRow();
}

    public void makeRow()
    {
        int i = 0;
        for (Knot k:knots)
        {
            if (k.getDirection() == Knot.KnotDirection.LEFT_EMPTY)
            {
                ropesDown.set(i,ropesUp.get(i));
                i++;
                continue;
            }
            if (i + 1 == ropesUp.size())
            {
                ropesDown.set(i,ropesUp.get(i));
                break;
            }
            if (k.getDirection() == Knot.KnotDirection.RIGHT || k.getDirection() == Knot.KnotDirection.LEFT)
            {
                int buf = ropesUp.get(i);
                ropesDown.set(i, ropesUp.get(i+1));
                ropesDown.set(i+1, buf);
            }
            else
            {
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

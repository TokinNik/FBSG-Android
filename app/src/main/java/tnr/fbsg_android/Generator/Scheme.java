package tnr.fbsg_android.Generator;

import java.util.ArrayList;

public class Scheme
{
    private ArrayList<Rope> ropeUp = new ArrayList<>();
    private ArrayList<Row> rows = new ArrayList<>();
    private ArrayList<Rope> ropeDown = new ArrayList<>();
    private int id;
    private String name;

    public Scheme(int id)
    {
        this.name = "Name";
        this.id = id;
        ropeUp.add(new Rope(0));
        ropeUp.add(new Rope(1));
        ropeUp.add(new Rope(2));
        ropeUp.add(new Rope(3));
        ropeUp.add(new Rope(4));
        ropeUp.add(new Rope(5));
        ArrayList<Knot> knots = new ArrayList<>();
        knots.add(new Knot());
        knots.add(new Knot());
        knots.add(new Knot());
        ArrayList<Integer> ropesUp = new ArrayList<>();
        ropesUp.add(0);
        ropesUp.add(1);
        ropesUp.add(2);
        ropesUp.add(3);
        ropesUp.add(4);
        ropesUp.add(5);
        rows.add(new Row(0, ropesUp, knots, Row.RowType.FULL, this));
        ArrayList<Knot> knots1 = new ArrayList<>();
        knots1.add(new Knot(Knot.KnotDirection.LEFT_EMPTY));
        knots1.add(new Knot());
        knots1.add(new Knot());
        knots1.add(new Knot(Knot.KnotDirection.RIGHT_EMPTY));
        rows.add(new Row(1, rows.get(0).getRopesDown(), knots1, Row.RowType.NOT_FULL, this));
        ropeDown.add(ropeUp.get(1));
        ropeDown.add(ropeUp.get(3));
        ropeDown.add(ropeUp.get(0));
        ropeDown.add(ropeUp.get(5));
        ropeDown.add(ropeUp.get(2));
        ropeDown.add(ropeUp.get(4));
    }

    public Scheme(int id, int rowCount, int ropeCount, ArrayList<Rope> ropeUp, Scheme oldScheme)
    {
        this.name = "Name";
        this.id = id;
        this.ropeUp = ropeUp;
        int i = 0;
        for (Row row: oldScheme.getRows())
        {
            ArrayList<Knot> knots = new ArrayList<>();
            for (Knot k: row.getKnots())
            {
                knots.add(new Knot(k.getDirection()));
            }

            rows.add(new Row(i, row.getRopesUp(), knots, row.getType(), this));
        }
        for (int j: rows.get(rowCount-1).getRopesDown())
        {
            if (j >= ropeUp.size())//TODO  K
                continue;
            ropeDown.add(ropeUp.get(j));
        }
    }

    public Scheme(int id, int rowCount, int ropeCount, ArrayList<Rope> ropeUp)
    {
        this.name = "Name";
        this.id = id;
        this.ropeUp = ropeUp;
        ArrayList<Integer> ropes = new ArrayList<>();
        for (int i = 0; i < ropeCount; i++)
        {
            ropes.add(i);
        }

        for (int i = 0; i < rowCount; i++)
        {
            ArrayList<Knot> knots = new ArrayList<>();
            if (ropeCount%2 == 0)
            {
                if (i%2 == 1)
                {
                    for (int j = 0; j < ropeCount; j++)
                    {
                        if (j == 0)
                        {
                            knots.add(new Knot(Knot.KnotDirection.LEFT_EMPTY));
                        }
                        else if (j == ropeCount-1)
                        {
                            knots.add(new Knot(Knot.KnotDirection.RIGHT_EMPTY));
                        }
                        else
                        {
                            knots.add(new Knot());
                            j++;
                        }
                    }
                    rows.add(new Row(i, ropes, knots, Row.RowType.NOT_FULL, this));
                }
                else
                {
                    for (int j = 0; j < ropeCount; j += 2)
                    {
                        knots.add(new Knot());
                    }
                    rows.add(new Row(i, ropes, knots, Row.RowType.FULL, this));
                }
            }
            else
            {
                if (i%2 == 1)
                {
                    for (int j = 0; j < ropeCount; j++)
                    {
                        if (j == 0)
                        {
                            knots.add(new Knot(Knot.KnotDirection.LEFT_EMPTY));
                        }
                        else
                        {
                            knots.add(new Knot());
                            j++;
                        }
                    }
                    rows.add(new Row(i, ropes, knots, Row.RowType.OPEN_LEFT, this));
                }
                else
                {
                    for (int j = 0; j < ropeCount; j++)
                    {
                        if (j == ropeCount-1)
                        {
                            knots.add(new Knot(Knot.KnotDirection.RIGHT_EMPTY));
                        }
                        else
                        {
                            knots.add(new Knot());
                        }
                    }
                    rows.add(new Row(i, ropes, knots, Row.RowType.OPEN_RIGHT, this));
                }
            }

            ropes.clear();
            ropes = (ArrayList<Integer>) rows.get(i).getRopesDown().clone();
        }

        for (int i: rows.get(rowCount-1).getRopesDown())
        {
            ropeDown.add(ropeUp.get(i));
        }

    }

    public Scheme(int id, ArrayList<Rope> ropeUp, ArrayList<Row> rows, ArrayList<Rope> ropeDown)
    {
        this.name = "Name";
        this.id = id;
        this.ropeUp = ropeUp;
        this.rows = rows;
        this.ropeDown = ropeDown;
    }

    public void makeScheme()
    {
        ArrayList<Integer> ropes = new ArrayList<>();
        for (Rope rope: ropeUp)
        {
            ropes.add(rope.getId());
        }
        for (Row row: rows)
        {
            row.setRopesUp(ropes);
            row.makeRow();
            ropes.clear();
            ropes.addAll(row.getRopesDown());
        }
    }


    public ArrayList<Rope> getRopeUp() {
        return ropeUp;
    }

    public void setRopeUp(ArrayList<Rope> ropeUp) {
        this.ropeUp = ropeUp;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }

    public ArrayList<Rope> getRopeDown() {
        return ropeDown;
    }

    public void setRopeDown(ArrayList<Rope> ropeDown) {
        this.ropeDown = ropeDown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

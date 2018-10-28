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

    public Scheme(int id, ArrayList<Rope> ropeUp, ArrayList<Row> rows, ArrayList<Rope> ropeDown)
    {
        this.id = id;
        this.ropeUp = ropeUp;
        this.rows = rows;
        this.ropeDown = ropeDown;
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

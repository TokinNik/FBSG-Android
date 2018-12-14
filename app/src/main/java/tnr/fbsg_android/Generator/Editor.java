package tnr.fbsg_android.Generator;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class Editor
{
    public static final String TAG = "EDITOR";
    private Scheme scheme;
    private boolean isChanged = false;

    public Editor(Scheme scheme)
    {
        this.scheme = scheme;
    }

    public void setScheme(Scheme scheme)
    {
        if (isChanged)
        {
            System.out.println("Scheme data not save!");
        }
        this.scheme = scheme;
    }

    public Scheme getScheme()
    {return scheme;}

    public void addRope()
    {
        isChanged = true;
        int preSize = scheme.getRopeUp().size()-1;
        Rope newRope = new Rope(preSize+1);
        scheme.getRopeUp().add(newRope);
        scheme.getRopeDown().add(newRope);
        boolean first = true;
        int i = 0;
        for (Row row :scheme.getRows())
        {
            switch (row.getType())
            {
                case FULL:
                    row.setType(Row.RowType.OPEN_RIGHT);
                    row.getKnots().add(new Knot(Knot.KnotDirection.RIGHT_EMPTY));
                    break;
                case NOT_FULL:
                    row.setType(Row.RowType.OPEN_LEFT);
                    row.getKnots().get(row.getKnots().size()-1).setDirection(Knot.KnotDirection.LEFT);
                    break;
                case OPEN_RIGHT:
                    row.setType(Row.RowType.FULL);
                    row.getKnots().get(row.getKnots().size()-1).setDirection(Knot.KnotDirection.LEFT);
                    break;
                case OPEN_LEFT:
                    row.setType(Row.RowType.NOT_FULL);
                    row.getKnots().add(new Knot(Knot.KnotDirection.RIGHT_EMPTY));
                    break;
            }
            ArrayList<Integer> ropesUp = new ArrayList<>();
            if (first)
            {
                first = false;
                ropesUp.addAll(scheme.getRows().get(0).getRopesUp());
                ropesUp.add(newRope.getId());
            }
            else
            {
                ropesUp.clear();
                ropesUp.addAll(scheme.getRows().get(i-1).getRopesDown());
            }
            row.setRopesUp(ropesUp);
            row.setRopesDown(ropesUp);
            row.makeRow();
            i++;
        }
        ArrayList<Rope> buf1 = scheme.getRopeUp();
        ArrayList<Rope> buf = new ArrayList<>();
        ArrayList<Integer> buf3 = scheme.getRows().get(scheme.getRows().size()-1).getRopesDown();
        for (i = 0; i < buf1.size(); i++)
        {
             buf.add(buf1.get(buf3.get(i)));
        }
        scheme.getRopeDown().clear();
        scheme.getRopeDown().addAll(buf);
    }

    public boolean decRope()
    {
        isChanged = true;
        int preSize = scheme.getRopeUp().size()-1;
        if (preSize < 4)
        {
            System.out.println("!!! 4 Ropes is min to scheme !!!");
            return false;
        }
        scheme.getRopeUp().remove(preSize);
        scheme.getRopeDown().remove(preSize);
        boolean first = true;
        int i = 0;
        for (Row row :scheme.getRows())
        {
            switch (row.getType())
            {
                case FULL:
                    row.setType(Row.RowType.OPEN_RIGHT);
                    row.getKnots().get(row.getKnots().size()-1).setDirection(Knot.KnotDirection.RIGHT_EMPTY);
                    break;
                case NOT_FULL:
                    row.setType(Row.RowType.OPEN_LEFT);
                    row.getKnots().remove(row.getKnots().size()-1);
                    break;
                case OPEN_RIGHT:
                    row.setType(Row.RowType.FULL);
                    row.getKnots().remove(row.getKnots().size()-1);
                    break;
                case OPEN_LEFT:
                    row.setType(Row.RowType.NOT_FULL);
                    row.getKnots().get(row.getKnots().size()-1).setDirection(Knot.KnotDirection.RIGHT_EMPTY);
                    break;
            }
            ArrayList<Integer> ropesUp = new ArrayList<>();
            if (first)
            {
                first = false;
                ropesUp.addAll(scheme.getRows().get(0).getRopesUp());
            }
            else
            {
                ropesUp.clear();
                ropesUp.addAll(scheme.getRows().get(i-1).getRopesDown());
            }
            row.setRopesUp(ropesUp);
            row.setRopesDown(ropesUp);
            row.makeRow();
            i++;
        }
        ArrayList<Rope> buf1 = scheme.getRopeUp();
        ArrayList<Rope> buf = new ArrayList<>();
        ArrayList<Integer> buf3 = scheme.getRows().get(scheme.getRows().size()-1).getRopesDown();
        for (i = 0; i < buf1.size(); i++)
        {
            buf.add(buf1.get(buf3.get(i)));
        }
        scheme.getRopeDown().clear();
        scheme.getRopeDown().addAll(buf);
        return true;
    }

    public void addRow()
    {
        isChanged = true;
        boolean fullRow = scheme.getRows().get(0).getType() == Row.RowType.FULL;
        int rowSize = scheme.getRows().size();
        int knotSize = scheme.getRows().get(0).getKnots().size();

        ArrayList<Knot> knots = new ArrayList<>();
        for (int i = 0; i < knotSize; i++)
        {
            if ( i + 1 < knotSize)
                knots.add(new Knot());
            else
                knots.add(new Knot(fullRow ? Knot.KnotDirection.LEFT : Knot.KnotDirection.RIGHT_EMPTY));
        }
        ArrayList<Integer> ropesUp = new ArrayList<>(scheme.getRows().get(rowSize-1).getRopesDown());
        scheme.getRows().add(new Row(rowSize, ropesUp, knots, fullRow ? Row.RowType.FULL : Row.RowType.OPEN_RIGHT, scheme));

        ArrayList<Knot> knots1 = new ArrayList<>();
        knots1.add(new Knot(Knot.KnotDirection.LEFT_EMPTY));
        knotSize = scheme.getRows().get(1).getKnots().size();
        for (int i = 1; i < knotSize; i++)
        {
            if ( i + 1 < knotSize)
                knots1.add(new Knot());
            else
                knots1.add(new Knot(fullRow ? Knot.KnotDirection.RIGHT_EMPTY: Knot.KnotDirection.LEFT));
        }
        scheme.getRows().add(new Row(rowSize+1, scheme.getRows().get(rowSize).getRopesDown(), knots1, fullRow ? Row.RowType.OPEN_RIGHT : Row.RowType.FULL, scheme));

        ArrayList<Rope> buf1 = scheme.getRopeUp();
        ArrayList<Rope> buf = new ArrayList<>();
        ArrayList<Integer> buf3 = scheme.getRows().get(rowSize + 1).getRopesDown();
        for (int i = 0; i < buf1.size(); i++)
        {
            buf.add(buf1.get(buf3.get(i)));
        }
        scheme.getRopeDown().clear();
        scheme.getRopeDown().addAll(buf);

    }

    public boolean decRow()
    {
        isChanged = true;
        int rowSize = scheme.getRows().size()-1;
        if (rowSize < 2)
        {
            System.out.println("!!! 2 Rows is min to scheme !!!");
            return false;
        }
        scheme.getRows().remove(rowSize);
        rowSize --;
        scheme.getRows().remove(rowSize);
        rowSize--;

        ArrayList<Rope> buf1 = scheme.getRopeUp();
        ArrayList<Rope> buf = new ArrayList<>();
        ArrayList<Integer> buf3 = scheme.getRows().get(rowSize).getRopesDown();
        for (int i = 0; i < buf1.size(); i++)
        {
            buf.add(buf1.get(buf3.get(i)));
        }
        scheme.getRopeDown().clear();
        scheme.getRopeDown().addAll(buf);
        return true;
    }

    public void changeRopeColor(int ropeId, int newColour)
    {
        scheme.getRopeUp().get(ropeId).setColour(newColour);
        reBuild();
    }

    public void changeKnotDirection(Knot knot, @Nullable Knot.KnotDirection newDirection)
    {
        if (newDirection != null)
            knot.setDirection(newDirection);
        else
            knot.changeDirection();

        reBuild();

        ArrayList<Rope> buf1 = scheme.getRopeUp();
        ArrayList<Rope> buf = new ArrayList<>();
        ArrayList<Integer> buf3 = scheme.getRows().get(scheme.getRows().size()-1).getRopesDown();
        for (int i = 0; i < buf1.size(); i++)
        {
            buf.add(buf1.get(buf3.get(i)));
        }
        scheme.getRopeDown().clear();
        scheme.getRopeDown().addAll(buf);
    }

    private void reBuild()
    {
        boolean first = true;
        int i = 0;
        for (Row row: scheme.getRows())
        {
            ArrayList<Integer> ropesUp = new ArrayList<>();
            if (first)
            {
                first = false;
                ropesUp.addAll(scheme.getRows().get(0).getRopesUp());
            }
            else
            {
                ropesUp.clear();
                ropesUp.addAll(scheme.getRows().get(i-1).getRopesDown());
            }
            row.setRopesUp(ropesUp);
            row.setRopesDown(ropesUp);
            row.makeRow();
            i++;
        }
    }
}

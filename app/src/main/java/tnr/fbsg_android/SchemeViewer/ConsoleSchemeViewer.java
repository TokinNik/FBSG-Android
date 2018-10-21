package tnr.fbsg_android.SchemeViewer;

import java.util.ArrayList;

import tnr.fbsg_android.Generator.Knot;
import tnr.fbsg_android.Generator.Rope;
import tnr.fbsg_android.Generator.Row;
import tnr.fbsg_android.Generator.Scheme;

public class ConsoleSchemeViewer
{
    private Scheme scheme;

    public ConsoleSchemeViewer(Scheme scheme)
    {
        this.scheme = scheme;
    }

    public void testScheme()
    {
        System.out.println("==|| Test Scheme #0 NoName ||==");
        System.out.println();
        System.out.println("0|1|2|3|4|5");
        System.out.println(" /   /   /      [1");
        System.out.println("1|0|3|2|5|4");
        System.out.println("/  /   /   \\0    [2");
        System.out.println("1|3|0|5|2|4");
        System.out.println();
        System.out.println("==|| End ||==");
    }

    public void viewScheme()
    {
        System.out.println("==|| Scheme #" + scheme.getId() + " - " + scheme.getName() + " ||==");
        System.out.println();
        for (Rope rope :scheme.getRopeUp())
        {
            System.out.print(rope.getId() + " - " + rope.getColour() + " | ");
        }
        System.out.println();
        for (Rope rope :scheme.getRopeUp())
        {
            System.out.print(rope.getId() + "|");
        }
        System.out.println();
        for (Row row: scheme.getRows())
        {
            StringBuilder ropesStr = new StringBuilder();
            StringBuilder knotsStr = new StringBuilder();
            boolean first = true;
            int i = 0;
            ArrayList<Integer> ropes = row.getRopesDown();
            for (Knot knot: row.getKnots())
            {
                switch (knot.getDirection())
                {
                    case LEFT:
                        if (!first)
                            knotsStr.append("  ");
                        knotsStr.append(" /");
                        ropesStr.append(String.valueOf(ropes.get(i))).append('|').append(String.valueOf(ropes.get(i+1))).append('|');
                        break;
                    case RIGHT:
                        if (!first)
                            knotsStr.append("  ");
                        knotsStr.append(" \\");
                        ropesStr.append(String.valueOf(ropes.get(i))).append('|').append(String.valueOf(ropes.get(i+1))).append('|');
                        break;
                    case LEFT_ANGLE:
                        if (!first)
                            knotsStr.append("  ");
                        knotsStr.append(" >");
                        ropesStr.append(String.valueOf(ropes.get(i))).append('|').append(String.valueOf(ropes.get(i+1))).append('|');
                        break;
                    case RIGHT_ANGLE:
                        if (!first)
                            knotsStr.append("  ");
                        knotsStr.append(" <");
                        ropesStr.append(String.valueOf(ropes.get(i))).append('|').append(String.valueOf(ropes.get(i+1))).append('|');
                        break;
                    case RIGHT_EMPTY:
                        knotsStr.append("  \\0");
                        ropesStr.append(String.valueOf(ropes.get(i)));
                        break;
                    case LEFT_EMPTY:
                        knotsStr.append("/0");
                        ropesStr.append(String.valueOf(ropes.get(i))).append('|');
                        i --;
                        break;
                }
                i += 2;
                first = false;
            }
            knotsStr.append("    [").append(String.valueOf(row.getId()));
            System.out.println(knotsStr.toString());
            System.out.println(ropesStr.toString());
        }
        for (Rope rope :scheme.getRopeDown())
        {
            System.out.print(rope.getId() + "|");
        }
        System.out.println();
        System.out.println();
        System.out.println("==|| End ||==");
    }


    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }
}

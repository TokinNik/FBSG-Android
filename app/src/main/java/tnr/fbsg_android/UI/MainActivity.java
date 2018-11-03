package tnr.fbsg_android.UI;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import tnr.fbsg_android.Generator.Editor;
import tnr.fbsg_android.Generator.Knot;
import tnr.fbsg_android.Generator.Row;
import tnr.fbsg_android.Generator.Scheme;
import tnr.fbsg_android.R;
import tnr.fbsg_android.SchemeViewer.ConsoleSchemeViewer;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = "MAIN_AVTIVITY";
    private Scheme currentScheme;
    private Editor editor;
    SchemeEditorView schemeEditorView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schemeEditorView = findViewById(R.id.editor);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Resources resources = getResources();
        currentScheme = new Scheme(0);
        editor = new Editor(currentScheme);
        schemeEditorView.setEditor(editor);
        editor.changeRopeColor(1, Color.rgb(120, 0, 0));
        editor.changeRopeColor(2, Color.rgb(0, 120, 0));
        editor.changeRopeColor(3, Color.rgb(0, 0, 120));
        editor.changeRopeColor(4, Color.rgb(120, 120, 0));
        editor.changeRopeColor(5, Color.rgb(120, 0, 120));
        editor.addRope();
        editor.addRope();
        editor.addRow();
        ConsoleSchemeViewer csv = new ConsoleSchemeViewer(currentScheme);
        csv.viewScheme();
        int i = 0;
        for (Row row : currentScheme.getRows())
        {
            for (Knot k : row.getKnots())
            {
                switch (k.getDirection())
                {
                    case LEFT:
                        schemeEditorView.addKnot(new ImageKnot(k, BitmapFactory.decodeResource(resources, R.drawable.red_box_left, options), row.getId(), i));
                        break;
                    case RIGHT:
                        schemeEditorView.addKnot(new ImageKnot(k, BitmapFactory.decodeResource(resources, R.drawable.red_box_right, options), row.getId(), i));
                        break;
                    case RIGHT_ANGLE:
                        schemeEditorView.addKnot(new ImageKnot(k, BitmapFactory.decodeResource(resources, R.drawable.red_box_right_angle, options), row.getId(), i));
                        break;
                    case LEFT_ANGLE:
                        schemeEditorView.addKnot(new ImageKnot(k, BitmapFactory.decodeResource(resources, R.drawable.red_box_left_angle, options), row.getId(), i));
                        break;
                    case RIGHT_EMPTY:
                        schemeEditorView.addKnot(new ImageKnot(k, BitmapFactory.decodeResource(resources, R.drawable.red_box_right_empty, options), row.getId(), i));
                        break;
                    case LEFT_EMPTY:
                        schemeEditorView.addKnot(new ImageKnot(k, BitmapFactory.decodeResource(resources, R.drawable.red_box_left_empty, options), row.getId(), i));
                        break;
                    default:
                        break;
                }
                schemeEditorView.addKnot(new DrawKnot(k, row.getId(), i));
                i++;
            }
            i = 0;
        }


    }


    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button_row_plus:
                Log.d(TAG, "onClick +Row");
                editor.addRow();
                int i = 0;
                for (Row row : currentScheme.getRows())
                {
                    if (row.getId() < currentScheme.getRows().size() - 2)
                        continue;
                    for (Knot k : row.getKnots())
                    {
                        schemeEditorView.addKnot(new DrawKnot(k, row.getId(), i));
                        i++;
                    }
                    i = 0;
                }
                schemeEditorView.invalidate();
                schemeEditorView.invalidate();

                break;

           default:
                break;

        }
    }
}

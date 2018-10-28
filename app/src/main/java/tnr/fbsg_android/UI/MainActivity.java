package tnr.fbsg_android.UI;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tnr.fbsg_android.Generator.Editor;
import tnr.fbsg_android.Generator.Knot;
import tnr.fbsg_android.Generator.Row;
import tnr.fbsg_android.Generator.Scheme;
import tnr.fbsg_android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SchemeEditorView schemeEditorView = findViewById(R.id.editor);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Resources resources = getResources();
        Scheme scheme = new Scheme(0);
        Editor editor = new Editor(scheme);
        schemeEditorView.setEditor(editor);
        int i = 0;
        for (Row row : scheme.getRows())
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

        editor.changeRopeColor(1, Color.rgb(120, 0, 0));
        editor.changeRopeColor(2, Color.rgb(0, 120, 0));
        editor.changeRopeColor(3, Color.rgb(0, 0, 120));
        editor.changeRopeColor(4, Color.rgb(120, 120, 0));
        editor.changeRopeColor(5, Color.rgb(120, 0, 120));
    }

}

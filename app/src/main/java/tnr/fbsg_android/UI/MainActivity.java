package tnr.fbsg_android.UI;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.Toast;

import tnr.fbsg_android.Generator.Editor;
import tnr.fbsg_android.Generator.Knot;
import tnr.fbsg_android.Generator.Rope;
import tnr.fbsg_android.Generator.Row;
import tnr.fbsg_android.Generator.Scheme;
import tnr.fbsg_android.R;
import tnr.fbsg_android.SchemeViewer.ConsoleSchemeViewer;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener
{
    public static final String TAG = "MAIN_AVTIVITY";
    private Scheme currentScheme;
    private Editor editor;
    private String saveNum;
    private Button saveButton;
    SchemeEditorView schemeEditorView;
    private SeekBar seekBarRed;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;
    private LinearLayout colPicker;
    private View.OnClickListener colorButtonListerner;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
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
        //ConsoleSchemeViewer csv = new ConsoleSchemeViewer(currentScheme);
        saveNum = "0";

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

        colPicker = findViewById(R.id.layout_color_picker);
        colorButtonListerner = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Button button = (Button) v;
                if (saveNum.equals("0"))
                {
                    saveNum = (String) button.getText();
                    saveButton = button;
                    button.setText(R.string.ok);
                    button.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 10));
                    int color = ((ColorDrawable)button.getBackground()).getColor();
                    Color.red(color);
                    seekBarRed.setProgress(Color.red(color));
                    seekBarGreen.setProgress(Color.green(color));
                    seekBarBlue.setProgress(Color.blue(color));
                    findViewById(R.id.layout_color_seek).setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                else if (button.getText().equals("Ok"))
                {
                    button.setLayoutParams(new LayoutParams(50, LayoutParams.WRAP_CONTENT, 0));
                    button.setText(saveNum);
                    saveNum = "0";
                    findViewById(R.id.layout_color_seek).setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
                }
            }
        };
        for (Rope rope: currentScheme.getRopeUp())
        {
            addColorButton(rope);
        }
        saveButton = findViewById(0);

        seekBarRed = findViewById(R.id.seekBar_color_red);
        seekBarRed.getProgressDrawable().setColorFilter(Color.RED,PorterDuff.Mode.SRC_ATOP);
        seekBarRed.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        seekBarRed.setOnSeekBarChangeListener(this);

        seekBarGreen = findViewById(R.id.seekBar_color_green);
        seekBarGreen.getProgressDrawable().setColorFilter(Color.GREEN,PorterDuff.Mode.SRC_ATOP);
        seekBarGreen.getThumb().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        seekBarGreen.setOnSeekBarChangeListener(this);

        seekBarBlue = findViewById(R.id.seekBar_color_blue);
        seekBarBlue.getProgressDrawable().setColorFilter(Color.BLUE,PorterDuff.Mode.SRC_ATOP);
        seekBarBlue.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        seekBarBlue.setOnSeekBarChangeListener(this);

    }


    public void onClick(View view)
    {
        int i = 0;
        switch (view.getId())
        {
            case R.id.button_row_plus:
                editor.addRow();
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
                schemeEditorView.reMathCoord();
                schemeEditorView.invalidate();
                break;

            case R.id.button_row_minus:
                if (!editor.decRow())
                {
                    Toast.makeText(this, R.string.minimal_amount_rows, Toast.LENGTH_SHORT).show();
                    break;
                }
                i = currentScheme.getRows().get(0).getKnots().size() + (currentScheme.getRows().get(0).getType() == Row.RowType.OPEN_RIGHT ? currentScheme.getRows().get(0).getKnots().size() : currentScheme.getRows().get(1).getKnots().size());
                while (i > 0)
                {
                    schemeEditorView.knotsDraw.remove(schemeEditorView.knotsDraw.size()-1);
                    i--;
                }
                schemeEditorView.reMathCoord();
                schemeEditorView.invalidate();
                break;

            case R.id.button_rope_plus:
                editor.addRope();
                addColorButton(currentScheme.getRopeUp().get(currentScheme.getRopeUp().size()-1));

                schemeEditorView.knotsDraw.clear();//musorisch

                for (Row row : currentScheme.getRows())
                {
                    for (Knot k : row.getKnots())
                    {
                        schemeEditorView.addKnot(new DrawKnot(k, row.getId(), i));
                        i++;
                    }
                    i = 0;
                }

                schemeEditorView.reMathCoord();
                schemeEditorView.invalidate();
                break;

            case R.id.button_rope_minus:
                if (!editor.decRope())
                {
                    Toast.makeText(this, R.string.minimal_amount_ropes, Toast.LENGTH_SHORT).show();
                    break;
                }

                deleteColorButton(currentScheme.getRopeUp().size());

                schemeEditorView.knotsDraw.clear();//musorisch

                for (Row row : currentScheme.getRows())
                {
                    for (Knot k : row.getKnots())
                    {
                        schemeEditorView.addKnot(new DrawKnot(k, row.getId(), i));
                        i++;
                    }
                    i = 0;
                }

                schemeEditorView.reMathCoord();
                schemeEditorView.invalidate();
                break;

           default:
                break;

        }
    }

    private void addColorButton(Rope rope)
    {
        int i = rope.getId();
        Button button = new Button(this);
        button.setLayoutParams(new LayoutParams(50, LayoutParams.WRAP_CONTENT, 0));
        button.setText(String.valueOf(i+1));
        button.setBackgroundColor(rope.getColour());
        button.setId(i);
        button.setOnClickListener(colorButtonListerner);
        colPicker.addView(button, i);
    }
    private void deleteColorButton(int index)
    {
        colPicker.removeViewAt(index);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        int color = Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress());
        saveButton.setBackgroundColor(color);
        if (!saveNum.equals("0"))

            editor.changeRopeColor(Integer.parseInt(saveNum)-1, color);
        schemeEditorView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

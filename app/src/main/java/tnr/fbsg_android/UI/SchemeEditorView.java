package tnr.fbsg_android.UI;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import tnr.fbsg_android.Generator.Editor;
import tnr.fbsg_android.Generator.Knot;
import tnr.fbsg_android.Generator.Rope;
import tnr.fbsg_android.Generator.Row;
import tnr.fbsg_android.Generator.Scheme;
import tnr.fbsg_android.R;
import tnr.fbsg_android.SchemeViewer.ConsoleSchemeViewer;

public class SchemeEditorView extends View
{
    public static final String TAG = "SCHEME_GENERATOR_VIEW";
    private final Paint paint = new Paint();
    BitmapFactory.Options options = new BitmapFactory.Options();
    private float posLeft;
    private float posTop;
    private float posTopMax;
    private float posLeftMax;
    private float posLeftStart;
    private float posTopStart;
    private float scaleFactor = 1.0f;
    private float knotSize = 25f;
    private boolean imageMode = false;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private int maxWidth = Integer.MAX_VALUE;
    ArrayList<ImageKnot> knotsImg;
    ArrayList<DrawKnot> knotsDraw;
    Editor editor;

    public SchemeEditorView(Context context) {
        super(context);
        init(context);
    }

    public SchemeEditorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchemeEditorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SchemeEditorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private  void init(Context context)
    {
        posLeftStart = 100;
        posTopStart = 300;
        posLeft = posLeftStart;
        posTop = posTopStart;
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        knotsImg = new ArrayList<>();
        knotsDraw = new ArrayList<>();
        options.inMutable = true;
        paint.setAntiAlias(true);
    }


    public void setEditor(Editor editor) {
        this.editor = editor;
    }
    public void switchMode()
    {
        imageMode = !imageMode;
        reMathCoord();
        invalidate();
    }

    public void addKnot(ImageKnot imageKnot)
    {
        knotsImg.add(imageKnot);
    }
    public void addKnot(DrawKnot drawKnot)
    {
        knotsDraw.add(drawKnot);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.d(TAG, "onMeasure()");
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);

        if(maxWidth > 0 && maxWidth < measuredWidth)
        {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, measureMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        paint.setColor(Color.GRAY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawColor(paint.getColor());

        if (imageMode)
        {
            for (int i = 0; i < knotsImg.size(); i++)
            {
                ImageKnot ik = knotsImg.get(i);
                if (ik.getRowId() % 2 == 0)
                    canvas.drawBitmap(ik.getImage(), posLeft + ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor,
                            posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f * scaleFactor, null);
                else
                    canvas.drawBitmap(ik.getImage(), posLeft + (ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor - ik.getImage().getWidth() / 2 * scaleFactor),
                            posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f * scaleFactor, null);
            }
        }
        else
        {


//        paint.setStyle(Paint.Style.FILL_AND_STROKE);//заготовка прооисовки нитей
//        paint.setStrokeWidth(10);
//        //canvas.drawLine(knotsDraw.get(0).getX(), knotsDraw.get(0).getY(), knotsDraw.get(3).getX(), knotsDraw.get(3).getY(), paint);
//
//        int knotsDrawSize = knotsDraw.size();
//        for (int i = 0; i < knotsDraw.size()/2; i++)
//        {
//            DrawKnot dk = knotsDraw.get(i);
//            paint.setColor(dk.getKnot().getFirstDown());
//            canvas.drawLine(dk.getX(), dk.getY(), knotsDraw.get(i + knotsDrawSize/2).getX(), knotsDraw.get(i + knotsDrawSize/2).getY(), paint);
//            paint.setColor(dk.getKnot().getSecondDown());
//            canvas.drawLine(dk.getX(), dk.getY(), knotsDraw.get(i + 1 + knotsDrawSize/2).getX(), knotsDraw.get(i + 1 + knotsDrawSize/2).getY(), paint);
//        }


            int knotsRowSize = editor.getScheme().getRows().get(0).getKnots().size();
            boolean openRow = editor.getScheme().getRows().get(0).getType() == Row.RowType.OPEN_RIGHT;
            int buf = 0;
            for (int i = 0; i < knotsDraw.size(); i++)
            {
                if (i % knotsRowSize == 0 && openRow)
                    buf++;

                DrawKnot dk = knotsDraw.get(i);
                float x;
                float y;

                if (dk.getRowId() % 2 == 0)
                {
                    x = posLeft + (dk.getKnotId() * knotSize * 4);
                } else
                {
                    x = posLeft + (dk.getKnotId() * knotSize * 4 - knotSize * 2);
                }

                y = posTop + dk.getRowId() * knotSize * 4f;

                dk.setX(x);
                dk.setY(y);

                if (i < knotsDraw.size() - knotsRowSize - 1)
                {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    paint.setStrokeWidth(10);
                    paint.setColor(dk.getKnot().getFirstDown());
                    if (dk.getKnot().getDirection() != Knot.KnotDirection.LEFT_EMPTY)
                    {
                        canvas.drawLine(x, y, knotsDraw.get(i + knotsRowSize - (openRow ? (buf % 2 == 1 ? 0 : 1) : 0)).getX(), knotsDraw.get(i + knotsRowSize - (openRow ? (buf % 2 == 1 ? 0 : 1) : 0)).getY(), paint);
                    }
                    paint.setColor(dk.getKnot().getSecondDown());
                    if (dk.getKnot().getDirection() != Knot.KnotDirection.RIGHT_EMPTY)
                    {
                        canvas.drawLine(x, y, knotsDraw.get(i + 1 + knotsRowSize - (openRow ? (buf % 2 == 1 ? 0 : 1) : 0)).getX(), knotsDraw.get(i + 1 + knotsRowSize - (openRow ? (buf % 2 == 1 ? 0 : 1) : 0)).getY(), paint);
                    }
                } else
                {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    paint.setStrokeWidth(10);
                    paint.setColor(dk.getKnot().getFirstDown());
                    if (dk.getKnot().getDirection() != Knot.KnotDirection.LEFT_EMPTY)
                    {
                        if (dk.getRowId() == editor.getScheme().getRows().get(editor.getScheme().getRows().size() - 2).getId())
                        {
                            canvas.drawLine(x, y, knotsDraw.get(i + knotsRowSize - (openRow ? (buf % 2 == 1 ? 0 : 1) : 0)).getX(), knotsDraw.get(i + knotsRowSize - (openRow ? (buf % 2 == 1 ? 0 : 1) : 0)).getY(), paint);
                        } else
                        {
                            canvas.drawLine(x, y, x - knotSize, y + knotSize * 4, paint);
                            canvas.drawCircle(x - knotSize, y + knotSize * 4, knotSize / 2, paint);
                        }
                    }
                    paint.setColor(dk.getKnot().getSecondDown());
                    if (dk.getKnot().getDirection() != Knot.KnotDirection.RIGHT_EMPTY)
                    {
                        canvas.drawLine(x, y, x + knotSize, y + knotSize * 4, paint);
                        canvas.drawCircle(x + knotSize, y + knotSize * 4, knotSize / 2, paint);
                    }
                }

                if (dk.getKnot().getDirection() != Knot.KnotDirection.LEFT_EMPTY && dk.getKnot().getDirection() != Knot.KnotDirection.RIGHT_EMPTY)
                {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(dk.getKnot().getColour());
                    paint.setStrokeWidth(1);
                    canvas.drawCircle(x, y, knotSize, paint);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(2);
                    canvas.drawCircle(x, y, knotSize, paint);
                } else
                {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(dk.getKnot().getColour());
                    paint.setStrokeWidth(1);
                    canvas.drawCircle(x, y, knotSize / 3, paint);
                }

                float shift = knotSize * 0.6f;

                switch (dk.getKnot().getDirection())
                {
                    case LEFT:
                        canvas.drawLine(x - shift, y + shift, x + shift, y - shift, paint);
                        canvas.drawLine(x - shift, y + shift, x - shift, y, paint);
                        canvas.drawLine(x - shift, y + shift, x, y + shift, paint);
                        break;
                    case RIGHT:
                        canvas.drawLine(x + shift, y + shift, x - shift, y - shift, paint);
                        canvas.drawLine(x + shift, y + shift, x + shift, y, paint);
                        canvas.drawLine(x + shift, y + shift, x, y + shift, paint);
                        break;
                    case LEFT_ANGLE:
                        canvas.drawLine(x - shift, y + shift, x, y, paint);
                        canvas.drawLine(x, y, x - shift, y - shift, paint);
                        canvas.drawLine(x - shift, y + shift, x - shift, y, paint);
                        canvas.drawLine(x - shift, y + shift, x, y + shift, paint);
                        break;
                    case RIGHT_ANGLE:
                        canvas.drawLine(x + shift, y + shift, x, y, paint);
                        canvas.drawLine(x, y, x + shift, y - shift, paint);
                        canvas.drawLine(x + shift, y + shift, x + shift, y, paint);
                        canvas.drawLine(x + shift, y + shift, x, y + shift, paint);
                        break;
                    default:
                        break;
                }
            }
        }
        for (Rope rope: editor.getScheme().getRopeUp())
        {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(rope.getColour());
            canvas.drawCircle(posLeft + knotSize * rope.getId() * 2 - 20, posTop - 50, 10, paint);
        }



        //DrawFullScheme(canvas);//TODO оптимизировать!
        drawFullSchemeBeta(canvas);


//        ArrayList<Integer> ropesDown = editor.getScheme().getRows().get(0).getRopesDown();
//        for (int i = 0; i < ropesDown.size(); i++)
//        {
//            paint.setStyle(Paint.Style.FILL_AND_STROKE);
//            paint.setColor(editor.getScheme().getRopeUp().get(ropesDown.get(i)).getColour());
//            canvas.drawCircle(posLeft + knotSize * i, posTop + 50, 10, paint);
//        }
    }

    public void reMathCoord()
    {
        for (DrawKnot dk: knotsDraw)
        {
            float x;
            float y;

            if (dk.getRowId() % 2 == 0)
            {
                x = posLeft + (dk.getKnotId() * knotSize * 4);
            } else
            {
                x = posLeft + (dk.getKnotId() * knotSize * 4 - knotSize * 2);
            }

            y = posTop + dk.getRowId() * knotSize * 4f;

            dk.setX(x);
            dk.setY(y);
        }
    }

    public void saveSignature()
    {
        posLeft = posLeftStart;
        posTop = posTopStart;
        scaleFactor = 1.0f;
        invalidate();
        int border = 60;
        int width = (int)(knotsDraw.get(knotsDraw.size()-1).getX() +  border);
        int height = (int)(knotsDraw.get(knotsDraw.size()-1).getY() + border*2);

        Bitmap  bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "scheme" + editor.getScheme().getId() + ".png");

        try
        {
            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
            finally
            {
                if (fos != null) fos.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void DrawFullScheme(Canvas canvas)
    {
        Path rhombus = new Path();
        int size = 20, size2 = size*2;
        float x, y, buf = 0;
        boolean repeat = false;
        for (int i = 0; i < knotsDraw.size(); i++)
        {
            DrawKnot dk = knotsDraw.get(i);
            if (dk.getKnot().getDirection() == Knot.KnotDirection.RIGHT_EMPTY || dk.getKnot().getDirection() == Knot.KnotDirection.LEFT_EMPTY)
            {
                continue;
            }

            x = posLeft - 50 + (dk.getRowId() + (repeat ? buf : 0)) * size;

            if (dk.getRowId()%2 == 0)
            {
                y = posTop - 100 - dk.getKnotId() * size2;
            }
            else
            {
                y = posTop - 100 - dk.getKnotId() * size2 + size;
            }



            rhombus.moveTo(x, y);
            rhombus.lineTo(x + size, y + size);
            rhombus.lineTo(x + size * 2, y);
            rhombus.lineTo(x + size, y - size);
            rhombus.lineTo(x, y);

            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            paint.setColor(dk.getKnot().getColour());
            canvas.drawPath(rhombus, paint);

            rhombus.reset();

            /*if (i+1 == knotsDraw.size() && x < dk.getX())
                {
                    buf = buf + dk.getRowId() + 1;
                    i = 0;
                    repeat = true;
                }*/
        }
    }

    private void drawFullSchemeBeta(Canvas canvas)
    {
        Path rhombus = new Path();
        int size = 20, size2 = size*2, i = 0, buf = 0;
        float x = 0, y = 0, borderX = knotsDraw.get(knotsDraw.size()-1).getX();
        Scheme shem = editor.getScheme();
        while (x < borderX)
        {
            Row row = shem.getRows().get(i);
            int j = 0;
            x = posLeft - 50 + buf * size;

            for (Knot k: row.getKnots())
            {
                if (k.getDirection() == Knot.KnotDirection.RIGHT_EMPTY || k.getDirection() == Knot.KnotDirection.LEFT_EMPTY)
                {
                    continue;
                }

                if (buf%2 == 0)
                {
                    y = posTop - 100 - j * size2;
                }
                else
                {
                    y = posTop - 100 - j * size2 - size;
                }

                Log.d(TAG, "XX = " + x + " YY = " + y + " Knot Dir = " + k.getDirection());

                rhombus.moveTo(x, y);
                rhombus.lineTo(x + size, y + size);
                rhombus.lineTo(x + size * 2, y);
                rhombus.lineTo(x + size, y - size);
                rhombus.lineTo(x, y);

                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(1);
                paint.setColor(k.getColour());
                canvas.drawPath(rhombus, paint);

                rhombus.reset();
                j++;
            }

            Log.d(TAG, "X = " + x + " BUF = " + buf + " BORDER = " + borderX + " Knots count = " + row.getKnots().size() + " i = " + i);

            i++;
            buf++;
            if (i == shem.getRows().size())
            {
                i = 0;
                //shem.makeScheme();
                //ConsoleSchemeViewer consoleSchemeViewer = new ConsoleSchemeViewer(shem);
                //consoleSchemeViewer.viewScheme();
                shem = new Scheme(buf, shem.getRows().size(), shem.getRopeDown().size(), shem.getRopeDown(), shem);
                //consoleSchemeViewer.viewScheme();
            }
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            //posLeft -= distanceX;//TODO ljhf,jnfnm
            //posTop -= distanceY;
            //invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
            //scaleFactor = 1.0f;//TODO убрать на релизе
            invalidate();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            float pointX = e.getX();
            float pointY = e.getY();
            if (imageMode)
            {
                for (ImageKnot ik : knotsImg)
                {
                    float y = posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f;
                    editor.changeKnotDirection(ik.getKnot(), null);
                    if (ik.getRowId() % 2 == 0)
                    {
                        float x = posLeft + ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor;
                        if (pointX >= x && pointX <= x + ik.getImage().getWidth() &&
                                pointY >= y && pointY <= y + ik.getImage().getHeight())
                        {
                            switch (ik.getKnot().getDirection())
                            {
                                case LEFT:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_left_25, options));
                                    invalidate();
                                    break;
                                case RIGHT:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_right_25, options));
                                    invalidate();
                                    break;
                                case RIGHT_ANGLE:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_right_angle_25, options));
                                    invalidate();
                                    break;
                                case LEFT_ANGLE:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_left_angle_25, options));
                                    invalidate();
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                    } else
                    {
                        float x = posLeft + (ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor - ik.getImage().getWidth() / 2 * scaleFactor);
                        if (pointX >= x && pointX <= x + ik.getImage().getWidth() &&
                                pointY >= y && pointY <= y + ik.getImage().getHeight())
                        {
                            switch (ik.getKnot().getDirection())
                            {
                                case LEFT:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_left_25, options));
                                    invalidate();
                                    break;
                                case RIGHT:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_right_25, options));
                                    invalidate();
                                    break;
                                case RIGHT_ANGLE:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_right_angle_25, options));
                                    invalidate();
                                    break;
                                case LEFT_ANGLE:
                                    ik.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.red_box_left_angle_25, options));
                                    invalidate();
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                    }
                }
            }
            else
            {
                for (DrawKnot dk : knotsDraw)
                {
                    if (Math.pow(pointX - dk.getX(), 2) + Math.pow(pointY - dk.getY(), 2) <= Math.pow(knotSize, 2))
                    {
                        editor.changeKnotDirection(dk.getKnot(), null);
                        invalidate();
                        break;
                    }
                }
            }
            return super.onSingleTapUp(e);
        }
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.2f, Math.min(scaleFactor, 3.0f));
            invalidate();

            return true;
        }


    }

}

package tnr.fbsg_android.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;

import tnr.fbsg_android.Generator.Editor;
import tnr.fbsg_android.Generator.Knot;
import tnr.fbsg_android.Generator.Rope;
import tnr.fbsg_android.Generator.Row;


public class SchemeFullView extends View
{
    public static final String TAG = "SCHEME_FULL_VIEW";
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
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private int maxWidth = Integer.MAX_VALUE;
    ArrayList<ImageKnot> knotsImg;
    ArrayList<DrawKnot> knotsDraw;
    Editor editor;

    public SchemeFullView(Context context) {
        super(context);
        init(context);
    }

    public SchemeFullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SchemeFullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SchemeFullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private  void init(Context context)
    {
        posLeftStart = 100;
        posTopStart = 100;
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

        //
        // if (scaleFactor >= 1) canvas.translate(0 - (getWidth() - getWidth()/scaleFactor), 0 - (getHeight() - getHeight()/scaleFactor));

        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawColor(paint.getColor());

        /*for (int i = 0; i < knotsImg.size(); i++)
        {
            ImageKnot ik = knotsImg.get(i);
            if (ik.getRowId()%2 == 0)
                canvas.drawBitmap(ik.getImage(), posLeft + ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor,
                        posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f * scaleFactor, null);
            else
                canvas.drawBitmap(ik.getImage(), posLeft + (ik.getKnotId() * ik.getImage().getWidth()*2 * scaleFactor - ik.getImage().getWidth()/2 * scaleFactor),
                        posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f * scaleFactor, null);
        }*/


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


        int knotsRowSize =  editor.getScheme().getRows().get(0).getKnots().size();
        boolean openRow = editor.getScheme().getRows().get(0).getType() == Row.RowType.OPEN_RIGHT;
        int buf = 0;
        for (int i = 0; i < knotsDraw.size(); i++)
        {
            if (i%knotsRowSize == 0 && openRow)
                buf++;

            DrawKnot dk = knotsDraw.get(i);
            float x;
            float y;

            if (dk.getRowId()%2 == 0)
            {
                x = posLeft + (dk.getKnotId() * knotSize * 4);
            }
            else
            {
                x = posLeft + (dk.getKnotId() * knotSize * 4 - knotSize * 2);
            }

            y = posTop + dk.getRowId() * knotSize * 4f;

            dk.setX(x);
            dk.setY(y);

            if (i < knotsDraw.size() - knotsRowSize - 1 )
            {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeWidth(10);
                paint.setColor(dk.getKnot().getFirstDown());
                if (dk.getKnot().getDirection() != Knot.KnotDirection.LEFT_EMPTY)
                {
                    canvas.drawLine(x, y, knotsDraw.get(i + knotsRowSize - (openRow ? (buf%2 == 1 ? 0 : 1) : 0)).getX(), knotsDraw.get(i + knotsRowSize - (openRow ? (buf%2 == 1 ? 0 : 1) : 0)).getY(), paint);
                }
                paint.setColor(dk.getKnot().getSecondDown());
                if (dk.getKnot().getDirection() != Knot.KnotDirection.RIGHT_EMPTY)
                {
                    canvas.drawLine(x, y, knotsDraw.get(i + 1 + knotsRowSize - (openRow ? (buf%2 == 1 ? 0 : 1) : 0)).getX(), knotsDraw.get(i + 1 + knotsRowSize - (openRow ? (buf%2 == 1 ? 0 : 1) : 0)).getY(), paint);
                }
            }
            else
            {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeWidth(10);
                paint.setColor(dk.getKnot().getFirstDown());
                if (dk.getKnot().getDirection() != Knot.KnotDirection.LEFT_EMPTY)
                {
                    if (dk.getRowId() == editor.getScheme().getRows().get(editor.getScheme().getRows().size()-2).getId())
                    {
                        canvas.drawLine(x, y, knotsDraw.get(i + knotsRowSize - (openRow ? (buf%2 == 1 ? 0 : 1) : 0)).getX(), knotsDraw.get(i + knotsRowSize - (openRow ? (buf%2 == 1 ? 0 : 1) : 0)).getY(), paint);
                    }
                    else
                    {
                        canvas.drawLine(x, y, x - knotSize, y + knotSize * 4, paint);
                        canvas.drawCircle(x - knotSize, y + knotSize*4, knotSize/2, paint);
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
            }
            else
            {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(dk.getKnot().getColour());
                paint.setStrokeWidth(1);
                canvas.drawCircle(x, y, knotSize/3, paint);
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
                    canvas.drawLine(x + shift, y + shift, x , y , paint);
                    canvas.drawLine(x, y, x + shift , y - shift , paint);
                    canvas.drawLine(x + shift, y + shift, x + shift, y, paint);
                    canvas.drawLine(x + shift, y + shift, x, y + shift, paint);
                    break;
                default:
                    break;
            }
        }

        for (Rope rope: editor.getScheme().getRopeUp())
        {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(rope.getColour());
            canvas.drawCircle(posLeft + knotSize * rope.getId() * 2 - 20, posTop - 50, 10, paint);
        }

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

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            posLeft -= distanceX;
            posTop -= distanceY;
            invalidate();
            return true;
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
/*
            for (ImageKnot ik: knotsImg)
            {
                float y = posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f;
                if (ik.getRowId()%2 == 0)
                {
                    float x = posLeft + ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor;
                    if (pointX >= x && pointX  <= x + ik.getImage().getWidth() &&
                            pointY >= y && pointY <= y + ik.getImage().getHeight())
                    {
                        ik.getKnot().changeDirection();
                        switch (ik.getKnot().getDirection())
                        {
                            case LEFT:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_left, options));
                                invalidate();
                                break;
                            case RIGHT:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_right, options));
                                invalidate();
                                break;
                            case RIGHT_ANGLE:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_right_angle, options));
                                invalidate();
                                break;
                            case LEFT_ANGLE:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_left_angle, options));
                                invalidate();
                                break;
                            default:
                                    break;
                             }
                             break;
                    }
                }
                else
                {
                    float x = posLeft + (ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor - ik.getImage().getWidth()/2 * scaleFactor);
                    if (pointX >= x && pointX  <= x + ik.getImage().getWidth() &&
                            pointY >= y && pointY <= y + ik.getImage().getHeight())
                    {
                        ik.getKnot().changeDirection();
                        switch (ik.getKnot().getDirection())
                        {
                            case LEFT:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_left, options));
                                invalidate();
                                break;
                            case RIGHT:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_right, options));
                                invalidate();
                                break;
                            case RIGHT_ANGLE:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_right_angle, options));
                                invalidate();
                                break;
                            case LEFT_ANGLE:
                                ik.setImage( BitmapFactory.decodeResource(getResources(),R.drawable.red_box_left_angle, options));
                                invalidate();
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                }
            }
*/
            for (DrawKnot dk: knotsDraw)
            {
                if (Math.pow(pointX - dk.getX(), 2) + Math.pow(pointY - dk.getY(), 2) <= Math.pow(knotSize, 2))
                {
                    editor.changeKnotDirection(dk.getKnot(), null);
                    invalidate();
                    break;
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

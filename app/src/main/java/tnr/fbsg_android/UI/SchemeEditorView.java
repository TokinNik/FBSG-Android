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

import tnr.fbsg_android.Generator.Knot;
import tnr.fbsg_android.R;

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
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private int maxWidth = Integer.MAX_VALUE;
    ArrayList<ImageKnot> knotsImg;

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
        posTopStart = 100;
        posLeft = posLeftStart;
        posTop = posTopStart;
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        knotsImg = new ArrayList<>();
        options.inMutable = true;
    }


    public void addKnot(ImageKnot imageKnot)
    {
        knotsImg.add(imageKnot);
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
        Log.d(TAG, "pnMeasure()");
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.GRAY);

        //
        // if (scaleFactor >= 1) canvas.translate(0 - (getWidth() - getWidth()/scaleFactor), 0 - (getHeight() - getHeight()/scaleFactor));

        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawColor(paint.getColor());
        for (int i = 0; i < knotsImg.size(); i++)
        {
            ImageKnot ik = knotsImg.get(i);
            if (ik.getRowId()%2 == 0)
                canvas.drawBitmap(ik.getImage(), posLeft + ik.getKnotId() * ik.getImage().getWidth() * 2 * scaleFactor,
                        posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f * scaleFactor, null);
            else
                canvas.drawBitmap(ik.getImage(), posLeft + (ik.getKnotId() * ik.getImage().getWidth()*2 * scaleFactor - ik.getImage().getWidth()/2 * scaleFactor),
                        posTop + ik.getRowId() * ik.getImage().getHeight() * 1.5f * scaleFactor, null);
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
            scaleFactor = 1.0f;//TODO убрать на релизе
            invalidate();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            float pointX = e.getX();
            float pointY = e.getY();

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

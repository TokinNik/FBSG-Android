package tnr.fbsg_android.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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

import tnr.fbsg_android.R;

public class SchemeEditorView extends View
{
    public static final String TAG = "SCHEME_GENERATOR_VIEW";
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
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap tempBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_box, options);
        Bitmap tempBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.red_box, options);
        Bitmap tempBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.red_box, options);
        Bitmap tempBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.red_box, options);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);

        if (scaleFactor >= 1)
            canvas.translate(0 - (getWidth() - getWidth()/scaleFactor), 0 - (getHeight() - getHeight()/scaleFactor));

        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawColor(paint.getColor());
        canvas.drawBitmap(tempBitmap, posLeft, posTop, null);
        canvas.drawBitmap(tempBitmap1, posLeft+100, posTop, null);
        canvas.drawBitmap(tempBitmap2, posLeft, posTop+100, null);
        canvas.drawBitmap(tempBitmap3, posLeft+100, posTop+100, null);
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
            scaleFactor = 1.0f;
            invalidate();
            return true;
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

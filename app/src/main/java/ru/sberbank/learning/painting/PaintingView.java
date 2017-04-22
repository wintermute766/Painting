package ru.sberbank.learning.painting;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by user10 on 22.04.2017.
 */

public class PaintingView extends View {

    private float lastX;
    private float lastY;

    private SparseArray<PointF> lastPoints = new SparseArray<>(10);

    private Bitmap cacheBitmap;
    private Canvas bitmapCanvas;

    private Paint linePaint;

    public PaintingView(Context context) {
        super(context);
        init();
    }

    public PaintingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaintingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setColor(getResources().getColor(R.color.colorAccent));
        linePaint.setStrokeWidth(getResources().getDimension(R.dimen.default_paint_width));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w != 0 && h != 0) {
            if (cacheBitmap != null) {
                cacheBitmap.recycle();
            }

            cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(cacheBitmap);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerId = event.getPointerId(event.getActionIndex());
                lastPoints.put(pointerId, new PointF(
                        event.getX(event.getActionIndex()),
                        event.getY(event.getActionIndex())
                ));
                return true;
            case MotionEvent.ACTION_MOVE:
                PointF point = lastPoints.get(
                        event.getPointerId(event.getActionIndex()));
                float x = event.getX(event.getActionIndex());
                float y = event.getY(event.getActionIndex());

                bitmapCanvas.drawLine(point.x, point.y, x, y, linePaint);
                point.set(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawRect(0.1f * getWidth(), 0.1f * getHeight(),
                    0.9f * getWidth(), 0.9f * getHeight(), linePaint);
        }

        canvas.drawBitmap(cacheBitmap, 0, 0, null);
    }

    public void clear() {
        bitmapCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}

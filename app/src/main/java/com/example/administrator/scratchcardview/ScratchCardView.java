package com.example.administrator.scratchcardview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ChuPeng on 2016/12/9.
 */

public class ScratchCardView extends View
{
    private Paint transparentPaint;//透明画笔
    private Path path;
    private Bitmap bottomBitmap;
    private Bitmap topBitmap;
    private Canvas topCanvas;

    public ScratchCardView(Context context)
    {
        super(context);
        init();
    }

    public ScratchCardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ScratchCardView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScratchCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //初始化
    private void init()
    {
        //初始化透明画笔
        transparentPaint = new Paint();
        transparentPaint.setAlpha(0);
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        transparentPaint.setStyle(Paint.Style.STROKE);
        transparentPaint.setStrokeWidth(50);
        transparentPaint.setStrokeJoin(Paint.Join.ROUND);
        transparentPaint.setStrokeCap(Paint.Cap.ROUND);
        path = new Path();
        //初始化底层图片
        bottomBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.card);
        //初始化顶层图片
        topBitmap = Bitmap.createBitmap(bottomBitmap.getWidth(), bottomBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //创建顶层画布
        topCanvas = new Canvas(topBitmap);
        //设置顶层画布为灰色
        topCanvas.drawColor(Color.GRAY);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(bottomBitmap, 0, 0, null);
        canvas.drawBitmap(topBitmap, 0, 0, null);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                topCanvas.drawPath(path, transparentPaint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}

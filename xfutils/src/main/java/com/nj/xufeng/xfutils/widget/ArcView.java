package com.nj.xufeng.xfutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xufeng on 15/10/30.
 */
public class ArcView extends View {


    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //圆直径=长+（长/2-宽）;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int x = (canvas.getWidth()/2-canvas.getHeight())/2;
        canvas.drawColor(Color.TRANSPARENT);                  //背景
        {
            Paint paint = new Paint();
            paint.setAntiAlias(true);                       //设置画笔为无锯齿
            paint.setColor(Color.WHITE);                    //设置画笔颜色
            paint.setStrokeWidth((float) 1.0);              //线宽
            paint.setStyle(Paint.Style.FILL);

            RectF oval=new RectF();                   //RectF对象
            oval.left=-x;                              //左边
            oval.top=0;                                   //上边
            oval.right= canvas.getWidth()+x;                             //右边
            oval.bottom= canvas.getWidth()+2*x;                           //下边
            canvas.drawArc(oval, 180,180, false, paint);    //绘制圆弧
        }
        {
            Paint paint = new Paint();
            paint.setAntiAlias(true);                       //设置画笔为无锯齿
            paint.setColor(Color.parseColor("#3a000000"));                    //设置画笔颜色
            paint.setStrokeWidth((float) 1.0);              //线宽
            paint.setStyle(Paint.Style.STROKE);

            RectF oval=new RectF();                   //RectF对象
            oval.left=-x;                              //左边
            oval.top=0;                                   //上边
            oval.right= canvas.getWidth()+x;                             //右边
            oval.bottom= canvas.getWidth()+2*x;                           //下边
            canvas.drawArc(oval, 180,180, false, paint);    //绘制圆弧
        }
    }
}

package com.xl.spriteeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;

import com.xl.spriteeditor.tool.Frame;

import java.util.ArrayList;

/*
帧编辑view控件
 */
public class FrameView extends View {
    Frame frame;

    public FrameView(Context context){
        super(context);
    }

    //设置帧
    public void setFrame(Frame frame){
        this.frame = frame;
    }

    //画一张图片 参数：

    /**
     *
     * @param bitmap     图片
     * @param origin_x  旋转 缩放 原点x
     * @param origin_y  旋转 缩放原点 y
     * @param x          绘制x坐标
     * @param y          绘制y坐标
     * @param zoom      缩放比例
     * @param flag      翻转状态 1左右翻转 2上下翻转
     * @param rotate    旋转角度
     * @param alpha     不透明度
     * @param color     颜色
     */
    public static void drawBitmap(Canvas canvas,Bitmap bitmap, int origin_x, int origin_y, int x, int y, float zoom,int flag, int rotate, int alpha, int color){
        Matrix matrix = new Matrix();Paint paint = new Paint();

        // 旋转
        matrix.postRotate(rotate,origin_x,origin_y);

        //设置透明度
        paint.setAlpha(alpha);

        //设置原点

        // 缩放
        matrix.postScale(zoom, zoom);

        if (flag == 1) //左右翻转
            matrix.setScale(-1, 1);
        if (flag == 2)  //上下翻转
            matrix.setScale(1, -1);

        //颜色渲染
        if(color!=0){
            paint.setColorFilter( new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)) ;
            paint.setColor(color);
        }

        canvas.drawBitmap(bitmap,matrix,paint);
    }

    //绘制


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //
        ArrayList<Frame.ClipRect> list_clipRect = frame.getClipRectList();
        for(int i=0;i<list_clipRect.size();i++){

        }

    }
}

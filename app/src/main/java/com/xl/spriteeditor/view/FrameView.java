package com.xl.spriteeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
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

    /*
  bitmap 绘制的bitmap
  cx cy 绘制到屏幕的x y坐标
  cw ch 绘制到屏幕上的宽高
  tx ty 裁剪区域xy坐标
  tw th 图片裁剪区域宽高
  origin_x 旋转/缩放原点x
  origin_y 旋转/缩放原点y
  zoom_x 横向缩放值
  zoom_y 纵向缩放值
  rotate 旋转角度
  color 绘制颜色 为0表示正常绘制
   */
    void N2J_drawBitmapEx(Canvas canvas,Bitmap bitmap, int cx,int cy,int cw,int ch,int tx,int ty,int tw,int th,float origin_x,float origin_y, float zoom_h,float zoom_v,
                          float rotate, int color){
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
//      if (flag == 1) //左右翻转
//          matrix.setScale(-1, 1,origin_x,origin_y);
//      if (flag == 2)  //上下翻转
//          matrix.setScale(1, -1,origin_x,origin_y);
        RectF src = new RectF(tx,ty,tx+tw,ty+th);
        RectF dst = new RectF(cx,cy,cw,ch);
//        matrix.mapRect(src,dst);
        // 缩放
        matrix.postScale(zoom_h, zoom_v,origin_x,origin_y);
        // 旋转
        matrix.postRotate(rotate,origin_x,origin_y);

        matrix.postTranslate(cx,cy);

        //颜色渲染
        /*
        if(color!=0){
            color = (alpha<<24)|(color&0xffffff);
            paint.setColorFilter( new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)) ;
            paint.setColor(color);
        }
        */
        if(color!=0){
            float r = ((float)((color>>16)&0xff))/255;
            float g = ((float)((color>>8)&0xff))/255;
            float b = ((float)((color)&0xff))/255;
            float a = ((float)((color>>24)&0xff))/255;

            // 生成色彩矩阵
            ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                    r,0,0,0,0,
                    0,g,0,0,0,
                    0,0,b,0,0,
                    0,0,0,a,0
            });
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

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

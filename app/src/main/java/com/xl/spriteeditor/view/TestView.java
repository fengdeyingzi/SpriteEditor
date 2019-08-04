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
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xl.game.tool.XL;

/**
 * 一个测试绘图的控件
 */
public class TestView extends View {
    Bitmap bitmap;
    int origin_x=0,origin_y=0;
    int x=100,y=100;
    float zoom=3.0f;
    int rotate=30;
    int alpha=190;
    int color=0x90f090;

    public TestView(Context context){
        super(context);
        this.bitmap = XL.getImageFromAssetsFile(context, "sprite_icon.png");
    }

    public TestView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        this.bitmap = XL.getImageFromAssetsFile(context, "sprite_icon.png");
    }

    public void serOriginX(int x){
        origin_x = x;
        invalidate();
    }

    public void setOriginY(int y){
        origin_y = y;
        invalidate();

    }

    public void setZoom(float zoom){
        this.zoom = zoom;
        invalidate();
    }


    /**
     * 实现图片的旋转 缩放 翻转 设置透明度绘制
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
    public static void drawBitmap(Canvas canvas,Bitmap bitmap, float origin_x, float origin_y, float x, float y, float zoom,int flag, float rotate, int alpha, int color){
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        if (flag == 1) //左右翻转
            matrix.setScale(-1, 1,origin_x,origin_y);
        if (flag == 2)  //上下翻转
            matrix.setScale(1, -1,origin_x,origin_y);
        // 缩放
        matrix.postScale(zoom, zoom,origin_x,origin_y);
        // 旋转
        matrix.postRotate(rotate,origin_x,origin_y);

        matrix.postTranslate(x,y);
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
            float a = ((float)alpha)/255;

            // 生成色彩矩阵
            ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                    r,0,0,0,0,
                    0,g,0,0,0,
                    0,0,b,0,0,
                    0,0,0,a,0
            });
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

//            ---------------------
//                    作者：启舰
//            来源：CSDN
//            原文：https://blog.csdn.net/harvic880925/article/details/51253944
//            版权声明：本文为博主原创文章，转载请附上博文链接！
        }
        else{
            //设置透明度
            paint.setAlpha(alpha);
        }

        canvas.drawBitmap(bitmap,matrix,paint);
    }

    //绘制一个十字
    private void drawLines(Canvas canvas,int x,int y){
        Paint paint = new Paint();
        canvas.drawLine(x,0,x,getHeight(),paint);
        canvas.drawLine(0,y,getWidth(),y,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


canvas.drawBitmap(bitmap,0,0,null);
        drawBitmap(canvas, bitmap,origin_x,origin_y,x,y,zoom,1,rotate,alpha,color);
        drawLines(canvas,x,y);
    }
}

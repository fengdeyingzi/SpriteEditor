package com.xl.spriteeditor.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.xl.game.tool.ImageUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
一个简单的图片翻转 旋转的工具
支持将图片进行翻转，旋转，流光特效
支持高亮特效 透明度特效
 */
public class BitmapSpriteHelper {
    Bitmap bitmap;
    public BitmapSpriteHelper(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    //获得bitmap裁剪阵列
    ArrayList<Bitmap> getBitmapList(int item_w, int item_h){
        ArrayList<Bitmap> list_bitmap = new ArrayList<>();
        for(int iy=0;iy+item_h<bitmap.getHeight();iy+=item_h){


        for(int ix=0;ix+item_w<bitmap.getWidth();ix+=item_w){
            list_bitmap.add(Bitmap.createBitmap(bitmap,ix,iy,item_h,item_h));
        }
        }
        return list_bitmap;
    }

    //翻转图片，获得翻转后的bitmap阵列
    Bitmap bitmapFlip(float zoom_v,float zoom_h){
        Bitmap bitmap_new = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap_new);
        Matrix matrix = new Matrix();
        float origin_x=((float)bitmap.getWidth())/2;
        float origin_y = ((float)bitmap.getHeight())/2;

        Paint paint = new Paint();
        matrix.postScale(zoom_h, zoom_v,origin_x,origin_y);
        canvas.drawBitmap(bitmap,matrix,paint);
        return bitmap_new;
    }

//翻转多张
    Bitmap bitmapFlip(int item_w,int item_h, float zoom_v,float zoom_h){
        Bitmap bitmap_new = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap_new);

        float origin_x=((float)item_w)/2;
        float origin_y = ((float)item_h)/2;

        Paint paint = new Paint();
        ArrayList<Bitmap > list_bitmap= getBitmapList(item_w,item_h);
        for(int i=0;i<list_bitmap.size();i++){
            int ix=0;int iy=0;
            Bitmap temp = list_bitmap.get(i);
            Matrix matrix = new Matrix();
            matrix.postScale(zoom_h, zoom_v,origin_x,origin_y);
            matrix.setTranslate(ix,iy);
            canvas.drawBitmap(temp,matrix,paint);
            ix+=item_w;
            if(ix+item_w<bitmap.getWidth()){
                ix=0;
                iy+= item_h;
            }
        }

        return bitmap_new;
    }

    //旋转图片，获得旋转后的bitmap阵列
    ArrayList<Bitmap> bitmapSpin(int size){
        ArrayList<Bitmap> list_bitmap = new ArrayList();

    }

    //透明度特效，获得处理后的bitmap阵列

    //流光特效

    //上下运动特效

    //



}

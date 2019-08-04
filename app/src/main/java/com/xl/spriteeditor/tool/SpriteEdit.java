package com.xl.spriteeditor.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class SpriteEdit {
    private static final String TAG = "SpriteEdit";
    //精灵文件名
    File filename;
    File file_bitmap;
    Bitmap bitmap; //精灵素材
    String name; //精灵文件名
    String author; //作者
    Bitmap icon; //缩略图
    ArrayList<Slice> array_slice;


    public SpriteEdit(){
        this.array_slice = new ArrayList<>();
    }

    public SpriteEdit(Bitmap bitmap){
        this.bitmap = bitmap;
        this.array_slice = new ArrayList<>();
    }

    public void setBitmapFile(File file){
        this.file_bitmap = file;
        this.bitmap = BitmapFactory.decodeFile(file_bitmap.getPath());
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }



    //单张切片
    public class Slice{
       int x;
       int y;
       int w;
       int h;

       public int getX(){
           return x;
       }
       public int getY(){
           return y;
       }

       public int getWidth(){
           return w;
       }

       public int getHeight(){
           return h;
       }

       public Rect getRect(){
           return new Rect(x,y,w,h);
       }
    }

    //添加一张切片 参数：x,y,w,h
    public void addSlice(int x,int y,int w,int h){
        Slice slice = new Slice();
        if(w<0){
            w = -w;
            x-= w;
        }
        if(h<0){
            h = -h;
            y -= h;
        }
        slice.x = x;
        slice.y = y;
        slice.w = w;
        slice.h= h;
        if(slice.x<0) {
            slice.w +=slice.x;
            slice.x=0;
        }
        if(slice.y<0) {
            slice.h += slice.y;
            slice.y=0;
        }
        if(slice.x + slice.w >bitmap.getWidth()) slice.w = bitmap.getWidth()-slice.x;
        if(slice.y + slice.h > bitmap.getHeight()) slice.h = bitmap.getHeight()-slice.y;
        Log.i(TAG, "addSlice: "+x+" "+y);
        array_slice.add(slice);
    }

    //删除一张切片 参数：位图点击的x y坐标
    public void deleteFromXY(int x,int y){
        for(int i=0;i<array_slice.size();i++){
            Slice slice = array_slice.get(i);
            if(x>=slice.x && y>=slice.y && x<slice.x+slice.w && y<slice.y+slice.h){
                array_slice.remove(slice);
                return;
            }
        }
    }

    public ArrayList<Slice> getSliceList(){
        return array_slice;
    }


    //创建一个精灵编辑 参数：画布宽度 画布高度

    //导入一张图片素材

    //获取图片素材列表

    //获取画布

    //获取动作列表

    //获取帧列表

    //获取切片列表（从画布获取）

    //


}

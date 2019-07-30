package com.xl.spriteeditor.tool;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * 2帧编辑 关联包含切片 可设置每个切片的移动 旋转 缩放
 */
public class Frame {

    public class ClipRect{
        int id; //关联的切片id
        public Bitmap bitmap; //最终处理的图片
        int x;
        int y;
        int origin_x; //原点坐标
        int origin_y;
        int zoom;
        int alpha; //不透明度
        int color; //颜色 为0表示不修改颜色
    }

    ArrayList<ClipRect> list_rect;

    public Frame(){
        this.list_rect = new ArrayList<>();
    }

    //获取所有切片
    public ArrayList<ClipRect> getClipRectList(){
        return list_rect;
    }

    //删除一张切片
    public void deleteSliceRect(ClipRect clipRect){
        list_rect.remove(clipRect);
    }

    //添加一张切片
    public void addSliceRect(int id,Bitmap bitmap){

        ClipRect clipRect = new ClipRect();
        clipRect.x = 0;
        clipRect.y= 0;
        clipRect.origin_x = bitmap.getWidth()/2;
        clipRect.origin_y = bitmap.getHeight()/2;
        clipRect.id = id;
    }

    //获取帧图片
    public Bitmap getBitmap(){

        return null;
    }


}

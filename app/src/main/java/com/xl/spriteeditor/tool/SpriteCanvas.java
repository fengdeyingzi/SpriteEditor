package com.xl.spriteeditor.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * 虚拟的画布素材，通过导入切片实现
 * 1 切片
 */
public class SpriteCanvas {


    /**
     * 单张切片类
     */
    public class SliceRect {
        int id;
        int x;
        int y;
        int width;
        int height;

    }

    Bitmap bitmap;
    ArrayList<SliceRect> list_rect;

    //创建一张画布
    public SpriteCanvas(Bitmap bitmap){
        this.bitmap = bitmap;
        this.list_rect = new ArrayList<>();
    }



    //移动一张切片

    //添加一张切片

    //通过id获取一张切片

    //通过坐标获取一张切片

    //获取一个切片的bitmap
    public Bitmap getSliceRectBitmap(int id){
        for(int i=0;i<list_rect.size();i++){
            SliceRect rect = list_rect.get(i);


            if(rect.id == id){
                return Bitmap.createBitmap(bitmap, rect.x, rect.y, rect.width, rect.height);
            }
        }
        return null;
    }

}

package com.xl.spriteeditor.tool;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * 3 动作编辑 包含帧
 */
public class Action {
    String name; //动作名字
    int id; //动作id
    ArrayList<Frame> list_action;

    public Action(){
        this.list_action = new ArrayList<>();
    }

    //获取动作缩略图（就是首张帧的图片）
    public Bitmap getFirstBitmap(){
        if(list_action.size()>0){
            return list_action.get(0).getBitmap();
        }
        return null;
    }



}

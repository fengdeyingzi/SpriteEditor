package com.xl.spriteeditor.tool;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xl.game.math.Str;
import com.xl.game.tool.FileUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;

/*
用于生成精灵文件及精灵图片的工具类

 */
public class SpriteFileBuilder {

    String text;

    //单张图片生成精灵
    @SuppressLint("DefaultLocale")
    public boolean imgToSprite(String path,int sprite_width,int sprite_height){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        String sprite_name = FileUtils.getName(path);
        File file = new File(path);
        File file_out = new File(file.getParent(), sprite_name.substring(0,sprite_name.length()-4)+".sprite");

        int bitmap_width = bitmap.getWidth();
        int bitmap_height = bitmap.getHeight();
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("<sprite bitmap=\"%s\" width=\"%d\" height=\"%d\" >\n",sprite_name, sprite_width, sprite_height));
        buffer.append("  <action name=\"none\" mode=\"1\" >\n");
        for(int iy = 0;iy+sprite_height<bitmap_height;iy+=sprite_height){
            for(int ix=0;ix+sprite_width<bitmap_width;ix+=sprite_width){
                buffer.append("    <picture >\n");
                buffer.append(String.format("      <rectflip \nx=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" px=\"%d\" py=\"%d\"\n      />\n", ix, iy, sprite_width, sprite_height,0,0));
                buffer.append("    </picture>\n");
            }
        }
        buffer.append("  </action>\n");
        buffer.append("</sprite>\n");
        text = buffer.toString();
        FileUtils.writeText(file_out.getPath(),buffer.toString());
        return true;
    }

    //获取文件后缀
    private String getFileEndName(String path){

        int endindex = Str.strrchr(path,'.');
        String endName = null;
        if(endindex>0) {
            endName = path.substring(endindex);
            endName = endName.toLowerCase();
            return endName;
        }
        return "";
    }

    //单个文件夹生成精灵
    @SuppressLint("DefaultLocale")
    public boolean singleDirToSprite(String path,int sprite_width,int sprite_height){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        String sprite_name = FileUtils.getName(path);
        File file = new File(path);
        File file_out = new File(path, file.getName()+".sprite");
        File[]  file_list= file.listFiles();
        int len = file_list.length;
        StringBuffer buffer = new StringBuffer();
        ArrayList<File> list_img = new ArrayList<>();
        buffer.append(String.format("<sprite bitmap=\"%s\" width=\"%d\" height=\"%d\" >\n",sprite_name, sprite_width, sprite_height));
        buffer.append("  <action name=\"none\" mode=\"1\" >\n");
        //过滤png图片
        for(int i=0;i<len;i++){
            File item = file_list[i];
            if(item.isFile()){
                if(getFileEndName(item.getName()).equals(".png")){
                    list_img.add(item);

                }
            }

        }

        Comparator comp = new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return compareName(o1.getName(), o2.getName());
            }

            //按名字比较 用于排列图片
            public int compareName(String str1,String str2){
                try {
                    byte[] b1 = str1.getBytes("GBK");
                    byte[] b2 = str2.getBytes("GBK");
                    int l1=b1.length;
                    int l2=b2.length;
                    int l=Math.min(l1, l2);
                    int k=0;
                    while(k<l){
                        int bt1=b1[k]&0xff;
                        int bt2=b2[k]&0xff;
                        if(bt1!=bt2)
                            return bt1-bt2;
                        k++;
                    }
                    return l1-l2;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return 0;
            }

        };
        Collections.sort(list_img,comp);
        for(int i=0;i<list_img.size();i++){
            File item = list_img.get(i);
            Bitmap bitmap1 = BitmapFactory.decodeFile(item.getPath());
            int ix=0;int iy=0;
            buffer.append("    <picture >\n");
            buffer.append(String.format("      <rectflip x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" px=\"%d\" py=\"%d\" />\n", ix, iy, bitmap1.getWidth(), bitmap1.getHeight(),0,0));
            buffer.append("    </picture>\n");
        }
        buffer.append("  </action>\n");
        buffer.append("</sprite>\n");
        FileUtils.writeText(file_out.getPath(),buffer.toString());
        text= buffer.toString();
        return true;
    }

    //多个文件夹生成精灵


    @NonNull
    @Override
    public String toString() {
        if(text != null)
        return text;
        return super.toString();
    }
}

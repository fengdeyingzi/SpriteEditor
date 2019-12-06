package com.xl.spriteeditor.tool;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.xl.game.math.Str;
import com.xl.game.tool.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    Bitmap bitmap;
    JSONObject jsonObject=null;
    private static final String TAG = "SpriteFileBuilder";

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
    public boolean singleDirToSprite(String path,int sprite_width,int sprite_height) throws JSONException {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        String sprite_name = FileUtils.getName(path);
        File file = new File(path);
        File file_out = new File(path, file.getName()+".sprite");
        File[]  file_list= file.listFiles();
        int len = file_list.length;
        int ix=0;int iy=0;
        int bitmap_width = 0;
        int bitmap_height = 0;
        StringBuffer buffer = new StringBuffer();
        ArrayList<File> list_img = new ArrayList<>();
        JSONObject json_sprite = new JSONObject();
        JSONArray array_actions = new JSONArray();
        json_sprite.put("bitmap",sprite_name);
        json_sprite.put("width",sprite_width);
        json_sprite.put("height",sprite_height);
        json_sprite.put("actions", array_actions);
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

            buffer.append("    <picture >\n");
            buffer.append(String.format("      <rectflip x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" px=\"%d\" py=\"%d\" />\n", 0,0, bitmap1.getWidth(), bitmap1.getHeight(),ix, iy));
            buffer.append("    </picture>\n");
            JSONObject obj_pic = new JSONObject();
            obj_pic.put("x",0);
            obj_pic.put("y",0);
            obj_pic.put("px",ix);
            obj_pic.put("py",iy);
            obj_pic.put("width",bitmap1.getWidth());
            obj_pic.put("height",bitmap1.getHeight());
            array_actions.put(obj_pic);
            ix+=bitmap1.getWidth();
            bitmap_width = ix+bitmap1.getWidth();
            bitmap_height = Math.max(bitmap1.getHeight(),bitmap_height);
        }
        buffer.append("  </action>\n");
        buffer.append("</sprite>\n");
        FileUtils.writeText(file_out.getPath(),buffer.toString());
        text= buffer.toString();
        json_sprite.put("bitmap_width", bitmap_width);
        json_sprite.put("bitmap_height", bitmap_height);

        //生成bitmap
        Bitmap bitmap_temp = Bitmap.createBitmap(bitmap_width,bitmap_height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap_temp);
        for(int i=0;i<list_img.size();i++){
            File item = list_img.get(i);
            Bitmap bitmap1 = BitmapFactory.decodeFile(item.getPath());
            ix+=bitmap1.getWidth();
            canvas.drawBitmap(bitmap1, ix,iy,null);
        }
        jsonObject = json_sprite;
        this.bitmap = bitmap_temp;
        return true;
    }

    //多个文件夹生成精灵
    public JSONObject dirToSprite(String path, int sprite_width, int sprite_height) throws JSONException {
        File dirPath = new File(path);

        int ix=0,iy=0;
        File[] list_temp_dir = dirPath.listFiles();
        int max_x=0;
        int max_y=0;
        ArrayList<File> list_action = FileListUtil.listDir(new File(path));


        JSONObject json_sprite = new JSONObject();
        JSONArray array_action = new JSONArray();
        json_sprite.put("actions",array_action);
        for(File file:list_action){
            ArrayList<File> list_file = FileListUtil.listFile(file);
            JSONArray array_picture = new JSONArray();
            for(File temp:list_file){
                String endName = FileUtils.getFileEndName(temp.getName());
                if(endName==null || !endName.equals(".png"))continue;
                JSONObject json_pic = new JSONObject();
                json_pic.put("path",temp.getAbsolutePath());
                json_pic.put("px",ix);
                json_pic.put("py",iy);
                json_pic.put("x",0);
                json_pic.put("y",0);
                json_pic.put("width",sprite_width);
                json_pic.put("height",sprite_height);
                ix+=sprite_width;
                max_x = Math.max(ix,max_x);
                array_picture.put(json_pic);
            }
            ix=0;
            iy+=sprite_height;
            max_y = Math.max(iy,max_y);
            array_action.put(array_picture);
        }
        json_sprite.put("name",dirPath.getName());
        json_sprite.put("width",sprite_width);
        json_sprite.put("height",sprite_height);
        json_sprite.put("bitmap",dirPath.getName()+".png");
        json_sprite.put("bitmap_width",max_x+sprite_width);
        json_sprite.put("bitmap_height",max_y+sprite_height);
        Bitmap bitmap = Bitmap.createBitmap(json_sprite.getInt("bitmap_width"),json_sprite.getInt("bitmap_height"),Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        for(int i=0;i<array_action.length();i++){
            JSONArray obj_action = array_action.getJSONArray(i);
            for(int j=0;j<obj_action.length();j++){
                JSONObject obj_picture  = obj_action.getJSONObject(j);
                Bitmap bitmap_temp = BitmapFactory.decodeFile(obj_picture.getString("path"));
                Log.i(TAG, "dirToSprite: "+obj_picture.getString("path"));
                int dx = obj_picture.getInt("px");
                int dy = obj_picture.getInt("py");
                if(bitmap_temp!=null){
                    canvas.drawBitmap(bitmap_temp, dx,dy,null);
                    Log.i(TAG, "dirToSprite: draw "+dx+","+dy);
                }

            }
        }
        this.bitmap = bitmap;
        jsonObject = json_sprite;
        return json_sprite;
    }





    @NonNull
    @Override
    public String toString() {
        if(text != null)
        return text;
        return super.toString();
    }

    public JSONObject getJSONObject(){
        return jsonObject;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

}

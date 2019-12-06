package com.xl.spriteeditor.tool;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FileListUtil {

    //获取文件夹中所有的文件，并排序返回
    public static ArrayList<File> listFile(File dirPath){
        File[] list_temp_dir = dirPath.listFiles();
        ArrayList<File> list_action = new ArrayList<>();
        if(list_temp_dir==null)return list_action;
        for(File temp:list_temp_dir){
            if(temp.isFile())
            list_action.add(temp);
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
        Collections.sort(list_action,comp);
        return list_action;
    }

    //获取文件夹中所有文件夹，并排序
    public static ArrayList<File> listDir(File dirPath){
        File[] list_temp_dir = dirPath.listFiles();
        ArrayList<File> list_action = new ArrayList<>();
        if(list_temp_dir==null)return list_action;
        for(File temp:list_temp_dir){
            if(temp.isDirectory())
                list_action.add(temp);
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
        Collections.sort(list_action,comp);
        return list_action;
    }
}

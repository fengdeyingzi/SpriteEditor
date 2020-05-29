package com.xl.spriteeditor.debug;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.xl.game.math.Str;
import com.xl.game.tool.DisplayUtil;
import com.xl.spriteeditor.R;


/**
 * suthor:风的影子
 * <p>
 * date:2020/5/29
 * desc:
 * version:1.0
 **/
public class DebugXmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        LinearLayout layout_root = new LinearLayout(context);
        LinearLayout linearlayout_0 = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams_0 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        linearlayout_0.setOrientation(LinearLayout.VERTICAL);
        com.google.android.material.appbar.AppBarLayout layout_appbar = new com.google.android.material.appbar.AppBarLayout(context);
        LinearLayout.LayoutParams layoutParams_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_appbar.setId(R.id.layout_appbar);
        androidx.appcompat.widget.Toolbar toolbar = new androidx.appcompat.widget.Toolbar(context);
        com.google.android.material.appbar.AppBarLayout.LayoutParams layoutParams_2 = new com.google.android.material.appbar.AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        toolbar.setId(R.id.toolbar);
        layout_appbar.addView(toolbar,layoutParams_2);
        linearlayout_0.addView(layout_appbar,layoutParams_1);
        LinearLayout linearlayout_3 = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams_3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        linearlayout_3.setPadding(DisplayUtil.dip2px(context, Str.atoi("16dp")), DisplayUtil.dip2px(context, Str.atoi("16dp")), DisplayUtil.dip2px(context, Str.atoi("16dp")), 0);
        linearlayout_3.setOrientation(LinearLayout.VERTICAL);
        Button btn_singleimg = new Button(context);
        LinearLayout.LayoutParams layoutParams_4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_singleimg.setId(R.id.btn_singleimg);
        btn_singleimg.setText("单张图片生成精灵");
        linearlayout_3.addView(btn_singleimg,layoutParams_4);
        Button btn_multiple_img = new Button(context);
        LinearLayout.LayoutParams layoutParams_5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_multiple_img.setId(R.id.btn_multiple_img);
        btn_multiple_img.setText("多张图片生成精灵");
        linearlayout_3.addView(btn_multiple_img,layoutParams_5);
        Button btn_dir_img = new Button(context);
        LinearLayout.LayoutParams layoutParams_6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_dir_img.setId(R.id.btn_dir_img);
        btn_dir_img.setText("文件夹生成精灵");
        linearlayout_3.addView(btn_dir_img,layoutParams_6);
        TextView textview_7 = new TextView(context);
        LinearLayout.LayoutParams layoutParams_7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        textview_7.setText("目前只实现了单张图片生成精灵，多张图片生成精灵正在开发中");
        linearlayout_3.addView(textview_7,layoutParams_7);
        linearlayout_0.addView(linearlayout_3,layoutParams_3);
        layout_root.addView(linearlayout_0,layoutParams_0);
        setContentView(layout_root);
setSupportActionBar(toolbar);
setContentView(R.layout.debug_weight);












    }
}

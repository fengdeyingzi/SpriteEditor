package com.xl.spriteeditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.xl.game.tool.ViewTool;


/**
 * suthor:风的影子
 * <p>
 * date:2019/11/28
 * desc:
 * version:1.0
 **/
public class LoadActivity extends Activity {

    Handler handler;
    private ImageView img_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        initView();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 3000);
    }

    void start() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }


    private void initView() {
        img_logo = (ImageView) findViewById(R.id.img_logo);
        ViewTool.setShowAnimation(img_logo,2500);
    }
}

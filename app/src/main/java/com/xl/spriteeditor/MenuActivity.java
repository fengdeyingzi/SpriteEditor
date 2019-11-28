package com.xl.spriteeditor;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.android.material.appbar.AppBarLayout;


/**
 * suthor:风的影子
 * <p>
 * date:2019/11/28
 * desc:
 * version:1.0
 **/
public class MenuActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 单张图片生成精灵
     */
    private Button btn_singleimg;
    /**
     * 多张图片生成精灵
     */
    private Button btn_multiple_img;
    /**
     * 文件夹生成精灵
     */
    private Button btn_dir_img;
    private Toolbar toolbar;
    private AppBarLayout layout_appbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_menu);
        initView();
        requestPermission();
    }

    private void initView() {
        btn_singleimg = (Button) findViewById(R.id.btn_singleimg);
        btn_singleimg.setOnClickListener(this);
        btn_multiple_img = (Button) findViewById(R.id.btn_multiple_img);
        btn_multiple_img.setOnClickListener(this);
        btn_dir_img = (Button) findViewById(R.id.btn_dir_img);
        btn_dir_img.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layout_appbar = (AppBarLayout) findViewById(R.id.layout_appbar);
        setSupportActionBar(toolbar);
        setTitle("sprite文件快速生成");
    }


    public void requestPermission() {
        PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {

            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_singleimg:
                gotoActivity(SingleToSpriteActivity.class);
                break;
            case R.id.btn_multiple_img:
                break;
            case R.id.btn_dir_img:
                break;
        }
    }

}

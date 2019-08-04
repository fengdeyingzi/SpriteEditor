package com.xl.spriteeditor;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 显示logo
 * 加载联网
 */
public class LogoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gotoActivity(SliceEditActivity.class);
    }



}

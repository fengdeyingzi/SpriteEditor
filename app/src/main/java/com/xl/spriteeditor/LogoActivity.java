package com.xl.spriteeditor;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class LogoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gotoActivity(SpriteListActivity.class);
    }



}

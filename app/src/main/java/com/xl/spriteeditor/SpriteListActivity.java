package com.xl.spriteeditor;

import android.os.Bundle;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.xl.spriteeditor.view.TestView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 精灵列表界面
 */
public class SpriteListActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprlist);

        setContentView(new TestView(this));

    }

    public void requestPermission(){
        PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {

            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {

            }
        }, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onResume() {
        requestPermission();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.xl.spriteeditor;

import android.Manifest;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.xl.spriteeditor.view.TestView;

/**
 * 精灵列表界面
 */
public class SpriteListActivity extends BaseActivity {

    private TestView textview;
    private SeekBar seek_origin_x;
    private SeekBar seek_origin_y;
    private SeekBar seek_zoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprlist);

        setContentView(R.layout.activity_test);
        initView();
seek_origin_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textview.serOriginX(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});


seek_origin_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textview.setOriginY(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
seek_zoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textview.setZoom(progress/100f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});

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

    private void initView() {
        textview = (TestView) findViewById(R.id.textview);
        seek_origin_x = (SeekBar) findViewById(R.id.seek_origin_x);
        seek_origin_y = (SeekBar) findViewById(R.id.seek_origin_y);
        seek_zoom = findViewById(R.id.seek_zoom);
    }
}

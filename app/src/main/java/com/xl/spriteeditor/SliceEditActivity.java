package com.xl.spriteeditor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xl.spriteeditor.tool.SpriteEdit;
import com.xl.spriteeditor.view.SliceEditView;

import java.io.File;

import androidx.annotation.Nullable;

public class SliceEditActivity extends BaseActivity implements View.OnClickListener {

    private SliceEditView slice_edit;
    /**
     * 切割模式
     */
    private Button btn_mode;
    private ImageView btn_more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliceedit);
        initView();
    }

    private void initView() {
        slice_edit = (SliceEditView) findViewById(R.id.slice_edit);
        btn_mode = (Button) findViewById(R.id.btn_mode);
        btn_mode.setOnClickListener(this);
        btn_more = (ImageView) findViewById(R.id.btn_more);
        btn_more.setOnClickListener(this);
        SpriteEdit spriteEdit = new SpriteEdit();
        spriteEdit.setBitmapFile(new File("/mnt/sdcard/1.jpg"));
        slice_edit.setSpriteEdit(spriteEdit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_mode:
                break;
            case R.id.btn_more:
                break;
        }
    }
}

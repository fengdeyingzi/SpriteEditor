package com.xl.spriteeditor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.xl.view.FileSelectView;

import androidx.annotation.Nullable;

public class MultipleImgToSpriteActivity extends Activity implements View.OnClickListener {

    /**
     * 输入精灵宽度
     */
    private EditText edit_width;
    /**
     * 输入精灵高度
     */
    private EditText edit_height;
    /**
     * 生成精灵
     */
    private Button btn_tosprite;
    /**
     * 预览
     */
    private Button btn_look;
    /**
     * 生成后信息
     */
    private TextView text_toinfo;
    private FileSelectView fileSelectView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_img_tosprite);
        initView();
    }


    private void initView() {
        edit_width = (EditText) findViewById(R.id.edit_width);
        edit_height = (EditText) findViewById(R.id.edit_height);
        btn_tosprite = (Button) findViewById(R.id.btn_tosprite);
        btn_tosprite.setOnClickListener(this);
        btn_look = (Button) findViewById(R.id.btn_look);
        btn_look.setOnClickListener(this);
        text_toinfo = (TextView) findViewById(R.id.text_toinfo);
        fileSelectView = (FileSelectView) findViewById(R.id.fileselectview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_tosprite:
                break;
            case R.id.btn_look:
                break;
        }
    }
}

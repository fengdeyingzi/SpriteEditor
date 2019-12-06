package com.xl.spriteeditor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xl.game.math.Str;
import com.xl.game.tool.ImageUtil;
import com.xl.spriteeditor.tool.SpriteFileBuilder;
import com.xl.view.FileSelectView;

import org.json.JSONException;

import java.io.File;

import androidx.annotation.Nullable;

public class MultipleImgToSpriteActivity extends BaseActivity implements View.OnClickListener {

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
        fileSelectView.selectDir();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_tosprite:
//                singleDirToSprite
                SpriteFileBuilder builder = new SpriteFileBuilder();
                int sprite_width = Str.atoi(edit_width.getText().toString());
                int sprite_height = Str.atoi(edit_height.getText().toString());
                if(sprite_width>0 && sprite_height>0 ){
                    try {
                        builder.singleDirToSprite(fileSelectView.getPath(), sprite_width,sprite_height);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        text_toinfo.setText(e.toString());
                    }
                    String info = builder.toString();
                    text_toinfo.setText(info);
                    File dirPath = new File(fileSelectView.getPath());
                    ImageUtil.saveBitmapToPNG(builder.getBitmap(),dirPath.getParentFile().getPath()+ File.separator+"sprite.png");
                }
                else {
                    toast("请输入正确的精灵宽高");
                }
                break;
            case R.id.btn_look:
                break;
        }
    }
}

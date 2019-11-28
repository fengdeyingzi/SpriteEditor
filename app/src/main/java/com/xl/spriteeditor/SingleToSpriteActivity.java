package com.xl.spriteeditor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xl.game.math.Str;
import com.xl.game.tool.FileUtils;
import com.xl.spriteeditor.tool.SpriteFileBuilder;
import com.xl.view.FileSelectView;

import androidx.annotation.Nullable;


/**
 * suthor:风的影子
 * <p>
 * date:2019/11/28
 * desc:
 * version:1.0
 **/
public class SingleToSpriteActivity extends BaseActivity implements View.OnClickListener {

    private FileSelectView fileselectview;
    /**
     * 当前图片信息：
     */
    private TextView text_info;
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
     * 单个动作生成精灵\n选择一张图片，然后输入精灵的宽高，根据这个宽高和图片的宽高生成精灵。
     */
    private TextView text_toinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tosprite);
        initView();
    }

    private void initView() {
        fileselectview = (FileSelectView) findViewById(R.id.fileselectview);
        text_info = (TextView) findViewById(R.id.text_info);
        edit_width = (EditText) findViewById(R.id.edit_width);
        edit_height = (EditText) findViewById(R.id.edit_height);
        btn_tosprite = (Button) findViewById(R.id.btn_tosprite);
        btn_tosprite.setOnClickListener(this);
        text_toinfo = (TextView) findViewById(R.id.text_toinfo);
        fileselectview.setOnSelectListener(new FileSelectView.OnSelectListener() {
            @Override
            public void onSelectFile(String path) {
                if(FileUtils.getFileEndName(path).equals(".png")){
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if(bitmap!=null){
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        text_info.setText(String.format("图片宽度：%d 图片高度：%d",width,height));

                    }
                }
                else{
                    toast("不是png图片");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_tosprite: //生成精灵
                SpriteFileBuilder builder = new SpriteFileBuilder();
                int sprite_width = Str.atoi(edit_width.getText().toString());
                int sprite_height = Str.atoi(edit_height.getText().toString());
                if(sprite_width>0 && sprite_height>0 ){
                    builder.imgToSprite(fileselectview.getPath(), sprite_width,sprite_height);
                    String info = builder.toString();
                    text_toinfo.setText(info);
                }
                else {
                    toast("请输入正确的精灵宽高");
                }

                break;
        }
    }




}

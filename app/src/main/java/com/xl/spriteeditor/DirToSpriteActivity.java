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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import androidx.annotation.Nullable;

public class DirToSpriteActivity extends BaseActivity implements View.OnClickListener {

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
     * 生成带多个动作的精灵\n选择一个文件夹，这个文件夹内包含多个子文件夹，文件夹名字按照action_001,action_002的方式命名，这些文件夹内包含多个图片，图片的名字按照xx_001.png,xx_002.png的方式命名，将这个文件夹生成一个精灵文件，包含精灵的png文件及sprite文件。
     */
    private TextView text_toinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir_tosprite);
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
        fileselectview.selectDir();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_tosprite:
                SpriteFileBuilder builder = new SpriteFileBuilder();
                int sprite_width = Str.atoi(edit_width.getText().toString());
                int sprite_height = Str.atoi(edit_height.getText().toString());
                if(sprite_width>0 && sprite_height>0 ){
                    try {
                        JSONObject jsonObject = builder.dirToSprite(fileselectview.getPath(), sprite_width,sprite_height);
                        //生成xml
                        String sprite_text = jsonSpriteToXml(jsonObject);
                        text_toinfo.setText(sprite_text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        toast(e.toString());
                        String info = builder.toString();
                        text_toinfo.setText(e.toString());
                    }

                }
                else {
                    toast("请输入正确的精灵宽高");
                }
                //保存bitmap到文件夹
                File fileDir = new File(fileselectview.getPath());

                ImageUtil.saveBitmapToPNG(builder.getBitmap(), fileDir.getPath()+ File.separator+"sprite.png");

                break;
        }
    }

    //将json转xml精灵
    String jsonSpriteToXml(JSONObject jsonObject) throws JSONException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<sprite \n");

        buffer.append("  bitmap=\""+jsonObject.getString("bitmap")+"\"\n");
        buffer.append("  width=\""+jsonObject.getInt("width")+"\" \n");
        buffer.append("  height=\""+jsonObject.getInt("height")+"\"\n");
        buffer.append("  bitmap_width=\""+jsonObject.getInt("bitmap_width")+"\"\n");
        buffer.append("  bitmap_height=\""+jsonObject.getInt("bitmap_height")+"\"\n");
        buffer.append(">\n");
        JSONArray array_action = jsonObject.getJSONArray("action");
        for(int i=0;i<array_action.length();i++){
            buffer.append("    <action\n");
            buffer.append("    name=\"action_"+i+"\"\n");
            buffer.append("    mode=\"1\"\n");
            buffer.append("    >\n");
            JSONArray array_pic = array_action.getJSONArray(i);
            for(int j=0;j<array_pic.length();j++){
                JSONObject obj_pic = array_pic.getJSONObject(j);
                buffer.append("      <picture\n");
                buffer.append("      >\n");
                buffer.append("        <rectflip\n");
                buffer.append("        x=\""+obj_pic.getInt("x")+"\"\n");
                buffer.append("        y=\""+obj_pic.getInt("y")+"\"\n");
                buffer.append("        width=\""+obj_pic.getInt("width")+"\"\n");
                buffer.append("        height=\""+obj_pic.getInt("height")+"\"\n");
                buffer.append("        px=\""+obj_pic.getInt("px")+"\"\n");
                buffer.append("        py=\""+obj_pic.getInt("py")+"\"\n");
                buffer.append("        />");
                buffer.append("      </picture>\n");
            }
            buffer.append("    </action>\n");
        }



        buffer.append("</sprite>\n");
        return buffer.toString();
    }


}

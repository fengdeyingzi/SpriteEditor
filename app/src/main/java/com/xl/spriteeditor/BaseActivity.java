package com.xl.spriteeditor;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


public void gotoActivity(Class cl){
    Intent intent = new Intent(this,cl);
    startActivity(intent);

}


public void toast(String text){
    Toast t = Toast.makeText(this,text, Toast.LENGTH_SHORT);
    t.setText(text);
    t.show();
}



}

package com.xl.spriteeditor;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


public void gotoActivity(Class cl){
    Intent intent = new Intent(this,cl);
    startActivity(intent);

}



}

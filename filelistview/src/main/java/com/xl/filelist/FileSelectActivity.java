package com.xl.filelist;

import android.app.Activity;
import android.os.Bundle;
import com.xl.view.FileSelectView;
import android.widget.LinearLayout;

public class FileSelectActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Material);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		FileSelectView fileSelectView = new FileSelectView(this);
		layout.addView(fileSelectView);
		setContentView(layout);
		
		//fileSelectView.setThemeBlack(false);
		fileSelectView.selectDir();
		
	}
	
}

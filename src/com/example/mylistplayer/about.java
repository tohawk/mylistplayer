package com.example.mylistplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class about extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setTitle("关于本播放器");
		TextView text = (TextView)findViewById(R.id.abouttext);
		TextView my = (TextView)findViewById(R.id.my);
		ImageView image = (ImageView)findViewById(R.id.image);
		image.setImageResource(R.drawable.tohawk180);
		text.setText(R.string.abouttext);
		my.setText(R.string.my);
	}
	
}

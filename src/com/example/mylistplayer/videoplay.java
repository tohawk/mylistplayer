package com.example.mylistplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class videoplay extends Activity{
	VideoView video;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.videoview);
		video = (VideoView)findViewById(R.id.video);
	//	Intent intent = new Intent();
		Intent intent = getIntent();
		String videopath=intent.getStringExtra("videopath");
	//	Toast.makeText(this, videopath, Toast.LENGTH_SHORT).show();
		MediaController controller = new MediaController(this);
		video.setMediaController(controller);
		controller.setMediaPlayer(video);
		
		if(videopath != null){	
		video.setVideoPath(videopath);
		video.start();
		}
		else
			Toast.makeText(this, "没有得到路径", Toast.LENGTH_SHORT).show();
		
	}
	@Override
	protected void onRestoreInstanceState(Bundle outState) {
	    int sec = (int) outState.getLong("time");
	        video.seekTo(sec);
	    super.onRestoreInstanceState(outState);
	    }
	 
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	        int sec = video.getCurrentPosition();
	    outState.putLong("time", sec);
	    super.onSaveInstanceState(outState);
	    }
	
}

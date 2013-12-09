package com.example.mylistplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener{
	ListView listview;
	List<String> path,dispname,image;
	//String[] path,dispname,image;
	private String[] mediainfo = {MediaStore.Video.Media.DATA,MediaStore.Video.Media.DISPLAY_NAME,
			MediaStore.Images.Thumbnails._ID};
	private void getVideoList(){
		
		path = new ArrayList<String>();
		dispname = new ArrayList<String>();
		image = new ArrayList<String>();
		String tmppath,tmpname,tmpimg;
		
		
		int i=0;
		//String[] path=null,dispname=null,image=null;
		Cursor cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				mediainfo, 
				null, null, null);
		if(cursor == null){
			Toast.makeText(this, "没有视频文件", Toast.LENGTH_SHORT).show();
		}
		else{
			cursor.moveToFirst();
			//ProgressBar precessbar = (ProgressBar)findViewById(R.id.loading);
			//precessbar.setVisibility(View.VISIBLE);
			while(!cursor.isLast()){
				tmppath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
				tmpname = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
				tmpimg = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
				image.add(tmpimg);
				dispname.add(tmpname);
				path.add(tmppath);
				cursor.moveToNext();
			}
			
			tmppath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
			tmpname = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
			tmpimg = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
			image.add(tmpimg);
			dispname.add(tmpname);
			path.add(tmppath);
			
			//precessbar.setVisibility(View.GONE);
			/*
			while(!cursor.isLast()){
				path[i] = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
				dispname[i] = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
				image[i] = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
				i++;
				cursor.moveToNext();
			}
			path[i] = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
			dispname[i] = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
			image[i] = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
			*/
		}
	}
	private void initAdpter(){
		/*
		
		ArrayAdapter<String> adpter = new ArrayAdapter<String>(this, R.layout.listtext, R.id.listext, dispname);
		listview.setAdapter(adpter);
		*/
	
		int i;
		List<Map<String, Object>> mlist = new ArrayList<Map<String,Object>>();
		int[] ids = {R.id.name,R.id.listimg};
		Map<String,Object> mmap;// = new HashMap<String, Object>();;
		Bitmap bitmap;
		for(i=0;i<dispname.size();i++){
			bitmap = ThumbnailUtils.createVideoThumbnail(path.get(i), MediaStore.Images.Thumbnails.MICRO_KIND);
			mmap = new HashMap<String, Object>();
			mmap.put("name",dispname.get(i));
			mmap.put("img", bitmap);
			mlist.add(mmap);
		}
		
		SimpleAdapter adpter = new SimpleAdapter(this, mlist, R.layout.listview, new String[]{"name","img"}, ids);
		
		adpter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View arg0, Object arg1, String arg2) {
				// TODO Auto-generated method stub
				if(arg0 instanceof ImageView && arg1 instanceof Bitmap ){
					ImageView vi = (ImageView)arg0;
					vi.setImageBitmap((Bitmap)arg1);
					return true;
				}
				else
					return false;
			}
		});
		
		listview.setAdapter(adpter);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ListView)findViewById(R.id.listview);
		setTitle("视频列表");
		
		getVideoList();
		initAdpter();
		
		listview.setOnItemClickListener(this);
		ActionBar action = getActionBar();
		action.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 1, R.string.exit);
		menu.add(0, 2, 2, R.string.abuot);
		MenuItem refresh = menu.add(0,0,0,"刷新");
		refresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		if(item.getItemId()==1){
			finish();
		}
		if(item.getItemId()==2){
			
			intent.setClass(this, about.class);
			startActivity(intent);
		}
		if(item.getItemId()==0){
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
			
			Toast.makeText(this, "已经刷新", Toast.LENGTH_SHORT).show();
			
			getVideoList();
			//当adpter为ArrayList时刷新用，能正常使用
			//adpter.notifyDataSetChanged();
			initAdpter();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//String index = path.get(arg2);
		//Toast.makeText(this,path.get(arg2), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.putExtra("videopath",path.get(arg2));
		intent.setClass(this, videoplay.class);
		startActivity(intent);
	}

}

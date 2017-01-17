package com.qtech.soft.activity;

import java.util.HashMap;

import com.neuedu.soft.activity.R;
import com.qtech.soft.service.UserInfoService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
/**
 * alt+/
 * @author 姚文豪
 *
 */
public class WelcomeActivity extends Activity {
	private UserInfoService userInfoService=null;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg){
			Intent intent=new Intent();
			switch (msg.what) {
			case 1:
//				实现activity的跳转
				intent.setClass(WelcomeActivity.this, LoginActivity.class);
//				记得finish（）!把自己关闭
				finish();
				break;
			case 2:
				intent.setClass(WelcomeActivity.this, MainActivity.class);
				HashMap<String, String>item=(HashMap<String, String>) msg.obj;
//				参数名必须与登录页面传递的名一致
				intent.putExtra("info", item);
			default:
				break;
			}
			startActivity(intent);
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		this.userInfoService=new UserInfoService(this);
	}
	protected void onResume(){
//		必须调用父类的该方法
		super.onResume();
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				SharedPreferences sp=getSharedPreferences("info", MODE_PRIVATE);
				String name=sp.getString("login_name","");
				String pwd=sp.getString("login_pwd","");
				HashMap<String, String>item=userInfoService.checkLogin(name, pwd);
//				不管数据有没有取到数据，都直接去数据库查询
				try {
//					休眠两秒钟
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				休眠，结束，再判断是否登陆
				Message msg=new Message();
				if(item==null){
					msg.what=1;
				}
				else{
//					表示登录成功去主页面
					msg.what=2;
					msg.obj=item;
				}
//				发送一个空消息
				handler.sendMessage(msg);
			}
		});
		t.start();
	}
}

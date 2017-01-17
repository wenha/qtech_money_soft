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
 * @author Ҧ�ĺ�
 *
 */
public class WelcomeActivity extends Activity {
	private UserInfoService userInfoService=null;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg){
			Intent intent=new Intent();
			switch (msg.what) {
			case 1:
//				ʵ��activity����ת
				intent.setClass(WelcomeActivity.this, LoginActivity.class);
//				�ǵ�finish����!���Լ��ر�
				finish();
				break;
			case 2:
				intent.setClass(WelcomeActivity.this, MainActivity.class);
				HashMap<String, String>item=(HashMap<String, String>) msg.obj;
//				�������������¼ҳ�洫�ݵ���һ��
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
//		������ø���ĸ÷���
		super.onResume();
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				SharedPreferences sp=getSharedPreferences("info", MODE_PRIVATE);
				String name=sp.getString("login_name","");
				String pwd=sp.getString("login_pwd","");
				HashMap<String, String>item=userInfoService.checkLogin(name, pwd);
//				����������û��ȡ�����ݣ���ֱ��ȥ���ݿ��ѯ
				try {
//					����������
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				���ߣ����������ж��Ƿ��½
				Message msg=new Message();
				if(item==null){
					msg.what=1;
				}
				else{
//					��ʾ��¼�ɹ�ȥ��ҳ��
					msg.what=2;
					msg.obj=item;
				}
//				����һ������Ϣ
				handler.sendMessage(msg);
			}
		});
		t.start();
	}
}

package com.qtech.soft.activity;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.neuedu.soft.activity.R;
import com.qtech.soft.service.UserInfoService;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText editLoginame;
	private EditText editLoginPwd;
	private CheckBox ckAutoLogin;
	private CheckBox ckShowPwd;
	private Button btnSubmit;
	private Button btnReset;
	private TextView tvToReg;
	private UserInfoService userInfoService=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("登录");
		setContentView(R.layout.activity_login);
		findView();
//		initData();
		setListener();
	}

	private void setListener() {
		this.btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				获取数据并验证
				String name=editLoginame.getText().toString();
				String pwd=editLoginPwd.getText().toString();
//				使用正则表达式验证
				String pattern="[a-zA-Z0-9]{6,12}";
				if(Pattern.matches(pattern,name)==false){
					editLoginame.setError("用户名格式有误");
//					editLoginame.setText("");
//					选择输入的内容
					editLoginame.selectAll();
					return;
				}
				if(Pattern.matches(pattern, pwd)==false){
					editLoginame.setError("密码输入有误");
//					editLoginame.setText("密码输入有误");
//					清空密码框
					editLoginPwd.setText("");
					return;
				}
//				应该去连接数据库，实现登陆验证
				HashMap<String, String>item=
						userInfoService.checkLogin(name, pwd);
				if(item==null){
//					应该弹出对话框！toast的show（）！！！！！！！！
					Toast.makeText(LoginActivity.this, "用户名或密码有误", 2000).show();
				}else{
//					需要自动登录
					if(ckAutoLogin.isChecked()){
						SharedPreferences sp=getSharedPreferences("info", MODE_PRIVATE);
//						获取编辑器
						SharedPreferences.Editor edit=sp.edit();
						edit.putString("login_name", name);
						edit.putString("login_pwd", pwd);
//						提交数据
						edit.commit();
					}
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
//					把登录信息传递到mainActivity
					intent.putExtra("info", item);
					startActivity(intent);
//					把该页面关掉
					finish();
//					跳转到主界面
				}
				//自动登陆,要写文件
			}
		});
		this.btnReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editLoginame.setText("");
				editLoginPwd.setText("");
//				把checkbox也清空
				ckAutoLogin.setChecked(false);
				ckShowPwd.setChecked(false);
//				设置密码框威隐藏显示
				editLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}
		});
		this.tvToReg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				跳转到注册页面，注册成功后，把用户名和密码显示到登录界面控件中
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this,RegActivity.class);
//				还需要获取返回值
				startActivityForResult(intent, 666);
			}
		});
//		给checkbox添加单击事件实现密码的隐藏与显示
		this.ckShowPwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ckShowPwd.isChecked()){
//					显示密码内容
					editLoginPwd.setTransformationMethod(
							HideReturnsTransformationMethod.getInstance());
				}else{
//					隐藏密码内容
					editLoginPwd.setTransformationMethod(
							PasswordTransformationMethod.getInstance());
				}
			}
		});
	}
//重写处理返回值得方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
//		添加处理返回数据的代码
		if(resultCode==200){
//			根据requestcode判断是谁发起请求的返回
			switch (requestCode) {
			case 666://是toreg发起的请求
//				获取返回的数据
				String name=data.getStringExtra("login_name");
				String pwd=data.getStringExtra("login_pwd");
//				给页面控件赋值
				editLoginame.setText(name);
				editLoginPwd.setText(pwd);
				break;
			default:
				break;
			}
		}
	}
	private void findView() {
//		初始化相关对象
		this.userInfoService=new UserInfoService(this);
		this.editLoginame=(EditText) findViewById(R.id.login_edit_loginname);
		this.editLoginPwd=(EditText) findViewById(R.id.login_edit_pwd);
		
		this.btnSubmit=(Button) findViewById(R.id.login_btn_login);
		this.btnReset=(Button) findViewById(R.id.login_btn_reset);
		this.tvToReg=(TextView) findViewById(R.id.login_tv_toreg);
		
		this.ckAutoLogin=(CheckBox) findViewById(R.id.login_ck_autologin);
		this.ckShowPwd=(CheckBox) findViewById(R.id.login_ck_showpwd);
	}
}

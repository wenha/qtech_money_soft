package com.qtech.soft.activity;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.neuedu.soft.activity.R;
import com.qtech.soft.service.UserInfoService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditMainActivity extends Activity {
	private EditText editLoginame;
	private EditText editLoginPwd;
	private EditText editnewLoginPwd01;
	private EditText editnewLoginPwd02;
	
	private RadioButton rbBoy;
	private RadioButton rbGirl;
	
	private Button btnSubmit;
	private Button btnBack;
	
	private UserInfoService userInfoService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("修改密码");
		setContentView(R.layout.activity_edit_main);
		findView();
		setListener();
	}

	private void findView() {
		userInfoService=new UserInfoService(this);
		this.editLoginame=(EditText) findViewById(R.id.edit_edit_loginname);
		this.editLoginPwd=(EditText) findViewById(R.id.edit_edit_loginpwd);
		this.editnewLoginPwd01=(EditText) findViewById(R.id.edit_edit_new_loginpwd01);
		this.editnewLoginPwd02=(EditText) findViewById(R.id.edit_edit_new_loginpwd02);
		
		this.rbBoy=(RadioButton) findViewById(R.id.edit_rb_boy);
		this.rbGirl=(RadioButton) findViewById(R.id.edit_rb_girl);
		
		this.btnBack=(Button) findViewById(R.id.edit_btn_reset);
		this.btnSubmit=(Button) findViewById(R.id.edit_btn_submit);
	}


	private void setListener() {
		Intent intent=getIntent();
		final HashMap<String, String>info=
				(HashMap<String, String>) intent.getSerializableExtra("info");
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String sex="男";
				if(rbGirl.isChecked()){
					sex="女";
				}
				final String name=editLoginame.getText().toString();
				String oldpwd=editLoginPwd.getText().toString();
				final String pwd1=editnewLoginPwd01.getText().toString();
				String pwd2=editnewLoginPwd02.getText().toString();
				String pattern="[a-zA-Z0-9]{6,12}";
				String id=info.get("user_id");
				{
				boolean flag=userInfoService.checkName(name);
				if(flag&&(!name.equals(info.get("user_name")))){
//					已经存在
					editLoginame.setError("该用户名已存在!");
					
					editLoginame.selectAll();
					editLoginame.requestFocus();
					return;
				}
			   }
				
				if(!Pattern.matches(pattern, name)){
					editLoginame.setError("格式错误!");
					editLoginame.selectAll();
					return;
				}
				if(!oldpwd.equals(info.get("user_pwd"))){
					editLoginPwd.setError("密码输入错误!");
					editLoginPwd.selectAll();
					return;
				}
				if(!pwd1.equals(pwd2)){
					Toast.makeText(EditMainActivity.this, "两次输入密码不一致!", 1000);
					editnewLoginPwd01.setText("");
					editnewLoginPwd02.setText("");
					editnewLoginPwd01.requestFocus();
					return;
				}
				boolean flag=userInfoService.doUpdate(name, pwd1, sex,id);
				if(flag){
					AlertDialog.Builder alert=
							new AlertDialog.Builder(EditMainActivity.this);
					alert.setTitle("提示");
					if(flag){
						alert.setMessage("修改成功！是否去登录界面？");
						alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								关闭当前页面，返回登录界面
//								同时把用户注册的用户名和密码传递到登录界面
								Intent intent=new Intent();
								intent.putExtra("login_name",name);
								intent.putExtra("login_pwd",pwd1);

								setResult(300,intent);
								
								intent.setClass(EditMainActivity.this, LoginActivity.class);
								startActivity(intent);
								finish();
							}
						});
						alert.setNegativeButton("取消", null);
					}else{
						alert.setMessage("修改失败,你输入的数据有误!");
						alert.setPositiveButton("确定", null);
					}
					alert.show();
				}
			}
		});
	}
//	private boolean checkusername(String name){
//		
//	}
}

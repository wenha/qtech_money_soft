package com.neuedu.soft.activity;

import java.util.regex.Pattern;

import com.neuedu.soft.service.UserInfoService;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegActivity extends Activity {
	
	private EditText editRegLoginame;
	private EditText editRegLoginPwd;
	private EditText editRegReLoginPwd;
	
	private RadioButton rbBoy;
	private RadioButton rbGirl;
	
	private Button btnRegSubmit;
	private Button btnRegReset;
	
//	创建相关联的对象
	private UserInfoService userInfoService=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("注册");
		setContentView(R.layout.activity_reg);
		findView();
//		initData();
		setListener();
	}

	private void setListener() {
//		this.editRegLoginame.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if(!hasFocus){
//					String name=editRegLoginame.getText().toString();
////					必须先验证格式，格式正确再验证是否可用
//					boolean flag=userInfoService.checkName(name);
//					if(flag){
////						已经存在
//						editRegLoginame.setError("该用户名已存在!");
//						
//						editRegLoginame.selectAll();
//						editRegLoginame.requestFocus();
//					}
//				}
//				
//			}
//		});
		this.btnRegSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String name=editRegLoginame.getText().toString();
				final String pwd=editRegLoginPwd.getText().toString();
				String rePwd=editRegReLoginPwd.getText().toString();
				String sex="男";
				if(rbGirl.isChecked()){
					sex="女";
				}
				
				//验证数据，与登录页面的验证相同
				//使用正则表达式验证
				String pattern="[a-zA-Z0-9]{6,12}";{
				boolean flag=userInfoService.checkName(name);
				if(flag){
					//已经存在
					editRegLoginame.setError("该用户名已存在!");
					
					editRegLoginame.selectAll();
					editRegLoginame.requestFocus();
					return;
				}}
				
				if(Pattern.matches(pattern,name)==false){
					editRegLoginame.setError("用户名以字母开头，数字结尾！");
					//editLoginame.setText("");
					//选择输入的内容
					editRegLoginPwd.selectAll();
					return;
				}
				if(Pattern.matches(pattern, pwd)==false){
					editRegLoginPwd.setError("密码格式输入有误");
					//editLoginame.setText("密码输入有误");
					//清空密码框
					editRegLoginPwd.setText("");
					editRegReLoginPwd.setText("");
					return;
				}
				
//				验证确认密码，只要判断是否与密码相同
				if(pwd.equals(rePwd)==false){
					Toast.makeText(RegActivity.this, "两次密码不一致", 1000).show();
		//			editRegLoginame.setText("");
					editRegReLoginPwd.setText("");
//					密码框获取焦点
					editRegLoginPwd.requestFocus();
					return;
				}
//				实现向数据库中插入数据
				boolean flag=userInfoService.doReg(name, pwd, sex);
//				判断是否成功
				if(flag){
					AlertDialog.Builder alert=
							new AlertDialog.Builder(RegActivity.this);
					alert.setTitle("提示");
					if(flag){
						alert.setMessage("注册成功！是否去登录界面？");
						alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								关闭当前页面，返回登录界面
//								同时把用户注册的用户名和密码传递到登录界面
								Intent intent=new Intent();
								intent.putExtra("login_name",name);
								intent.putExtra("login_pwd",pwd);
//								把数据返回
								setResult(200,intent);
//								,并把当前页面关闭
								finish();
								
							}
						});
						alert.setNegativeButton("取消", null);
					}else{
						alert.setMessage("注册失败,你输入的数据有误!");
						alert.setPositiveButton("确定", null);
					}
					alert.show();//!!!!!!!
				}else{

				}
			}
		});
		this.btnRegReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				清空控件中的内容，与登录界面的重置功能相同
				editRegLoginame.setText("");
				editRegReLoginPwd.setText("");
				editRegLoginPwd.setText("");
			}
		});
	}

	private void findView() {
//		初始化相关对象
		this.userInfoService=new UserInfoService(this);
		this.editRegLoginame=(EditText) findViewById(R.id.reg_edit_loginname);
		this.editRegLoginPwd=(EditText) findViewById(R.id.reg_edit_loginpwd);
		this.editRegReLoginPwd=(EditText) findViewById(R.id.reg_edit_re_loginpwd);
		
		this.btnRegSubmit=(Button) findViewById(R.id.reg_btn_submit);
		this.btnRegReset=(Button) findViewById(R.id.reg_btn_reset);
		
		this.rbBoy=(RadioButton) findViewById(R.id.reg_rb_boy);
		this.rbGirl=(RadioButton) findViewById(R.id.reg_rb_girl);
	}
}

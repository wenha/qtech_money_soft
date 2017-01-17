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
		actionBar.setTitle("��¼");
		setContentView(R.layout.activity_login);
		findView();
//		initData();
		setListener();
	}

	private void setListener() {
		this.btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				��ȡ���ݲ���֤
				String name=editLoginame.getText().toString();
				String pwd=editLoginPwd.getText().toString();
//				ʹ��������ʽ��֤
				String pattern="[a-zA-Z0-9]{6,12}";
				if(Pattern.matches(pattern,name)==false){
					editLoginame.setError("�û�����ʽ����");
//					editLoginame.setText("");
//					ѡ�����������
					editLoginame.selectAll();
					return;
				}
				if(Pattern.matches(pattern, pwd)==false){
					editLoginame.setError("������������");
//					editLoginame.setText("������������");
//					��������
					editLoginPwd.setText("");
					return;
				}
//				Ӧ��ȥ�������ݿ⣬ʵ�ֵ�½��֤
				HashMap<String, String>item=
						userInfoService.checkLogin(name, pwd);
				if(item==null){
//					Ӧ�õ����Ի���toast��show��������������������
					Toast.makeText(LoginActivity.this, "�û�������������", 2000).show();
				}else{
//					��Ҫ�Զ���¼
					if(ckAutoLogin.isChecked()){
						SharedPreferences sp=getSharedPreferences("info", MODE_PRIVATE);
//						��ȡ�༭��
						SharedPreferences.Editor edit=sp.edit();
						edit.putString("login_name", name);
						edit.putString("login_pwd", pwd);
//						�ύ����
						edit.commit();
					}
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
//					�ѵ�¼��Ϣ���ݵ�mainActivity
					intent.putExtra("info", item);
					startActivity(intent);
//					�Ѹ�ҳ��ص�
					finish();
//					��ת��������
				}
				//�Զ���½,Ҫд�ļ�
			}
		});
		this.btnReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editLoginame.setText("");
				editLoginPwd.setText("");
//				��checkboxҲ���
				ckAutoLogin.setChecked(false);
				ckShowPwd.setChecked(false);
//				�����������������ʾ
				editLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}
		});
		this.tvToReg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				��ת��ע��ҳ�棬ע��ɹ��󣬰��û�����������ʾ����¼����ؼ���
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this,RegActivity.class);
//				����Ҫ��ȡ����ֵ
				startActivityForResult(intent, 666);
			}
		});
//		��checkbox��ӵ����¼�ʵ���������������ʾ
		this.ckShowPwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ckShowPwd.isChecked()){
//					��ʾ��������
					editLoginPwd.setTransformationMethod(
							HideReturnsTransformationMethod.getInstance());
				}else{
//					������������
					editLoginPwd.setTransformationMethod(
							PasswordTransformationMethod.getInstance());
				}
			}
		});
	}
//��д������ֵ�÷���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
//		��Ӵ��������ݵĴ���
		if(resultCode==200){
//			����requestcode�ж���˭��������ķ���
			switch (requestCode) {
			case 666://��toreg���������
//				��ȡ���ص�����
				String name=data.getStringExtra("login_name");
				String pwd=data.getStringExtra("login_pwd");
//				��ҳ��ؼ���ֵ
				editLoginame.setText(name);
				editLoginPwd.setText(pwd);
				break;
			default:
				break;
			}
		}
	}
	private void findView() {
//		��ʼ����ض���
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

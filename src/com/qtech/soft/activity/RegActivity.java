package com.qtech.soft.activity;

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
	
//	����������Ķ���
	private UserInfoService userInfoService=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("ע��");
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
////					��������֤��ʽ����ʽ��ȷ����֤�Ƿ����
//					boolean flag=userInfoService.checkName(name);
//					if(flag){
////						�Ѿ�����
//						editRegLoginame.setError("���û����Ѵ���!");
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
				String sex="��";
				if(rbGirl.isChecked()){
					sex="Ů";
				}
				
				//��֤���ݣ����¼ҳ�����֤��ͬ
				//ʹ��������ʽ��֤
				String pattern="[a-zA-Z0-9]{6,12}";{
				boolean flag=userInfoService.checkName(name);
				if(flag){
					//�Ѿ�����
					editRegLoginame.setError("���û����Ѵ���!");
					
					editRegLoginame.selectAll();
					editRegLoginame.requestFocus();
					return;
				}}
				
				if(Pattern.matches(pattern,name)==false){
					editRegLoginame.setError("�û�������ĸ��ͷ�����ֽ�β��");
					//editLoginame.setText("");
					//ѡ�����������
					editRegLoginPwd.selectAll();
					return;
				}
				if(Pattern.matches(pattern, pwd)==false){
					editRegLoginPwd.setError("�����ʽ��������");
					//editLoginame.setText("������������");
					//��������
					editRegLoginPwd.setText("");
					editRegReLoginPwd.setText("");
					return;
				}
				
//				��֤ȷ�����룬ֻҪ�ж��Ƿ���������ͬ
				if(pwd.equals(rePwd)==false){
					Toast.makeText(RegActivity.this, "�������벻һ��", 1000).show();
		//			editRegLoginame.setText("");
					editRegReLoginPwd.setText("");
//					������ȡ����
					editRegLoginPwd.requestFocus();
					return;
				}
//				ʵ�������ݿ��в�������
				boolean flag=userInfoService.doReg(name, pwd, sex);
//				�ж��Ƿ�ɹ�
				if(flag){
					AlertDialog.Builder alert=
							new AlertDialog.Builder(RegActivity.this);
					alert.setTitle("��ʾ");
					if(flag){
						alert.setMessage("ע��ɹ����Ƿ�ȥ��¼���棿");
						alert.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								�رյ�ǰҳ�棬���ص�¼����
//								ͬʱ���û�ע����û��������봫�ݵ���¼����
								Intent intent=new Intent();
								intent.putExtra("login_name",name);
								intent.putExtra("login_pwd",pwd);
//								�����ݷ���
								setResult(200,intent);
//								,���ѵ�ǰҳ��ر�
								finish();
								
							}
						});
						alert.setNegativeButton("ȡ��", null);
					}else{
						alert.setMessage("ע��ʧ��,���������������!");
						alert.setPositiveButton("ȷ��", null);
					}
					alert.show();//!!!!!!!
				}else{

				}
			}
		});
		this.btnRegReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				��տؼ��е����ݣ����¼��������ù�����ͬ
				editRegLoginame.setText("");
				editRegReLoginPwd.setText("");
				editRegLoginPwd.setText("");
			}
		});
	}

	private void findView() {
//		��ʼ����ض���
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

package com.neuedu.soft.activity;

import com.neuedu.soft.service.TypeInfoService;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class TypeActivity extends Activity {
//	收入类型
	private RadioButton radioIncome;
//	支出类型
	private RadioButton radioSpend;
	
	private EditText editTypeName;
	private EditText editTypeContent;
	
	private Button btnSubmit;
	private Button btnBack;

	private TypeInfoService typeInfoService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("添加类别");
		setContentView(R.layout.activity_type);
		findView();
		setListener();
	}

	private void findView() {
//		初始化
		typeInfoService=new TypeInfoService(this);
		
		this.radioIncome=(RadioButton) findViewById(R.id.type_radio_income);
		this.radioSpend=(RadioButton) findViewById(R.id.type_radio_spend);
		
		this.editTypeName=(EditText) findViewById(R.id.type_edit_type_name);
		this.editTypeContent=(EditText) findViewById(R.id.type_eidt_type_content);
		
		this.btnSubmit=(Button) findViewById(R.id.type_btn_submit);
		this.btnBack=(Button) findViewById(R.id.type_btn_back);
	}

	private void setListener() {
		this.btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int typeStatus=1;
				if(radioSpend.isChecked()){
					typeStatus=2;
				}
				String typeName=editTypeName.getText().toString();
				String typeContent=editTypeContent.getText().toString();
//				插入到数据库中
				boolean flag=typeInfoService.doSave(typeName, typeContent, typeStatus);
				String msg="添加失败!";
				if(flag){
					msg="类型添加成功!";
				}
//				记得show（）；！！！！！！
				Toast.makeText(TypeActivity.this, msg, 1000).show();
			}
		});
		this.btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
}

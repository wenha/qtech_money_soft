package com.neuedu.soft.activity;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends Activity {
	private TextView textName;
	private TextView textSex;
	private Button btnEdit;
	private Button btnBack;
	
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("个人信息");
		setContentView(R.layout.activity_info);
		findView();
		setListener();
	}

	private void findView() {
		this.textName=(TextView) findViewById(R.id.info_text_name);
		this.textSex=(TextView) findViewById(R.id.info_text_sex);
		
		this.btnBack=(Button) findViewById(R.id.info_btn_back);
		this.btnEdit=(Button) findViewById(R.id.info_btn_edit);
		intent=getIntent();
		HashMap<String, String>item=
				(HashMap<String, String>) intent.getSerializableExtra("info");
		String name=item.get("user_name");
		String sex=item.get("user_sex");
		textName.setText(name);
		textSex.setText(sex);
		
	}

	private void setListener() {
		this.btnEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(InfoActivity.this, EditMainActivity.class);
				finish();
				startActivity(intent);
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

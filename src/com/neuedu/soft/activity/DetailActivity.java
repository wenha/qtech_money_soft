package com.neuedu.soft.activity;

import java.util.HashMap;

import com.neuedu.soft.service.SumInfoService;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity {
	private TextView textType;
	private TextView textPrice;
	private TextView textContent;
	private Button btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		setContentView(R.layout.activity_detail);
		findView();
		setListener();
	}

	private void findView() {
		this.textContent=(TextView) findViewById(R.id.detail_text_content);
		this.textPrice=(TextView) findViewById(R.id.detail_text_price);
		this.textType=(TextView) findViewById(R.id.detail_text_type);
		this.btnBack=(Button) findViewById(R.id.detail_btn_back);
		Intent intent=getIntent();
		HashMap<String, String>item=
				(HashMap<String, String>) intent.getSerializableExtra("item");
		String type=item.get("type_name");
		String price=item.get("income_price");
		String content=item.get("income_content");
		textContent.setText(content);
		textPrice.setText(price);
		textType.setText(type);
	}

	private void setListener() {
		this.btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}

package com.neuedu.soft.activity;

import java.util.HashMap;

import com.neuedu.soft.service.SumInfoService;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SumActivity extends Activity {
	private TextView textIncome;
	private TextView textOutcome;
	private TextView textRest;
	private Button btnBack;
	private SumInfoService sumInfoService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("统计");
		setContentView(R.layout.activity_sum);
		findView();
		setListener();
	}
	private void setListener() {
		this.btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	private void findView() {
		sumInfoService=new SumInfoService(this);
		this.textIncome=(TextView) findViewById(R.id.sum_text_income);
		this.textOutcome=(TextView) findViewById(R.id.sum_text_outcome);
		this.textRest=(TextView) findViewById(R.id.sum_text_rest);
		this.btnBack=(Button) findViewById(R.id.sum_btn_back);
		initData();
	}
	private void initData() {
		HashMap<String, String> item=sumInfoService.findSum();
		double income=0;
		double outcome=0;
		if(item.get("income_sum")!=null){
			income=Double.parseDouble(item.get("income_sum")) ;
			
		}
		if(item.get("outcome_sum")!=null){
			outcome=Double.parseDouble(item.get("outcome_sum"));
		}
		double rest=income-outcome;
		textIncome.setText(String.valueOf(income));
		textOutcome.setText(String.valueOf(outcome));
		textRest.setText(String.valueOf(rest));
//		设置颜色的值
//		必须放在res/values/colors.xml
		int color=0;
		if(rest<0){
			color=R.color.red;
		}else if(rest>0){
			color=R.color.blue;
		}
		else{
			color=R.color.black;
		}
		textRest.setTextColor(color);
	}
}

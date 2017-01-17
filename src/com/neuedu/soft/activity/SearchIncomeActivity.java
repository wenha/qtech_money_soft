package com.neuedu.soft.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.neuedu.soft.service.IncomeInfoSevice;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchIncomeActivity extends Activity {
	private TextView textbegintime;
	private TextView textendtime;
	private Button btnSearch;
	private Button btnBack;
	ListView listview;
	
	private List<HashMap<String, String>>data;
	private SimpleAdapter adapter;
	private IncomeInfoSevice incomeInfoService;
	Calendar calendar;
	int year1;
	int month1;
	int day1;
	int year2;
	int month2;
	int day2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("收入查询");
		setContentView(R.layout.activity_search_income);
		findView();
		init();
		setListener();
	}


	private void init() {
		calendar=Calendar.getInstance();
		this.year1=calendar.get(Calendar.YEAR);
//		该month比实际月份小1
		this.month1=calendar.get(Calendar.MONTH);
		this.day1=calendar.get(Calendar.DAY_OF_MONTH);
		this.textbegintime.setText(year1+"-"+(month1+1)+"-"+day1);
		year2=year1;
		month2=month1;
		day2=day1;
		this.textendtime.setText(year1+"-"+(month1+1)+"-"+day1);
	}


	private void setListener() {
		this.listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				获取改行数据
//				通过意图传递到下一个界面
				Intent intent=new Intent();
				HashMap<String, String> item=data.get(position);
				intent.putExtra("item", item);
			    intent.setClass(SearchIncomeActivity.this, DetailActivity.class);
			    startActivity(intent);
			}
		});
		this.btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String begin=textbegintime.getText().toString();
				String end=textendtime.getText().toString();
//				从数据库中查找
				List<HashMap<String, String>> list=incomeInfoService.findByDate(begin, end);
				if(list==null){
					Toast.makeText(SearchIncomeActivity.this, "没有", 2000).show();
				}else{
					data.clear();
//					添加数据
					data.addAll(list);
//					唤醒适配器
					adapter.notifyDataSetChanged();
				}
			}
		});
		this.textbegintime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog=new DatePickerDialog(SearchIncomeActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							 
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								textbegintime.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
							}
						}, year1, month1, day1);
				dialog.show();
			}
		});
		this.textendtime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DatePickerDialog dialog=new DatePickerDialog(SearchIncomeActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								textendtime.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
							}
						}, year2, month2, day2);
				dialog.show();
			}
		});
		this.btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void findView() {
		this.incomeInfoService=new IncomeInfoSevice(this);
		this.textbegintime=(TextView) findViewById(R.id.search_oncome_text_begin);
		this.textendtime=(TextView) findViewById(R.id.search_oncome_text_end);
		this.btnSearch=(Button) findViewById(R.id.search_income_btn_search);
		this.btnBack=(Button) findViewById(R.id.search_income_btn_back);
		this.listview=(ListView) findViewById(R.id.search_income_listView);
		data=new ArrayList<HashMap<String,String>>();
		adapter=new SimpleAdapter(this,data,R.layout.search_result_item,
				new String[]{"income_date","type_name"},
				new int[]{R.id.search_item_date,R.id.search_item_type});
		listview.setAdapter(adapter);
	}
}

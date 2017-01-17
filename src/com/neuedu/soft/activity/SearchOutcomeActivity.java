package com.neuedu.soft.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.neuedu.soft.service.IncomeInfoSevice;
import com.neuedu.soft.service.OutcomeInfoService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchOutcomeActivity extends Activity {
	private TextView textbegintime;
	private TextView textendtime;
	private Button btnSearch;
	private Button btnBack;
	private ListView listview;
	
	private List<HashMap<String, String>>data;
	private SimpleAdapter adapter;
	private OutcomeInfoService outcomeInfoService;
	
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
		actionBar.setTitle("֧����ѯ");
		setContentView(R.layout.activity_search_outcome);
		findView();
		init();
		setListener();
	}


	private void setListener() {
		this.btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String begin=textbegintime.getText().toString();
				String end=textendtime.getText().toString();
//				�����ݿ��в���
				List<HashMap<String, String>> list=outcomeInfoService.findByDate(begin, end);
				if(list==null){
					Toast.makeText(SearchOutcomeActivity.this, "û��", 2000).show();
				}else{
					data.clear();
//					�������
					data.addAll(list);
//					����������
					adapter.notifyDataSetChanged();
				}
			}
		});
		this.textbegintime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog=new DatePickerDialog(SearchOutcomeActivity.this, 
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
				
				DatePickerDialog dialog=new DatePickerDialog(SearchOutcomeActivity.this, 
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
		this.listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				��ȡ��������
//				ͨ����ͼ���ݵ���һ������
				Intent intent=new Intent();
				HashMap<String, String> item1=data.get(position);
				HashMap<String, String> item=new HashMap<String, String>();
				item.put("income_price", item1.get("outcome_price"));
				item.put("income_content", item1.get("outcome_content"));
				item.put("type_name", item1.get("type_name"));
				intent.putExtra("item", item);
			    intent.setClass(SearchOutcomeActivity.this, DetailActivity.class);
			    startActivity(intent);
			}
		});
	}


	private void init() {
		calendar=Calendar.getInstance();
		this.year1=calendar.get(Calendar.YEAR);
//		��month��ʵ���·�С1
		this.month1=calendar.get(Calendar.MONTH);
		this.day1=calendar.get(Calendar.DAY_OF_MONTH);
		this.textbegintime.setText(year1+"-"+(month1+1)+"-"+day1);
		year2=year1;
		month2=month1;
		day2=day1;
		this.textendtime.setText(year1+"-"+(month1+1)+"-"+day1);
		
	}


	private void findView() {
		this.outcomeInfoService=new OutcomeInfoService(this);
		this.textbegintime=(TextView) findViewById(R.id.search_oncome_text_begin);
		this.textendtime=(TextView) findViewById(R.id.search_oncome_text_end);
		this.btnSearch=(Button) findViewById(R.id.search_income_btn_search);
		this.btnBack=(Button) findViewById(R.id.search_income_btn_back);
		this.listview=(ListView) findViewById(R.id.search_income_listView);
		data=new ArrayList<HashMap<String,String>>();
		adapter=new SimpleAdapter(this,data,R.layout.search_result_item,
				new String[]{"outcome_date","type_name"},
				new int[]{R.id.search_item_date,R.id.search_item_type});
		listview.setAdapter(adapter);
		
	}
}

package com.neuedu.soft.activity;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class JsqActivity extends Activity {
	private TextView textResult;
	private TextView textbegintime;
	private TextView textendtime;
	private EditText editPrice;
	private EditText editLv;
	private Button btnJs;
	
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
		actionBar.setTitle("计算");
		setContentView(R.layout.activity_jsq);
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

	private void findView() {
		this.btnJs=(Button) findViewById(R.id.jsq_btn_back);
		editPrice=(EditText) findViewById(R.id.jsq_edit_price);
		editLv=(EditText) findViewById(R.id.jsq_edit_lv);
		
		textbegintime=(TextView) findViewById(R.id.jsq_text_begin);
		textendtime=(TextView) findViewById(R.id.jsq_text_end);
		textResult=(TextView) findViewById(R.id.jsq_text_result);
	}

	private void setListener() {
		this.textbegintime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog=new DatePickerDialog(JsqActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							 
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								textbegintime.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
								year1=year;
								
							}
						}, year1, month1, day1);
				dialog.show();
			}
		});
		this.textendtime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DatePickerDialog dialog=new DatePickerDialog(JsqActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								textendtime.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
								year2=year;
							}
						}, year2, month2, day2);
				dialog.show();
			}
		});
		btnJs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					double price=0;
					double lv=0;
					try {
					double time=0;
					
					if(year2<year1){
						Toast.makeText(JsqActivity.this, "结束时间不能小于起始时间", 1000).show();
						return;
					}					
					else{
						time=year2-year1;
					}
					price=Double.parseDouble(editPrice.getText().toString());
					lv=Double.parseDouble(editLv.getText().toString());
					String result=price*time*lv/100+"";
					textResult.setText(result);
					
				} catch (NumberFormatException e) {
					Toast.makeText(JsqActivity.this, "利率或金额格式有误!", 1000).show();
				}
			}
		});
	}
//	public int caculate(){
//		int time=0;
//		if(year2<year1){
//			Toast.makeText(this, "结束时间不能小于起始时间", 1000);
//		}
//////		else{
//////			if(day2<day1){
//////				month2-=1;
//////				day2=da
//////			}
//////			if(month2<month1){
//////				year2-=1;
//////			}
//////			if(year2<year1){
//////				Toast.makeText(this, "结束时间不能小于起始时间", 1000);
//////			}
////			
////		}
//		else{
//			time=year2-year1;
//		}
//		return time;
//	}
}

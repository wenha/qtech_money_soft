package com.qtech.soft.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.neuedu.soft.activity.R;
import com.qtech.soft.service.IncomeInfoSevice;
import com.qtech.soft.service.OutcomeInfoService;
import com.qtech.soft.service.TypeInfoService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OutcomeActivity extends Activity {
//	显示录入的时间
	private TextView textDate;
	private TextView textType;//类型
	
	private EditText editPrice;
	private EditText editContent;//说明
	
	private Button btnSave;
	private Button btnBack;
//	
	private TypeInfoService typeInfoService;
	private OutcomeInfoService outcomeInfoSevice;
	
//	创建保存选择的类名,id
	private String typeName;
	private int typeId;
	
//	设置时间给页面显示和对话框使用
	private int year;
	private int month;
	private int day;
	private Calendar calendar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("支出");
		setContentView(R.layout.activity_outcome);
		findView();
		setListener();
	}
	private void findView() {
		outcomeInfoSevice=new OutcomeInfoService(this);
		typeInfoService=new TypeInfoService(this);
		textDate=(TextView) findViewById(R.id.income_text_date);
		textType=(TextView) findViewById(R.id.income_text_type);
		
		editContent=(EditText) findViewById(R.id.income_text_content);
		editPrice=(EditText) findViewById(R.id.income_text_price);
		
		btnSave=(Button) findViewById(R.id.income_btn_save);
		btnBack=(Button) findViewById(R.id.income_btn_back);
		initData();
	}
//	创建保存类别名的集合
	private List<String>typeNames;
//	创建保存类别编号的集合
	private List<String>typeIds;
	private void initData() {
		calendar=Calendar.getInstance();
		this.year=calendar.get(Calendar.YEAR);
//		该month比实际月份小1
		this.month=calendar.get(Calendar.MONTH);
		this.day=calendar.get(Calendar.DAY_OF_MONTH);
		this.textDate.setText(year+"-"+(month+1)+"-"+day);
		List<HashMap<String, String>>data=
				this.typeInfoService.findByStatus(2);
		typeNames=new ArrayList<String>();
		typeIds=new ArrayList<String>();
		if(data!=null&&data.size()>0){
//			从查询的结果中获得每一行每一列的值
			for(int i=0;i<data.size();i++){
				HashMap<String, String>item=data.get(i);
				typeNames.add(item.get("type_name"));
				typeIds.add(item.get("type_id"));
			}
			textType.setText(typeNames.get(0));//设置初始时的类型选择的是
			typeId=Integer.parseInt(typeIds.get(0));
		}
	}
	private void setListener() {
		this.btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		保存按钮
		
		this.btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				double price=0;
				if(!editPrice.getText().toString().equals(""))
					price=Double.parseDouble(editPrice.getText().toString());
				String time=textDate.getText().toString();
				String content=editContent.getText().toString();
				boolean flag=outcomeInfoSevice.doSave(time, typeId, price, content);
				String msg="添加失败!";
				if(flag){
					msg="添加成功!";
				}
				Toast.makeText(OutcomeActivity.this, msg, 1000).show();
			}
		});
//		显示类别
		this.textType.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int index=0;
				String type=textType.getText().toString();
				index=Collections.binarySearch(typeNames, type);
				typeId=index;
				AlertDialog.Builder alert=new AlertDialog.Builder(OutcomeActivity.this);
				alert.setTitle("提示");
				alert.setIcon(R.drawable.icon);
//				把list集合转换为string类型的数组
				String [] items=typeNames.toArray(new String[]{});
				alert.setSingleChoiceItems(items, index, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						typeName=typeNames.get(which);
						typeId=Integer.parseInt(typeIds.get(which));
						
					}
				});
				alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(typeName==null)
							typeName=typeNames.get(0);
						textType.setText(typeName);
						
					}
				});
				alert.setNegativeButton("取消", null);
				alert.show();
				
			}
		});
//		给日期文本添加时间
		this.textDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog=new DatePickerDialog(OutcomeActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								String m="";
								String d="";
								if(monthOfYear+1<10){
									m="0"+(monthOfYear+1);
								}
								if(dayOfMonth<10){
									d="0"+dayOfMonth;
								}
								textDate.setText(year+"-"+m+"-"+d);
							}
						}, year, month, day);
				dialog.show();
			}
		});
	}
	@Override
		protected void onResume() {			
			super.onResume();
			if(typeNames.size()==0){
				AlertDialog.Builder alert=
						new AlertDialog.Builder(this);
				alert.setTitle("请先添加类别!");
				alert.setCancelable(false);
				alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent=new Intent(OutcomeActivity.this,TypeActivity.class);
						
						finish();
						startActivity(intent);
					}
				});
				alert.show();
			}
		}
}

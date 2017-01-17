package com.neuedu.soft.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.neuedu.soft.service.IncomeInfoSevice;
import com.neuedu.soft.service.TypeInfoService;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeActivity extends Activity {
//	显示录入的时间
	private TextView textDate;
	private TextView textType;//类型
	
	private EditText editPrice;
	private EditText editContent;//说明
	
	private Button btnSave;
	private Button btnBack;
//	
	private TypeInfoService typeInfoService;
	private IncomeInfoSevice incomeInfoSevice;
	
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
		actionBar.setTitle("收入");
		setContentView(R.layout.activity_income);
		findView();
		setListener();
	}
	private void findView() {
		incomeInfoSevice=new IncomeInfoSevice(this);
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
				this.typeInfoService.findByStatus(1);
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
			typeName=typeNames.get(0);
			typeId=Integer.parseInt(typeIds.get(0));
		}
	}
	private void setListener() {
//		返回按钮
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
//				日期
				String time=textDate.getText().toString();
//				
				double price=0;
				if(!editPrice.getText().toString().equals(""))
						price=Double.parseDouble(editPrice.getText().toString());
				String content=editContent.getText().toString();
				boolean flag=incomeInfoSevice.doSave(time,typeId, price, content);
				String msg="添加失败";
				if(flag){
					msg="添加成功";
				}
				Toast.makeText(IncomeActivity.this, msg, 1000).show();
			}
		});
//		点击类别显示对话框用于选择类别
		this.textType.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int index=0;
//				利用工具类查询数据操作数组Arrays
				String type=textType.getText().toString();
//				判断类别显示的内容是第几项
//				for(int i=0;i<typeNames.size();i++){
//					if(typeNames.get(i).trim().equals(type.trim())){
//						index=i;
//						break;
//					}
//				}
				
				index=Collections.binarySearch(typeNames, type);
//				目的获取id是为了向数据库表中插入数据
				typeId=index;//保存时候用
				AlertDialog.Builder alert=new AlertDialog.Builder(IncomeActivity.this);
				alert.setTitle("请选择类别:");
				alert.setIcon(R.drawable.icon);
//				item的内容来源于数据库
//				把list集合转换为string类型的数组
				String [] items=typeNames.toArray(new String[]{});
				alert.setSingleChoiceItems(items,index, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
//						只负责选择的项的内容，不负责更新页面 
//						将来点击确定按钮时候，再去更新界面
//						目的是在页面中显示
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
//		给日期文本添加事件
		this.textDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				显示时间控件对话框
				DatePickerDialog dialog=new DatePickerDialog(IncomeActivity.this, 
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
			// TODO Auto-generated method stub
			super.onResume();
			if(typeNames.size()==0){
				AlertDialog.Builder alert=
						new AlertDialog.Builder(this);
				alert.setTitle("请先添加类别!");
				alert.setCancelable(false);
				alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent=new Intent(IncomeActivity.this,TypeActivity.class);
						
						finish();
						startActivity(intent);
					}
				});
				alert.show();
			}
		}
}

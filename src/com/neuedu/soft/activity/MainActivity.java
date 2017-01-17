package com.neuedu.soft.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvInfo;
	private GridView gridView;
	private List<HashMap<String, String>>data;
	private SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("功能界面");
		setContentView(R.layout.activity_main);
		findView();
		setListener();
	}

	private void setListener() {
		this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					toActivity(TypeActivity.class);
					break;
				case 1:
					toActivity(IncomeActivity.class);
					break;
				case 2:
					toActivity(OutcomeActivity.class);
					break;
				case 3:
					toActivity(SearchIncomeActivity.class);
					break;
				case 4:
					toActivity(JsqActivity.class);
					break;
				case 5:
					toActivity(SearchOutcomeActivity.class);
					break;
				case 6:
					toActivity(SumActivity.class);
					break;
				case 7:
					Intent intent=getIntent();
					intent.setClass(MainActivity.this, InfoActivity.class);
					startActivity(intent);
					break;
				case 8://关闭自己
//					应该弹出选择框，弹出选择框再退出
					AlertDialog.Builder alert=
					new AlertDialog.Builder(MainActivity.this);
//					此处打点出
					alert.setTitle("提示");
//					设置不撤销
					alert.setCancelable(false);
					alert.setIcon(android.R.drawable.ic_dialog_alert);
					alert.setMessage("你确定要退出吗");
					alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							
						}
					});
					alert.setNegativeButton("取消", null);
					alert.show();
					break;
				}
			}
		});
	}
//	单独写实现跳转的方法
	/**
	 * 
	 * @param cla:表示要去的activity的类型
	 */
	private void toActivity(Class cla){
		Intent intent=new Intent();
		intent.setClass(this, cla);
		startActivity(intent);
	}
	private void findView() {
		tvInfo=(TextView) findViewById(R.id.main_text_info);
//		给gridview单独制定一个布局,以下写法与我们的listview相同
		gridView=(GridView) findViewById(R.id.main_grid);
		data=new ArrayList<HashMap<String,String>>();
		adapter=new SimpleAdapter(this,data,R.layout.main_grid_item,
				new String[]{"image"},new int[]{R.id.main_grid_item_image});
//		把适配器加到里面去
		gridView.setAdapter(adapter);
		initData();
	}

	private void initData() {
//		获取传递的意图
		Intent intent=getIntent();
		HashMap<String, String>item=
				(HashMap<String, String>) intent.getSerializableExtra("info");
		String str="欢迎["+item.get("user_name")+"]今天是:";
		Date date=new Date();
//		 HH:mm:ss"
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
		str+=f.format(date);
		tvInfo.setText(str);
//		给gridview加数据
		
		HashMap<String, String>image=new HashMap<String, String>();
//		类别维护
		image.put("image", R.drawable.category+"");
		data.add(image);
//		日常收入
		image=new HashMap<String, String>();
		image.put("image", R.drawable.income+"");
		data.add(image);
//		日常支出
		image=new HashMap<String, String>();
		image.put("image", R.drawable.spend+"");
		data.add(image);
//		收入查询
		image=new HashMap<String, String>();
		image.put("image", R.drawable.sselect+"");
		data.add(image);
//		存款计算器
		image=new HashMap<String, String>();
		image.put("image", R.drawable.jsq+"");
		data.add(image);
//		查询支出
		image=new HashMap<String, String>();
		image.put("image", R.drawable.zselect+"");
		data.add(image);
//		统计
		image=new HashMap<String, String>();
		image.put("image", R.drawable.sum+"");
		data.add(image);
//		个人信息
		image=new HashMap<String, String>();
		image.put("image", R.drawable.person+"");
		data.add(image);
//		退出
		image=new HashMap<String, String>();
		image.put("image", R.drawable.out+"");
		data.add(image);
		adapter.notifyDataSetChanged();
		
	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode==300){
//			finish();
//		}
//	}
}

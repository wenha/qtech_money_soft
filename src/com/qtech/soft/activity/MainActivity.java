package com.qtech.soft.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.neuedu.soft.activity.R;

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
		actionBar.setTitle("���ܽ���");
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
				case 8://�ر��Լ�
//					Ӧ�õ���ѡ��򣬵���ѡ������˳�
					AlertDialog.Builder alert=
					new AlertDialog.Builder(MainActivity.this);
//					�˴�����
					alert.setTitle("��ʾ");
//					���ò�����
					alert.setCancelable(false);
					alert.setIcon(android.R.drawable.ic_dialog_alert);
					alert.setMessage("��ȷ��Ҫ�˳���");
					alert.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							
						}
					});
					alert.setNegativeButton("ȡ��", null);
					alert.show();
					break;
				}
			}
		});
	}
//	����дʵ����ת�ķ���
	/**
	 * 
	 * @param cla:��ʾҪȥ��activity������
	 */
	private void toActivity(Class cla){
		Intent intent=new Intent();
		intent.setClass(this, cla);
		startActivity(intent);
	}
	private void findView() {
		tvInfo=(TextView) findViewById(R.id.main_text_info);
//		��gridview�����ƶ�һ������,����д�������ǵ�listview��ͬ
		gridView=(GridView) findViewById(R.id.main_grid);
		data=new ArrayList<HashMap<String,String>>();
		adapter=new SimpleAdapter(this,data,R.layout.main_grid_item,
				new String[]{"image"},new int[]{R.id.main_grid_item_image});
//		���������ӵ�����ȥ
		gridView.setAdapter(adapter);
		initData();
	}

	private void initData() {
//		��ȡ���ݵ���ͼ
		Intent intent=getIntent();
		HashMap<String, String>item=
				(HashMap<String, String>) intent.getSerializableExtra("info");
		String str="��ӭ["+item.get("user_name")+"]������:";
		Date date=new Date();
//		 HH:mm:ss"
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
		str+=f.format(date);
		tvInfo.setText(str);
//		��gridview������
		
		HashMap<String, String>image=new HashMap<String, String>();
//		���ά��
		image.put("image", R.drawable.category+"");
		data.add(image);
//		�ճ�����
		image=new HashMap<String, String>();
		image.put("image", R.drawable.income+"");
		data.add(image);
//		�ճ�֧��
		image=new HashMap<String, String>();
		image.put("image", R.drawable.spend+"");
		data.add(image);
//		�����ѯ
		image=new HashMap<String, String>();
		image.put("image", R.drawable.sselect+"");
		data.add(image);
//		��������
		image=new HashMap<String, String>();
		image.put("image", R.drawable.jsq+"");
		data.add(image);
//		��ѯ֧��
		image=new HashMap<String, String>();
		image.put("image", R.drawable.zselect+"");
		data.add(image);
//		ͳ��
		image=new HashMap<String, String>();
		image.put("image", R.drawable.sum+"");
		data.add(image);
//		������Ϣ
		image=new HashMap<String, String>();
		image.put("image", R.drawable.person+"");
		data.add(image);
//		�˳�
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

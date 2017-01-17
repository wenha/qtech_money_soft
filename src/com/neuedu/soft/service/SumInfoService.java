package com.neuedu.soft.service;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.neuedu.soft.dao.SumInfoDao;

public class SumInfoService {
	private SumInfoDao sumInfoDao;
	public SumInfoService(Context context){
		this.sumInfoDao=new SumInfoDao(context);
	}
	public HashMap<String, String> findSum(){
		HashMap<String, String> item=null;
		try {
			List<HashMap<String, String>> data=
					this.sumInfoDao.findSum();
			if(data!=null&&data.size()>0){
				item=data.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
}

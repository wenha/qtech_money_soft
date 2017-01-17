package com.qtech.soft.service;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.qtech.soft.dao.OutcomeInfoDao;

public class OutcomeInfoService {
	private OutcomeInfoDao outcomeInfoDao;
	public OutcomeInfoService(Context context){
		outcomeInfoDao=new OutcomeInfoDao(context);
	}
	public boolean doSave(String outcomeDate,int outcomeTypeId,
			double outcomePrice,String outcomeContent){
		boolean flag=false;
		try {
			outcomeInfoDao.save(outcomeDate, outcomeTypeId, outcomePrice, outcomeContent);
			flag=true;//!!!!!
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return flag;
	}
	public List<HashMap<String, String>>findByDate(String begin,String end){
		List<HashMap<String, String>> data=null;
		try {
			data=this.outcomeInfoDao.findByDate(begin, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}

}

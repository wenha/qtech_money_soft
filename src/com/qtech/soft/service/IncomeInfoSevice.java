package com.qtech.soft.service;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.qtech.soft.dao.IncomeIndoDao;

public class IncomeInfoSevice {
	private IncomeIndoDao incomeInfoDao;
	public IncomeInfoSevice(Context context){
		incomeInfoDao=new IncomeIndoDao(context);
	}
	public boolean doSave(String incomeDate,int incomeTypeId,
			double incomePrice,String incomeContent){
		boolean flag=false;
		try {
			incomeInfoDao.save(incomeDate, incomeTypeId, incomePrice, incomeContent);
			flag=true;//!!!!!
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return flag;
	}
	public List<HashMap<String, String>>findByDate(String begin,String end){
		List<HashMap<String, String>> data=null;
		try {
			data=this.incomeInfoDao.findByDate(begin, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}

}

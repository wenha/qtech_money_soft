package com.neuedu.soft.service;

import java.util.HashMap;
import java.util.List;



import android.content.Context;

import com.neuedu.soft.dao.TypeInfoDao;

public class TypeInfoService {
	private TypeInfoDao typeInfoDao;
	public TypeInfoService(Context context){
		typeInfoDao=new TypeInfoDao(context);
	}

	public boolean doSave(String typeName,String typeContent,int typeStatus){
		boolean flag=false;
		try {
			typeInfoDao.save(typeName, typeContent, typeStatus);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
//	根据类别(1.收入，2.支出)查询类别的名称
	public List<HashMap<String,String>>findByStatus(int type){
		 List<HashMap<String,String>> data=null;
		 try {
			data=typeInfoDao.findByStatus(type);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 return data;
	}
}

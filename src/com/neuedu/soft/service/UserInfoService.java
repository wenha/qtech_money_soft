package com.neuedu.soft.service;

import java.util.HashMap;
/**
 * 把void类型的返回至变成boolean类型的
 * 异常处理
 */
import java.util.List;

import android.content.Context;

import com.neuedu.soft.dao.UserInfoDao;

public class UserInfoService {
	private UserInfoDao userinfoDao;
	public UserInfoService(Context context){
		userinfoDao=new UserInfoDao(context);
	}
	public boolean doReg(String name,String pwd,String sex){
		boolean flag=false;
		try {
			this.userinfoDao.save(name, pwd, sex);
			flag=true;//切记！！！
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
//	登录成功会查到唯一的一行数据
	public HashMap<String, String>checkLogin(String name,String pwd){
		HashMap<String, String> item=null;
		try {
			List<HashMap<String, String>>data=this.userinfoDao.findByName(name, pwd);
			if(data!=null&&data.size()>0){
				item=data.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	public boolean doUpdate(String name,String pwd,String sex,String id){
		boolean flag=false;
		try {
			userinfoDao.update(name, pwd, sex,id);
			flag=true;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return flag;
	}
	public boolean checkName(String name){
		boolean flag=false;
		try {
			List<HashMap<String, String>> data=
					this.userinfoDao.findByName(name);
			if(data!=null&&data.size()>0)
				flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
		
	}

}

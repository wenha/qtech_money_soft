package com.neuedu.soft.dao;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.neu.dao.BaseDao;
/**
 * 封装了对表user_info表的所有sql语句
 * @author 田清华1
 *
 */
public class UserInfoDao extends BaseDao {

	public UserInfoDao(Context context) {
		super(context);
	}
    public void save(String name,String pwd,String sex){
//	必须指明要操作的列
	    String sql="insert into user_info(user_name,user_pwd,user_sex)"
			      + " values(?,?,?)";
//	    顺序必须与列？的顺序一致
	    super.executeUpdate(sql, name,pwd,sex);
    }
//   登录
    public List<HashMap<String, String>>findByName(String name,String pwd){
//    	编写sql语句换行必加空格
    	String sql="select user_id,user_name,user_sex,user_pwd"
    			+ " from user_info"
    			+ " where user_name=? and user_pwd=?";
    	return super.executeQuery(sql,name,pwd);
    }
    public void update(String name,String pwd,String sex,String id){
    	String sql="update user_info"
    			+ " set user_name=?,user_pwd=?,user_sex=?"
    			+ " where user_id=?";
    	super.executeUpdate(sql, name,pwd,sex,id);
    }
    public List<HashMap<String, String>>findByName(String name){
//    	我们的目的只是为了判断用户名是否存在，不需要表中的数据，显示常量即可，提高我们的效率
    	String sql="select 1 as a from user_info"
    			+ " where user_name=?";
    	return super.executeQuery(sql, name);
    }
}

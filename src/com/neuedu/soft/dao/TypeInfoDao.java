package com.neuedu.soft.dao;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.neu.dao.BaseDao;

public class TypeInfoDao extends BaseDao {

	public TypeInfoDao(Context context) {
		super(context);
		
	}
	/**
	 * 
	 * @param typename:类别名称
	 * @param typeContent：类别描述
	 * @param typeStatus：类别分类1：收入2：表示支出
	 */
	public void save(String typeName,String typeContent,int typeStatus){
//		换行加空格，所有 标点都为英文
		String sql="insert into type_info(type_name,type_content,type_status)"
				+ " values(?,?,?)";
//		调用弗雷的方法，执行sql语句
		super.executeUpdate(sql, typeName,typeContent,typeStatus);
		
	}
//	根据类别(1.收入，2.支出)查询类别的名称
	public List<HashMap<String, String>>findByStatus(int type){
		String sql="select type_id,type_name from type_info"
				+ " where type_status=?";
		return super.executeQuery(sql, type+"");
	}

}

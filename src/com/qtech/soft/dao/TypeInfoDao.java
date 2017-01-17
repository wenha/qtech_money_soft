package com.qtech.soft.dao;

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
	 * @param typename:�������
	 * @param typeContent���������
	 * @param typeStatus��������1������2����ʾ֧��
	 */
	public void save(String typeName,String typeContent,int typeStatus){
//		���мӿո����� ��㶼ΪӢ��
		String sql="insert into type_info(type_name,type_content,type_status)"
				+ " values(?,?,?)";
//		���ø��׵ķ�����ִ��sql���
		super.executeUpdate(sql, typeName,typeContent,typeStatus);
		
	}
//	�������(1.���룬2.֧��)��ѯ��������
	public List<HashMap<String, String>>findByStatus(int type){
		String sql="select type_id,type_name from type_info"
				+ " where type_status=?";
		return super.executeQuery(sql, type+"");
	}

}

package com.qtech.soft.dao;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.neu.dao.BaseDao;
/**
 * ��װ�˶Ա�user_info�������sql���
 * @author ���廪1
 *
 */
public class UserInfoDao extends BaseDao {

	public UserInfoDao(Context context) {
		super(context);
	}
    public void save(String name,String pwd,String sex){
//	����ָ��Ҫ��������
	    String sql="insert into user_info(user_name,user_pwd,user_sex)"
			      + " values(?,?,?)";
//	    ˳��������У���˳��һ��
	    super.executeUpdate(sql, name,pwd,sex);
    }
//   ��¼
    public List<HashMap<String, String>>findByName(String name,String pwd){
//    	��дsql��任�бؼӿո�
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
//    	���ǵ�Ŀ��ֻ��Ϊ���ж��û����Ƿ���ڣ�����Ҫ���е����ݣ���ʾ�������ɣ�������ǵ�Ч��
    	String sql="select 1 as a from user_info"
    			+ " where user_name=?";
    	return super.executeQuery(sql, name);
    }
}

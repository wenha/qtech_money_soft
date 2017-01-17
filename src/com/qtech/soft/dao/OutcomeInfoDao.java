package com.qtech.soft.dao;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.neu.dao.BaseDao;

public class OutcomeInfoDao extends BaseDao {

	public OutcomeInfoDao(Context context) {
		super(context);
		
	}
	public void save(String outcomeDate,
			int outcomeTypeId,
			double outcomePrice,
			String outcomeContent){
		String sql="insert into outcome_info(outcome_date,"
				+ "outcome_type_id,outcome_price,"
				+ "outcome_content)"
				+ "values(?,?,?,?)";
		super.executeUpdate(sql, outcomeDate,outcomeTypeId,outcomePrice,outcomeContent);
	}
	public List<HashMap<String, String>>findByDate(String begin,String end){
//		String sql="select a.income_id,a.income_price,"
//				+ " a.income_date,a.income_content,"
//				+ "b.type_name,b.type_content"
//				+ " from income_info as a inner join type_info as b"
//				+ " on a.income_type_id=b.type_id";
		String sql="select outcome_id,outcome_price,"
				+ " outcome_date,outcome_content,"
				+ " type_name,type_content"
				+ " from outcome_info as a inner join type_info as b"
				+ " on a.outcome_type_id=b.type_id"
				+ " where a.outcome_date>=? and a.outcome_date<=?";
		return super.executeQuery(sql, begin,end);
	}
	
		

}

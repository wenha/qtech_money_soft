package com.qtech.soft.dao;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.neu.dao.BaseDao;

public class IncomeIndoDao extends BaseDao {

	public IncomeIndoDao(Context context) {
		super(context);
		
	}
	public void save(String incomeDate,
			int incomeTypeId,
			double incomePrice,
			String incomeContent){
		String sql="insert into income_info(income_date,"
				+ "income_type_id,income_price,"
				+ "income_content)"
				+ "values(?,?,?,?)";
		super.executeUpdate(sql, incomeDate,incomeTypeId,incomePrice,incomeContent);
		
		
	}
	public List<HashMap<String, String>>findByDate(String begin,String end){
//		String sql="select a.income_id,a.income_price,"
//				+ " a.income_date,a.income_content,"
//				+ "b.type_name,b.type_content"
//				+ " from income_info as a inner join type_info as b"
//				+ " on a.income_type_id=b.type_id";
		String sql="select income_id,income_price,"
				+ " income_date,income_content,"
				+ " type_name,type_content"
				+ " from income_info as a inner join type_info as b"
				+ " on a.income_type_id=b.type_id"
				+ " where a.income_date>=? and a.income_date<=?";
		return super.executeQuery(sql, begin,end);
	}

}

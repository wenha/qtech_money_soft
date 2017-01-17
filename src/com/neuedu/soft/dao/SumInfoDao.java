package com.neuedu.soft.dao;

import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.neu.dao.BaseDao;

public class SumInfoDao extends BaseDao {

	public SumInfoDao(Context context) {
		super(context);
	}
	public List<HashMap<String, String>> findSum(){
		String sql="select"
				+ " (select sum(income_price) a from income_info) income_sum,"
				+ " (select sum(outcome_price) b from outcome_info) outcome_sum";
		return super.executeQuery(sql);
	}

}

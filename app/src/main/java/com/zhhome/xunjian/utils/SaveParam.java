package com.zhhome.xunjian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveParam {

	private static SaveParam instance;
	public static SaveParam getInstance(){
		if (instance==null){
			instance = new SaveParam();
		}
		return instance;
	}
	public static String USER_ID = "user_id";//用户ID

	public void saveLoginParam(Context context, String spname, String spstr) {
		SharedPreferences sp = context.getSharedPreferences("loginparam",
				Activity.MODE_PRIVATE);

		sp.edit().putString(spname, spstr).commit();
	}

	public void saveintLoginParam(Context context, String spname, int spstr) {
		SharedPreferences sp = context.getSharedPreferences("loginparam",
				Activity.MODE_PRIVATE);

		sp.edit().putInt(spname, spstr).commit();
	}
	public int getintLoginParam(Context context, String spname) {
		SharedPreferences sp = context.getSharedPreferences("loginparam",
				Activity.MODE_PRIVATE);
		int param = sp.getInt(spname, 0);
		return param;
	}

	public String getLoginParam(Context context, String spname) {
		SharedPreferences sp = context.getSharedPreferences("loginparam",
				Activity.MODE_PRIVATE);
		String param = sp.getString(spname, null);
		return param;
	}
	public void clearData(Context context) {
		SharedPreferences sp = context.getSharedPreferences("loginparam",
				Activity.MODE_PRIVATE);
		sp.edit().clear().commit();
	}


}

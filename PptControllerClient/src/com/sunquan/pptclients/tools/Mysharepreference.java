package com.sunquan.pptclients.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class Mysharepreference {

	 
	public  static String DEFAULT_SERVER_IP="192.168.0.1";
	public  static String DEFAULT_SERVER_PORT="60000";
    //1.存储共享参数的内容
	public static boolean saveMessage(String key,String value,Context context) {
		boolean flag = false;
		// 建立共享参数的文件，一般不带后缀名，默认会保存成.xml的文件
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		// 对数据进行编辑
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		flag=editor.commit();//将数据持久化到存储介质  可能是存储卡或者sd卡
		return flag;
	}
	//2.获取共享参数的内容
	public static String getMessage(String key,Context context)
	{
		SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		//获得共享参数
	 
		String name=sharedPreferences.getString(key, null);//null后一个参数表示缺省值
		
		if(name==null)
		{
			if(key.equals("ip"))
				return DEFAULT_SERVER_IP;
			if(key.equals("port"))
				return DEFAULT_SERVER_PORT;
		}
		return name;
		
		
	}

}

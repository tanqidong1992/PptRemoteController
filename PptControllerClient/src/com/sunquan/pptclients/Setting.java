package com.sunquan.pptclients;

import com.sunquan.pptclients.tools.Mysharepreference;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends Activity  {
	
	private EditText et_ipAddress;
	private EditText et_port;
	private Button bt_save;
	public static final String ip="ip";
	public static final String port="port";
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.setting);
	        
	        et_ipAddress=(EditText) findViewById(R.id.ipaddress);
	        
	        et_port=(EditText) findViewById(R.id.port);
	        
	        bt_save=(Button) findViewById(R.id.save);
	        
	        
	        et_port.setText(Mysharepreference.getMessage(port, this));
	        et_ipAddress.setText(Mysharepreference.getMessage(ip, this));
	        bt_save.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String ip=et_ipAddress.getText().toString().trim();
					String port=et_port.getText().toString().trim();
					
					Mysharepreference.saveMessage("ip", ip, Setting.this);
					Mysharepreference.saveMessage("port", port, Setting.this);
					Toast.makeText(Setting.this, "±£´æ³É¹¦", Toast.LENGTH_SHORT).show();
					
					
				}
			});
	  }
	  
	  

}

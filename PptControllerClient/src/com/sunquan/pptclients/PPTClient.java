package com.sunquan.pptclients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;














import com.sunquan.pptclients.tools.Mysharepreference;
import com.sunquan.pptclients.tools.isConnect_Internet;
import com.tqd.client.TcpClient;
import com.tqd.entity.Message;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PPTClient extends Activity implements OnClickListener {
	private Button conn;
	private Button start;
	private Button mEnterPenMode;
 
	private  boolean flag = false;
	LinearLayout mLinearLayout;
	private Socket sock;
	private ObjectOutputStream fromClient;
	private ObjectInputStream fromServer;
	protected int mStartX;
	protected int mStartY;
	 

	protected static final int minDistance = 50;
	protected static final int PEN_MODE = 1;
	protected static final int NORMAL_MODE = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        conn = (Button)this.findViewById(R.id.connect);
        conn.setOnClickListener(this);
        start = (Button)this.findViewById(R.id.start);
        mEnterPenMode = (Button)this.findViewById(R.id.enter_pen_mode);
      
        mLinearLayout=(LinearLayout) findViewById(R.id.touch_dire);
        
        mLinearLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			
				
				
				int pointCount=event.getPointerCount();
				int action=event.getAction();
				int currentX=(int) event.getX();
				int currentY=(int) event.getY();
				if(mMode==NORMAL_MODE)
				{
			 
					
					if(action==MotionEvent.ACTION_DOWN)
					{
	 
						mStartX=currentX;
						mStartY=currentY;
					}
					if(action==MotionEvent.ACTION_UP)
					{
						int dX=currentX-mStartX;
						int dY=currentY-mStartY;
						int absDx=Math.abs(dX);
								int absDy=Math.abs(dY);
						if( absDx>minDistance || absDy>=minDistance){
							Message msg=new Message();
							if(absDx>absDy)
							{
								if(dX>0)
									msg.setcType(CommandType.MOVE_RIGHT);
								else
									msg.setcType(CommandType.MOVE_LEFT);	
							}
							else
							{
								if(dY>0)
									msg.setcType(CommandType.MOVE_DOWN);
								else
									msg.setcType(CommandType.MOVE_UP);	
							}
							if(mTcpClient!=null)
							mTcpClient.sendMsgAsync(msg);
							else
								Toast.makeText(PPTClient.this, "请连接后在发送指令", Toast.LENGTH_SHORT).show();
								
						}
					}
				
				}
				
				if(mMode==NORMAL_MODE)
				{
					
					if(action==MotionEvent.ACTION_DOWN)
					{
						
					}
					
					if(action==MotionEvent.ACTION_MOVE)
					{
						
					}
					
					if(action==MotionEvent.ACTION_UP)
					{
						
					}
				}
				return true;
			}
		});
        
    }
    public void onClick(View v) {
        
    	if (!isConnect_Internet.isConnect(PPTClient.this)) {
    		Toast.makeText(PPTClient.this, "请连接局域网...", Toast.LENGTH_SHORT).show();
			return;
		}
    	if(v.getId()==R.id.connect&&flag==false){
    		String ret;
    		flag=connect();
    		if(flag){
    			ret=new String("连接成功");
    			start.setOnClickListener(this);
    		    mEnterPenMode.setOnClickListener(this);
    			//back.setOnClickListener(this);
    		   // forward.setOnClickListener(this);
    		}
    		else ret=new String("连接失败");
    		Toast.makeText(PPTClient.this,ret, Toast.LENGTH_SHORT).show();
    		return;
    	}
    	if(flag==false){Toast.makeText(PPTClient.this, "请连接电脑...", Toast.LENGTH_SHORT).show();}
    	//Choices  choice;
    	Message msg=new Message();
    	switch (v.getId()) {
		case R.id.start:
			
			msg.setcType(CommandType.START_PPT);
			
			break;
		case R.id.enter_pen_mode:
			msg.setcType(CommandType.ENTER_PEN_MODE);
			 
			break;
	 
		default:
			break;
		}
    	
    	if(mTcpClient!=null)
    	mTcpClient.sendMsgAsync(msg);
		
	}
    
    private TcpClient mTcpClient;
    public  boolean connect()
    {
 
    	com.sunquan.pptclients.tools.Mysharepreference mSharepreference=new Mysharepreference();
    	
    	String hostIP=mSharepreference.getMessage(Setting.ip, this);
    	String sPort=mSharepreference.getMessage(Setting.port, this);
		int port=Integer.parseInt(sPort);
		mTcpClient=new TcpClient(hostIP, port);
		//return mTcpClient.isConnected();
		return true;
    }
    /**
     * 监听BACK键
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {	
		if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("退出？");
			builder.setMessage("You will exit this app...");
			//builder.setIcon(R.drawable.stat_sys_warning);
			builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME); 
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(startMain);
					System.exit(0);
				}
			});
			builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			builder.show();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.addSubMenu("exit ppt");
		 
		menu.addSubMenu("setting");
		
		
		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
	String menuItemTitle=	(String) item.getTitle();
	Log.i("menuItemTitle", menuItemTitle);
	
	System.out.println("menuItemTitle-->"+menuItemTitle);
		if(menuItemTitle.equals("setting"))
		{
			
			Intent intent=new Intent();
			intent.setClass(this, Setting.class);
			
			startActivity(intent);
			System.out.println("on selected"+"setting menu item");
		}
		if(menuItemTitle.equals("exit ppt"))
		{
			
			if(mTcpClient!=null)
			{
				Message msg=new Message();
				msg.setcType(CommandType.EXIT_PPT);
				mTcpClient.sendMsgAsync(msg);
				if(mMode==NORMAL_MODE)
				{
					mMode=PEN_MODE;
				}
				else
					mMode=NORMAL_MODE;
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}
     
    
    public int mMode=NORMAL_MODE;
    
	
}
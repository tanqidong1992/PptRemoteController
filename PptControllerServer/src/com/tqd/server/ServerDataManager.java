package com.tqd.server;

import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class ServerDataManager {
	
	/**
	 *在线的ip地址
	 */
	private static Map<SocketAddress,ChannelHandlerContext> mOnlineUser=new HashMap<SocketAddress,ChannelHandlerContext>();
	
	
	public static void add(SocketAddress sa,ChannelHandlerContext ps)
	{
		mDefaultTableModel.addRow(new Object[]{sa,new Date()});
		mOnlineUser.put(sa, ps);
	}
	
	public static void remove(SocketAddress sa)
	{
		
		ChannelHandlerContext ps=	mOnlineUser.get(sa);
		
		if(ps!=null)
		{
			ps.disconnect();
		}
		
		mOnlineUser.remove(sa);
		for(int i=0;i<mDefaultTableModel.getRowCount();i++)
		{
			
			Object o=mDefaultTableModel.getValueAt(i, 0);
			
			if(o.equals(sa))
				mDefaultTableModel.removeRow(i);
		}
		 
	}
	
	private static DefaultTableModel mDefaultTableModel;
	
	public static void setDefaultTableModel(DefaultTableModel dtm)
	{
		mDefaultTableModel=dtm;
	}

}

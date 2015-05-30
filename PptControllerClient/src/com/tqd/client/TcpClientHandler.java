package com.tqd.client;

import org.apache.log4j.Logger;

import com.sunquan.pptclients.PPTClient;

import android.os.Handler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {
	//private static final Logger logger = Logger.getLogger(TcpClientHandler.class);
	Handler mHandler;
	public TcpClientHandler(Handler handler) {
		// TODO Auto-generated constructor stub
		mHandler=handler;
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//messageReceived����,���ƺܱ�Ť������һ���ڲ�����.
		//logger.info("client���յ����������ص���Ϣ:"+msg);
		if(msg instanceof byte [])
		{
			byte []data=(byte[])msg;
			if(data.length>1024)
			mHandler.obtainMessage(PPTClient.BITMAP_RECEIVED, msg).sendToTarget();
			else
				System.out.println(new String(data));
			
				
		}
		
		
		
	}

   
}

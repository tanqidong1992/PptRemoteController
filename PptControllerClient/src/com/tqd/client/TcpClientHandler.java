package com.tqd.client;

import java.lang.ref.WeakReference;

import org.apache.log4j.Logger;

import com.sunquan.pptclients.PPTClient;

import android.os.Handler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;


public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {
	//private static final Logger logger = Logger.getLogger(TcpClientHandler.class);
	
	/**
	 * 修改Handler为弱引用，当对应Activity已经销毁的时候，这个Handler也会销毁
	 */
	WeakReference<Handler> mHandler;
	public TcpClientHandler(Handler handler) {
		// TODO Auto-generated constructor stub
		mHandler=new WeakReference<Handler>(handler);
		
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//messageReceived方法,名称很别扭，像是一个内部方法.
		//logger.info("client接收到服务器返回的消息:"+msg);
		if(msg instanceof byte [])
		{
			byte []data=(byte[])msg;
			if(data.length>1024)
			{
				
				if(mHandler.get()!=null){
					
					mHandler.get().obtainMessage(PPTClient.BITMAP_RECEIVED, msg).sendToTarget();
				}else
				{
					//当Handler为null的时候，表示Activity已经销毁了，这个链接应该断开
					if(!ctx.isRemoved()){
						ctx.disconnect();
					}
					
				}
				
			
			}else
				System.out.println(new String(data));
			
				
		}
		
		
	 	ReferenceCountUtil.release(msg);
	}

   
}

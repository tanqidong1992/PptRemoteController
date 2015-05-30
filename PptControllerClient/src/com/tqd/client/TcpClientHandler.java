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
	 * �޸�HandlerΪ�����ã�����ӦActivity�Ѿ����ٵ�ʱ�����HandlerҲ������
	 */
	WeakReference<Handler> mHandler;
	public TcpClientHandler(Handler handler) {
		// TODO Auto-generated constructor stub
		mHandler=new WeakReference<Handler>(handler);
		
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
			{
				
				if(mHandler.get()!=null){
					
					mHandler.get().obtainMessage(PPTClient.BITMAP_RECEIVED, msg).sendToTarget();
				}else
				{
					//��HandlerΪnull��ʱ�򣬱�ʾActivity�Ѿ������ˣ��������Ӧ�öϿ�
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

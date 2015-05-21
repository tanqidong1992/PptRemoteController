package com.tqd.server;
 

	import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tqd.core.PPtControlCore;
import com.tqd.entity.Message;

	


	public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {
		
		private com.tqd.core.PPtControlCore mPPtControlCore=new PPtControlCore();
		Gson mGson=new Gson();

	    private static final Logger logger = Logger.getLogger(TcpServerHandler.class);

	    @Override
	    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
	       //logger.info("SERVER接收到消息:"+msg);
	       System.out.println("SERVER接收到消息:"+msg);
	       
	       Message message=null;
	       try{
	       message=mGson.fromJson((String)msg, Message.class);
	       }catch(JsonSyntaxException e)
	       {
	    	   e.printStackTrace();
	       }

  
	       if(message!=null)
	       {
	    	   mPPtControlCore.processCommand(message.getcType());
	       }
	       
			ctx.channel().writeAndFlush("yes, server is accepted you ,nice !"+msg);
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
	    	
	    	 System.out.println("Unexpected exception from downstream.");
	    	 
	        ctx.close();
	    }
	}


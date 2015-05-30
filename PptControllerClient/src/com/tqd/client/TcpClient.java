package com.tqd.client;
 

import android.os.Handler;

import com.google.gson.Gson;
import com.sunquan.pptclients.PPTClient;
import com.tqd.entity.Message;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
 


public class TcpClient {
//	private static final Logger logger = Logger.getLogger(TcpClient.class);
	public   String HOST = "192.168.0.1";
	public   int PORT = 9999;
	
	public   Bootstrap bootstrap;
	public   Channel channel;
	Handler mHandler;
	private Gson mGson;
	public TcpClient(String hostIP, int port,Handler handler) {
		mHandler=handler;
		mGson=new Gson();
		HOST = hostIP;
		PORT = port;
		new Thread()
		{
			public void run() {
				
				bootstrap = getBootstrap();
				channel = getChannel(HOST,PORT);
				
				if(isConnected())
				{
					mHandler.obtainMessage(PPTClient.CONNECTEED).sendToTarget();
				}
				else
				{
					mHandler.obtainMessage(PPTClient.CONNECTION_FAIL).sendToTarget();
				}
			};
		}.start();

	}

	/**
	 * 初始化Bootstrap
	 * @return
	 */
	public   final Bootstrap getBootstrap(){
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
				pipeline.addLast("decoder", new ByteArrayDecoder());
				pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
				pipeline.addLast("handler", new TcpClientHandler(mHandler));
			}
		});
		b.option(ChannelOption.SO_KEEPALIVE, true);
		return b;
	}

	public   final Channel getChannel(String host,int port){
		Channel channel = null;
		try {
			channel = bootstrap.connect(host, port).sync().channel();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.printf("连接Server(IP[%s],PORT[%s])失败", host,port);
			//logger.error(String.format("连接Server(IP[%s],PORT[%s])失败", host,port),e);
			return null;
		}
		return channel;
	}
	
	public void sendMsgAsync(final String msg)
	{
		new Thread()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sendMsg(msg);
			}
		}.start();
		
	}
	
	public   void sendMsg(String msg) {
		if(channel!=null){
			try {
				channel.writeAndFlush(msg).sync();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("消息发送失败,连接异常!");
				channel=null;
			}
		}else{
			System.out.println("消息发送失败,连接尚未建立!");
			//logger.warn("消息发送失败,连接尚未建立!");
			channel=getChannel(HOST, PORT);
		}
	}

	public boolean isConnected() {
		// TODO Auto-generated method stub
		if(channel!=null)
			return true;
		return false;
	}
	
	public void sendMsgAsync(Message msg) {
		// TODO Auto-generated method stub
		String data=mGson.toJson(msg);
		
		sendMsgAsync(data);
	}

	public void reconnect(String hostIP, int port2) {
		// TODO Auto-generated method stub
		
		if(isConnected()){
			
			mHandler.obtainMessage(PPTClient.CONNECTEED).sendToTarget();
			return ;
		}
		new Thread()
		{
			public void run() {
				
				if(bootstrap==null)
				bootstrap = getBootstrap();
				
				if(channel==null)
				channel = getChannel(HOST,PORT);
				
				if(isConnected())
				{
					mHandler.obtainMessage(PPTClient.CONNECTEED).sendToTarget();
				}
				else
				{
					mHandler.obtainMessage(PPTClient.CONNECTION_FAIL).sendToTarget();
				}
			};
		}.start();
	}

     
}
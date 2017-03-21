package com.util;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import com.rmi.ServiceConsumer;

public class ClientAsynchronousUtil implements Runnable{
	public static  ServiceConsumer serviceConsumer;
	public static  ClientAsynchronousUtil clientAsynchronousUtil;
	private static Thread thread;
	static{
		clientAsynchronousUtil=new ClientAsynchronousUtil();
		thread=new Thread(clientAsynchronousUtil);
		thread.start();
	}
	@Override
	public void run() {
		try {
			serviceConsumer=new ServiceConsumer();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static  ServiceConsumer getServiceConsumer(){
		return clientAsynchronousUtil.serviceConsumer;
	}
}

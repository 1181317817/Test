package com.util;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		ClientAsynchronousUtil clientAsynchronousUtil=new ClientAsynchronousUtil();
		System.out.println("异步线程");
		Thread.sleep(15000);
		System.out.println(clientAsynchronousUtil.getServiceConsumer());
		while(true){
			Thread.sleep(2000);
			System.out.println("123");
		}
	}
}

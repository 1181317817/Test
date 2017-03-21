package test;

import com.util.ClientAsynchronousUtil;

public class Test5 {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ClientAsynchronousUtil clientAsynchronousUtil=new ClientAsynchronousUtil();
		Thread.sleep(15000);
		String s=clientAsynchronousUtil.getServiceConsumer().queryByAlias("233222456545553", "dealDoctor","杨林艳", "admin","admin");
		System.out.println(s);
	}
}

package test;

import com.rmi.RequestConfig;
import com.util.ClientAsynchronousUtil;

public class Test3 {
	public static void main(String[] args) throws Exception {
		// 需要zookerper的服务器地址，直接从服务器上面的节点拿到服务对象
		/*RequestConfig requestConfig = new RequestConfig();
		requestConfig.setOperation(RequestConfig.QUERY);
		requestConfig.setEffectiveness(true);
		requestConfig.setUsername("admin");
		requestConfig.setPassword("123456");
		requestConfig.setComponentID("233222456545554");
		ServiceConsumer serviceProvider = new ServiceConsumer();*/
		/*RequestConfig requestConfig = new RequestConfig();
		requestConfig.setOperation(RequestConfig.UPDATA);
		requestConfig.setServiceCode("test1-m-test12399");
		requestConfig.setAuthenticity(true);
		requestConfig.setEffectiveness(true);
		requestConfig.setUsername("admin");
		requestConfig.setPassword("123456");
		requestConfig.setComponentID("233222456545554");
		
		
		ContentInfo contentInfo=new ContentInfo();
		contentInfo.setAlias("visitNO");
		contentInfo.setContent("wo12");
		contentInfo.setFeatureCode("123-visitNO-99");
		ContentInfo contentInfo1=new ContentInfo();
		contentInfo1.setAlias("patientID");
		contentInfo1.setContent("shi123");
		contentInfo1.setFeatureCode("123-patientID-99");
		ContentInfo contentInfo2=new ContentInfo();
		contentInfo2.setAlias("cardName123");
		contentInfo2.setContent("我改了");
		contentInfo2.setFeatureCode("123-cardName-99");
		
		
		List<ContentInfo> contentInfos=new ArrayList<ContentInfo>();
		contentInfos.add(contentInfo);
		contentInfos.add(contentInfo1);
		contentInfos.add(contentInfo2);
		ServiceConsumer serviceProvider = new ServiceConsumer();
		Long beginTime=new Date().getTime();
		System.out.println(beginTime);
		System.out.println(ConsumerUtil.getServiceConsumer().queryByAlias("233222456545553","sex", "男", "admin", "admin"));
		System.out.println("第一次时间"+(new Date().getTime()-beginTime));
		beginTime=new Date().getTime();*/
		ClientAsynchronousUtil clientAsynchronousUtil=new ClientAsynchronousUtil();
		Thread.sleep(15000);
		RequestConfig requestConfig =new RequestConfig();
		requestConfig.setOperation(RequestConfig.QUERY);
		requestConfig.setEffectiveness(true);
		requestConfig.setUsername("admin");
		requestConfig.setPassword("admin");
		requestConfig.setComponentID("233222456545554");
		requestConfig.setServiceCode("GZHC-7156d7f0-321b-4f1e-b4e7-13145221");
		String s=clientAsynchronousUtil.getServiceConsumer().queryOne(requestConfig);
		/*JSONArray jsonObject=JSONArray.fromObject(s.replaceAll("\"null\"","null"));*/
		
		System.out.println(s.toString());
	}
	/*public static void main(String[] args) throws Exception {
		RequestConfig requestConfig = new RequestConfig();
		requestConfig.setOperation(RequestConfig.QUERY);
		requestConfig.setEffectiveness(true);
		requestConfig.setUsername("admin");
		requestConfig.setPassword("123456");
		requestConfig.setComponentID("233222456545554");
		ServiceConsumer serviceProvider = new ServiceConsumer();
		System.out.println(serviceProvider.queryOne(requestConfig));
	}*/
	
}

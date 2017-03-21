package test.test;

import com.function.data.service.RMIService;
import com.rmi.ServiceConsumer;

public class test2 {
	public static void main(String[] args) throws Exception {
		ServiceConsumer server=new ServiceConsumer();
		RMIService service=(RMIService)server.lookup();
		System.out.println(service);
	}
	/*public static void main(String[] args) throws IOException, NotBoundException, NoContentException  {
		RemoteService remoteService=new RemoteService();
		RequestConfig requestConfig=new RequestConfig();
		requestConfig.setOperation(RequestConfig.QUERY);
		requestConfig.setEffectiveness(true);
		requestConfig.setUsername("admin");
		requestConfig.setPassword("123456");
		requestConfig.setServiceCode("RIS2016006");
		requestConfig.setComponentID("233222456545553");
		String name=remoteService.queryOne(JsonUtil.queryOrDeleteJson(requestConfig));
		System.out.println(name);
	}*/
}

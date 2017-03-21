package test;

import java.util.HashMap;
import java.util.Map;

import com.rmi.RequestConfig;
import com.util.ClientAsynchronousUtil;

public class Test4 {
	public static void main(String[] args) throws Exception {
		ClientAsynchronousUtil clientAsynchronousUtil=new ClientAsynchronousUtil();
		Thread.sleep(15000);
		RequestConfig requestConfig = new RequestConfig();
		requestConfig.setAuthenticity(true);
		requestConfig.setComponentID("233222456545552");
		requestConfig.setUsername("admin");
		requestConfig.setPassword("admin");
		requestConfig.setOperation(requestConfig.INSERT);
		requestConfig.setEffectiveness(true);
		requestConfig.setServiceCode("GZHC-7156d7f0-321b-4f1e-b4e7-1314529cqa1s1");

		Person person = new Person();
		person.setAge(99);// visitNO，patientID，cardName
		// person.setEmail("99233@.qq.comwwwwwwwwwwwwwwwwwwww");
		person.setUsername("男");
		person.setYes(false);

		// 将管道号和属性的映射配置在map对象中，属性首字母可以小写，并将表名和行ID设置在此map中，用于自动拼接数据特征码，
		Map<String, Boolean> pipelineDirectory = new HashMap<String, Boolean>();
		// 表示对10000的管道号的属性不进行验证
		pipelineDirectory.put("10000", false);
		// 传入验证对象，其中封装了哪些数据需要和不需要验证
		requestConfig.setPipelineDirectory(pipelineDirectory);

		  //将管道号和对象属性的映射配置在map对象中，属性首字母可以小写，并将表名和行ID设置在此map中，用于自动拼接数据特征码，
        Map<String,String> pipelineForContent=new HashMap<String,String>();

        //表名的key是固定的，此为设置表名
       pipelineForContent.put("tableName", "person");
       //行id的key也是固定的，此为设置行ID，也就是这行数据的ID值
       pipelineForContent.put("lineId","123456789WQEQWSAD");
       //设置属性对应的管道号别名
       pipelineForContent.put("username", "cardName");
       pipelineForContent.put("yes", "patientID");
       pipelineForContent.put("age", "visitNO");
       Map<String,String> featureCodeForContent=new HashMap<String,String>();
		// 调用核心对象新增数据的方法，传入请求的配置对象，内容对象，管道号和属性映射的map对象，特征码和属性的映射map对象,
		String result=clientAsynchronousUtil.getServiceConsumer().saveService(requestConfig, person, pipelineForContent, featureCodeForContent);// 新方法可以传递对象
		System.out.println(result);
	}
}

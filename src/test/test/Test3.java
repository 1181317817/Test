package test.test;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.zookeeper.KeeperException;

import com.rmi.RequestConfig;
import com.rmi.ServiceConsumer;
import com.util.ClientAsynchronousUtil;

public class Test3 {
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException, NotBoundException {
    	ClientAsynchronousUtil clientAsynchronousUtil=new ClientAsynchronousUtil();
		Thread.sleep(15000);
		EsbExamVo order=new EsbExamVo();
		order.setPatientID("99233");
		order.setOrderID("99233");
		order.setOrderStatus("1");
		order.setName("小明");
		order.setBirthday(new Date().getTime()+"");
		order.setSex("男");
		order.setAge("99");
		order.setAgeUnit("岁");
		order.setFromDept("抽血化验科");
		order.setFromDoctor("123");
		order.setInPatientNO("123");
		order.setOutPatientNO("123");
		order.setSickBedNO("123");
		order.setCaseHistory("123");
		/*order.setFromDoctor("123");
		order.setFromDoctor("123");
		order.setFromDoctor("123");
		order.setFromDoctor("123");*/
		/*
	      private String ;  //住院号
	    private String ; //门诊号
	      private String ; //床号
	    private String ; // 病历摘要
	    private String clinicDiagnosis; //临床诊断
	    private String visceras; //检查部位（多个部位以英文半角分号“;”分隔）
	    private String fee; //费用
	    private String reqDate; //申请时间
	    private String isInPatient; //是否住院（住院：Y其他：N）
	    private String catalog; //病人来源:门诊、住院、体检等
	      private String diagnosisNO; //诊疗卡号
	    private String moduleName; //模设备型  超声:UIS,放射:RIS,内窥镜:EIS，病理:PIS
	    private String machineType; //设备类型（彩超、黑白超、腹腔镜、宫腔镜、CT、DX、MR、CR、RF、DSA、SC牙片,等）
	    private String status; //申请状态(已发起,已终止,已完成)
*/		
		
		String result="";
		 ServiceConsumer serviceConsumer = clientAsynchronousUtil.getServiceConsumer();
		try {
	        RequestConfig requestConfig = null;
				try {
					requestConfig = new RequestConfig();
				} catch (Exception err) {
					err.printStackTrace();
				}
			// 设置数据是否进入测试数据,为真为正式数据，为假为测试数据
			requestConfig.setAuthenticity(false);
			// 设置组件号
			requestConfig.setComponentID("233222456545552");
			// 通过管道号设置要对哪个字段进行为空的验证，默认为需要为空验证，为空包括空字符串 (**************************)
			Map<String, Boolean> pipelineDirectory = new HashMap<String, Boolean>();

			  pipelineDirectory.put("sex", false);
			  pipelineDirectory.put("age", false);
			  pipelineDirectory.put("ageUnit", false);
			  pipelineDirectory.put("fromDept", false);
			  pipelineDirectory.put("fromDoctor", false);
			   pipelineDirectory.put("inPatientNO", false);
			  pipelineDirectory.put("outPatientNO", false);
			   pipelineDirectory.put("sickBedNO", false);
			  pipelineDirectory.put("caseHistory", false);
			  pipelineDirectory.put("clinicDiagnosis", false);
			  pipelineDirectory.put("visceras", false);
			  pipelineDirectory.put("fee", false);
			  pipelineDirectory.put("reqDate", false);
			   pipelineDirectory.put("isInPatient", false);
			  pipelineDirectory.put("catalog", false);
			  pipelineDirectory.put("diagnosisNO", false);
			  pipelineDirectory.put("moduleName", false);
			  pipelineDirectory.put("machineType", false);
			  pipelineDirectory.put("status", false);
			  
			// 传入验证对象，其中封装了哪些数据需要和不需要验证
			requestConfig.setPipelineDirectory(pipelineDirectory);
			// 设置数据状态，主要是查询的时候用于是否要查询到删除的数据，为true就是包括删除的数据
			requestConfig.setEffectiveness(true);
			// 设置操作标识,表示此次操作数据的状态，delete删除，update修改，insert增加，query查询
			requestConfig.setOperation(RequestConfig.INSERT);
			
			// 设置业务特征码 格式为"GZHC"+"-"+"耗材ID",表示表单的唯一性
			 requestConfig.setServiceCode("EXAM_APPLY_DETAIL-" + UUID.randomUUID());
			
			// 将管道号和对象属性的映射配置在map对象中，属性首字母可以小写，并将表名和行ID设置在此map中，用于自动拼接数据特征码，
			Map<String, String> pipelineForContent = new HashMap<String, String>();
			// 表名的key是固定的，此为设置表名
			pipelineForContent.put("tableName", "EXAM_APPLY_DETAIL");
			// 行id的key也是固定的，此为设置行ID，也就是这行数据的ID值
			pipelineForContent.put("lineId", UUID.randomUUID().toString());			
				
			
			// 设置对象属性对应的管道号别名(**************************)
			pipelineForContent.put("patientID", "patientID");
			pipelineForContent.put("orderID", "orderID");
			pipelineForContent.put("orderStatus", "orderStatus");
			pipelineForContent.put("name", "name");
			pipelineForContent.put("birthday", "birthday");
			
			pipelineForContent.put("sex", "sex");
			pipelineForContent.put("age", "age");
			pipelineForContent.put("ageUnit", "ageUnit");
			pipelineForContent.put("fromDept", "fromDept");
			pipelineForContent.put("fromDoctor", "fromDoctor");
			  pipelineForContent.put("inPatientNO", "inPatientNO");
			pipelineForContent.put("outPatientNO", "outPatientNO");
			  pipelineForContent.put("sickBedNO", "sickBedNO");
			pipelineForContent.put("caseHistory", "caseHistory");
			pipelineForContent.put("clinicDiagnosis", "clinicDiagnosis");
			pipelineForContent.put("visceras", "visceras");
			pipelineForContent.put("fee", "fee");
			pipelineForContent.put("reqDate", "reqDate");
			  pipelineForContent.put("isInPatient", "isInPatient");
			pipelineForContent.put("catalog", "catalog");
			pipelineForContent.put("diagnosisNO", "diagnosisNO");
			pipelineForContent.put("moduleName", "moduleName");
			pipelineForContent.put("machineType", "machineType");
			pipelineForContent.put("status", "status");
			
			// 当属性有自定义的数据特征码时而不需要自动拼接的数据特征码格式时，就通过这个map对象设置属性的自定义特征码(可以不设置)
			Map<String, String> featureCodeForContent = new HashMap<String, String>();
	
			// 调用核心对象新增数据的方法
			try {
					serviceConsumer.saveService(requestConfig, order,
							pipelineForContent, featureCodeForContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
    }
}

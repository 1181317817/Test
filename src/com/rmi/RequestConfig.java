package com.rmi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.util.PropertiesUtil;

/**
 * 请求数据的头信息，用于对数据做一些配置
 * 
 * @author Administrator
 */
public class RequestConfig {
	public static final String DELETE = "delete";// 删除
	public static final String QUERY = "query"; // 查询
	public static final String UPDATA = "update"; // 修改
	public static final String INSERT = "insert";// 增加
	public static final String CONDITIONQUERY = "conditionQuery";

	public RequestConfig() throws IOException{
		Map<String,String> map=this.getUsernameAndPassword();
		this.username=map.get("username");
		this.password=map.get("password");
	}
	private String operation; // 操作标识
	private String username;
	private String password;

	private Boolean authenticity;// 打包为真进入正式数据，如果为假则进入测试数据,表示数据的是否为为正式数据
	private Boolean effectiveness;// 数据状态
	private String componentID;
	private String serviceCode;// 业务特征码 格式为"GZHC"+"-"+"耗材ID",表示表单的唯一性

	// 指定角标将会验证该数据主体目录content是否为空。如果为false就不进行验证，默认为true，key是数据的角标，value是数据是否验证的布尔值
	private Map<Integer, Boolean> dataDirectory = new HashMap<Integer, Boolean>();
	// 指定管道号将会验证该数据主体目录content是否为空。如果为false就不进行验证，默认为true，key是数据的角标，value是数据是否验证的布尔值
	private Map<String, Boolean> pipelineDirectory = new HashMap<String, Boolean>();

	public Map<String, Boolean> getPipelineDirectory() {
		return pipelineDirectory;
	}

	public void setPipelineDirectory(Map<String, Boolean> pipelineDirectory) {
		this.pipelineDirectory = pipelineDirectory;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAuthenticity() {
		return authenticity;
	}

	public void setAuthenticity(Boolean authenticity) {
		this.authenticity = authenticity;
	}

	public Boolean getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(Boolean effectiveness) {
		this.effectiveness = effectiveness;
	}

	public String getComponentID() {
		return componentID;
	}

	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Map<Integer, Boolean> getDataDirectory() {
		return dataDirectory;
	}

	public void setDataDirectory(Map<Integer, Boolean> dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

	@Override
	public String toString() {
		return "RequestConfig [authenticity=" + authenticity + ", effectiveness=" + effectiveness + ", componentID="
				+ componentID + ", serviceCode=" + serviceCode + ", dataDirectory=" + dataDirectory + ", operation="
				+ operation + ", username=" + username + ", password=" + password + "]";
	}

	public  Map<String, String> getUsernameAndPassword() throws IOException {
		// 配置文件的相对路径
		File file = new File(this.getClass().getResource("/").getPath()+"\\heavenpool-rmi.properties");
		String username =PropertiesUtil.getPropertiesValue(file, "username");
		String password =PropertiesUtil.getPropertiesValue(file, "password");
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		return map;
	}
}

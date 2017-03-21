package com.rmi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	public static String saveOrUpdateJson(RequestConfig requestConfig, List<ContentInfo> contentInfos)
			throws NoContentException, IOException {
		
		StringBuffer jsonSb = new StringBuffer();
		jsonSb.append("{");
		jsonSb.append("\"main\":");
		jsonSb.append("{ ");
		jsonSb.append("\"Authenticity\":");
		if (requestConfig.getAuthenticity() != null) {
			jsonSb.append(requestConfig.getAuthenticity());
		} else {
			throw new NoContentException("Authenticity不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"Effectiveness\":");
		if (requestConfig.getEffectiveness() != null) {
			jsonSb.append(requestConfig.getEffectiveness());
		} else {
			throw new NoContentException("Effectiveness不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"ComponentID\":");
		if (requestConfig.getComponentID() != null && !"".equals(requestConfig.getComponentID().trim())) {
			jsonSb.append("\"" + requestConfig.getComponentID() + "\"");
		} else {
			throw new NoContentException("ComponentID不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"ServiceCode\":");
		if (requestConfig.getServiceCode() != null && !"".equals(requestConfig.getServiceCode().trim())) {
			jsonSb.append("\"" + requestConfig.getServiceCode() + "\"");
		} else {
			throw new NoContentException("ServiceCode不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"DataDirectory\":");
		jsonSb.append("{");
		// 获取DataDirectory拥有的角标数量，根据里面的角标数量就能知道有多少条数据，就能知道对多少条数据要进行验证
		Integer dataDirectoryIndex = contentInfos.size();
		// 通过管道号，表示对哪个数据验证
		Map<String, Boolean> pipelineDirectory = requestConfig.getPipelineDirectory();
		// 循环出内容对象的角标，并且根据内容对象的管道号，来判断是否要进行为空验证
		for (int c = 0; c < dataDirectoryIndex; c++) {
			ContentInfo contentInfo = contentInfos.get(c);
			String alias = contentInfo.getAlias();// 拿到管道号
			Boolean isValidate = pipelineDirectory.get(alias);// 拿到指定管道号的数据判断是否需要验证，默认为验证，如果没有设置值就为验证
			if (null == isValidate) {
				jsonSb.append("\"" + c + "\"");
				jsonSb.append(":");
				jsonSb.append("\"" + true + "\"");
				// 当循环到最后一个角标以括号结尾
				if (c == dataDirectoryIndex - 1) {
					jsonSb.append("},");
				} else {
					jsonSb.append(",");
				}
			} else {
				jsonSb.append("\"" + c + "\"");
				jsonSb.append(":");
				jsonSb.append("\"" + isValidate + "\"");
				// 当循环到最后一个角标以括号结尾
				if (c == dataDirectoryIndex - 1) {
					jsonSb.append("},");
				} else {
					jsonSb.append(",");
				}
			}
		}
		jsonSb.append("\"Content\":[");
		for (int c = 0; c < contentInfos.size(); c++) {
			ContentInfo contentInfo = contentInfos.get(c);
			jsonSb.append("{");
			// 判断管道号
			if (contentInfo.getAlias() != null && !"".equals(contentInfo.getAlias().trim())) {
				jsonSb.append("\"Alias\":");
				jsonSb.append("\"" + contentInfo.getAlias() + "\"");
				jsonSb.append(",");
			} else {
				throw new NoContentException("管道号不能为空");
			}
			// 判断数据特征码
			if (contentInfo.getFeatureCode() != null && !"".equals(contentInfo.getFeatureCode().trim())) {
				jsonSb.append("\"FeatureCode\":");
				jsonSb.append("\"" + contentInfo.getFeatureCode() + "\"");
				jsonSb.append(",");
			} else {
				throw new NoContentException("数据特征码不能为空");
			}
			// 拿到内容的管道号
			String alias = contentInfo.getAlias();
			// 判断数据内容(判断该管道号是否有手动设置为空验证，默认为要验证)
			if (pipelineDirectory.get(alias) == null || pipelineDirectory.get(alias) == true) {
				if (contentInfo.getContent() != null && !"".equals(contentInfo.getContent().trim())) {
					// 为true或者false时不加双引号
					if (contentInfo.getContent().equals("true") || contentInfo.getContent().equals("false")) {
						jsonSb.append("\"Content\":");
						jsonSb.append("" + contentInfo.getContent() + "}");
					} else {
						jsonSb.append("\"Content\":");
						jsonSb.append("\"" + contentInfo.getContent() + "\"}");
					}
				} else {
					throw new NoContentException("管道号为:" + contentInfo.getAlias() + "  的数据内容不能为空");
				}
			} else {
				if (contentInfo.getContent() != null && !"true".equals(contentInfo.getContent())
						&& !"false".equals(contentInfo.getContent())) {
					jsonSb.append("\"Content\":");
					
					if(contentInfo.getContent() == null){
						jsonSb.append("\"\"}");
					}else{
						jsonSb.append("\"" + contentInfo.getContent() + "\"}");
					}
				
				} else {
					jsonSb.append("\"Content\":");
					if(contentInfo.getContent() == null){
						jsonSb.append("\"\"}");
					}else{
						jsonSb.append("\"" + contentInfo.getContent() + "\"}");
					}
					/*jsonSb.append("\"Content\":");
					jsonSb.append("\"" + contentInfo.getContent() + "\"}");*/
				}
			}
			if (c != contentInfos.size() - 1) {
				jsonSb.append(",");
			}
		}
		jsonSb.append("]},");
		jsonSb.append("\"Config\":");
		jsonSb.append("{");
		jsonSb.append("\"Username\":");
		if (requestConfig.getUsername() == null || "".equals(requestConfig.getUsername().trim())) {
			throw new NoContentException("用户名不能为空");
		}
		jsonSb.append("\"" + requestConfig.getUsername() + "\",");
		jsonSb.append("\"Password\":");
		if (requestConfig.getPassword() == null || "".equals(requestConfig.getPassword().trim())) {
			throw new NoContentException("密码不能为空");
		}
		jsonSb.append("\"" + requestConfig.getPassword() + "\",");
		jsonSb.append("\"Operation\":");
		if (requestConfig.getOperation() == null || "".equals(requestConfig.getOperation().trim())) {
			throw new NoContentException("操作标识不能为空");
		}
		jsonSb.append("\"" + requestConfig.getOperation() + "\"");
		jsonSb.append("}}");
		return jsonSb.toString();
	}

	/**
	 * 将数据变成json
	 * 
	 * @param requestConfig
	 * @param contentInfos
	 * @return
	 * @throws NoContentException
	 *             内容为空的异常
	 */
	public static String insertOrUpdateJson(RequestConfig requestConfig, List<ContentInfo> contentInfos)
			throws NoContentException {
		StringBuffer jsonSb = new StringBuffer();
		jsonSb.append("{");
		jsonSb.append("\"main\":");
		jsonSb.append("{ ");
		jsonSb.append("\"Authenticity\":");
		if (requestConfig.getAuthenticity() != null) {
			jsonSb.append(requestConfig.getAuthenticity());
		} else {
			throw new NoContentException("Authenticity不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"Effectiveness\":");
		if (requestConfig.getEffectiveness() != null) {
			jsonSb.append(requestConfig.getEffectiveness());
		} else {
			throw new NoContentException("Effectiveness不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"ComponentID\":");
		if (requestConfig.getComponentID() != null && !"".equals(requestConfig.getComponentID().trim())) {
			jsonSb.append("\"" + requestConfig.getComponentID() + "\"");
		} else {
			throw new NoContentException("ComponentID不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"ServiceCode\":");
		if (requestConfig.getServiceCode() != null && !"".equals(requestConfig.getServiceCode().trim())) {
			jsonSb.append("\"" + requestConfig.getServiceCode() + "\"");
		} else {
			throw new NoContentException("ServiceCode不能为空");
		}
		jsonSb.append(",");
		jsonSb.append("\"DataDirectory\":");
		jsonSb.append("{");
		// 获取DataDirectory拥有的角标数量，根据里面的角标数量就能知道有多少条数据，就能知道对多少条数据要进行验证
		Integer dataDirectoryIndex = contentInfos.size();
		Map<Integer, Boolean> dataDirectoryMap = requestConfig.getDataDirectory();
		for (int c = 0; c < dataDirectoryIndex; c++) {
			Boolean isValidate = dataDirectoryMap.get(c);// 拿到指定角标的数据判断是否需要验证，默认为验证，如果没有设置值就为验证
			if (null == isValidate) {
				jsonSb.append("\"" + c + "\"");
				jsonSb.append(":");
				jsonSb.append("\"" + true + "\"");
				// 当循环到最后一个角标以括号结尾
				if (c == dataDirectoryIndex - 1) {
					jsonSb.append("},");
				} else {
					jsonSb.append(",");
				}
			} else {
				jsonSb.append("\"" + c + "\"");
				jsonSb.append(":");
				jsonSb.append("\"" + isValidate + "\"");
				// 当循环到最后一个角标以括号结尾
				if (c == dataDirectoryIndex - 1) {
					jsonSb.append("},");
				} else {
					jsonSb.append(",");
				}
			}
		}
		jsonSb.append("\"Content\":[");
		for (int c = 0; c < contentInfos.size(); c++) {
			ContentInfo contentInfo = contentInfos.get(c);
			jsonSb.append("{");
			// 判断管道号
			if (contentInfo.getAlias() != null && !"".equals(contentInfo.getAlias().trim())) {
				jsonSb.append("\"Alias\":");
				jsonSb.append("\"" + contentInfo.getAlias() + "\"");
				jsonSb.append(",");
			} else {
				throw new NoContentException("管道号不能为空");
			}
			// 判断数据特征码
			if (contentInfo.getFeatureCode() != null && !"".equals(contentInfo.getFeatureCode().trim())) {
				jsonSb.append("\"FeatureCode\":");
				jsonSb.append("\"" + contentInfo.getFeatureCode() + "\"");
				jsonSb.append(",");
			} else {
				throw new NoContentException("管道号为:" + contentInfo.getAlias() + "  的数据特征码不能为空");
			}
			// 判断数据内容
			if (dataDirectoryMap.get(c) == null || dataDirectoryMap.get(c) == true) {
				if (contentInfo.getContent() != null) {
					jsonSb.append("\"Content\":");
					jsonSb.append("\"" + contentInfo.getContent() + "\"}");
				} else {
					throw new NoContentException("管道号为:" + contentInfo.getAlias() + "  的数据内容不能为空");
				}
			} else {
				if (contentInfo.getContent() == null || "true".equals(contentInfo.getContent())
						|| "false".equals(contentInfo.getContent())) {
					jsonSb.append("\"Content\":");
					if(contentInfo.getContent() == null){
						jsonSb.append("\"\"}");
					}else{
						jsonSb.append("" + contentInfo.getContent() + "}");
					}
				} else {
					jsonSb.append("\"Content\":");
					jsonSb.append("\"" + contentInfo.getContent() + "\"}");
				}
			}
			if (c != contentInfos.size() - 1) {
				jsonSb.append(",");
			}
		}
		jsonSb.append("]},");
		jsonSb.append("\"Config\":");
		jsonSb.append("{");
		jsonSb.append("\"Username\":");
		if (requestConfig.getUsername() == null || "".equals(requestConfig.getUsername().trim())) {
			throw new NoContentException("用户名不能为空");
		}
		jsonSb.append("\"" + requestConfig.getUsername() + "\",");
		jsonSb.append("\"Password\":");
		if (requestConfig.getPassword() == null || "".equals(requestConfig.getPassword().trim())) {
			throw new NoContentException("密码不能为空");
		}
		jsonSb.append("\"" + requestConfig.getPassword() + "\",");
		jsonSb.append("\"Operation\":");
		if (requestConfig.getOperation() == null || "".equals(requestConfig.getOperation().trim())) {
			throw new NoContentException("操作标识不能为空");
		}
		jsonSb.append("\"" + requestConfig.getOperation() + "\"");
		jsonSb.append("}}");
		return jsonSb.toString();
	}

	/**
	 * 查询删除的json转换格式，不需要实体内容,只需要向后台传入业务号
	 * 
	 * @param requestConfig
	 *            请求配置对象
	 * @return
	 * @throws NoContentException
	 */
	public static String queryOrDeleteJson(RequestConfig requestConfig) throws NoContentException {
		StringBuffer jsonSb = new StringBuffer();
		jsonSb.append("{");

		jsonSb.append("\"main\":{");

		jsonSb.append("\"Effectiveness\":");
		if (requestConfig.getEffectiveness() == null || "".equals(requestConfig.getEffectiveness())) {
			throw new NoContentException("Effectiveness不能为空");
		}
		jsonSb.append(requestConfig.getEffectiveness());
		jsonSb.append(",");

		jsonSb.append("\"ComponentID\":");
		if (requestConfig.getComponentID() == null || "".equals(requestConfig.getComponentID())) {
			throw new NoContentException("ComponentID不能为空");
		}
		jsonSb.append("\"" + requestConfig.getComponentID() + "\"");
		jsonSb.append(",");


		jsonSb.append("\"ServiceCode\":");
		if (requestConfig.getServiceCode() != null && !"".equals(requestConfig.getServiceCode())) {
			jsonSb.append("\"" + requestConfig.getServiceCode() + "\"");
			jsonSb.append("},");
		} else {
			jsonSb.append("" + requestConfig.getServiceCode() + "");
			jsonSb.append("},");
		}

		jsonSb.append("\"Config\":");
		jsonSb.append("{");
		jsonSb.append("\"Username\":");
		if (requestConfig.getUsername() == null || "".equals(requestConfig.getUsername().trim())) {
			throw new NoContentException("用户名不能为空");
		}
		jsonSb.append("\"" + requestConfig.getUsername() + "\",");
		jsonSb.append("\"Password\":");
		if (requestConfig.getPassword() == null || "".equals(requestConfig.getPassword().trim())) {
			throw new NoContentException("密码不能为空");
		}
		jsonSb.append("\"" + requestConfig.getPassword() + "\",");
		jsonSb.append("\"Operation\":");
		if (requestConfig.getOperation() == null || "".equals(requestConfig.getOperation().trim())) {
			throw new NoContentException("操作标识不能为空");
		}
		jsonSb.append("\"" + requestConfig.getOperation() + "\"");
		jsonSb.append("}");

		jsonSb.append("}");
		return jsonSb.toString();
	}

	public static List<ContentInfo> getContentInfo(Object bean, Map<String, String> pipelineForContent,
			Map<String, String> featureCodeForContent)
			throws IllegalArgumentException, IllegalAccessException, NoContentException {
		String tableName = pipelineForContent.get("tableName");
		String lineId = pipelineForContent.get("lineId");
		if (tableName == null || tableName.trim() == "") {
			throw new NoContentException("拼接数据特征码的表名不能为空");
		}
		if (lineId == null || lineId.trim() == "") {
			throw new NoContentException("拼接数据特征码的行Id不能为空");
		}
		List<ContentInfo> contentInfos = new ArrayList<>();
		Field[] fs = bean.getClass().getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			f.setAccessible(true); // 设置些属性是可以访问的
			String fieldName = f.getName();
			Object beanValue = f.get(bean); // 得到此属性的值
			// 属性首字母变成小写
			String lowerCase = fieldName.substring(0, 1).toLowerCase();
			StringBuffer sb = new StringBuffer(fieldName);
			sb.replace(0, 1, lowerCase);
			String lowerCaseFieldName = sb.toString();
			String alias = pipelineForContent.get(lowerCaseFieldName);// 得到该属性对应的管道号

			ContentInfo contentInfo = new ContentInfo();
			// 如果是日期类型做特殊处理
			if (beanValue instanceof Date) {
				Date dateValue = (Date) beanValue;
				contentInfo.setContent(dateValue.getTime() + "");
			} else {
				if (beanValue == null) {
					contentInfo.setContent(null);
				} else {
					contentInfo.setContent(beanValue + "");
				}
			}
			if (alias != null && alias.trim() != "") {
				contentInfo.setAlias(alias);
			} else {
				throw new NoContentException(lowerCaseFieldName + "属性的管道号不能为空");
			}
			if (featureCodeForContent != null && featureCodeForContent.get(lowerCaseFieldName) != null) {
				contentInfo.setFeatureCode(featureCodeForContent.get(lowerCaseFieldName));
			} else {
				contentInfo.setFeatureCode(tableName + "-" + lowerCaseFieldName + "-" + lineId);
			}
			contentInfos.add(contentInfo);
		}
		return contentInfos;
	}

	
}

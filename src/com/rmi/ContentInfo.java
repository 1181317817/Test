package com.rmi;

/**
 * 封装Content传输主体的信息
 * 
 * @author Administrator
 */
public class ContentInfo {
	private String alias; // 管道号
	private String featureCode; // {{基本资料}}-{{名称}}-21254665", //数据特征码 =
								// 主表表名-名称字段名-行ID
	private String content; // 数据内容，真实数据

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ContentInfo [alias=" + alias + ", featureCode=" + featureCode + ", content=" + content + "]";
	}

}

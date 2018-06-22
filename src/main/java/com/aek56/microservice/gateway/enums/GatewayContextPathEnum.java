package com.aek56.microservice.gateway.enums;

/**
 * 网关contextPath枚举
 *	
 * @author HongHui
 * @date   2017年7月27日
 */
public enum GatewayContextPathEnum {

	SYS("sys","用户中心模块上下文路径"),
	ASSETS("assets","资产模块上下文路径"),
	REPAIR("repair","维修模块上下文路径");
	
	private String contextPath;
	private String desc;
	
	private GatewayContextPathEnum(String contextPath, String desc) {
		this.contextPath = contextPath;
		this.desc = desc;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}

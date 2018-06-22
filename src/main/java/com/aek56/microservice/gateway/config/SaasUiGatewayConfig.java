package com.aek56.microservice.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SaasUI配置信息
 *	
 * @author HongHui
 * @date   2017年7月4日
 */
@Component
@ConfigurationProperties(prefix = "gateway")
public class SaasUiGatewayConfig {
	
	//主机名称
	private String hostname;
	//端口号
	private String port;
	//上下文路径
	private String contextPath;
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
}

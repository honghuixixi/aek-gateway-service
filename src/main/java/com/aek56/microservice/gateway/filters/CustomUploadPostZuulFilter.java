package com.aek56.microservice.gateway.filters;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aek56.microservice.gateway.config.SaasUiGatewayConfig;
import com.aek56.microservice.gateway.model.Result;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 文件上传Zuul POST过滤器
 * 
 * @author HongHui
 * @date   20170年7月4日
 */
//@Component
public class CustomUploadPostZuulFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUploadPostZuulFilter.class);
	//文件上传URI前缀
	private static final String FILE_UPLOAD_URI_PREFIX = "/api/upload/";
	//访问文件URI前缀
	private static final String FILE_ACCESS_URI_PREFIX = "/api/file";
	
	@Autowired
	private SaasUiGatewayConfig saasUiGatewayConfig;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object run() {
		LOGGER.info("=====文件上传zuul post过滤器开始======");
		RequestContext rctx = RequestContext.getCurrentContext();
		String requestUri = rctx.getRequest().getRequestURI();
		String requestMethod = rctx.getRequest().getMethod();
		LOGGER.info("1.进入过滤器的URI:" + requestUri );
		LOGGER.info("2.URI请求方法:" + requestMethod );
		if(requestUri.contains(FILE_UPLOAD_URI_PREFIX)){
			LOGGER.info("3.开始对文件上传返回的地址进行处理.");
			try {
				InputStream responseDataInputStream = rctx.getResponseDataStream();
				String responseBody = IOUtils.toString(responseDataInputStream, "UTF-8");
				LOGGER.info("4.上传响应数据=" + responseBody);
				Result<List<String>> result = new Gson().fromJson(responseBody, Result.class);
				if("200".equals(result.getCode())){
					List<String> data = result.getData();
					List<String> newUrls = new ArrayList<String>();
					for (String url : data) {
						LOGGER.info(url);
						StringBuilder response = new StringBuilder();
						response.append("http://")
							.append(saasUiGatewayConfig.getHostname())      //主机名
							.append(":")                                    //冒号
							.append(saasUiGatewayConfig.getPort())          //端口号
							.append(FILE_ACCESS_URI_PREFIX)   //上下文路径
							.append(url);
						LOGGER.info("5.组装后文件路径=" + response.toString());
						newUrls.add(response.toString());
					}
					result.setData(newUrls);
					responseBody = new Gson().toJson(result);
				}
				rctx.setResponseStatusCode(Integer.valueOf(result.getCode()));
				rctx.setResponseBody(responseBody);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("=====文件上传zuul post过滤器结束======");
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 11;
	}
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

}

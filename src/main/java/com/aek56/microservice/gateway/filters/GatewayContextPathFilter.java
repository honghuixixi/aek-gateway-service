package com.aek56.microservice.gateway.filters;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aek56.microservice.gateway.enums.GatewayContextPathEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * ContextPathFilter 暂时不用
 * 
 * @author HongHui
 * @date   2017年7月27日
 */
//@Component
public class GatewayContextPathFilter extends ZuulFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayContextPathFilter.class);
	
	@Override
	public Object run() {
		RequestContext  rctx = RequestContext.getCurrentContext();
		String requestUri = rctx.getRequest().getRequestURI(); 
		LOGGER.debug("REQUEST_URL =" + requestUri);
		String newRequestUri = getNewRequestURI(requestUri);
		LOGGER.debug("New RequestUri =" + newRequestUri);
		if(StringUtils.isNotBlank(newRequestUri)){
			rctx.put("requestURI", newRequestUri);
			rctx.set("requestURI", newRequestUri);
			HttpServletRequest request = rctx.getRequest();
			request.setAttribute("requestURI", newRequestUri);
			rctx.setRequest(request);
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}
	
	@Override
	public boolean shouldFilter() {
		return true;
	}
	
	public static String getNewRequestURI(String uri){
		String[] uris = uri.split("/");
		List<String> uriList = new ArrayList<String>();
		String contextPath = "";
		for(int i=0; i < uris.length; i++){
			if(StringUtils.isNotBlank(uris[i])){
				uriList.add(uris[i]);
			}
		}
		for (String url : uriList) {
			if(url.equals(GatewayContextPathEnum.SYS.getContextPath())
			   || url.equals(GatewayContextPathEnum.ASSETS.getContextPath())
			   || url.equals(GatewayContextPathEnum.REPAIR.getContextPath())){
				contextPath = url;
				break;
			}
		}
		if(StringUtils.isNotBlank(contextPath)){
			String[] urls = uri.split(contextPath);
			return urls[0]+contextPath+"/"+contextPath+urls[1];
		}
		return uri;
	}
}

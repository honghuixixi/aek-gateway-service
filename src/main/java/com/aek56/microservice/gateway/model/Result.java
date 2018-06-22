package com.aek56.microservice.gateway.model;

/**
 * 接口返回实体类
 *	
 * @author HongHui
 * @date   2017年7月4日
 */
public class Result<T> {

	public Result() {
	}

	public Result(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	//状态码.200(OK)
	private String code;

	//返回的消息
	private String msg;

	//返回的数据
	private T data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

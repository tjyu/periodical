package com.cn.periodical.enums;

public enum SystemIdEnums {
	/**
	 * 编辑系统
	 * */
	EDIT_SYS("111","EDIT_SYS"),
	/**
	 * 专家系统
	 * */
	EXPERT_SYS("222","EXPERT_SYS"),
	/**
	 * 作者系统
	 * */
	AUTHOR_SYS("333","AUTHOR_SYS"),
	/**
	 * 读者系统
	 * */
	READER_SYS("444","READER_SYS");
	
	private String code;
	private String name;
	SystemIdEnums(String code,String name){
		this.code=code;
		this.name=name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

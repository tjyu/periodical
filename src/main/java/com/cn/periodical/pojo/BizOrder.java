package com.cn.periodical.pojo;

import java.io.Serializable;

public class BizOrder implements Serializable {

	public BizOrder() {
		// TODO Auto-generated constructor stub
	}
	private String pCnName;
	private String oStatus;
	private String oAmount;
	private String piName;
	private String piUrl;
	private String payNo;
	private String payAmount;
	private String eAccountNo;
	private String eAccountName;
	private String orderNo;
	private String pYear;
	private String subNums;
	private String amount;
	private String submitTime;
	private String auditTime;
	private String userId;
	private String erAccount;
	private String erName;
	private String aPayAmount;
	
	
	
	public String getaPayAmount() {
		return aPayAmount;
	}
	public void setaPayAmount(String aPayAmount) {
		this.aPayAmount = aPayAmount;
	}
	public String getErAccount() {
		return erAccount;
	}
	public void setErAccount(String erAccount) {
		this.erAccount = erAccount;
	}
	public String getErName() {
		return erName;
	}
	public void setErName(String erName) {
		this.erName = erName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getpYear() {
		return pYear;
	}
	public void setpYear(String pYear) {
		this.pYear = pYear;
	}
	public String getSubNums() {
		return subNums;
	}
	public void setSubNums(String subNums) {
		this.subNums = subNums;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getpCnName() {
		return pCnName;
	}
	public void setpCnName(String pCnName) {
		this.pCnName = pCnName;
	}
	public String getoStatus() {
		return oStatus;
	}
	public void setoStatus(String oStatus) {
		this.oStatus = oStatus;
	}
	public String getoAmount() {
		return oAmount;
	}
	public void setoAmount(String oAmount) {
		this.oAmount = oAmount;
	}
	public String getPiName() {
		return piName;
	}
	public void setPiName(String piName) {
		this.piName = piName;
	}
	public String getPiUrl() {
		return piUrl;
	}
	public void setPiUrl(String piUrl) {
		this.piUrl = piUrl;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String geteAccountNo() {
		return eAccountNo;
	}
	public void seteAccountNo(String eAccountNo) {
		this.eAccountNo = eAccountNo;
	}
	public String geteAccountName() {
		return eAccountName;
	}
	public void seteAccountName(String eAccountName) {
		this.eAccountName = eAccountName;
	}
}

package com.elux.ado.customer;

import java.io.Serializable;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8382463973524457066L;
	private int cusID;
	private String cusName;
	private String cusAddress;
	private String cusAddrForInvoice;
	private String cusEAddress;
	
	public Customer(int cusID, String cusName, String cusAddress, String cusAddrForInvoice, String cusEAddress) {
		this.cusID = cusID;
		this.cusName = cusName;
		this.cusAddress = cusAddress;
		this.cusAddrForInvoice = cusAddrForInvoice;
		this.cusEAddress = cusEAddress;
	}
	public int getCusID() {
		return cusID;
	}
	public void setCusID(int cusID) {
		this.cusID = cusID;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getCusAddress() {
		return cusAddress;
	}
	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}
	public String getCusAddrForInvoice() {
		return cusAddrForInvoice;
	}
	public void setCusAddrForInvoice(String cusAddrForInvoice) {
		this.cusAddrForInvoice = cusAddrForInvoice;
	}
	public String getCusEAddress() {
		return cusEAddress;
	}
	public void setCusEAddress(String cusEAddress) {
		this.cusEAddress = cusEAddress;
	}
	
}

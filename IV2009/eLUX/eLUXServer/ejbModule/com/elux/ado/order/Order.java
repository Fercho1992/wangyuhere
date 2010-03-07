package com.elux.ado.order;

import java.io.Serializable;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ordID;
	private String ordStatus;
	private String ordTime;
	private int cusID;

	public Order(int ordID, String ordStatus, String ordTime, int cusID) {
		this.ordID = ordID;
		this.ordStatus = ordStatus;
		this.ordTime = ordTime;
		this.setCusID(cusID);
	}

	public Order() {

	}

	public int getOrdID() {
		return ordID;
	}

	public void setOrdID(int ordID) {
		this.ordID = ordID;
	}

	public String getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}

	public String getOrdTime() {
		return ordTime;
	}

	public void setOrdTime(String ordTime) {
		this.ordTime = ordTime;
	}

	public void setCusID(int cusID) {
		this.cusID = cusID;
	}

	public int getCusID() {
		return cusID;
	}
}

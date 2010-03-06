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
}

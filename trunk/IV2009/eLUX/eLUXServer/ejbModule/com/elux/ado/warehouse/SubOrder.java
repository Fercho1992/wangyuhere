package com.elux.ado.warehouse;

import java.io.Serializable;

public class SubOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1656186163924269248L;

	private int subOrderID;
	private String subOrderTime;
	private String subOrderStatus;
	private int subOrderAmount;
	public int getSubOrderID() {
		return subOrderID;
	}
	public void setSubOrderID(int subOrderID) {
		this.subOrderID = subOrderID;
	}
	public String getSubOrderTime() {
		return subOrderTime;
	}
	public void setSubOrderTime(String subOrderTime) {
		this.subOrderTime = subOrderTime;
	}
	public String getSubOrderStatus() {
		return subOrderStatus;
	}
	public void setSubOrderStatus(String subOrderStatus) {
		this.subOrderStatus = subOrderStatus;
	}
	public int getSubOrderAmount() {
		return subOrderAmount;
	}
	public void setSubOrderAmount(int subOrderAmount) {
		this.subOrderAmount = subOrderAmount;
	}
}

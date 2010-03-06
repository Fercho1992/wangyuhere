package com.elux.ado.order;

import java.io.Serializable;

public class OrderItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int getOrdItermID() {
		return ordItermID;
	}
	public void setOrdItermID(int ordItermID) {
		this.ordItermID = ordItermID;
	}
	public String getOrdItermAmount() {
		return ordItermAmount;
	}
	public void setOrdItermAmount(String ordItermAmount) {
		this.ordItermAmount = ordItermAmount;
	}
	public float getOrdPrice() {
		return ordPrice;
	}
	public void setOrdPrice(float ordPrice) {
		this.ordPrice = ordPrice;
	}
	private int ordItermID;
	private String ordItermAmount;
	private float ordPrice;
}

package com.elux.ado.order;

import java.io.Serializable;

public class OrderItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ordItermID;
	private int ordItermAmount;
	private double ordPrice;
	private int ProID;
	private int OrderID;

        public OrderItem(int amount, double price, int proID){
            this.ordItermAmount = amount;
            this.ordPrice = price;
            this.ProID = proID;
        }
	
	public int getOrdItermID() {
		return ordItermID;
	}
	public void setOrdItermID(int ordItermID) {
		this.ordItermID = ordItermID;
	}
	public int getOrdItermAmount() {
		return ordItermAmount;
	}
	public void setOrdItermAmount(int ordItermAmount) {
		this.ordItermAmount = ordItermAmount;
	}
	public double getOrdPrice() {
		return ordPrice;
	}
	public void setOrdPrice(double ordPrice) {
		this.ordPrice = ordPrice;
	}
	public int getProID() {
		return ProID;
	}
	public void setProID(int proID) {
		ProID = proID;
	}
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	
	
	
}

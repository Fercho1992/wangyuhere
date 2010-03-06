package com.elux.ado.warehouse;

import java.io.Serializable;

public class WarehouseProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6989486804446061419L;

	private int wareProID;
	private int amount;
	private int limitLevel;
	public int getWareProID() {
		return wareProID;
	}
	public void setWareProID(int wareProID) {
		this.wareProID = wareProID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getLimitLevel() {
		return limitLevel;
	}
	public void setLimitLevel(int limitLevel) {
		this.limitLevel = limitLevel;
	}
	
	
	
}

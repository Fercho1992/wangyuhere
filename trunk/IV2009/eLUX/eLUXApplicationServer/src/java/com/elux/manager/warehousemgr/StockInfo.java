package com.elux.manager.warehousemgr;

import java.io.Serializable;

public class StockInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StockInfo(int ProductID, String ProductName, int WareID, int StockLevel, int Amount) {
		this.ProductID = ProductID;
		this.ProductName = ProductName;
		this.WareID = WareID;
		this.StockLevel = StockLevel;
		this.Amount = Amount;
	}
	
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public int getWareID() {
		return WareID;
	}
	public void setWareID(int wareID) {
		WareID = wareID;
	}
	public int getStockLevel() {
		return StockLevel;
	}
	public void setStockLevel(int stockLevel) {
		StockLevel = stockLevel;
	}
	public int getAmount() {
		return Amount;
	}
	public void setAmount(int amount) {
		Amount = amount;
	}
	private int ProductID;
	private String ProductName;
	private int WareID;
	private int StockLevel;
	private int Amount;

}

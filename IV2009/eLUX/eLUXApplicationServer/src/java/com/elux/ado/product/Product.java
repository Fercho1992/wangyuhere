package com.elux.ado.product;

import java.io.Serializable;

public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5713192315928789434L;
	private int proID;
	private String proName;
	private double proPrice;
	private int proCatID;

	Product(int proID, String proName, double proPrice) {
		this.proID = proID;
		this.proName = proName;
		this.proPrice = proPrice;
	}

	public Product() {
	}
	
	

	public int getProCatID() {
		return proCatID;
	}

	public void setProCatID(int proCatID) {
		this.proCatID = proCatID;
	}

	public int getProID() {
		return proID;
	}

	public void setProID(int proID) {
		this.proID = proID;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public double getProPrice() {
		return proPrice;
	}

	public void setProPrice(double proPrice) {
		this.proPrice = proPrice;
	}

}

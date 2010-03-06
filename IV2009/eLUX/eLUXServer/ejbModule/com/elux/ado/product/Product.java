package com.elux.ado.product;

import java.io.Serializable;

public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5713192315928789434L;
	private int proID;
	private String proName;
	private String proPrice;

	Product(int proID, String proName, String proPrice) {
		this.proID = proID;
		this.proName = proName;
		this.proPrice = proPrice;
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

	public String getProPrice() {
		return proPrice;
	}

	public void setProPrice(String proPrice) {
		this.proPrice = proPrice;
	}

}

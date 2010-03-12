package com.elux.ado.product;

import java.io.Serializable;

public class ProductCategory implements Serializable {
	
	/**
         *
	 */
	private static final long serialVersionUID = -7189549802554120439L;
	private int proCatID;
	private String proCatName;

	ProductCategory(int proCatID, String proCatName) {
		this.proCatID = proCatID;
		this.proCatName = proCatName;
	}

	public ProductCategory() {
	}

	public int getProCatID() {
		return proCatID;
	}

	public void setProCatID(int proCatID) {
		this.proCatID = proCatID;
	}

	public String getProCatName() {
		return proCatName;
	}

	public void setProCatName(String proCatName) {
		this.proCatName = proCatName;
	}

}

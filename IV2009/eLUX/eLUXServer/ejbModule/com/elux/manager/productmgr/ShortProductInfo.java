package com.elux.manager.productmgr;

import java.io.Serializable;

public class ShortProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -385910378433902644L;

	private int id;
	private String name;
	private double price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}

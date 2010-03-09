package com.elux.ado.customer;

import java.io.Serializable;

public class Rebate implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int rebID;
	private double rebate;
	private int cusID;
	private int proCatID;
	
	public int getRebID() {
		return rebID;
	}
	public void setRebID(int rebID) {
		this.rebID = rebID;
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	public int getCusID() {
		return cusID;
	}
	public void setCusID(int cusID) {
		this.cusID = cusID;
	}
	public int getProCatID() {
		return proCatID;
	}
	public void setProCatID(int proCatID) {
		this.proCatID = proCatID;
	}
	
}

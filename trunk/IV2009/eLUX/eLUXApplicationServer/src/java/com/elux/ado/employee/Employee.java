package com.elux.ado.employee;

import java.io.Serializable;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3162546340866741941L;
	private int perID;
	private String perName;
	private String perAddr;
	private String position;
	public int getPerID() {
		return perID;
	}
	public void setPerID(int perID) {
		this.perID = perID;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	public String getPerAddr() {
		return perAddr;
	}
	public void setPerAddr(String perAddr) {
		this.perAddr = perAddr;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}

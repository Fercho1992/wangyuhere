package com.elux.ado.warehouse;

import java.io.Serializable;

public class SubSupplier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8697456195076468316L;
	private int subSupID;
	private String subSupName;
	public int getSubSupID() {
		return subSupID;
	}
	public void setSubSupID(int subSupID) {
		this.subSupID = subSupID;
	}
	public String getSubSupName() {
		return subSupName;
	}
	public void setSubSupName(String subSupName) {
		this.subSupName = subSupName;
	}
	
	
	
}

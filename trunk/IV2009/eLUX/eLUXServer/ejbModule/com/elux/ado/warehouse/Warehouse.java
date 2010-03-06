package com.elux.ado.warehouse;

import java.io.Serializable;

public class Warehouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4556341512024482789L;
	
	private int warID;
	private String warName;
	private String warLocation;
	public int getWarID() {
		return warID;
	}
	public void setWarID(int warID) {
		this.warID = warID;
	}
	public String getWarName() {
		return warName;
	}
	public void setWarName(String warName) {
		this.warName = warName;
	}
	public String getWarLocation() {
		return warLocation;
	}
	public void setWarLocation(String warLocation) {
		this.warLocation = warLocation;
	}
	
	
}

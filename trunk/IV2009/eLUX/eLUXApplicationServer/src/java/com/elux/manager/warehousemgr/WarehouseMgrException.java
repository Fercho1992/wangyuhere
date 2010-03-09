package com.elux.manager.warehousemgr;

import javax.ejb.EJBException;

public class WarehouseMgrException extends EJBException {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String userMsg;
	
	public WarehouseMgrException(String userMsg, String message) {
		super(message);
		this.userMsg = userMsg;
	}

	public String getUserMsg() {
		return userMsg;
	}

	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
}

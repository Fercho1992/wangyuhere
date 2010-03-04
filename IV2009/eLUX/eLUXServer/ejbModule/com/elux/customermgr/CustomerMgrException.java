package com.elux.customermgr;

import javax.ejb.EJBException;

public class CustomerMgrException extends EJBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4185485727878835634L;
	
	private String userMsg;
	
	public CustomerMgrException(String userMsg, String message) {
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

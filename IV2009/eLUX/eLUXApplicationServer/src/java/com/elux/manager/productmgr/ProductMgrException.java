package com.elux.manager.productmgr;

import javax.ejb.EJBException;

public class ProductMgrException extends EJBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4185485727878835634L;
	
	private String userMsg;
	
	public ProductMgrException(String userMsg, String message) {
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

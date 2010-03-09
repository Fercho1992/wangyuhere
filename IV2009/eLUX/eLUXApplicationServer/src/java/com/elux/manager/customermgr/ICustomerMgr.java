package com.elux.manager.customermgr;

import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;

public interface ICustomerMgr {

	public Customer getCustomerInfo(int cusID) throws CustomerMgrException;
	public void setCustomerInfo(Customer customer) throws CustomerMgrException;
	public void saveCorrespondence(DocContact docContact)throws CustomerMgrException;
	public double getDiscount(int cusID, int proCatID) throws CustomerMgrException;
}

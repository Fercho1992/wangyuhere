package com.elux.manager.customermgr;

import com.elux.ado.customer.Customer;

public interface CustomerMgr {

	public Customer getCustomerInfo(int cusID) throws CustomerMgrException;
}

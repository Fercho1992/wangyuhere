package com.elux.customermgr;

import com.elux.ado.customer.Customer;

public interface CustomerMgr {

	public Customer getCustomerInfo(int cusID) throws CustomerMgrException;
}

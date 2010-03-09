package com.elux.system.employeesys;

import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;
import com.elux.manager.customermgr.CustomerMgrBean;
import com.elux.manager.customermgr.ICustomerMgr;
import com.elux.manager.ordermgr.IOrderMgr;
import com.elux.manager.ordermgr.OrderMgrBean;
import com.elux.manager.warehousemgr.IWarehouseMgr;
import com.elux.manager.warehousemgr.StockInfo;
import com.elux.manager.warehousemgr.WarehouseMgrBean;

public class EmployeeSysBean implements IEmployeeSys {

	private IOrderMgr orderMgr = new OrderMgrBean();
	private ICustomerMgr customerMgr = new CustomerMgrBean();
	private IWarehouseMgr warehouseMgr = new WarehouseMgrBean();

	@Override
	public Customer getCustomerInfo(int cusID) {
		// TODO Auto-generated method stub
		return customerMgr.getCustomerInfo(cusID);
	}

	@Override
	public StockInfo getStockInfo(int productID, int wareID) {
		// TODO Auto-generated method stub
		return warehouseMgr.getStockInfo(productID, wareID);
	}

	@Override
	public void removeNonDelvOrder(int ordID) {
		// TODO Auto-generated method stub
		orderMgr.removeNonDelvOrder(ordID);
	}

	@Override
	public void saveCorrespondence(DocContact docContact) {
		// TODO Auto-generated method stub
		customerMgr.saveCorrespondence(docContact);
	}

	@Override
	public void setCustomerInfo(Customer customer) {
		// TODO Auto-generated method stub
		customerMgr.setCustomerInfo(customer);
	}

}

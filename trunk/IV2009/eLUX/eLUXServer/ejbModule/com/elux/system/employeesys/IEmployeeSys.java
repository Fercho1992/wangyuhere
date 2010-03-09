package com.elux.system.employeesys;


import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;
import com.elux.manager.warehousemgr.StockInfo;

public interface IEmployeeSys {

	public Customer getCustomerInfo(int cusID);

	public void setCustomerInfo(Customer customer);

	public void removeNonDelvOrder(int ordID);

	public StockInfo getStockInfo(int productID, int wareID);

	public void saveCorrespondence(DocContact docContact);
}

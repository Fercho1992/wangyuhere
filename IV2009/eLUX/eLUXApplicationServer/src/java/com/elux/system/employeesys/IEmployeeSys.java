package com.elux.system.employeesys;


import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;
import com.elux.ado.order.Order;
import com.elux.manager.warehousemgr.StockInfo;
import java.util.Vector;

public interface IEmployeeSys {

	public Customer getCustomerInfo(int cusID);

         public Customer getCustomerInfoByOrderID(int ordID);

	public void setCustomerInfo(Customer customer);
        public Vector<Order> searchNonDelvOrder(String orderStatus);

	public void removeNonDelvOrder(int ordID);

        public Vector<Order> searchNonDelvOrder(String orderStatus);

	public StockInfo getStockInfo(int productID, int wareID);

	public void saveCorrespondence(DocContact docContact);
}

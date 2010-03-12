package com.elux.system.employeesys;


import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;
import com.elux.ado.order.Order;
import com.elux.manager.warehousemgr.StockInfo;
import java.util.Vector;

public interface IEmployeeSys {
/**
	   * through input customer's ID, get the customer's information
	   *
	   * @param input  customer's ID is a int type
	   * @return Customer is a datatype class, including customer's infomation:ID,Name,Address,Address for invoice and Email address.
	   * @throws CustomerMgrException
	   */
	public Customer getCustomerInfo(int cusID);

         public Customer getCustomerInfoByOrderID(int ordID);
/**
	   * update customer's information.
	   *
	   * @param input data type is Customer class,including customer's infomation:ID,Name,Address,Address for invoice and Email address.
	   * @return
	   * @throws CustomerMgrException
	   */
	public void setCustomerInfo(Customer customer);
        /**
	   * Search Orders whose states are non-delivered .
	   *
	   * @param input order status is a string type.
   	   * @return a series of orders which are non-delivered.
	   * @throws OrderMgrException
         */
        public Vector<Order> searchNonDelvOrder(String orderStatus);
 /**
	   * remove orders, the orders' status must be non-delivered. .
	   *
	   * @param input Order's ID is int.
           * @return
	   * @throws OrderMgrException
	   */
	public void removeNonDelvOrder(int ordID);
/**
 *
 * @param productID
 * @param wareID
 * @return
 */

	public StockInfo getStockInfo(int productID, int wareID);
/**
	   * employees have contact with customers, employee should save this contact information.
	   *
	   * @param input type is DocContact, including the information that should be saved:start time, finish time,doc time,cusID, Personel ID and Content.
	   * @return
	   * @throws CustomerMgrException
	   */
	public void saveCorrespondence(DocContact docContact);
}

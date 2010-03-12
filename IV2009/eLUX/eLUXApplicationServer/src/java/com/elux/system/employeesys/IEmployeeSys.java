package com.elux.system.employeesys;

import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;
import com.elux.ado.order.Order;
import com.elux.manager.customermgr.CustomerMgrException;
import com.elux.manager.ordermgr.OrderMgrException;
import com.elux.manager.warehousemgr.StockInfo;
import com.elux.manager.warehousemgr.WarehouseMgrException;
import java.util.Vector;

public interface IEmployeeSys {

    /**
     * through input customer's ID, get the customer's information
     *
     * @param input  customer's ID is int type
     * @return Customer. If cusID doesn't exit, return null.
     * @throws CustomerMgrException if  execute SQL statements wrong or input cusID <=0
     */
    public Customer getCustomerInfo(int cusID) throws CustomerMgrException;

    /**
     * through input order's ID, get the customer's information
     *
     * @param input  ordID is int type
     * @return Customer. If ordID doesn't exit, return null.
     * @throws OrderMgrException if execute SQL statements wrong or input ordID <=0 in the process of finding order information via ordID
     * @throws CustomerMgrException if  execute SQL statements wrong or input cusID <=0 in the process of finding customer information via cusID     */
    public Customer getCustomerInfoByOrderID(int ordID) throws OrderMgrException, CustomerMgrException;

    /**
     * update customer's information.
     *
     * @param input object:Customer.
     * @return
     * @throws CustomerMgrException if input customer object is null
     * or  execute SQL statements wrong.
     */
    public void setCustomerInfo(Customer customer) throws CustomerMgrException;

    /**
     * Search Orders whose states are non-delivered .
     *
     * @param
     * @return Vector<Order>. If there is no nondelivered order, return null
     * @throws OrderMgrException if   execute SQL statements wrong
     */
    public Vector<Order> searchNonDelvOrder() throws OrderMgrException;

    /**
     * remove orders, the orders' status must be non-delivered. .
     *
     * @param int ordID.
     * @return
     * @throws OrderMgrException if  execute SQL statements wrong or input ordID <=0
     */
    public void removeNonDelvOrder(int ordID) throws OrderMgrException;

    /**
     * Get stock information of a certain product at certain warehouse .
     *
     * @param input int productID.
     * @param input int wareID.
     * @return StockInfo. If product ID or ware ID doesn't exit, return null
     * @throws WarehouseMgrException if execute SQL statements wrong or input productID <=0 or wareID <=0
     */
    public StockInfo getStockInfo(int productID, int wareID) throws WarehouseMgrException;

    /**
     * employees have contact with customers, employee should save this contact information.
     *
     * @param input object:DocContact
     * @return
     * @throws CustomerMgrException if input DocContact is null
     * or if   execute SQL statements wrong.
     */
    public void saveCorrespondence(DocContact docContact) throws CustomerMgrException;
}

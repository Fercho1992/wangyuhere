package com.elux.system.employeesys;

import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;
import com.elux.ado.order.Order;
import com.elux.manager.customermgr.ICustomerMgr;
import com.elux.manager.ordermgr.IOrderMgr;
import com.elux.manager.warehousemgr.IWarehouseMgr;
import com.elux.manager.warehousemgr.StockInfo;
import java.util.Vector;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Remote(IEmployeeSys.class)
@Stateless(name = "IEmployeeSys")
public class EmployeeSysBean implements IEmployeeSys {

    private IOrderMgr orderMgr;
    private ICustomerMgr customerMgr;
    private IWarehouseMgr warehouseMgr;

    public EmployeeSysBean() throws NamingException {
        Context ctx = new InitialContext();

        ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
        ctx.addToEnvironment("java.naming.factory.url.pkgs",
                "org.jboss.naming:org.jnp.interfaces");

        orderMgr = (IOrderMgr) ctx.lookup("IOrderMgr/remote");
        customerMgr = (ICustomerMgr) ctx.lookup("ICustomerMgr/remote");
        warehouseMgr = (IWarehouseMgr) ctx.lookup("IWarehouseMgr/remote");
    }

    @Override
    public Customer getCustomerInfo(int cusID) {
        return customerMgr.getCustomerInfo(cusID);
    }

    @Override
    public StockInfo getStockInfo(int productID, int wareID) {
        return warehouseMgr.getStockInfo(productID, wareID);
    }

    @Override
    public Vector<Order> searchNonDelvOrder() {
        return orderMgr.searchNonDelvOrder();
    }

    @Override
    public void removeNonDelvOrder(int ordID) {
        // TODO Auto-generated method stub
        orderMgr.removeNonDelvOrder(ordID);
    }

    @Override
    public void saveCorrespondence(DocContact docContact) {
        customerMgr.saveCorrespondence(docContact);
    }

    @Override
    public void setCustomerInfo(Customer customer) {
        customerMgr.setCustomerInfo(customer);
    }
 /**
     * through input order's ID, get the customer's information
     *
     * @param input  ordID is int type
     * @return Customer. If ordID doesn't exit, return null.
     * @throws OrderMgrException if  a database access error occurs
     * or the given parameter is not a Statement constant indicating whether auto-generated keys should be returned
     * or the SQL statement does not return a ResultSet object
     * @throws CustomerMgrException if  a database access error occurs
     * or the given parameter is not a Statement constant indicating whether auto-generated keys should be returned
     * or the SQL statement does not return a ResultSet object
     */
    public Customer getCustomerInfoByOrderID(int ordID) {
        Order order = orderMgr.getOrder(ordID);
        return getCustomerInfo(order.getCusID());
    }
}

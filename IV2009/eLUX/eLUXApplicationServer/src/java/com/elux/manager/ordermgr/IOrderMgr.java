package com.elux.manager.ordermgr;

import java.util.Vector;

import com.elux.ado.order.Order;
import com.elux.ado.order.OrderItem;

public interface IOrderMgr {
	/**
	   * Search Orders whose states are non-delivered .
	   *
	   * @param input order status is a string type.
   	   * @return a series of orders which are non-delivered.
	   * @throws OrderMgrException
         */
	public Vector<Order> searchNonDelvOrder(String orderStatus) throws OrderMgrException;
        /**
	   * remove orders, the orders' status must be non-delivered. .
	   *
	   * @param input Order's ID is int.
           * @return
	   * @throws OrderMgrException
	   */
	public void removeNonDelvOrder(int ordID) throws OrderMgrException;
        /**
	   * get certain customer's discount about certain product's category .
	   *
	   * @param input Customer's ID is int.
           * @param input Product Category's ID.
	   * @return the discount is double type.
	   * @throws OrderMgrException
	   */
	public double getDiscount(int cusID, int proCatID) throws OrderMgrException;
        /**
	   * get certain order's information according to order ID .
	   *
	   * @param input Order's ID is int.
           * @return the Order is a datatype class, including order's information:CusID,OrdID,OrderStatus and Order time.
	   * @throws OrderMgrException
	   */
    public Order getOrder(int ordID) throws OrderMgrException;
    /**
	   * a list of orderitems consists an order.Put this order's information into database.
	   *
	   * @param input Customer's ID is int.
           * @param OrderItem is a datatype including orderitem's information.
	   * @return
	   * @throws OrderMgrException
	   */
	public void sendOrder(int cusID, Vector<OrderItem> orderItemList) throws OrderMgrException;
//	public Vector<Integer> getTop10ID() throws OrderMgrException;

}

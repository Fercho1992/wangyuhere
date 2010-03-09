package com.elux.manager.ordermgr;

import java.util.Vector;

import com.elux.ado.order.Order;
import com.elux.ado.order.OrderItem;

public interface IOrderMgr {
	
	public Vector<Order> searchNonDelvOrder(String orderStatus) throws OrderMgrException;
	public void removeNonDelvOrder(int ordID) throws OrderMgrException;
	public double getDiscount(int cusID, int proCatID) throws OrderMgrException;
    public Order getOrder(int ordID) throws OrderMgrException;
//	public Vector<OrderItem> orderProduct(int cusID, int proID, int amount) throws OrderMgrException;
	public void sendOrder(Order order, Vector<OrderItem> orderItemList) throws OrderMgrException;
//	public Vector<Integer> getTop10ID() throws OrderMgrException;

}

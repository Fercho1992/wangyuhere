package com.elux.manager.ordermgr;

import java.util.Vector;

import com.elux.ado.order.Order;

public interface IOrderMgr {
	
	public Vector<Order> searchNonDelvOrder(String orderStatus) throws OrderMgrException;
	public void removeNonDelvOrder(int ordID) throws OrderMgrException;
	public double getDiscount(int cusID, int proCatID) throws OrderMgrException;
	public void orderProduct(int cusID, int proID, int amount) throws OrderMgrException;
	public void sendOrder(int cusID) throws OrderMgrException;
//	public Vector<Integer> getTop10ID() throws OrderMgrException;

}

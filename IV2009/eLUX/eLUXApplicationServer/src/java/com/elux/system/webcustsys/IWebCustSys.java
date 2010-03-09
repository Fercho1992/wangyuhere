package com.elux.system.webcustsys;

import java.util.Vector;

import com.elux.ado.order.Order;
import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.manager.productmgr.ShortProductInfo;

public interface IWebCustSys {

	public Vector<ProductCategory> getProductCategories();

	public Vector<ShortProductInfo> getProductByCategory(int categoryID);

	public ProductInfo getProductInfo(int productID);
	
	public double getDiscount(int cusID, int proCatID);
	
	public void sendOrder(Order order, Vector<OrderItem> orderItemList);
}

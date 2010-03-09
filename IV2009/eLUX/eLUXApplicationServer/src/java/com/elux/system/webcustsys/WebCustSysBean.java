package com.elux.system.webcustsys;

import java.util.Vector;

import com.elux.ado.order.Order;
import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.customermgr.CustomerMgrBean;
import com.elux.manager.customermgr.ICustomerMgr;
import com.elux.manager.ordermgr.IOrderMgr;
import com.elux.manager.ordermgr.OrderMgrBean;
import com.elux.manager.productmgr.IProductMgr;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.manager.productmgr.ProductMgrBean;
import com.elux.manager.productmgr.ShortProductInfo;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote(IWebCustSys.class)
@Stateless(name = "IWebCustSys")
public class WebCustSysBean implements IWebCustSys {

	private IOrderMgr orderMgr = new OrderMgrBean();
	private IProductMgr productMgr = new ProductMgrBean();
	private ICustomerMgr customerMgr = new CustomerMgrBean();

	@Override
	public double getDiscount(int cusID, int proCatID) {
		return customerMgr.getDiscount(cusID, proCatID);
	}

	@Override
	public Vector<ShortProductInfo> getProductByCategory(int categoryID) {
		return productMgr.getProductByCategory(categoryID);
	}

	@Override
	public Vector<ProductCategory> getProductCategories() {
		return productMgr.getProductCategories();
	}

	@Override
	public ProductInfo getProductInfo(int productID) {
		return productMgr.getProductInfo(productID);
	}

	@Override
	public void sendOrder(Order order, Vector<OrderItem> orderItemList) {
		orderMgr.sendOrder(order, orderItemList);
	}

}

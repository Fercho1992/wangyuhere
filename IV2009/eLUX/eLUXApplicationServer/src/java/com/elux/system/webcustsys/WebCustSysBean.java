package com.elux.system.webcustsys;

import java.util.Vector;

import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.customermgr.ICustomerMgr;
import com.elux.manager.ordermgr.IOrderMgr;
import com.elux.manager.productmgr.IProductMgr;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.manager.productmgr.ShortProductInfo;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Remote(IWebCustSys.class)
@Stateless(name = "IWebCustSys")
public class WebCustSysBean implements IWebCustSys {

	private IOrderMgr orderMgr;
	private ICustomerMgr customerMgr;
	private IProductMgr productMgr;

    public WebCustSysBean() throws NamingException {
        Context ctx = new InitialContext();

        ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
        ctx.addToEnvironment("java.naming.factory.url.pkgs",
                "org.jboss.naming:org.jnp.interfaces");

        orderMgr = (IOrderMgr) ctx.lookup("IOrderMgr/remote");
        customerMgr = (ICustomerMgr) ctx.lookup("ICustomerMgr/remote");
        productMgr = (IProductMgr) ctx.lookup("IProductMgr/remote");
    }

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
	public void sendOrder(int cusID, Vector<OrderItem> orderItemList) {
		orderMgr.sendOrder(cusID, orderItemList);
	}

}

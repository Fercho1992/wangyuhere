package com.elux.system.webcustsys;

import java.util.Vector;

import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.manager.productmgr.ShortProductInfo;

public interface IWebCustSys {
/**
	   * Get all the product categories .
	   *
	   * @param
   	   * @return a list of Product Categories.
	   * @throws ProductMgrException
         */
	public Vector<ProductCategory> getProductCategories();
  /**
	   * Get products' information under certain Category .
	   *
	   * @param input Category's ID.
   	   * @return a series of Short Product Information, including: ID, Name, Price .
	   * @throws ProductMgrException
         */
	public Vector<ShortProductInfo> getProductByCategory(int categoryID);
 /**
	   * Get product's information according to ProductID.
	   *
	   * @param input ProductID is a int type.
   	   * @return ProductInfo is a datatype class, including ID, name, price, category, grades and comments.
	   * @throws ProductMgrException
         */
	public ProductInfo getProductInfo(int productID);
	/**
	   * get certain customer's discount about certain product's category .
	   *
	   * @param input Customer's ID is int.
           * @param input Product Category's ID.
	   * @return the discount is double type.
	   * @throws CustomerMgrException
	   */
	public double getDiscount(int cusID, int proCatID);
	/**
	   * a list of orderitems consists an order.Put this order's information into database.
	   *
	   * @param input Customer's ID is int.
           * @param OrderItem is a datatype including orderitem's information.
	   * @return
	   * @throws OrderMgrException
	   */
	public void sendOrder(int cusID, Vector<OrderItem> orderItemList);
}

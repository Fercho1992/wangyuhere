package com.elux.system.webcustsys;

import java.util.Vector;

import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.manager.productmgr.ProductMgrException;
import com.elux.manager.productmgr.ShortProductInfo;

public interface IWebCustSys {
    /**
     * Select all the product categories from database.
     *
     * @param
     * @return a list of the Objects: ProductCategory.
     * @throws ProductMgrException if a database access error occurs 
     * or the given parameter is not a Statement constant indicating whether auto-generated keys should be returned
     * or the SQL statement does not return a ResultSet object
     */
    public Vector<ProductCategory> getProductCategories() throws ProductMgrException;

    /**
     * Get products' information under certain Category .
     *
     * @param categoryID - the certain Category ID.
     * @return a list of the Objects: ShortProductInfo, including: ID, Name, Price .
     * @throws ProductMgrException if a database access error occurs
     * or the given parameter is not a Statement constant indicating whether auto-generated keys should be returned
     * or the SQL statement does not return a ResultSet object
     */
    public Vector<ShortProductInfo> getProductByCategory(int categoryID) throws ProductMgrException;

    public ProductInfo getProductInfo(int productID);

    public double getDiscount(int cusID, int proCatID);

    public void sendOrder(int cusID, Vector<OrderItem> orderItemList);
}

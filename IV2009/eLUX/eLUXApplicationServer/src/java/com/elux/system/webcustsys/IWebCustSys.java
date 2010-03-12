package com.elux.system.webcustsys;

import java.util.Vector;

import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.ordermgr.OrderMgrException;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.manager.productmgr.ProductMgrException;
import com.elux.manager.productmgr.ShortProductInfo;

public interface IWebCustSys {

    /**
     * Select all the product categories from database.
     *
     * @param
     * @return a list of the Objects: ProductCategory or an empty collection if there is no data about product category in database.
     * @throws ProductMgrException if execute SQL statements wrong.
     */
    public Vector<ProductCategory> getProductCategories() throws ProductMgrException;

    /**
     * Get products' information under certain Category .
     *
     * @param categoryID - the specific Category's ID.
     * @return a list of the Objects: ShortProductInfo or an empty cellection if categoryID cannot be found.
     * @throws ProductMgrException if execute SQL statements wrong or if input <= 0.
     */
    public Vector<ShortProductInfo> getProductByCategory(int categoryID) throws ProductMgrException;

    /**
     * Get the specific product's information according to ProductID.
     *
     * @param productID - the certain Product ID.
     * @return a datatype ProductInfo or null if productID cannot be found.
     * @throws ProductMgrException if execute SQL statements wrong or if input <= 0.
     */
    public ProductInfo getProductInfo(int productID) throws ProductMgrException;

    /**
     * Get the discount belongs to the specific customer about the specific product's category .
     *
     * @param cusID - the specific customer's ID
     *        proCatID - the specific product category's ID
     * @return the double type number which is the discount of the specific category belongs to this specific customer.
     * @throws ProductMgrException if execute SQL statements wrong or if input <= 0.
     */
    public double getDiscount(int cusID, int proCatID) throws OrderMgrException;

    /**
     * store this order's information into database.
     *
     * @param cusID - the specific customer's ID.
     *        orderItemList - a list of Object: OrderItem which belongs to the specific customer.
     * @return
     * @throws OrderMgrException if execute SQL statements wrong or orderItemList contains null element or if cusID <= 0.
     */
    public void sendOrder(int cusID, Vector<OrderItem> orderItemList) throws OrderMgrException;
}

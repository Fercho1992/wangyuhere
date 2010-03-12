package com.elux.system.webcustsys;

import java.util.Vector;

import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.customermgr.CustomerMgrException;
import com.elux.manager.ordermgr.OrderMgrException;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.manager.productmgr.ProductMgrException;
import com.elux.manager.productmgr.ShortProductInfo;

public interface IWebCustSys {

    /**
     * Select all the product categories from database.
     *
     * @param
     * @return Vector<ProductCategory> or an empty Vector if there is no data about product category in database.
     * @throws ProductMgrException if execute SQL statements wrong.
     */
    public Vector<ProductCategory> getProductCategories() throws ProductMgrException;




    /**
     * Get products' information under certain Category .
     *
     * @param categoryID - the specific Category's ID.
     * @return Vector<ShortProductInfo> or an empty Vector if categoryID cannot be found.
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
     * @param proCatID - the specific product category's ID
     * @return the double type number which is the discount of the specific category belongs to this specific customer.
     * @throws CustomerMgrException if execute SQL statements wrong or if input <= 0.
     */
    public double getDiscount(int cusID, int proCatID) throws CustomerMgrException;





    /**
     * store this order's information into database.
     *
     * @param cusID - the specific customer's ID.
     * @param orderItemList - Vector<OrderItem>.
     * @return
     * @throws OrderMgrException if execute SQL statements wrong or orderItemList contains OrderItem with null attribute or if cusID <= 0.
     */
    public void sendOrder(int cusID, Vector<OrderItem> orderItemList) throws OrderMgrException;
}

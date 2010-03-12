package com.elux.manager.warehousemgr;

public interface IWarehouseMgr {

     /**
	   * Get stock information of a certain product at certain warehouse .
	   *
	   * @param input Product's ID.
           * @param input warehouse's ID.
   	   * @return StockInfo is a datatype class, including Product ID, Product Name, Warehouse ID, Stocklevel and account .
	   * @throws WarehouseMgrException
         */
	public StockInfo getStockInfo(int productID, int wareID)
			throws WarehouseMgrException;

}

package com.elux.manager.warehousemgr;

public interface IWarehouseMgr {

	public StockInfo getStockInfo(int productID, int wareID)
			throws WarehouseMgrException;

}

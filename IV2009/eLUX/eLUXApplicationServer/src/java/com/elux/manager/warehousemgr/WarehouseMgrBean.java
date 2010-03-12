package com.elux.manager.warehousemgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;


@Remote(IWarehouseMgr.class)
@Stateless(name = "IWarehouseMgr")
public class WarehouseMgrBean implements IWarehouseMgr {

	@Resource(mappedName = "java:/jdbc/eLUX")
	private DataSource dataSource;

	@Override
        /**
	   * Get stock information of a certain product at certain warehouse .
	   *
	   * @param input int productID.
           * @param input int wareID.
   	   * @return StockInfo. If product ID or ware ID doesn't exit, return null
	   * @throws WarehouseMgrException
         */
	public StockInfo getStockInfo(int productID, int wareID)throws WarehouseMgrException{

		try {
			
			Connection con = dataSource.getConnection();
			
			String query = "SELECT eLUX_Product.ProID, eLUX_Product.ProName, eLUX_Warehouse.WarID, [eLUX_Warehouse-Product].LimitLevel, [eLUX_Warehouse-Product].Amount FROM eLUX_Warehouse INNER JOIN (eLUX_Product INNER JOIN [eLUX_Warehouse-Product] ON eLUX_Product.ProID = [eLUX_Warehouse-Product].ProID) ON eLUX_Warehouse.WarID = [eLUX_Warehouse-Product].WarID WHERE eLUX_Product.ProID=? AND eLUX_Warehouse.WarID=?;";
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setInt(1, productID);
			stmt.setInt(2,wareID);
			ResultSet rs = stmt.executeQuery();
			
			StockInfo stockinfo = null;
			if(rs.next()) {
				
				stockinfo = new StockInfo(rs.getInt("ProID"), rs.getString("ProName"),
						rs.getInt("WarID"), rs.getInt("LimitLevel"),
						rs.getInt("Amount"));
			}

            rs.close();
            stmt.close();
            con.close();
			
			return stockinfo;

		} catch (SQLException ex) {
			throw new WarehouseMgrException("Get Stock info failed!",
					"Get ware ID = " + wareID + "product ID=" + productID + "failed because of "
							+ ex.getMessage());
		}
	}

}

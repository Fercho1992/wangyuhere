package com.elux.manager.customermgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.elux.ado.customer.Customer;

@Remote(CustomerMgr.class)
@Stateless(name = "CustomerMgr")
public class CustomerMgrBean implements CustomerMgr {

	@Resource(mappedName = "java:/jdbc/eLUX")
	private DataSource dataSource;

	@Override
	public Customer getCustomerInfo(int cusID) throws CustomerMgrException {
		try {
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_Customer WHERE CusID = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, cusID);
			ResultSet rs = stmt.executeQuery();
			
			Customer customer = null;
			if(rs.next()) {
				customer = new Customer(rs.getInt("CusID"), rs.getString("CusName"),
						rs.getString("CusAddress"), rs.getString("CusAddrforInvoice"),
						rs.getString("CusEAddress"));
			}
			
			return customer;

		} catch (SQLException ex) {
			throw new CustomerMgrException("Get customer info failed!",
					"Get customer ID = " + cusID + " failed because of "
							+ ex.getMessage());
		}
	}

}

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
import com.elux.ado.customer.DocContact;

@Remote(ICustomerMgr.class)
@Stateless(name = "ICustomerMgr")
public class CustomerMgrBean implements ICustomerMgr {

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
			if (rs.next()) {
				customer = new Customer(rs.getInt("CusID"), rs
						.getString("CusName"), rs.getString("CusAddress"), rs
						.getString("CusAddrforInvoice"), rs
						.getString("CusEAddress"));
			}

			return customer;

		} catch (SQLException ex) {
			throw new CustomerMgrException("Get customer info failed!",
					"Get customer ID = " + cusID + " failed because of "
							+ ex.getMessage());
		}
	}

	@Override
	public void saveCorrespondence(DocContact docContact)
			throws CustomerMgrException {
		if (docContact == null) {
			throw new CustomerMgrException(
					"Insert failed: Doc contact is missing",
					"saveCorrespondence() - the parameter docContact is null!");
		}

		try {
			Connection con = dataSource.getConnection();

			String query = "INSERT INTO eLUX_DocContact (Starttime, Finishtime, Doctime, CusID, PerID, Content) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(query);

			stmt.setString(1, docContact.getStartTime());
			stmt.setString(2, docContact.getFinishTime());
			stmt.setString(3, docContact.getDocTime());
			stmt.setInt(4, docContact.getCusID());
			stmt.setInt(5, docContact.getPerID());
			stmt.setString(6, docContact.getContent());

			stmt.executeUpdate();

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			throw new CustomerMgrException("saveCorrespondence failed, database failure",
					"saveCorrespondence() failed: Database said: " + ex.getMessage());
		}

	}
	
	public void setCustomerInfo(Customer customer) throws CustomerMgrException {
		if (customer == null) {
			throw new CustomerMgrException(
					"Insert failed: customer is missing",
					"setCustomerInfo() - the parameter customer is null!");
		}

		try {
			Connection con = dataSource.getConnection();

			String query = "UPDATE eLUX_Customer SET CusName=?, CusAddress=?, CusAddrforInvoice=?, CusEAddress=? WHERE CusID=?";
			PreparedStatement stmt = con.prepareStatement(query);

			stmt.setString(1, customer.getCusName());
			stmt.setString(2, customer.getCusAddress());
			stmt.setString(3, customer.getCusAddrForInvoice());
			stmt.setString(4, customer.getCusEAddress());
            stmt.setInt(5, customer.getCusID());

			stmt.executeUpdate();

			stmt.close();
			con.close();
		} catch (SQLException ex) {
			throw new CustomerMgrException("setCustomerInfo failed, database failure",
					"setCustomerInfo() failed: Database said: " + ex.getMessage());
		}
	}

	@Override
	public double getDiscount(int cusID, int proCatID)
			throws CustomerMgrException {
		try {
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_Rebate WHERE CusID = ? AND ProCatID = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, cusID);
			stmt.setInt(2, proCatID);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getDouble("Rebate");
			}

		} catch (SQLException ex) {
			throw new CustomerMgrException("getDiscount failed!",
					"Get Discount customer ID = " + cusID + " failed because of "
							+ ex.getMessage());
		}
		return 1;
	}

}

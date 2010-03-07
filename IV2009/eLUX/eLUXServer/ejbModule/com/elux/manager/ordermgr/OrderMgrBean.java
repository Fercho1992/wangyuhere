package com.elux.manager.ordermgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.elux.ado.order.Order;

@Remote(IOrderMgr.class)
@Stateless(name = "IOrderMgr")
public class OrderMgrBean implements IOrderMgr {

	@Resource(mappedName = "java:/jdbc/eLUX")
	private DataSource dataSource;

	private int unSentcusID = 0;
	Vector<Integer> unSentproID = new Vector<Integer>();
	Vector<Integer> unSentproAmount = new Vector<Integer>();

	public Order searchNonDelvOrder(String orderStatus)
			throws OrderMgrException {
		try {
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_Order WHERE OrderStatus = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, orderStatus);
			ResultSet rs = stmt.executeQuery();

			Order order = null;
			if (rs.next()) {
				order = new Order(rs.getInt("OrdID"), rs
						.getString("OrderStatus"), rs.getString("OrderTime"));
			}

			return order;

		} catch (SQLException ex) {
			throw new OrderMgrException("Get order info failed!",
					"Get order Status = " + orderStatus + " failed because of "
							+ ex.getMessage());
		}
	}

	@Override
	public double getDiscount(int cusID, int proCatID) throws OrderMgrException {
		try {
			Connection con = dataSource.getConnection();
			String query_rebate = "SELECT Rebate FROM eLUX_Rebate WHERE CusID = ? AND ProCatID = ?";
			PreparedStatement stmt_rebate = con.prepareStatement(query_rebate);
			stmt_rebate.setInt(1, cusID);
			stmt_rebate.setInt(2, proCatID);
			ResultSet rs_rebate = stmt_rebate.executeQuery();

			double rebate = 0;
			if (rs_rebate.next()) {
				rebate = rs_rebate.getDouble("Rebate");
			}

			return rebate;

		} catch (SQLException ex) {
			throw new OrderMgrException("Get rebate info failed!",
					"Get Customer ID = " + cusID
							+ " and Product Category ID = " + proCatID
							+ " failed because of " + ex.getMessage());
		}

	}

	@Override
	public void orderProduct(int cusID, int proID, int amount)
			throws OrderMgrException {
		unSentproID.addElement(proID);
		unSentproAmount.addElement(amount);
		unSentcusID = cusID;

	}

	@Override
	public void removeNonDelvOrder(int ordID) throws OrderMgrException {
		try {
			Connection con = dataSource.getConnection();
			String query = "DELET * FROM eLUX_Order WHERE OrdID = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, ordID);
			stmt.executeQuery();

		} catch (SQLException ex) {
			throw new OrderMgrException("Delete non-order failed!",
					"Order ID = " + ordID + " failed because of "
							+ ex.getMessage());
		}

	}

	@Override
	public void sendOrder(int cusID) throws OrderMgrException {
		try {
			cusID = this.unSentcusID;

			Connection con = dataSource.getConnection();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String orderTime = format.format(new Date());
			String query_order = "INSERT INTO eLUX_Order (OrderState, OrderTime, CusID) VALUES ('nondelivered',?,?)";
			PreparedStatement stmt_order = con.prepareStatement(query_order);
			stmt_order.setString(1, orderTime);
			stmt_order.setInt(2, cusID);
			stmt_order.executeQuery();

			String query_orderID = "SELECT MAX(OrdID) AS orderID FROM eLUX_Order";
			PreparedStatement stmt_orderID = con
					.prepareStatement(query_orderID);
			ResultSet rs_orderID = stmt_orderID.executeQuery();

			int orderID = 0;
			if (rs_orderID.next()) {
				orderID = rs_orderID.getInt("orderID");
			}
			for (int i = 0; i < this.unSentproID.size(); i++) {
				int proID = this.unSentproID.get(i);
				int amount = this.unSentproAmount.get(i);

				String query_proCatPrice = "SELECT ProCatID, ProPrice FROM eLUX_Product WHERE ProID = ?";
				PreparedStatement stmt_proCatPrice = con
						.prepareStatement(query_proCatPrice);
				stmt_proCatPrice.setInt(1, proID);
				ResultSet rs_proCatPrice = stmt_proCatPrice.executeQuery();

				int proCategoryID = 0;
				double price = 0;
				if (rs_proCatPrice.next()) {
					proCategoryID = rs_proCatPrice.getInt("ProCatID");
					price = rs_proCatPrice.getDouble("ProPrice");
				}
				double rebate = this.getDiscount(cusID, proCategoryID);

				double newprice = rebate * price;

				String query_orderItem = "INSERT INTO eLUX_OrderItem (OrdItemAmount, OrdPrice, ProID, OrderID) VALUES (?,?,?,?)";
				PreparedStatement stmt_orderItem = con
						.prepareStatement(query_orderItem);
				stmt_orderItem.setInt(1, amount);
				stmt_orderItem.setDouble(2, newprice);
				stmt_orderItem.setInt(3, proID);
				stmt_orderItem.setInt(4, orderID);
				stmt_orderItem.executeQuery();

			}
			
			this.unSentproID.removeAllElements();
			this.unSentproAmount.removeAllElements();

		} catch (SQLException ex) {
			throw new OrderMgrException("Order Product failed!",
					"Customer ID = " + cusID + " failed because of "
							+ ex.getMessage());
		}

	}

}

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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

import com.elux.ado.order.Order;
import com.elux.ado.order.OrderItem;

@Remote(IOrderMgr.class)
@Stateless(name = "IOrderMgr")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderMgrBean implements IOrderMgr {

	@Resource(mappedName = "java:/jdbc/eLUX")
	private DataSource dataSource;

        /**
         *
         * @param orderStatus
         * @return
         * @throws OrderMgrException
         */
	public Vector<Order> searchNonDelvOrder(String orderStatus)
			throws OrderMgrException {
		try {
			Vector<Order> nonDelvOrderList = new Vector<Order>();

			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_Order WHERE OrderStatus = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, orderStatus);
			ResultSet rs = stmt.executeQuery();

			Order order = null;
			while (rs.next()) {
				order = new Order(rs.getInt("OrdID"), rs
						.getString("OrderStatus"), rs.getString("OrderTime"),
						rs.getInt("CusID"));

				nonDelvOrderList.addElement(order);
			}

			return nonDelvOrderList;

		} catch (SQLException ex) {
			throw new OrderMgrException("Get order info failed!",
					"Get order Status = " + orderStatus + " failed because of "
							+ ex.getMessage());
		}
	}

        /**
         *
         * @param cusID
         * @param proCatID
         * @return
         * @throws OrderMgrException
         */
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
	public void removeNonDelvOrder(int ordID) throws OrderMgrException {
		try {
			Connection con = dataSource.getConnection();
			String query = "DELETE * FROM eLUX_Order WHERE OrdID = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, ordID);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new OrderMgrException("Delete non-order failed!",
					"Order ID = " + ordID + " failed because of "
							+ ex.getMessage());
		}

	}

	@Override
	public void sendOrder(int cusID, Vector<OrderItem> orderItemList)
			throws OrderMgrException {
		try {
			Connection con = dataSource.getConnection();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String orderTime = format.format(new Date());
			String query_order = "INSERT INTO eLUX_Order (OrderStatus, OrderTime, CusID) VALUES ('nondelivered',?,?)";
			PreparedStatement stmt_order = con.prepareStatement(query_order);
			stmt_order.setString(1, orderTime);
			stmt_order.setInt(2, cusID);
			int orderID = stmt_order.executeUpdate();

			for (OrderItem item : orderItemList) {
				item.setOrderID(orderID);

				String query_proCatPrice = "SELECT ProCatID, ProPrice FROM eLUX_Product WHERE ProID = ?";
				PreparedStatement stmt_proCatPrice = con
						.prepareStatement(query_proCatPrice);
				stmt_proCatPrice.setInt(1, item.getProID());
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
				stmt_orderItem.setInt(1, item.getOrdItermAmount());
				stmt_orderItem.setDouble(2, newprice);
				stmt_orderItem.setInt(3, item.getProID());
				stmt_orderItem.setInt(4, orderID);
				stmt_orderItem.executeUpdate();

			}
		} catch (SQLException ex) {
			throw new OrderMgrException("Order Product failed!",
					" failed because of " + ex.getMessage());
		}

	}

    public Order getOrder(int ordID) throws OrderMgrException {
        try {
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_Order WHERE OrdID = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, ordID);
			ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Order order = new Order();
                order.setCusID(rs.getInt("CusID"));
                order.setOrdID(rs.getInt("OrdID"));
                order.setOrdStatus(rs.getString("OrderStatus"));
                order.setOrdTime(rs.getString("OrderTime"));
                return order;
            }

		} catch (SQLException ex) {
			throw new OrderMgrException("Delete non-order failed!",
					"Order ID = " + ordID + " failed because of "
							+ ex.getMessage());
		}
        return null;
    }

}

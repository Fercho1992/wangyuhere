package com.elux.manager.productmgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.elux.ado.product.Comment;
import com.elux.ado.product.ProductCategory;

@Remote(IProductMgr.class)
@Stateless(name = "IProductMgr")
public class ProductMgrBean implements IProductMgr{

	@Resource(mappedName = "java:/jdbc/eLUX")
	private DataSource dataSource;
	
	@Override
	public Vector<ShortProductInfo> getProductByCategory(int categoryID)
			throws ProductMgrException {
		try {
			Vector<ShortProductInfo> results = new Vector<ShortProductInfo>();
			
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_Product WHERE ProCatID=?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, categoryID);
			ResultSet rs = stmt.executeQuery();
			ShortProductInfo info=null;
			while(rs.next()) {
				info=new ShortProductInfo();
				info.setId(rs.getInt("ProID"));
				info.setName(rs.getString("ProName"));
				info.setPrice(rs.getDouble("ProPrice"));
				results.add(info);
			}
			
			return results;

		} catch (SQLException ex) {
			throw new ProductMgrException("Get product by category failed!",
					"Get product by category failed because of "
							+ ex.getMessage());
		}
	}

	@Override
	public Vector<ProductCategory> getProductCategories()
			throws ProductMgrException {
		try {
			Vector<ProductCategory> results = new Vector<ProductCategory>();
			
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_ProductCategory";
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				ProductCategory pc = new ProductCategory();
				pc.setProCatID(rs.getInt("ProCatID"));
				pc.setProCatName(rs.getString("ProCatName"));
				results.add(pc);
			}
			
			return results;

		} catch (SQLException ex) {
			throw new ProductMgrException("Get product categories failed!",
					"Get product categories failed because of "
							+ ex.getMessage());
		}
	}
	
	private String getProductCategoryName(int id) throws SQLException {
		Connection con = dataSource.getConnection();
		String query = "SELECT * FROM eLUX_ProductCategory WHERE ProCatID=?";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		String name = "no name";
		if(rs.next()) {
			name = rs.getString("ProCatName");
		}
		return name;
	}

	@Override
	public ProductInfo getProductInfo(int productID) throws ProductMgrException {
		try {	
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM eLUX_Product WHERE ProID=?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, productID);
			ResultSet rs = stmt.executeQuery();
			
			ProductInfo info = null;
			if(rs.next()) {
				info = new ProductInfo();
				info.setId(rs.getInt("ProID"));
				info.setName(rs.getString("ProName"));
				info.setPrice(rs.getDouble("ProPrice"));
				int id = rs.getInt("ProCatID");
				info.setCategory(getProductCategoryName(id));
				info.setGrade(getGradeByProductID(productID));
				info.setComments(getCommentsByProductID(productID));
			}
			
			return info;

		} catch (SQLException ex) {
			throw new ProductMgrException("Get product categories failed!",
					"Get product categories failed because of "
							+ ex.getMessage());
		}
	}
	
	@Override
	public int getGradeByProductID(int productID) throws ProductMgrException {
		try {	
			Connection con = dataSource.getConnection();
			String query = "SELECT Avg(Grade) AS GradeAverage FROM eLUX_Grade WHERE (((eLUX_Grade.[ProID])=?))";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, productID);
			ResultSet rs = stmt.executeQuery();
			
			int result = 0;
			if(rs.next()) {
				result = rs.getInt("GradeAverage");
			}
			
			return result;

		} catch (SQLException ex) {
			throw new ProductMgrException("Get grade by product ID failed!",
					"Get grade by product ID failed because of "
							+ ex.getMessage());
		}
	}
	
	public Vector<Comment> getCommentsByProductID(int productID) throws ProductMgrException {
		try {	
			Connection con = dataSource.getConnection();
			String query = "SELECT eLUX_Comment.* FROM eLUX_Comment WHERE ProID=?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, productID);
			ResultSet rs = stmt.executeQuery();
			
			Vector<Comment> result = new Vector<Comment>();
			while(rs.next()) {
				Comment c = new Comment();
				c.setComID(rs.getInt("ComID"));
				c.setComContents(rs.getString("ComContents"));
				c.setComIDRef(rs.getInt("ComIDRef"));
				c.setProID(productID);
				result.add(c);
			}
			
			return result;

		} catch (SQLException ex) {
			throw new ProductMgrException("GetCommentsByProductID failed!",
					"GetCommentsByProductID failed because of "
							+ ex.getMessage());
		}
	}

	@Override
	public Vector<ShortProductInfo> getProductShortInfo(int[] proIDs)
			throws ProductMgrException {
		return null;
	}

	@Override
	public void gradeProduct(int proID, int cusID, int grade)
			throws ProductMgrException {
	}
	
}

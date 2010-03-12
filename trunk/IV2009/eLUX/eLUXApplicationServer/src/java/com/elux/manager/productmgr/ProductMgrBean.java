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
        /**
	   * Get products' information under certain Category .
	   *
	   * @param input Category's ID.
   	   * @return a series of Short Product Information, including: ID, Name, Price .
	   * @throws ProductMgrException
         */
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
            rs.close();
            stmt.close();
            con.close();
			
			return results;

		} catch (SQLException ex) {
			throw new ProductMgrException("Get product by category failed!",
					"Get product by category failed because of "
							+ ex.getMessage());
		}
	}

	@Override
        /**
	   * Get all the product categories .
	   *
	   * @param
   	   * @return a list of Product Categories.
	   * @throws ProductMgrException
         */
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
            rs.close();
            stmt.close();
            con.close();
			
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
        rs.close();
        stmt.close();
        con.close();
		return name;
	}

	@Override
        /**
	   * Get product's information according to ProductID.
	   *
	   * @param input ProductID is a int type.
   	   * @return ProductInfo is a datatype class, including ID, name, price, category, grades and comments.
	   * @throws ProductMgrException
         */
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

            rs.close();
            stmt.close();
            con.close();
			return info;

		} catch (SQLException ex) {
			throw new ProductMgrException("Get product categories failed!",
					"Get product categories failed because of "
							+ ex.getMessage());
		}
	}
	
	@Override
        /**
	   * Get certain product's grade according to ID .
	   *
	   * @param input product's ID is int type.
   	   * @return product's grade is double tpye.
	   * @throws ProductMgrException
         */
	public double getGradeByProductID(int productID) throws ProductMgrException {
		try {	
			Connection con = dataSource.getConnection();
			String query = "SELECT Avg(Grade) AS GradeAverage FROM eLUX_Grade WHERE (((eLUX_Grade.[ProID])=?))";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, productID);
			ResultSet rs = stmt.executeQuery();
			
			double result = 0;
			if(rs.next()) {
				result = rs.getDouble("GradeAverage");
			}

            rs.close();
            stmt.close();
            con.close();
			
			return result;

		} catch (SQLException ex) {
			throw new ProductMgrException("Get grade by product ID failed!",
					"Get grade by product ID failed because of "
							+ ex.getMessage());
		}
	}

        /**
	   * Get all the comments about a Product.
	   *
	   * @param input Product ID is int type.
   	   * @return a list of comments. Comment is a class, including commentID, productID, CommentID reference.
	   * @throws ProductMgrException
         */
	
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

            rs.close();
            stmt.close();
            con.close();
            
			return result;

		} catch (SQLException ex) {
			throw new ProductMgrException("GetCommentsByProductID failed!",
					"GetCommentsByProductID failed because of "
							+ ex.getMessage());
		}
	}

	@Override
         /**
	   * Get product's short information according to its ID.
	   *
	   * @param input Product ID is int type.
   	   * @return a list of Product's information.
	   * @throws ProductMgrException
         */
	public Vector<ShortProductInfo> getProductShortInfo(int[] proIDs)
			throws ProductMgrException {
		return null;
	}

	@Override
         /**
	   * Give grade to a certain product.
	   *
	   * @param input Product ID, customer's ID and grade.
   	   * @return 
	   * @throws ProductMgrException
         */
	public void gradeProduct(int proID, int cusID, int grade)
			throws ProductMgrException {
	}
	
}

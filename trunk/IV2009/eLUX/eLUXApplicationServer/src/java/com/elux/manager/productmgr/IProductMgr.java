package com.elux.manager.productmgr;
import java.util.Vector;
import com.elux.ado.product.*;

public interface IProductMgr {

     /**
	   * Get all the product categories .
	   *
	   * @param
   	   * @return a list of Product Categories.
	   * @throws ProductMgrException
         */
	public Vector<ProductCategory> getProductCategories() throws ProductMgrException;
        /**
	   * Get products' information under certain Category .
	   *
	   * @param input Category's ID.
   	   * @return a series of Short Product Information, including: ID, Name, Price .
	   * @throws ProductMgrException
         */
	public Vector<ShortProductInfo> getProductByCategory(int categoryID) throws ProductMgrException;
        /**
	   * Get product's information according to ProductID.
	   *
	   * @param input ProductID is a int type.
   	   * @return ProductInfo is a datatype class, including ID, name, price, category, grades and comments.
	   * @throws ProductMgrException
         */
	public ProductInfo getProductInfo(int productID) throws ProductMgrException;
        /**
	   * Give grade to a certain product.
	   *
	   * @param input Product ID, customer's ID and grade.
   	   * @return
	   * @throws ProductMgrException
         */
	public void gradeProduct(int proID, int cusID, int grade) throws ProductMgrException;
        /**
	   * Get product's short information according to its ID.
	   *
	   * @param input Product ID is int type.
   	   * @return a list of Product's information.
	   * @throws ProductMgrException
         */
	public Vector<ShortProductInfo> getProductShortInfo(int[] proIDs) throws ProductMgrException;
        /**
	   * Get certain product's grade according to ID .
	   *
	   * @param input product's ID is int type.
   	   * @return product's grade is double tpye.
	   * @throws ProductMgrException
         */
	public double getGradeByProductID(int productID) throws ProductMgrException;
         /**
	   * Get all the comments about a Product.
	   *
	   * @param input Product ID is int type.
   	   * @return a list of comments. Comment is a class, including commentID, productID, CommentID reference.
	   * @throws ProductMgrException
         */
	public Vector<Comment> getCommentsByProductID(int productID) throws ProductMgrException;
}

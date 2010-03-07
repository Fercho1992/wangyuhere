package com.elux.manager.productmgr;
import java.util.Vector;
import com.elux.ado.product.*;

public interface IProductMgr {

	public Vector<ProductCategory> getProductCategories() throws ProductMgrException;
	public Vector<ShortProductInfo> getProductByCategory(int categoryID) throws ProductMgrException;
	public ProductInfo getProductInfo(int productID) throws ProductMgrException;
	public void gradeProduct(int proID, int cusID, int grade) throws ProductMgrException;
	public Vector<ShortProductInfo> getProductShortInfo(int[] proIDs) throws ProductMgrException;
	public double getGradeByProductID(int productID) throws ProductMgrException;
	public Vector<Comment> getCommentsByProductID(int productID) throws ProductMgrException;
}

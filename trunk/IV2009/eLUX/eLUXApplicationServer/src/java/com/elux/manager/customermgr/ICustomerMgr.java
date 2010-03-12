package com.elux.manager.customermgr;

import com.elux.ado.customer.Customer;
import com.elux.ado.customer.DocContact;

public interface ICustomerMgr {
/**
	   * through input customer's ID, get the customer's information
	   *
	   * @param input  customer's ID is a int type
	   * @return Customer is a datatype class, including customer's infomation:ID,Name,Address,Address for invoice and Email address.
	   * @throws CustomerMgrException
	   */
	public Customer getCustomerInfo(int cusID) throws CustomerMgrException;
        /**
	   * update customer's information.
	   *
	   * @param input data type is Customer class,including customer's infomation:ID,Name,Address,Address for invoice and Email address.
	   * @return
	   * @throws CustomerMgrException
	   */
	public void setCustomerInfo(Customer customer) throws CustomerMgrException;
        /**
	   * employees have contact with customers, employee should save this contact information.
	   *
	   * @param input type is DocContact, including the information that should be saved:start time, finish time,doc time,cusID, Personel ID and Content.
	   * @return
	   * @throws CustomerMgrException
	   */
	public void saveCorrespondence(DocContact docContact)throws CustomerMgrException;
        /**
	   * get certain customer's discount about certain product's category .
	   *
	   * @param input Customer's ID is int.
           * @param input Product Category's ID.
	   * @return the discount is double type.
	   * @throws CustomerMgrException
	   */
	public double getDiscount(int cusID, int proCatID) throws CustomerMgrException;
}

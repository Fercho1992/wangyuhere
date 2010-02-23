package IS7.bookdb.customermgr;

import IS7.bookdb.Customer;

public interface CustomerMgr {
	//Inserts a new customer in the database
    //Throws CustomerMgrException if an DB error occurs, or if the parameter is invalid
    public void insertCustomer(Customer customer) throws CustomerMgrException;
}

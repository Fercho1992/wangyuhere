package IS7.bookdb.customermgr;

import IS7.bookdb.Customer;

import java.sql.*;
import javax.sql.DataSource;

import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.annotation.Resource;

@Remote(CustomerMgr.class)
@Stateless(name = "CustomerMgr")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CustomerMgrBean implements CustomerMgr
{
    // Declare database connection
    @Resource(mappedName = "java:/jdbc/BookDB")
    private DataSource mDataSource;

    /** Inserts a new customer in the database * */
    public void insertCustomer(Customer customer) throws CustomerMgrException
    {
        if (customer == null)
        {
            throw new CustomerMgrException("Insert failed: Customer data is missing", "insertCustomer() - the parameter customer is null!");
        }

        if (customer.getName() == null)
        {
            throw new CustomerMgrException("Insert failed: Customer name is missing", "insertCustomer() - the parameter customer.name is null!");
        }

        try
        {

            Connection con = mDataSource.getConnection();

            String name = customer.getName();
            String address = "";
            if (customer.getAddress() != null)
            {
                address = customer.getAddress();
            }

            String city = "";
            if (customer.getCity() != null)
            {
                city = customer.getCity();
            }

            String country = "";
            if (customer.getCountry() != null)
            {
                country = customer.getCountry();
            }

            String query = "INSERT INTO Customer (name, address, city, country) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, city);
            stmt.setString(4, country);

            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch (SQLException ex)
        {
            throw new CustomerMgrException("Insert failed, database failure",
                    "CustomerMgrBean.insertCustomer() Insert of customer '" + customer.getName() + "' failed: Database said: " + ex.getMessage());
        }
    }
}
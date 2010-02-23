package test;

import javax.naming.Context;
import javax.naming.InitialContext;

import IS7.bookdb.Customer;
import IS7.bookdb.customermgr.CustomerMgr;
import IS7.bookdb.customermgr.CustomerMgrException;

import java.util.Scanner;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CustomerMgrTestClient
{

    public static void main(String[] args)
    {
        try
        {
            Context ctx = new InitialContext();
            ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
            ctx.addToEnvironment("java.naming.factory.url.pkgs",
                    "org.jboss.naming:org.jnp.interfaces");
            Logger.getRootLogger().setLevel(Level.OFF);

            CustomerMgr customerMgr = (CustomerMgr) ctx.lookup("CustomerMgr/remote");

            Scanner s = new Scanner(System.in);
            System.out.println("This will add a new customer. Provide the information required!");
            System.out.println("---------------------------------------------------------------");
            System.out.print("Name: ");
            String name = s.nextLine();
            System.out.print("Address: ");
            String address = s.nextLine();
            System.out.print("City: ");
            String city = s.nextLine();
            System.out.print("Country: ");
            String country = s.nextLine();
            customerMgr.insertCustomer(new Customer(0, name, address, city,
                    country));
            System.out.println("New customer inserted into database successfully!");
        } // end of try block
        catch (CustomerMgrException cex)
        {
            System.err.println("Server exception: " + cex.getUserMessage() + "\n" + cex.getMessage());
        }
        catch (Exception ex)
        {
            System.err.println("Caught an unexpected exception :" + ex);
        }

    }
}

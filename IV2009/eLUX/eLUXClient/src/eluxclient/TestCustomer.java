package eluxclient;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.elux.ado.Customer;
import com.elux.customermgr.CustomerMgr;

public class TestCustomer {

	public static void main(String[] args) {
		try {
			Context ctx = new InitialContext();
            ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
            ctx.addToEnvironment("java.naming.factory.url.pkgs",
                    "org.jboss.naming:org.jnp.interfaces");
            Logger.getRootLogger().setLevel(Level.OFF);
            
            CustomerMgr cusMgr = (CustomerMgr) ctx.lookup("CustomerMgr/remote");
            Customer c = cusMgr.getCustomerInfo(1);
            
            System.out.println(c.getCusName());
		} catch (Exception ex) {
            System.err.println("Caught an unexpected exception :" + ex);
            ex.printStackTrace();
        }
	}
}

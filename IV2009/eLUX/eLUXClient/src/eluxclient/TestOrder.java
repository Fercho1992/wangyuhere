package eluxclient;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.elux.ado.order.Order;
import com.elux.manager.ordermgr.*;

public class TestOrder {

	public static void main(String[] args) {
		try {
			Context ctx = new InitialContext();
            ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
            ctx.addToEnvironment("java.naming.factory.url.pkgs",
                    "org.jboss.naming:org.jnp.interfaces");
            Logger.getRootLogger().setLevel(Level.OFF);
            
            IOrderMgr ordMgr = (IOrderMgr) ctx.lookup("IOrderMgr/remote");
            Order o = ordMgr.searchNonDelvOrder("nondelivered");
            
            System.out.println(o.getOrdID());
		} catch (Exception ex) {
            System.err.println("Caught an unexpected exception :" + ex);
            ex.printStackTrace();
        }
	}
}

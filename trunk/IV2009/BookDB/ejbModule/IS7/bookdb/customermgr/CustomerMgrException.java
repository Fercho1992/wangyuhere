package IS7.bookdb.customermgr;

import javax.ejb.EJBException;

public class CustomerMgrException extends EJBException {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2451074562635068223L;
	private String mUserMsg;

	    public CustomerMgrException(String userMsg, String message)
	    {
	        super(message);
	        mUserMsg = userMsg;
	    }

	    public String getUserMessage()
	    {
	        return mUserMsg;
	    }
}

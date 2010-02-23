package IS7.bookdb.subjectmgr;

import javax.ejb.EJBException;

public class SubjectMgrException extends EJBException {
	private static final long serialVersionUID = -2451074562635068223L;
	private String mUserMsg;
	
	public SubjectMgrException(String userMsg, String message)
    {
        super(message);
        mUserMsg = userMsg;
    }

    public String getUserMessage()
    {
        return mUserMsg;
    }
}

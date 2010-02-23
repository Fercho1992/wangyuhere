package IS7.bookdb.subjectmgr;

import java.util.Vector;

import IS7.bookdb.Subject;

public interface SubjectMgr {

	public Vector<Subject> getAllSubjects() throws Exception;
	
	public void insertSubject(Subject subject) throws SubjectMgrException;
}

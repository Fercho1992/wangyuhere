package IS7.bookdb;

public class Subject implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 503878772356422533L;
	private String subject;
	private int subjectNr;
	
	public Subject(int nr, String s) {
		subjectNr = nr;
		subject = s;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getSubjectNr() {
		return subjectNr;
	}

	public void setSubjectNr(int subjectNr) {
		this.subjectNr = subjectNr;
	}
	
	
}

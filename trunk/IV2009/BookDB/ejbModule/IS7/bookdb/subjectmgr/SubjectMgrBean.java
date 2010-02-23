package IS7.bookdb.subjectmgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

import IS7.bookdb.Subject;

@Remote(SubjectMgr.class)
@Stateless(name = "SubjectMgr")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SubjectMgrBean implements SubjectMgr {

	@Resource(mappedName = "java:/jdbc/BookDB")
    private DataSource mDataSource;
	
	@Override
	public Vector<Subject> getAllSubjects() throws Exception {
		try
        {
            Vector<Subject> subjects = new Vector<Subject>();

            Connection con = mDataSource.getConnection();
            String query = "SELECT * FROM subject";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Subject newSubject = new Subject(rs.getInt("SubjectNr"), rs.getString("Subject"));
                subjects.add(newSubject);
            }
            stmt.close();
            con.close();
            return subjects;
        }
        catch (SQLException ex)
        {
            // Throw an error that the client can catch
            throw new Exception("getAllSubjects() - DB Error: " + ex.getMessage());
        }
	}

	@Override
	public void insertSubject(Subject subject) throws SubjectMgrException {
		if (subject == null)
        {
            throw new SubjectMgrException("Insert failed: Subject data is missing", "insertSubject() - the parameter subject is null!");
        }

        if (subject.getSubject() == null)
        {
            throw new SubjectMgrException("Insert failed: Subject subject is missing", "insertSubject() - the parameter Subject.subject is null!");
        }

        try
        {

            Connection con = mDataSource.getConnection();

            String s = subject.getSubject();

            String query = "INSERT INTO subject (Subject) VALUES (?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, s);

            stmt.executeUpdate();

            stmt.close();
            con.close();
        }
        catch (SQLException ex)
        {
            throw new SubjectMgrException("Insert failed, database failure",
                    "SubjectMgrBean.insertSubject() Insert of subject '" + subject.getSubject() + "' failed: Database said: " + ex.getMessage());
        }
		
	}

}

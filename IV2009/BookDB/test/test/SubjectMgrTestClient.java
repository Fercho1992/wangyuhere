package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import IS7.bookdb.Subject;
import IS7.bookdb.subjectmgr.SubjectMgr;

//VS4E -- DO NOT REMOVE THIS LINE!
public class SubjectMgrTestClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JButton btnAdd;
	private JTextField txtName;
	private JButton btnAll;
	private JTextArea taResult;
	private JScrollPane jScrollPane0;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

	public SubjectMgrTestClient() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(12, 12, 12), new Leading(
				14, 10, 10)));
		add(getBtnAdd(), new Constraints(new Leading(257, 67, 10, 10),
				new Leading(9, 12, 12)));
		add(getTxtName(), new Constraints(new Leading(101, 142, 10, 10),
				new Leading(12, 12, 12)));
		add(getBtnAll(), new Constraints(new Leading(348, 10, 10), new Leading(
				9, 12, 12)));
		add(getJScrollPane0(), new Constraints(new Leading(23, 397, 10, 10),
				new Leading(51, 307, 10, 10)));
		setSize(438, 368);
	}

	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("Add");
			btnAdd.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					btnAddActionActionPerformed(event);
				}
			});
		}
		return btnAdd;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTextArea0());
		}
		return jScrollPane0;
	}

	private JTextArea getJTextArea0() {
		if (taResult == null) {
			taResult = new JTextArea();
		}
		return taResult;
	}

	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
		}
		return txtName;
	}

	private JButton getBtnAll() {
		if (btnAll == null) {
			btnAll = new JButton();
			btnAll.setText("Get All");
			btnAll.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event) {
					btnAllActionActionPerformed(event);
				}
			});
		}
		return btnAll;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Subject Name:");
		}
		return jLabel0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SubjectMgrTestClient frame = new SubjectMgrTestClient();
				frame
						.setDefaultCloseOperation(SubjectMgrTestClient.EXIT_ON_CLOSE);
				frame.setTitle("SubjectMgrTestClient");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void btnAddActionActionPerformed(ActionEvent event) {
		try {
			Context ctx = new InitialContext();
			ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
			ctx.addToEnvironment("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			Logger.getRootLogger().setLevel(Level.OFF);

			SubjectMgr subjectMgr = (SubjectMgr) ctx
					.lookup("SubjectMgr/remote");

			subjectMgr.insertSubject(new Subject(0, txtName.getText()));

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
			ex.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Added successfully!");
		showAllSubject();
		
	}
	
	private void showAllSubject() {
		try {
			Context ctx = new InitialContext();
			ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
			ctx.addToEnvironment("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			Logger.getRootLogger().setLevel(Level.OFF);

			SubjectMgr subjectMgr = (SubjectMgr) ctx
					.lookup("SubjectMgr/remote");

			Vector<Subject> subjects = subjectMgr.getAllSubjects();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SubjectNr\tSubject\n");
			if(subjects.size() == 0) sb.append("No subjects\n");
			for(Subject s: subjects) {
				sb.append(s.getSubjectNr()+"\t"+s.getSubject()+"\n");
			}
			
			taResult.setText(sb.toString());

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void btnAllActionActionPerformed(ActionEvent event) {
		showAllSubject();
	}

}

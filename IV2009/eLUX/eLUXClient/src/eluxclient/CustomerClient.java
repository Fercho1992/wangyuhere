//VS4E -- DO NOT REMOVE THIS LINE!
package eluxclient;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.elux.ado.product.ProductCategory;
import com.elux.manager.productmgr.IProductMgr;
import com.elux.manager.productmgr.ShortProductInfo;


public class CustomerClient extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JButton jButton0;
	private JComboBox jComboBox0;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JTextField jTextField0;
	private JLabel jLabel1;
	DefaultComboBoxModel categoryBox = new DefaultComboBoxModel();

	Context ctx;
	IProductMgr productMgr;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

	public CustomerClient() {
		initComponents();
		try {
			ctx = new InitialContext();

			ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
			ctx.addToEnvironment("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			Logger.getRootLogger().setLevel(Level.OFF);

			productMgr = (IProductMgr) ctx.lookup("IProductMgr/remote");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
			ex.printStackTrace();
		}
		jComboBox0.setModel(categoryBox);
		initCategory();
		
		
	}

	private void initComponents() {
		setTitle("Customer Client");
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(20, 10, 10), new Leading(
				28, 44, 437)));
		add(getJTextField0(), new Constraints(new Leading(398, 12, 12),
				new Leading(26, 50, 297)));
		add(getJLabel1(), new Constraints(new Leading(336, 10, 10),
				new Leading(28, 50, 297)));
		add(getJButton0(), new Constraints(new Leading(361, 104, 10, 10),
				new Leading(67, 50, 297)));
		add(getJComboBox0(), new Constraints(new Leading(85, 221, 12, 12),
				new Leading(24, 10, 10)));
		add(getJScrollPane0(), new Constraints(new Bilateral(11, 12, 22),
				new Bilateral(99, 12, 26, 403)));
		setSize(477, 419);
	}

	private void initCategory() {
		Vector<ProductCategory> categoryList = productMgr.getProductCategories();
		if (categoryList == null) {
			showError("No products category was fetched from the server!");
			this.categoryBox.removeAllElements();
			this.getJComboBox0();

		}else {
			for(ProductCategory list: categoryList){
				this.categoryBox.addElement(list.getProCatName());
			}
		}

	}
	
	private void initProducts() {
		Vector<ShortProductInfo> productList = productMgr.getProductByCategory(jComboBox0.getSelectedIndex());
		if (productList == null) {
			showError("No products category was fetched from the server!");
			this.getJTable0();
		} else {
			String[] headers = new String[] {"Product ID", "Product", "Product Price"};
			String[][] contents  = new String[productList.size()][3];
			for (int i = 0; i < productList.size(); i++){
				contents[i][0] = String.valueOf(productList.get(i).getId());
                contents[i][1] = productList.get(i).getName();
                contents[i][2] = String.valueOf(productList.get(i).getPrice());
			}
			jTable0.setModel(new DefaultTableModel(contents, headers));
		}
		}
	
	

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Amount:");
		}
		return jLabel1;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setText("jTextField0");
		}
		return jTextField0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new DefaultTableModel(new Object[][] {
					{ "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] {
					"Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable0;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] {}));
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
		}
		return jComboBox0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Add to Cart");
		}
		return jButton0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Category:");
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

	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error",
				JOptionPane.ERROR_MESSAGE);
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
				CustomerClient frame = new CustomerClient();
				frame.setDefaultCloseOperation(CustomerClient.EXIT_ON_CLOSE);
				frame.setTitle("CustomerClient");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}

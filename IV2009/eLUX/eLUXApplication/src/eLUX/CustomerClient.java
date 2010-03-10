/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CustomerClient.java
 *
 * Created on Mar 9, 2010, 3:19:14 PM
 */
package eLUX;

import com.elux.ado.order.OrderItem;
import com.elux.ado.product.ProductCategory;
import com.elux.manager.productmgr.ProductMgrException;
import com.elux.manager.productmgr.ShortProductInfo;
import com.elux.system.webcustsys.IWebCustSys;
import java.util.Hashtable;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wangyu
 */
public class CustomerClient extends javax.swing.JFrame {

    private IWebCustSys customerSys;
    private String rebate;
    private String proName;
    private Hashtable<Integer, Integer> indexId = new Hashtable<Integer, Integer>();
    private Vector<OrderItem> itemList = new Vector<OrderItem>();

    /** Creates new form CustomerClient */
    public CustomerClient() {
        initComponents();
        try {
            Context ctx = new InitialContext();

            ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            ctx.addToEnvironment(Context.PROVIDER_URL, "127.0.0.1:1099");
            ctx.addToEnvironment("java.naming.factory.url.pkgs",
                    "org.jboss.naming:org.jnp.interfaces");

            customerSys = (IWebCustSys) ctx.lookup("IWebCustSys/remote");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            ex.printStackTrace();
        }
        
    }

    private void setEnable() {
        jLabel1.setEnabled(true);
        cbCategory.setEnabled(true);
        lblDiscount.setEnabled(true);
        btnCart.setEnabled(true);
        taProducts.setEnabled(true);

        jLabel1.setEnabled(false);
        jLabel2.setText("Logged in as Customer ID: ");
        txtCusID.setEnabled(false);
        btnlogin.setEnabled(false);
    }

    private void updateCategory() {
        try {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            Vector<ProductCategory> categoryList = customerSys.getProductCategories();
            if (categoryList == null) {
                showError("No products category was fetched from the server!");
            } else {
                int i = 0;
                for (ProductCategory list : categoryList) {
                    model.addElement(list.getProCatName());
                    indexId.put(i, list.getProCatID());
                    i++;
                }
            }
            cbCategory.setModel(model);
        } catch (ProductMgrException ex) {
            showError(ex.getMessage());
        }
    }

    private void updateRebate(int catID) {
        lblDiscount.setText("Discount: " + String.valueOf(customerSys.getDiscount(Integer.parseInt(txtCusID.getText()), catID)));
        rebate = lblDiscount.getText();
    }

    private void updateProducts(int catID) {
        Vector<ShortProductInfo> productList = customerSys.getProductByCategory(catID);
        if (productList == null) {
            showError("No products category was fetched from the server!");
        } else {
            String[] headers = new String[]{"Product ID", "Product", "Product Price"};
            String[][] contents = new String[productList.size()][3];
            for (int i = 0; i < productList.size(); i++) {
                contents[i][0] = String.valueOf(productList.get(i).getId());
                contents[i][1] = productList.get(i).getName();
                contents[i][2] = String.valueOf(productList.get(i).getPrice());
            }
            taProducts.setModel(new DefaultTableModel(contents, headers) {

                boolean[] canEdit = new boolean[]{
                    false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
        }
    }

    public Vector<OrderItem> addToCart(OrderItem item, String productName) {
        itemList.addElement(item);
        return itemList;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        taProducts = new javax.swing.JTable();
        btnCart = new javax.swing.JButton();
        lblDiscount = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCusID = new javax.swing.JTextField();
        btnlogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Customer Client");

        jLabel1.setText("Category:");
        jLabel1.setEnabled(false);

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategory.setEnabled(false);
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        taProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Product Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        taProducts.setEnabled(false);
        taProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(taProducts);

        btnCart.setText("Go to Cart");
        btnCart.setEnabled(false);
        btnCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartActionPerformed(evt);
            }
        });

        lblDiscount.setText("Discount:");
        lblDiscount.setEnabled(false);

        jLabel2.setText("Please enter your Customer ID:");

        btnlogin.setText("Login");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDiscount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtCusID, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                .addGap(10, 10, 10)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnlogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCusID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCart)
                    .addComponent(lblDiscount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        updateCategory();
        this.updateProducts(indexId.get(cbCategory.getSelectedIndex()));
        this.updateRebate(indexId.get(cbCategory.getSelectedIndex()));
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void taProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taProductsMouseClicked
        int productID = Integer.parseInt(taProducts.getModel().getValueAt(taProducts.getSelectedRow(), 0).toString());
        new ProductInformation(this, true, productID, customerSys, rebate).setVisible(true);
    }//GEN-LAST:event_taProductsMouseClicked

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        this.setEnable();
    }//GEN-LAST:event_btnloginActionPerformed

    private void btnCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartActionPerformed
        new CustomerCart(this, true, customerSys, Integer.parseInt(txtCusID.getText()), this.itemList, proName).setVisible(true);
    }//GEN-LAST:event_btnCartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new CustomerClient().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCart;
    private javax.swing.JButton btnlogin;
    private javax.swing.JComboBox cbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JTable taProducts;
    private javax.swing.JTextField txtCusID;
    // End of variables declaration//GEN-END:variables
}

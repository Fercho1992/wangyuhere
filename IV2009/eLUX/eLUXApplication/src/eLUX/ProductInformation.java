/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProductInformation.java
 *
 * Created on 2010-3-9, 16:44:45
 */

package eLUX;

import com.elux.ado.order.OrderItem;
import com.elux.ado.product.Comment;
import com.elux.manager.productmgr.ProductInfo;
import com.elux.system.webcustsys.IWebCustSys;
import java.util.Vector;

/**
 *
 * @author binbin
 */
public class ProductInformation extends javax.swing.JDialog {

    /** Creates new form ProductInformation */
    private int proID;
    private IWebCustSys cusSys;
    private String reb;


    public ProductInformation(java.awt.Frame parent, boolean modal, int productID, IWebCustSys customerSys, String rebate) {
        super(parent, modal);
        initComponents();

        proID = productID;
        cusSys = customerSys;
        reb = rebate;
        lblID.setText("The Product you selected ID: " + String.valueOf(proID));
        showInfo(productID);
    }

    private ProductInformation(int proID) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private void showInfo(int proID){
        ProductInfo productInfo = cusSys.getProductInfo(proID);
        Vector<Comment> comments = new Vector<Comment>();

        String proName = "Product Name: " + productInfo.getName()+ "/n";
        String proPrice = "Product Price: " + productInfo.getPrice() + "/n";
        String proCate = "Belongs to Category: " + productInfo.getCategory()+ "/n";
        String proGrade = "Grade: " + productInfo.getGrade()+ "/n";
        String column = "----------------------------------/n";
        txaContant.setText(proName + proPrice + proCate + proGrade + column);

        for(Comment list: comments){
            int ref = list.getComIDRef();
            String com = "ID: " + list.getComID() + "/n" + list.getComments();
            if (ref == 0) {
                txaComment.append(com + "/n" + column + "/n");
            } else {
                String comComment = "This is a sub-comment for Comment ID: " + ref + "/n";
                txaComment.append(comComment + com + "/n" + column + "/n");
            }
        }
        
    }

    private OrderItem addToCart(int amount, double price, int proID){
        OrderItem item = new OrderItem(amount, price, proID);
        return item;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txaContant = new javax.swing.JTextArea();
        btnadd = new javax.swing.JButton();
        lblID = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaComment = new javax.swing.JTextArea();
        lblDiscount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Product Information");

        txaContant.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        txaContant.setColumns(20);
        txaContant.setEditable(false);
        txaContant.setRows(5);
        txaContant.setEnabled(false);
        jScrollPane1.setViewportView(txaContant);

        btnadd.setText("Add the Product to Cart");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        lblID.setText("jLabel1");

        jLabel1.setText("Amount:");

        txaComment.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        txaComment.setColumns(20);
        txaComment.setEditable(false);
        txaComment.setRows(5);
        txaComment.setEnabled(false);
        jScrollPane2.setViewportView(txaComment);

        lblDiscount.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblDiscount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(lblID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnadd))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnadd)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDiscount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblID)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        new CustomerCart(this.addToCart(Integer.parseInt(txtAmount.getText()),Double.parseDouble(this.reb), this.proID));
    }//GEN-LAST:event_btnaddActionPerformed

    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JLabel lblID;
    private javax.swing.JTextArea txaComment;
    private javax.swing.JTextArea txaContant;
    private javax.swing.JTextField txtAmount;
    // End of variables declaration//GEN-END:variables

}

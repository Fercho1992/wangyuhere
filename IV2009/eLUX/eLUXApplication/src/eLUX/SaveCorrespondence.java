/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SaveCorrespondence.java
 *
 * Created on 2010-3-9, 16:50:42
 */
package eLUX;

import com.elux.ado.customer.DocContact;
import com.elux.manager.customermgr.ICustomerMgr;
import com.elux.manager.customermgr.CustomerMgrException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JOptionPane;
import com.elux.system.employeesys.IEmployeeSys;

/**
 *
 * @author Administrator
 */
public class SaveCorrespondence extends javax.swing.JPanel {

    private IEmployeeSys employeeSystem;

    ;

    /** Creates new form SaveCorrespondence */
    public SaveCorrespondence(IEmployeeSys system) {
        initComponents();
        this.employeeSystem = system;
        dContact = new DocContact();
    }
    //private int docID;
    //private String sTime;
    //private String fTime;
    private DocContact dContact;

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
        /* private void saveCorrespondence(int docID,String sTime,String fTime,String dtime,int cID,int pID,String dContent){
        dContact.setDocID(docID);
        dContact.setStartTime(sTime);
        dContact.setFinishTime(fTime);
        dContact.setDocTime(dtime);
        dContact.setCusID(cID);
        dContact.setPerID(pID);
        dContact.setContent(dContent);*/

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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fTimeText = new javax.swing.JTextField();
        dTimeText = new javax.swing.JTextField();
        cIDText = new javax.swing.JTextField();
        pIDText = new javax.swing.JTextField();
        sTimeText = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dContent = new javax.swing.JTextArea();
        saveButton = new javax.swing.JButton();
        cButton = new javax.swing.JButton();

        setName("DocContent"); // NOI18N

        jLabel1.setText("FinishTime");
        jLabel1.setName("FinishTime"); // NOI18N

        jLabel2.setText("DocTime");

        jLabel3.setText("CusID");

        jLabel4.setText("PerID");

        jLabel5.setText("DocContent");

        sTimeText.setName("StarTime"); // NOI18N

        jLabel6.setText("StartTime");
        jLabel6.setName("StartTime"); // NOI18N

        dContent.setColumns(20);
        dContent.setRows(5);
        jScrollPane1.setViewportView(dContent);

        saveButton.setText("Save");
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveButtonMouseClicked(evt);
            }
        });

        cButton.setText("Cancel");
        cButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cButton)
                        .addContainerGap(149, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(sTimeText, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dTimeText, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pIDText, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fTimeText, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(cIDText)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(sTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cIDText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pIDText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cButton)
                    .addComponent(saveButton))
                .addContainerGap())
        );

        fTimeText.getAccessibleContext().setAccessibleName("FinishTime");
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseClicked
        // TODO add your handling code here:

        try {

            dContact.setStartTime(sTimeText.getText());
            dContact.setFinishTime(fTimeText.getText());
            dContact.setDocTime(dTimeText.getText());
            dContact.setCusID(Integer.parseInt(cIDText.getText()));
            dContact.setPerID(Integer.parseInt(pIDText.getText()));
            dContact.setContent(dContent.getText());
            employeeSystem.saveCorrespondence(dContact);
        } catch (Exception ex) {
            showError(ex.getMessage());
        }




    }//GEN-LAST:event_saveButtonMouseClicked

    private void cButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cButtonMouseClicked
        // TODO add your handling code here:
        sTimeText.setText("");
        fTimeText.setText("");
        dTimeText.setText("");
        cIDText.setText("");
        pIDText.setText("");
        dContent.setText("");
    }//GEN-LAST:event_cButtonMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cButton;
    private javax.swing.JTextField cIDText;
    private javax.swing.JTextArea dContent;
    private javax.swing.JTextField dTimeText;
    private javax.swing.JTextField fTimeText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pIDText;
    private javax.swing.JTextField sTimeText;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

   
}

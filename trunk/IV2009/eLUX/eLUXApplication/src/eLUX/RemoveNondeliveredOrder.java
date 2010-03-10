/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RemoveNondeliveredOrder.java
 *
 * Created on 2010-3-10, 10:09:46
 */
package eLUX;

import com.elux.ado.order.Order;
import com.elux.system.employeesys.IEmployeeSys;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class RemoveNondeliveredOrder extends javax.swing.JPanel {

    private IEmployeeSys employeeSystem;
    Vector<Order> order = new Vector<Order>();
    private String orderstatus = "";

    /** Creates new form RemoveNondeliveredOrder */
    public RemoveNondeliveredOrder(IEmployeeSys system) {
        initComponents();
        this.employeeSystem = system;
        orderstatus = "nondelivered";
        order = employeeSystem.searchNonDelvOrder(orderstatus);
        addToList();
        
    }

    private void addToList() {
        DefaultListModel list = new DefaultListModel();
        for (Order ord : order) {
            list.addElement(ord.getOrdID() + "- Ordered Time: " + ord.getOrdTime());
        }
        listOrder.setModel(list);
    }

    private void remoOrder() {
        order.removeElementAt(listOrder.getSelectedIndex());
        addToList();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jDialog1 = new javax.swing.JDialog();
        Remove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOrder = new javax.swing.JList();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        Remove.setText("Remove");
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });

        listOrder.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listOrder);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(Remove)
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Remove)
                        .addGap(22, 22, 22))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        // TODO add your handling code here:
        if (listOrder.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a Order to remove", "Error", 0);
            return;
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Really want to remove the selected Order Item?", "Decision", 0);
            if (choice != 0) {
                return;
            } else {
                remoOrder();
            }
        }
    }//GEN-LAST:event_RemoveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Remove;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listOrder;
    // End of variables declaration//GEN-END:variables
}

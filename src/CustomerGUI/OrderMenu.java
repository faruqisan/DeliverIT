package CustomerGUI;

import Classess.*;
import KonekDB.KoneksiMySQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Coding
 */
public class OrderMenu extends javax.swing.JFrame {

    public static String userSession ;
    CustomerDAO dao = new CustomerDAO();

    public OrderMenu(String newCustomer) throws SQLException {
        Customer customer = dao.setCustomerData(newCustomer);
        initComponents();
        setLocationRelativeTo(null);
        welcomeLabel.setText(welcomeLabel.getText() + customer.getName());
        emailLabel.setText(customer.geteMail());
        dao.setTableData(newCustomer, orderTable);
        orderTable.setShowHorizontalLines(false);
    }     
     
    private void submitOrderData(String newUserSession) {
        try {
            int goodsamount = Integer.parseInt(textGoodsAmount.getText());
            OrderForm orderForm = new OrderForm();
            orderForm.setCusName(userSession);
            orderForm.setDate(LocalDate.now().toString());
            orderForm.deliverAddress = txtDestAdd.getText().toUpperCase();
            orderForm.pickupAddress = txtPickupAddress.getText().toUpperCase();
            orderForm.deliverType = combDelivType.getSelectedItem().toString().toUpperCase();
            orderForm.goodsAmount = goodsamount;
            orderForm.goodsType = goodsTypeComb.getSelectedItem().toString().toUpperCase();
            orderForm.pickupAddress = txtPickupAddress.getText().toUpperCase();
            orderForm.deliverAddress = txtDestAdd.getText().toString();

            KoneksiMySQL k = new KoneksiMySQL();
            Connection c = k.getConnection();
            Statement st = c.createStatement();

            String sql = "INSERT INTO `order_data`(`order_id`,`userOrdered`, `order_date`,"
                    + " `oder_type`, `order_pickup_address`, `oder_destination_address`,"
                    + " `order_goods_type`, `order_goods_amount`, `order_status`) VALUES "
                    + "(NULL,'" + newUserSession + "','" + orderForm.getDate() + "','" + orderForm.deliverType
                    + "','" + orderForm.pickupAddress + "','" + orderForm.deliverAddress + "','" + orderForm.goodsType
                    + "','" + orderForm.goodsAmount + "','0')";

            st.execute(sql);
            JOptionPane.showMessageDialog(rootPane, "Order Submit Succesfull");
            clearOrderForm();

        } catch (SQLException ex) {
            Logger.getLogger(OrderMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            dao.setTableData(newUserSession, orderTable);
            //setTableData(newUserSession);
        } catch (SQLException ex) {
            Logger.getLogger(OrderMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cancelOrder(String newUserSession) throws SQLException {
        if (orderTable.getSelectedRow()== -1) {
            JOptionPane.showMessageDialog(rootPane, "Data not selected");
        } else {
            String orderID = orderTable.getValueAt(orderTable.getSelectedRow(), 0).toString();
            int pilihanUser = JOptionPane.showConfirmDialog(rootPane, "Delete This Order ? " + "\nOrderID = " + orderID);

            if (pilihanUser == 0) {
                KoneksiMySQL k = new KoneksiMySQL();
                Connection c = k.getConnection();
                Statement st = c.createStatement();
                String sql = "DELETE FROM `deliverit_main`.`order_data` WHERE `order_data`.`order_id` =" + orderID;
                st.execute(sql);
                JOptionPane.showMessageDialog(rootPane, "Data Deleted Succesfully");
                dao.setTableData(newUserSession, orderTable);
            }
        }

    }

    public void clearOrderForm() {
        textGoodsAmount.setText("");
        txtPickupAddress.setText("");
        txtDestAdd.setText("");
        combDelivType.setSelectedIndex(0);
        goodsTypeComb.setSelectedIndex(0);
    }

    public boolean checkForm() {
        boolean result = false;
        if (textGoodsAmount.getText().equals("") && txtPickupAddress.getText().equals("") && txtDestAdd.getText().equals("")) {
            JOptionPane.showMessageDialog(orderForm, "Goods Amount , Pickup Address , and Destination Address can't be empty !");
        } else if (textGoodsAmount.getText().equals("") && !txtPickupAddress.getText().isEmpty() && !txtDestAdd.getText().isEmpty()) {
            JOptionPane.showMessageDialog(orderForm, "Goods Amount can't be empty");
        } else if (txtPickupAddress.getText().equals("") && !textGoodsAmount.getText().isEmpty() && !txtDestAdd.getText().isEmpty()) {
            JOptionPane.showMessageDialog(orderForm, "Pickup Address can't be empty");
        } else if (txtDestAdd.getText().equals("") && !textGoodsAmount.getText().isEmpty() && !txtPickupAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(orderForm, "Destination Address can't be empty");
        } else if (textGoodsAmount.getText().equals("") && txtPickupAddress.getText().equals("") && !txtDestAdd.getText().isEmpty()) {
            JOptionPane.showMessageDialog(orderForm, "Goods Amount , and Pickup Address can't be empty !");
        } else if (txtDestAdd.getText().equals("") && txtPickupAddress.getText().equals("") && !textGoodsAmount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(orderForm, "Destination Address , and Pickup Address can't be empty !");
        } else if (txtDestAdd.getText().equals("") && textGoodsAmount.getText().equals("") && !txtPickupAddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(orderForm, "Destination Address , and Goods amount can't be empty !");
        } else if (combDelivType.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(orderForm, "Deliver Type Service Must Be Normal or Fast");
        } else if (goodsTypeComb.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(orderForm, "Goods Type Must Select from List");
        } else {
            result = true;
        }
        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        orderForm = new javax.swing.JFrame();
        orderTitleLabel = new javax.swing.JLabel();
        delivTypeLabel = new javax.swing.JLabel();
        combDelivType = new javax.swing.JComboBox();
        labelPckAdd = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        goodsTypeComb = new javax.swing.JComboBox();
        labelGoodsAmount = new javax.swing.JLabel();
        textGoodsAmount = new javax.swing.JTextField();
        txtPickupAddress = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDestAdd = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        labelInformationStatus = new javax.swing.JLabel();
        cmbBoxGoodsUnit = new javax.swing.JComboBox();
        welcomeLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        btnMkOrder = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();

        orderForm.setTitle("Deliver IT | Order Form");
        orderForm.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        orderForm.setLocationByPlatform(true);
        orderForm.setMinimumSize(new java.awt.Dimension(270, 350));
        orderForm.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orderTitleLabel.setText("Order Form DeliverIT");
        orderForm.getContentPane().add(orderTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 11, -1, -1));

        delivTypeLabel.setText("Deliver Type");
        orderForm.getContentPane().add(delivTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 46, -1, -1));

        combDelivType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "NORMAL", "FAST" }));
        combDelivType.setToolTipText("");
        combDelivType.setName("Tes"); // NOI18N
        combDelivType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                combDelivTypeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                combDelivTypeMouseExited(evt);
            }
        });
        orderForm.getContentPane().add(combDelivType, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 43, 101, -1));

        labelPckAdd.setText("Pickup Address");
        orderForm.getContentPane().add(labelPckAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, -1, -1));

        jLabel1.setText("Goods Type");
        orderForm.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 74, -1, -1));

        goodsTypeComb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Electronic", "Furniture", "Food", "Raw" }));
        goodsTypeComb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                goodsTypeCombMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                goodsTypeCombMouseExited(evt);
            }
        });
        orderForm.getContentPane().add(goodsTypeComb, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 74, 101, -1));

        labelGoodsAmount.setText("Goods Amount");
        orderForm.getContentPane().add(labelGoodsAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, -1, -1));

        textGoodsAmount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textGoodsAmountMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                textGoodsAmountMouseExited(evt);
            }
        });
        orderForm.getContentPane().add(textGoodsAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 105, 42, -1));

        txtPickupAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtPickupAddressMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtPickupAddressMouseExited(evt);
            }
        });
        orderForm.getContentPane().add(txtPickupAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 136, 101, -1));

        jLabel2.setText("Destination Address");
        orderForm.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 167, -1, -1));

        txtDestAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtDestAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtDestAddMouseExited(evt);
            }
        });
        orderForm.getContentPane().add(txtDestAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 167, 101, -1));

        btnSubmit.setText("Submit");
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubmitMouseClicked(evt);
            }
        });
        orderForm.getContentPane().add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 236, -1, -1));

        btnClear.setText("Clear");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });
        orderForm.getContentPane().add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 236, 65, -1));

        jButton1.setText("Cancel");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        orderForm.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 236, -1, -1));

        labelInformationStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        orderForm.getContentPane().add(labelInformationStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 220, 20));

        cmbBoxGoodsUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Box", "Unit", "Ton" }));
        orderForm.getContentPane().add(cmbBoxGoodsUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 105, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Deliver-IT | Order Menu");

        welcomeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        welcomeLabel.setText("Welcome ");

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Order Date", "Order Type", "Pickup Address", "Destination Address", "Goods Type", "Goods Amount", "Goods Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        orderTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        orderTable.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        jScrollPane1.setViewportView(orderTable);
        if (orderTable.getColumnModel().getColumnCount() > 0) {
            orderTable.getColumnModel().getColumn(0).setResizable(false);
            orderTable.getColumnModel().getColumn(1).setResizable(false);
            orderTable.getColumnModel().getColumn(2).setResizable(false);
            orderTable.getColumnModel().getColumn(3).setResizable(false);
            orderTable.getColumnModel().getColumn(4).setResizable(false);
            orderTable.getColumnModel().getColumn(5).setResizable(false);
            orderTable.getColumnModel().getColumn(6).setResizable(false);
            orderTable.getColumnModel().getColumn(7).setResizable(false);
        }

        btnMkOrder.setText("Make Order");
        btnMkOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMkOrderMouseClicked(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        btnCancelOrder.setText("Cancel Order");
        btnCancelOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelOrderMouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Order List Table");

        btnLogOut.setText("Log Out");
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogOutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(welcomeLabel)
                    .addComponent(btnCancelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMkOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(welcomeLabel)
                            .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMkOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelOrder)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh)
                    .addComponent(btnLogOut))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMkOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMkOrderMouseClicked
        // TODO add your handling code here:
        orderForm.setVisible(true);
    }//GEN-LAST:event_btnMkOrderMouseClicked

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        // TODO add your handling code here:
        if (checkForm() == true) {
            submitOrderData(userSession);
        }

    }//GEN-LAST:event_btnSubmitMouseClicked

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // TODO add your handling code here:
        clearOrderForm();
    }//GEN-LAST:event_btnClearMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        orderForm.setVisible(false);
    }//GEN-LAST:event_jButton1MouseClicked

    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        try {
            // TODO add your handling code here:
            dao.setTableData(userSession, orderTable);
        } catch (SQLException ex) {
            Logger.getLogger(OrderMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRefreshMouseClicked

    private void combDelivTypeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combDelivTypeMouseEntered
        // TODO add your handling code here:
        labelInformationStatus.setText("Select Delivery Service Type");
    }//GEN-LAST:event_combDelivTypeMouseEntered

    private void combDelivTypeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combDelivTypeMouseExited
        // TODO add your handling code here:
        labelInformationStatus.setText("");
    }//GEN-LAST:event_combDelivTypeMouseExited

    private void goodsTypeCombMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goodsTypeCombMouseEntered
        // TODO add your handling code here:
        labelInformationStatus.setText("Select Goods Type");
    }//GEN-LAST:event_goodsTypeCombMouseEntered

    private void goodsTypeCombMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goodsTypeCombMouseExited
        // TODO add your handling code here:
        labelInformationStatus.setText("");
    }//GEN-LAST:event_goodsTypeCombMouseExited

    private void textGoodsAmountMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textGoodsAmountMouseEntered
        // TODO add your handling code here:
        labelInformationStatus.setText("Set Goods Amount");
    }//GEN-LAST:event_textGoodsAmountMouseEntered

    private void textGoodsAmountMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textGoodsAmountMouseExited
        // TODO add your handling code here:
        labelInformationStatus.setText("");
    }//GEN-LAST:event_textGoodsAmountMouseExited

    private void txtPickupAddressMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPickupAddressMouseEntered
        // TODO add your handling code here:
        labelInformationStatus.setText("Set Pickup Address");
    }//GEN-LAST:event_txtPickupAddressMouseEntered

    private void txtPickupAddressMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPickupAddressMouseExited
        // TODO add your handling code here:
        labelInformationStatus.setText("");
    }//GEN-LAST:event_txtPickupAddressMouseExited

    private void txtDestAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDestAddMouseEntered
        // TODO add your handling code here:
        labelInformationStatus.setText("Set Destination Address");
    }//GEN-LAST:event_txtDestAddMouseEntered

    private void txtDestAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDestAddMouseExited
        // TODO add your handling code here:
        labelInformationStatus.setText("");
    }//GEN-LAST:event_txtDestAddMouseExited

    private void btnCancelOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelOrderMouseClicked
        try {
            // TODO add your handling code here:
            cancelOrder(userSession);
        } catch (SQLException ex) {
            Logger.getLogger(OrderMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelOrderMouseClicked

    private void btnLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseClicked
        // TODO add your handling code here:
        new LoginForm().setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnLogOutMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new OrderMenu(userSession).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnMkOrder;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox cmbBoxGoodsUnit;
    private javax.swing.JComboBox combDelivType;
    private javax.swing.JLabel delivTypeLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JComboBox goodsTypeComb;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelGoodsAmount;
    private javax.swing.JLabel labelInformationStatus;
    private javax.swing.JLabel labelPckAdd;
    private javax.swing.JFrame orderForm;
    private javax.swing.JTable orderTable;
    private javax.swing.JLabel orderTitleLabel;
    private javax.swing.JTextField textGoodsAmount;
    private javax.swing.JTextField txtDestAdd;
    private javax.swing.JTextField txtPickupAddress;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}

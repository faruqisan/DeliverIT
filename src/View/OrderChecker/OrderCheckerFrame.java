/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.OrderChecker;

import Model.Bill;
import java.awt.geom.RoundRectangle2D;
import Model.OrderChecker;
import View.Login.LoginForm;
import MySQL.Koneksi.KoneksiMySQL;
import java.awt.Color;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import net.proteanit.sql.DbUtils;

public class OrderCheckerFrame extends javax.swing.JFrame {

    public static String userSession;
    OrderChecker orderChecker = new OrderChecker();
    Connection con = new KoneksiMySQL().getConnection();
    String tableUsed;

    String columnHeader[] = {"Order ID", "User Ordered", "Order Date", "Order"
        + " Type", "Pickup Address", "Destination Address", "Goods Type", "Amount", "Unit", "Status"};
    int jumlahOrder;
    int xMouse;
    int yMouse;

    public OrderCheckerFrame(String user) {
        setCheckerData(user);
        initComponents();
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 25, 25));
        labelOrderChecekerName.setText(orderChecker.getName());
        setTableDataUnconfirmed();
        tableUsed = "Unconfirmed";
        setOrderStatusLabel(tableUsed);

    }

    private void deleteBill(int rowSelected) {
        int orderId = (int) orderTable.getValueAt(rowSelected, 0);
        try {
            String sql = "DELETE FROM `deliverit_main`.`transaction` WHERE `transaction`.`order_id` ="+orderId+";";
            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void makeBill(int rowSelected) {
        Bill paymentBill = new Bill();
        paymentBill.setBillAmount(countCost(rowSelected));
        paymentBill.setCusName(orderTable.getValueAt(rowSelected, 1).toString());
        paymentBill.setDate(orderTable.getValueAt(rowSelected, 2).toString());
        paymentBill.setOrderID((int) orderTable.getValueAt(rowSelected, 0));
        paymentBill.setId(null);
        paymentBill.setOrderCheckerName(orderChecker.getName());

        try {
            String sql = "INSERT INTO `deliverit_main`.`transaction`"
                    + " (`transaction_id`,`order_id`, `orderchecker_name`, `username`, `total`) VALUES"
                    + " (NULL, '" + paymentBill.getOrderID() + "', '" + paymentBill.getOrderCheckerName() + "', '" + paymentBill.getCusName() + "', '" + paymentBill.getBillAmount() + "');";

            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int countCost(int rowSelected) {

        int costPerUnit = 0;
        int multiply = (int) orderTable.getValueAt(rowSelected, 7);
        String orderUnit = orderTable.getValueAt(rowSelected, 8).toString();

        switch (orderUnit) {
            case "BOX":
                costPerUnit = 8000;
                break;
            case "UNIT":
                costPerUnit = 15000;
                break;
            case "TON":
                costPerUnit = 90000;
                break;
            default:
        }

        int finalCost =0;

        if (orderTable.getValueAt(rowSelected, 3).toString().equalsIgnoreCase("fast")) {
            finalCost=(costPerUnit*multiply)+200000;
        } else {
            finalCost=costPerUnit*multiply;
        }
        return finalCost;
    }

    private void setOrderStatusLabel(String status) {
        jumlahOrder = orderTable.getRowCount();
        String a = Integer.toString(jumlahOrder);
        titleTotOrd.setText("Total " + status + " Order");
        totalOrderLabel.setText(a);
    }

    private void setCheckerData(String user) {
        try {
            KoneksiMySQL k = new KoneksiMySQL();
            Connection c = k.getConnection();
            Statement st = c.createStatement();
            String sql = "SELECT * FROM `user_data` WHERE `username` LIKE '" + user + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                orderChecker.setName(rs.getString("name"));
                orderChecker.seteMail(rs.getString("email"));
                orderChecker.setPhoneNumber(rs.getString("phoneNumber"));
            }
        } catch (SQLException ex) {

        } catch (NullPointerException e) {

        }
    }

    private void setTableDataConfirmed() {
        tableStatus.setText("Confirmed Data");
        tableUsed = "Confirmed Data";
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM ORDER_DATA WHERE ORDER_STATUS=1";
            ResultSet rs = st.executeQuery(sql);

            orderTable.setModel(DbUtils.resultSetToTableModel(rs));
            for (int i = 0; i < orderTable.getColumnModel().getColumnCount(); i++) {
                orderTable.getColumnModel().getColumn(i).setHeaderValue(columnHeader[i]);
            }
            for (int i = 0; i < orderTable.getRowCount(); i++) {
                if (orderTable.getValueAt(i, 9).toString().equalsIgnoreCase("false")) {
                    orderTable.setValueAt("Pending", i, 9);
                } else {
                    orderTable.setValueAt("Confirmed", i, 9);
                }
            }

        } catch (SQLException ex) {

        }
        setOrderStatusLabel(tableUsed);
    }

    private void setTableDataUnconfirmed() {
        tableUsed = "Unconfirmed Data";
        tableStatus.setText("Unconfirmed Data");
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM ORDER_DATA WHERE ORDER_STATUS=0";
            ResultSet rs = st.executeQuery(sql);

            orderTable.setModel(DbUtils.resultSetToTableModel(rs));
            for (int i = 0; i < orderTable.getColumnModel().getColumnCount(); i++) {
                orderTable.getColumnModel().getColumn(i).setHeaderValue(columnHeader[i]);
            }
            for (int i = 0; i < orderTable.getRowCount(); i++) {
                if (orderTable.getValueAt(i, 9).toString().equalsIgnoreCase("false")) {
                    orderTable.setValueAt("Pending", i, 9);
                } else {
                    orderTable.setValueAt("Confirmed", i, 9);
                }
            }

        } catch (SQLException ex) {

        }
        setOrderStatusLabel(tableUsed);
    }

    private void confirmOrder() {
        try {
            int rowSelected = orderTable.getSelectedRow();
            if (orderTable.getValueAt(rowSelected, 9).toString().equalsIgnoreCase("Confirmed")) {
                JOptionPane.showMessageDialog(rootPane, "Data already Confirmed");
            } else {
                String orderID = orderTable.getValueAt(rowSelected, 0).toString();
                int pilihanUser = JOptionPane.showConfirmDialog(rootPane, "Confirm This Order ? " + "\nOrderID = " + orderID);

                if (pilihanUser == 0) {
                    Statement st = con.createStatement();
                    String sql = "UPDATE `deliverit_main`.`order_data` SET `order_status` = '1' WHERE `order_data`.`order_id` =" + orderID + ";";
                    st.execute(sql);
                    JOptionPane.showMessageDialog(rootPane, "Confirmed");
                    makeBill(rowSelected);
                    setTableDataUnconfirmed();

                }

            }
        } catch (SQLException e) {

        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(rootPane, "Data not selected");
        }
        setOrderStatusLabel("");

    }

    private void unconfirmOrder() {
        try {
            int rowSelected = orderTable.getSelectedRow();
            if (orderTable.getValueAt(rowSelected, 9).toString().equalsIgnoreCase("Pending")) {
                JOptionPane.showMessageDialog(rootPane, "Data already Unconfirmed");
            } else {
                String orderID = orderTable.getValueAt(rowSelected, 0).toString();
                int pilihanUser = JOptionPane.showConfirmDialog(rootPane, "Unconfirm This Order ? " + "\nOrderID = " + orderID);

                if (pilihanUser == 0) {
                    Statement st = con.createStatement();
                    String sql = "UPDATE `deliverit_main`.`order_data` SET `order_status` = '0' WHERE `order_data`.`order_id` =" + orderID + ";";
                    st.execute(sql);
                    deleteBill(rowSelected);
                    JOptionPane.showMessageDialog(rootPane, "Unconfirmed");
                    setTableDataConfirmed();
                }
            }
        } catch (SQLException e) {

        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(rootPane, "Data not selected");
        }
        setOrderStatusLabel(tableUsed);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        workerInformationPanel = new javax.swing.JPanel();
        labelOrderChecekerName = new javax.swing.JLabel();
        notificationPanel = new javax.swing.JPanel();
        totalOrderLabel = new javax.swing.JLabel();
        titleTotOrd = new javax.swing.JLabel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        tableStatus = new javax.swing.JLabel();
        operationPanel = new javax.swing.JPanel();
        confirmOrderButton = new javax.swing.JButton();
        unconfirmOrderButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        logoutMenu = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        confirmedOrderMenuItem = new javax.swing.JMenuItem();
        unconfirmedOrderMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mainPanelMouseDragged(evt);
            }
        });
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mainPanelMousePressed(evt);
            }
        });

        workerInformationPanel.setBackground(new java.awt.Color(255, 255, 255));

        labelOrderChecekerName.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        javax.swing.GroupLayout workerInformationPanelLayout = new javax.swing.GroupLayout(workerInformationPanel);
        workerInformationPanel.setLayout(workerInformationPanelLayout);
        workerInformationPanelLayout.setHorizontalGroup(
            workerInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workerInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelOrderChecekerName, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );
        workerInformationPanelLayout.setVerticalGroup(
            workerInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(workerInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelOrderChecekerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        notificationPanel.setBackground(new java.awt.Color(255, 255, 255));

        titleTotOrd.setText("Total Order");

        javax.swing.GroupLayout notificationPanelLayout = new javax.swing.GroupLayout(notificationPanel);
        notificationPanel.setLayout(notificationPanelLayout);
        notificationPanelLayout.setHorizontalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleTotOrd, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalOrderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        notificationPanelLayout.setVerticalGroup(
            notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(notificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleTotOrd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalOrderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        tablePanel.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(orderTable);

        tableStatus.setText("   ");

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                    .addGroup(tablePanelLayout.createSequentialGroup()
                        .addComponent(tableStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        operationPanel.setBackground(new java.awt.Color(255, 255, 255));

        confirmOrderButton.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        confirmOrderButton.setText("Confirm Order");
        confirmOrderButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmOrderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmOrderButtonMouseClicked(evt);
            }
        });

        unconfirmOrderButton.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        unconfirmOrderButton.setText("Unconfirm Order");
        unconfirmOrderButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        unconfirmOrderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unconfirmOrderButtonMouseClicked(evt);
            }
        });

        refreshButton.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        refreshButton.setText("Refresh");
        refreshButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshButtonMouseClicked(evt);
            }
        });
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout operationPanelLayout = new javax.swing.GroupLayout(operationPanel);
        operationPanel.setLayout(operationPanelLayout);
        operationPanelLayout.setHorizontalGroup(
            operationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, operationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(operationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(refreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(unconfirmOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirmOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                .addContainerGap())
        );
        operationPanelLayout.setVerticalGroup(
            operationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(confirmOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unconfirmOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(workerInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(operationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notificationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(workerInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notificationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(operationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jMenuBar1MouseDragged(evt);
            }
        });
        jMenuBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuBar1MousePressed(evt);
            }
        });

        mainMenu.setText("Main");
        mainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainMenuMouseClicked(evt);
            }
        });

        logoutMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        logoutMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlocked.png"))); // NOI18N
        logoutMenu.setText("Logout");
        logoutMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMenuMouseClicked(evt);
            }
        });
        logoutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuActionPerformed(evt);
            }
        });
        mainMenu.add(logoutMenu);

        exitMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/power.png"))); // NOI18N
        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        mainMenu.add(exitMenu);

        jMenuBar1.add(mainMenu);

        viewMenu.setText("View");
        viewMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewMenuMouseClicked(evt);
            }
        });

        confirmedOrderMenuItem.setText("Confirmed Order");
        confirmedOrderMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmedOrderMenuItemMouseClicked(evt);
            }
        });
        confirmedOrderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmedOrderMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(confirmedOrderMenuItem);

        unconfirmedOrderMenuItem.setText("Unconfirmed Order");
        unconfirmedOrderMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unconfirmedOrderMenuItemMouseClicked(evt);
            }
        });
        unconfirmedOrderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unconfirmedOrderMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(unconfirmedOrderMenuItem);

        jMenuBar1.add(viewMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        // TODO add your handling code here:'
        System.exit(0);
    }//GEN-LAST:event_exitMenuActionPerformed

    private void mainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainMenuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mainMenuMouseClicked

    private void logoutMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMenuMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_logoutMenuMouseClicked

    private void logoutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuActionPerformed
        // TODO add your handling code here:
        new LoginForm().setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_logoutMenuActionPerformed

    private void confirmOrderButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmOrderButtonMouseClicked
        // TODO add your handling code here:
            confirmOrder();
    }//GEN-LAST:event_confirmOrderButtonMouseClicked

    private void unconfirmOrderButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unconfirmOrderButtonMouseClicked
        // TODO add your handling code here:

            unconfirmOrder();
    }//GEN-LAST:event_unconfirmOrderButtonMouseClicked

    private void jMenuBar1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jMenuBar1MousePressed

    private void jMenuBar1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jMenuBar1MouseDragged

    private void mainPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_mainPanelMousePressed

    private void mainPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_mainPanelMouseDragged

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void refreshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseClicked
        // TODO add your handling code here:
        setTableDataUnconfirmed();
    }//GEN-LAST:event_refreshButtonMouseClicked

    private void viewMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewMenuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_viewMenuMouseClicked

    private void confirmedOrderMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmedOrderMenuItemMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_confirmedOrderMenuItemMouseClicked

    private void unconfirmedOrderMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unconfirmedOrderMenuItemMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_unconfirmedOrderMenuItemMouseClicked

    private void confirmedOrderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmedOrderMenuItemActionPerformed
        // TODO add your handling code here:
        setTableDataConfirmed();
    }//GEN-LAST:event_confirmedOrderMenuItemActionPerformed

    private void unconfirmedOrderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unconfirmedOrderMenuItemActionPerformed
        // TODO add your handling code here:
        setTableDataUnconfirmed();
    }//GEN-LAST:event_unconfirmedOrderMenuItemActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderCheckerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderCheckerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderCheckerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderCheckerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderCheckerFrame(userSession).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmOrderButton;
    private javax.swing.JMenuItem confirmedOrderMenuItem;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelOrderChecekerName;
    private javax.swing.JMenuItem logoutMenu;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel notificationPanel;
    private javax.swing.JPanel operationPanel;
    private javax.swing.JTable orderTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel tableStatus;
    private javax.swing.JLabel titleTotOrd;
    private javax.swing.JLabel totalOrderLabel;
    private javax.swing.JButton unconfirmOrderButton;
    private javax.swing.JMenuItem unconfirmedOrderMenuItem;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JPanel workerInformationPanel;
    // End of variables declaration//GEN-END:variables
}

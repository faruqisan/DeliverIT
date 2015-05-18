package View.Customer;

import Model.Customer;
import Model.OrderForm;
import View.Login.LoginForm;
import MySQL.Koneksi.KoneksiMySQL;
import java.awt.Font;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Coding
 */
public class OrderMenu extends javax.swing.JFrame {

    public static String userSession;
    CustomerDAO dao = new CustomerDAO();
    int mouseX;
    int mouseY;

    public OrderMenu(String newCustomer) throws SQLException {
        Customer customer = dao.setCustomerData(newCustomer);
        initComponents();
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 25, 25));
        welcomeLabel.setText(welcomeLabel.getText() + customer.getName());
        dao.setTableData(newCustomer, orderTable);
        orderTable.setShowHorizontalLines(false);
        notification();
    }
    
    public void notification(){
        int confirmedOrder=0;
        for(int i=0;i<orderTable.getRowCount();i++){
            if(orderTable.getValueAt(i,8).toString().equalsIgnoreCase("Confirmed")){
                confirmedOrder++;
            }
        }
        if(confirmedOrder!=0){
            notificationLabel.setText(confirmedOrder+" Order Confirmed !");
        }else{
            notificationLabel.setText("");
        }
    }
    
    private void payOrder(String newUserSession) {

        try {
            if (orderTable.getValueAt(orderTable.getSelectedRow(), 8).toString().equalsIgnoreCase("Pending")) {
                JOptionPane.showMessageDialog(null, "Order Still Pending");
            } else {
              new PaymentMenu(orderTable.getValueAt(orderTable.getSelectedRow(), 0).toString()).setVisible(true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(rootPane, "Order Not Selected");
        }
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
            orderForm.deliverAddress = txtDestAdd.getText();
            orderForm.goodsUnit = cmbBoxGoodsUnit.getSelectedItem().toString().toUpperCase();

            KoneksiMySQL k = new KoneksiMySQL();
            Connection c = k.getConnection();
            Statement st = c.createStatement();

            String sql = "INSERT INTO `order_data`(`order_id`,`userOrdered`, `order_date`,"
                    + " `oder_type`, `order_pickup_address`, `oder_destination_address`,"
                    + " `order_goods_type`, `order_goods_amount`, `order_status`, `unit`) VALUES "
                    + "(NULL,'" + newUserSession + "','" + orderForm.getDate() + "','" + orderForm.deliverType
                    + "','" + orderForm.pickupAddress + "','" + orderForm.deliverAddress + "','" + orderForm.goodsType
                    + "','" + orderForm.goodsAmount + "','0','" + orderForm.goodsUnit + "')";

            st.execute(sql);
            JOptionPane.showMessageDialog(rootPane, "Order Submit Succesfull");
            clearOrderForm();

        } catch (SQLException ex) {
            Logger.getLogger(OrderMenu.class.getName()).log(Level.SEVERE, null, ex);
        }

        dao.setTableData(newUserSession, orderTable);
        notification();

    }

    private void cancelOrder(String newUserSession) throws SQLException {

        try {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(rootPane, "Data not selected");
        }
        notification();

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
        jPanel1 = new javax.swing.JPanel();
        orderTitleLabel = new javax.swing.JLabel();
        combDelivType = new javax.swing.JComboBox();
        delivTypeLabel = new javax.swing.JLabel();
        lblGoodsType = new javax.swing.JLabel();
        goodsTypeComb = new javax.swing.JComboBox();
        labelGoodsAmount = new javax.swing.JLabel();
        textGoodsAmount = new javax.swing.JTextField();
        cmbBoxGoodsUnit = new javax.swing.JComboBox();
        txtPickupAddress = new javax.swing.JTextField();
        labelPckAdd = new javax.swing.JLabel();
        lblDestAdd = new javax.swing.JLabel();
        txtDestAdd = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        labelInformationStatus = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        btnMkOrder = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        btnPayment = new javax.swing.JButton();
        notificationLabel = new javax.swing.JLabel();

        orderForm.setTitle("Deliver IT | Order Form");
        orderForm.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        orderForm.setResizable(false);
        orderForm.setSize(313, 450);
        orderForm.setLocationByPlatform(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        orderTitleLabel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        orderTitleLabel.setText("Order Form DeliverIT");

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

        delivTypeLabel.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        delivTypeLabel.setText("Deliver Type");

        lblGoodsType.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        lblGoodsType.setText("Goods Type");

        goodsTypeComb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Electronic", "Furniture", "Food", "Raw" }));
        goodsTypeComb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                goodsTypeCombMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                goodsTypeCombMouseExited(evt);
            }
        });

        labelGoodsAmount.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        labelGoodsAmount.setText("Goods Amount");

        textGoodsAmount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textGoodsAmountMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                textGoodsAmountMouseExited(evt);
            }
        });

        cmbBoxGoodsUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Box", "Unit", "Ton" }));

        txtPickupAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtPickupAddressMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtPickupAddressMouseExited(evt);
            }
        });

        labelPckAdd.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        labelPckAdd.setText("Pickup Address");

        lblDestAdd.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        lblDestAdd.setText("Destination Address");

        txtDestAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtDestAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtDestAddMouseExited(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubmitMouseClicked(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });

        jButton1.setText("Cancel");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        labelInformationStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDestAdd)
                            .addComponent(labelPckAdd))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPickupAddress)
                            .addComponent(txtDestAdd)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelGoodsAmount)
                            .addComponent(lblGoodsType)
                            .addComponent(delivTypeLabel))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textGoodsAmount)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbBoxGoodsUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(goodsTypeComb, 0, 163, Short.MAX_VALUE)
                            .addComponent(combDelivType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(orderTitleLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelInformationStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(orderTitleLabel)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delivTypeLabel)
                    .addComponent(combDelivType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGoodsType)
                    .addComponent(goodsTypeComb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGoodsAmount)
                    .addComponent(textGoodsAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBoxGoodsUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPickupAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPckAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDestAdd)
                    .addComponent(txtDestAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelInformationStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout orderFormLayout = new javax.swing.GroupLayout(orderForm.getContentPane());
        orderForm.getContentPane().setLayout(orderFormLayout);
        orderFormLayout.setHorizontalGroup(
            orderFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        orderFormLayout.setVerticalGroup(
            orderFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Deliver-IT | Order Menu");
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

        welcomeLabel.setFont(new java.awt.Font("Marketing Script", 0, 24)); // NOI18N
        welcomeLabel.setText("Welcome ");

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Order Date", "Order Type", "Pickup Address", "Destination Address", "Goods Type", "Goods Amount", "Goods Unit", "Goods Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        orderTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        orderTable.setDropMode(javax.swing.DropMode.INSERT_ROWS);
        orderTable.setGridColor(new java.awt.Color(255, 255, 255));
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
            orderTable.getColumnModel().getColumn(8).setResizable(false);
        }

        btnMkOrder.setBackground(new java.awt.Color(153, 153, 255));
        btnMkOrder.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnMkOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnMkOrder.setText("Make Order");
        btnMkOrder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMkOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMkOrderMouseClicked(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(153, 153, 255));
        btnRefresh.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        btnCancelOrder.setBackground(new java.awt.Color(153, 153, 255));
        btnCancelOrder.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnCancelOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelOrder.setText("Cancel Order");
        btnCancelOrder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelOrderMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Order List Table");

        btnLogOut.setBackground(new java.awt.Color(255, 102, 102));
        btnLogOut.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        btnLogOut.setText("Log Out");
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogOutMouseClicked(evt);
            }
        });

        btnPayment.setBackground(new java.awt.Color(153, 153, 255));
        btnPayment.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnPayment.setForeground(new java.awt.Color(255, 255, 255));
        btnPayment.setText("Pay");
        btnPayment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPaymentMouseClicked(evt);
            }
        });

        notificationLabel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        notificationLabel.setForeground(new java.awt.Color(255, 51, 51));
        notificationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        notificationLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                notificationLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                notificationLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(334, 334, 334)
                        .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnMkOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(welcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(btnMkOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        dao.setTableData(userSession, orderTable);
        notification();
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

    private void btnPaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentMouseClicked
        // TODO add your handling code here:
        payOrder(userSession);
    }//GEN-LAST:event_btnPaymentMouseClicked

    private void mainPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMousePressed
        // TODO add your handling code here:
        mouseX=evt.getX();
        mouseY=evt.getY();
    }//GEN-LAST:event_mainPanelMousePressed

    private void mainPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseDragged
        // TODO add your handling code here:
        int x=evt.getXOnScreen();
        int y=evt.getYOnScreen();
        this.setLocation(x-mouseX, y-mouseY);
    }//GEN-LAST:event_mainPanelMouseDragged

    private void notificationLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationLabelMouseEntered
        notificationLabel.setFont(new java.awt.Font("Calibri", 0, 24));
    }//GEN-LAST:event_notificationLabelMouseEntered

    private void notificationLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationLabelMouseExited
        // TODO add your handling code here:
        notificationLabel.setFont(new java.awt.Font("Calibri", 0, 18));
    }//GEN-LAST:event_notificationLabelMouseExited

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
                if ("Windows".equals(info.getName())) {
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
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox cmbBoxGoodsUnit;
    private javax.swing.JComboBox combDelivType;
    private javax.swing.JLabel delivTypeLabel;
    private javax.swing.JComboBox goodsTypeComb;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelGoodsAmount;
    private javax.swing.JLabel labelInformationStatus;
    private javax.swing.JLabel labelPckAdd;
    private javax.swing.JLabel lblDestAdd;
    private javax.swing.JLabel lblGoodsType;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel notificationLabel;
    private javax.swing.JFrame orderForm;
    private javax.swing.JTable orderTable;
    private javax.swing.JLabel orderTitleLabel;
    private javax.swing.JTextField textGoodsAmount;
    private javax.swing.JTextField txtDestAdd;
    private javax.swing.JTextField txtPickupAddress;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}

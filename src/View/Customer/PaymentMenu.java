package View.Customer;

import Model.Bill;
import Model.OrderForm;
import MySQL.Koneksi.KoneksiMySQL;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PaymentMenu extends javax.swing.JFrame {

    OrderForm orderForm;
    Bill billPayment;

    Connection con = new KoneksiMySQL().getConnection();
    public static String orderID = "15";

    public PaymentMenu(String orderID) {
        initComponents();
        setOrderForm(orderID);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, this.getWidth(), this.getHeight(), 25, 25));
        setPaymentForm(orderID);
    }

    private void pay() {
        try {
            billPayment.setPaymentMethod(comboBoxPaymentMethod.getSelectedItem().toString());
            billPayment.setBankAccount(textBankAccount.getText());
            payToBank();
            setPaymentForm(orderID);
            showReport();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Data Belum Lengkap");
        }
    }
    
    private void payToBank(){
        
        try {
            String sql="UPDATE `deliverit_main`.`transaction` SET `payment_status` = 'Paid' WHERE `transaction`.`transaction_id` ="+billPayment.getTransactionId()+";";
            Statement st=con.prepareStatement(sql);
            st.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showReport(){
        JOptionPane.showMessageDialog(null, "Transaksi Berhasil Dilaksanakan");
    }

    private void setOrderForm(String orderId) {
        orderForm = new OrderForm();

        try {
            String sql = "SELECT ORDER_ID,ORDER_DATE,ODER_TYPE,order_pickup_address,"
                    + "ODER_DESTINATION_ADDRESS,ORDER_GOODS_TYPE,ORDER_GOODS_AMOUNT,"
                    + "UNIT FROM ORDER_DATA WHERE ORDER_ID='" + orderId + "';";
            Statement st = con.createStatement();
            st.executeQuery(sql);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                orderForm.setId(rs.getString("ORDER_ID"));
                orderForm.setDate(rs.getString("ORDER_DATE"));
                orderForm.deliverType = rs.getString("ODER_TYPE");
                orderForm.pickupAddress = rs.getString("ORDER_PICKUP_ADDRESS");
                orderForm.deliverAddress = rs.getString("oder_destination_address");
                orderForm.goodsType = rs.getString("ORDER_GOODS_TYPE");
                orderForm.goodsAmount = Integer.parseInt(rs.getString("ORDER_GOODS_AMOUNT"));
                orderForm.goodsUnit = rs.getString("UNIT");
            }
            setLabelOrder();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPaymentForm(String orderID) {
        billPayment = new Bill();

        try {
            String sql = "SELECT transaction_id,orderchecker_name,payment_status,total from transaction where order_id='" + orderID + "';";
            Statement st = con.createStatement();
            st.executeQuery(sql);
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                billPayment.setTransactionId(Integer.parseInt(rs.getString("transaction_id")));
                billPayment.setOrderCheckerName(rs.getString("orderchecker_name"));
                billPayment.setBillAmount(Integer.parseInt(rs.getString("total")));
                billPayment.setPaymentStatus(rs.getString("payment_status"));
                
                if(billPayment.getPaymentStatus().equalsIgnoreCase("null")||billPayment.getPaymentStatus().equalsIgnoreCase("")){
                    billPayment.setPaymentStatus("Not Yet Paid Off");
                }
                
            }
            setPaymentLabel();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPaymentLabel() {
        labelValueTransactionID.setText(String.valueOf(billPayment.getTransactionId()));
        labelValueOrderID2.setText(orderForm.getId());
        labelValueOrderCheckerName.setText(billPayment.getOrderCheckerName());
        labelValuePaymentStatus.setText(billPayment.getPaymentStatus());
        labelValueTotalPrice.setText(String.valueOf(billPayment.getBillAmount()) + " Rupiah");
        textTotalPayment.setText(billPayment.getBillAmount() + " Rupiah");
    }

    private void setLabelOrder() {
        labelValueOrderID.setText(orderForm.getId());
        labelValueOrderDate.setText(orderForm.getDate());
        labelValueOrderType.setText(orderForm.deliverType);
        labelValuePickupAddress.setText(orderForm.pickupAddress);
        labelValueDestinationAddresss.setText(orderForm.deliverAddress);
        labelValueGoodsType.setText(orderForm.goodsType);
        labelValueGoodsAmount.setText(orderForm.goodsAmount + " " + orderForm.goodsUnit);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelBody1 = new javax.swing.JPanel();
        tabbedPanel = new javax.swing.JTabbedPane();
        panelOrderData = new javax.swing.JPanel();
        labelOrderID = new javax.swing.JLabel();
        labelOrderDate = new javax.swing.JLabel();
        labelOrderType = new javax.swing.JLabel();
        labelPickupAdd = new javax.swing.JLabel();
        labelDestinationAdd = new javax.swing.JLabel();
        labelGoodsType = new javax.swing.JLabel();
        labelGoodsAmount = new javax.swing.JLabel();
        labelValueOrderID = new javax.swing.JLabel();
        labelValueOrderDate = new javax.swing.JLabel();
        labelValueOrderType = new javax.swing.JLabel();
        labelValuePickupAddress = new javax.swing.JLabel();
        labelValueDestinationAddresss = new javax.swing.JLabel();
        labelValueGoodsType = new javax.swing.JLabel();
        labelValueGoodsAmount = new javax.swing.JLabel();
        panelPaymentData = new javax.swing.JPanel();
        labelTransactionID = new javax.swing.JLabel();
        labelOrderID2 = new javax.swing.JLabel();
        labelOrderCheckerName = new javax.swing.JLabel();
        labelTotalPrice = new javax.swing.JLabel();
        labelValueTransactionID = new javax.swing.JLabel();
        labelValueOrderID2 = new javax.swing.JLabel();
        labelValueOrderCheckerName = new javax.swing.JLabel();
        labelValueTotalPrice = new javax.swing.JLabel();
        labelPaymentStatus = new javax.swing.JLabel();
        labelValuePaymentStatus = new javax.swing.JLabel();
        panelOperation = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        operationPane = new javax.swing.JPanel();
        buttonPay = new javax.swing.JButton();
        labelPaymentVia = new javax.swing.JLabel();
        comboBoxPaymentMethod = new javax.swing.JComboBox();
        labelBankAccount = new javax.swing.JLabel();
        textBankAccount = new javax.swing.JTextField();
        labelAccountPin = new javax.swing.JLabel();
        passAccountField = new javax.swing.JPasswordField();
        labelTotalPayment = new javax.swing.JLabel();
        textTotalPayment = new javax.swing.JTextField();
        imagesOnlinePaymentsMethod = new javax.swing.JLabel();
        panelFooter = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        labelHeaderLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Payment Menu"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelBody1.setBackground(new java.awt.Color(255, 255, 255));

        tabbedPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelOrderData.setBackground(new java.awt.Color(255, 255, 255));
        panelOrderData.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelOrderID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrderID.setText("Order ID  ");

        labelOrderDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrderDate.setText("Order Date ");

        labelOrderType.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOrderType.setText("Order Type");

        labelPickupAdd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelPickupAdd.setText("Order Pickup Address");

        labelDestinationAdd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelDestinationAdd.setText("Order Destination Address");

        labelGoodsType.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelGoodsType.setText("Order Goods Type");

        labelGoodsAmount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelGoodsAmount.setText("Order Goods Amount");

        labelValueOrderID.setText(" ");

        labelValueOrderDate.setText(" ");

        labelValueOrderType.setText("      ");

        labelValueDestinationAddresss.setText(" ");

        labelValueGoodsType.setText(" ");

        labelValueGoodsAmount.setText(" ");

        javax.swing.GroupLayout panelOrderDataLayout = new javax.swing.GroupLayout(panelOrderData);
        panelOrderData.setLayout(panelOrderDataLayout);
        panelOrderDataLayout.setHorizontalGroup(
            panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrderDataLayout.createSequentialGroup()
                        .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelOrderType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelPickupAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(labelOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelValueOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelValueOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelValueOrderType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelValuePickupAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelOrderDataLayout.createSequentialGroup()
                        .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelDestinationAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(labelGoodsType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelGoodsAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelValueDestinationAddresss, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(labelValueGoodsType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelValueGoodsAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(327, 327, 327))
        );
        panelOrderDataLayout.setVerticalGroup(
            panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrderID)
                    .addComponent(labelValueOrderID))
                .addGap(18, 18, 18)
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelOrderDate)
                    .addComponent(labelValueOrderDate))
                .addGap(18, 18, 18)
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelValueOrderType)
                    .addComponent(labelOrderType))
                .addGap(18, 18, 18)
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelValuePickupAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelPickupAdd))
                .addGap(18, 18, 18)
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDestinationAdd)
                    .addComponent(labelValueDestinationAddresss))
                .addGap(18, 18, 18)
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGoodsType)
                    .addComponent(labelValueGoodsType))
                .addGap(18, 18, 18)
                .addGroup(panelOrderDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGoodsAmount)
                    .addComponent(labelValueGoodsAmount))
                .addContainerGap(97, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Order Data", panelOrderData);

        panelPaymentData.setBackground(new java.awt.Color(255, 255, 255));
        panelPaymentData.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelTransactionID.setText("Transaction ID ");

        labelOrderID2.setText("Order ID");

        labelOrderCheckerName.setText("Order Checker Name");

        labelTotalPrice.setText("Total Price");

        labelValueTransactionID.setText("jLabel1");

        labelValueOrderID2.setText("jLabel2");

        labelValueOrderCheckerName.setText("jLabel3");

        labelValueTotalPrice.setText("jLabel4");

        labelPaymentStatus.setText("Payment Status");

        labelValuePaymentStatus.setText("jLabel2");

        javax.swing.GroupLayout panelPaymentDataLayout = new javax.swing.GroupLayout(panelPaymentData);
        panelPaymentData.setLayout(panelPaymentDataLayout);
        panelPaymentDataLayout.setHorizontalGroup(
            panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPaymentDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelPaymentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTransactionID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelOrderID2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelOrderCheckerName, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(labelTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelValueTransactionID, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(labelValueOrderID2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelValueOrderCheckerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelValueTotalPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelValuePaymentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(223, Short.MAX_VALUE))
        );
        panelPaymentDataLayout.setVerticalGroup(
            panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPaymentDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTransactionID)
                    .addComponent(labelValueTransactionID))
                .addGap(18, 18, 18)
                .addGroup(panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrderID2)
                    .addComponent(labelValueOrderID2))
                .addGap(18, 18, 18)
                .addGroup(panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrderCheckerName)
                    .addComponent(labelValueOrderCheckerName))
                .addGap(18, 18, 18)
                .addGroup(panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTotalPrice)
                    .addComponent(labelValueTotalPrice))
                .addGap(18, 18, 18)
                .addGroup(panelPaymentDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPaymentStatus)
                    .addComponent(labelValuePaymentStatus))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        tabbedPanel.addTab("Payment Data", panelPaymentData);

        javax.swing.GroupLayout panelBody1Layout = new javax.swing.GroupLayout(panelBody1);
        panelBody1.setLayout(panelBody1Layout);
        panelBody1Layout.setHorizontalGroup(
            panelBody1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
            .addGroup(panelBody1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBody1Layout.createSequentialGroup()
                    .addComponent(tabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelBody1Layout.setVerticalGroup(
            panelBody1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelBody1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
        );

        panelOperation.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setToolTipText("");

        operationPane.setBackground(new java.awt.Color(255, 255, 255));
        operationPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonPay.setBackground(new java.awt.Color(21, 101, 192));
        buttonPay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonPay.setForeground(new java.awt.Color(255, 255, 255));
        buttonPay.setText("Pay");
        buttonPay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonPay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonPayMouseClicked(evt);
            }
        });

        labelPaymentVia.setText("Payment Via");

        comboBoxPaymentMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Visa", "Master Card", "PayPal" }));
        comboBoxPaymentMethod.setSelectedIndex(-1);

        labelBankAccount.setText("Bank Account");

        labelAccountPin.setText("Account PIN");

        labelTotalPayment.setText("Total Payment");

        textTotalPayment.setEditable(false);

        imagesOnlinePaymentsMethod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paymentMethods.png"))); // NOI18N
        imagesOnlinePaymentsMethod.setText(" ");

        javax.swing.GroupLayout operationPaneLayout = new javax.swing.GroupLayout(operationPane);
        operationPane.setLayout(operationPaneLayout);
        operationPaneLayout.setHorizontalGroup(
            operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(operationPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, operationPaneLayout.createSequentialGroup()
                        .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPaymentVia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelBankAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textBankAccount)
                            .addComponent(comboBoxPaymentMethod, 0, 115, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, operationPaneLayout.createSequentialGroup()
                        .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAccountPin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTotalPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textTotalPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(passAccountField)))
                    .addGroup(operationPaneLayout.createSequentialGroup()
                        .addComponent(imagesOnlinePaymentsMethod)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        operationPaneLayout.setVerticalGroup(
            operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, operationPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPaymentVia)
                    .addComponent(comboBoxPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBankAccount)
                    .addComponent(textBankAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAccountPin)
                    .addComponent(passAccountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(operationPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTotalPayment)
                    .addComponent(textTotalPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imagesOnlinePaymentsMethod)
                .addGap(18, 18, 18)
                .addComponent(buttonPay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Operation", operationPane);

        javax.swing.GroupLayout panelOperationLayout = new javax.swing.GroupLayout(panelOperation);
        panelOperation.setLayout(panelOperationLayout);
        panelOperationLayout.setHorizontalGroup(
            panelOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
            .addGroup(panelOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, Short.MAX_VALUE))
        );
        panelOperationLayout.setVerticalGroup(
            panelOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
            .addGroup(panelOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
        );

        panelFooter.setBackground(new java.awt.Color(21, 101, 192));

        javax.swing.GroupLayout panelFooterLayout = new javax.swing.GroupLayout(panelFooter);
        panelFooter.setLayout(panelFooterLayout);
        panelFooterLayout.setHorizontalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFooterLayout.setVerticalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        panelHeader.setBackground(new java.awt.Color(21, 101, 192));

        labelHeaderLabel.setFont(new java.awt.Font("Marketing Script", 0, 36)); // NOI18N
        labelHeaderLabel.setForeground(new java.awt.Color(255, 255, 255));
        labelHeaderLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelHeaderLabel.setText("Payment Menu");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/power.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelHeaderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(labelHeaderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBody1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelOperation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBody1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void buttonPayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonPayMouseClicked
        // TODO add your handling code here:
        pay();
    }//GEN-LAST:event_buttonPayMouseClicked

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
            java.util.logging.Logger.getLogger(PaymentMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentMenu(orderID).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPay;
    private javax.swing.JComboBox comboBoxPaymentMethod;
    private javax.swing.JLabel imagesOnlinePaymentsMethod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelAccountPin;
    private javax.swing.JLabel labelBankAccount;
    private javax.swing.JLabel labelDestinationAdd;
    private javax.swing.JLabel labelGoodsAmount;
    private javax.swing.JLabel labelGoodsType;
    private javax.swing.JLabel labelHeaderLabel;
    private javax.swing.JLabel labelOrderCheckerName;
    private javax.swing.JLabel labelOrderDate;
    private javax.swing.JLabel labelOrderID;
    private javax.swing.JLabel labelOrderID2;
    private javax.swing.JLabel labelOrderType;
    private javax.swing.JLabel labelPaymentStatus;
    private javax.swing.JLabel labelPaymentVia;
    private javax.swing.JLabel labelPickupAdd;
    private javax.swing.JLabel labelTotalPayment;
    private javax.swing.JLabel labelTotalPrice;
    private javax.swing.JLabel labelTransactionID;
    private javax.swing.JLabel labelValueDestinationAddresss;
    private javax.swing.JLabel labelValueGoodsAmount;
    private javax.swing.JLabel labelValueGoodsType;
    private javax.swing.JLabel labelValueOrderCheckerName;
    private javax.swing.JLabel labelValueOrderDate;
    private javax.swing.JLabel labelValueOrderID;
    private javax.swing.JLabel labelValueOrderID2;
    private javax.swing.JLabel labelValueOrderType;
    private javax.swing.JLabel labelValuePaymentStatus;
    private javax.swing.JLabel labelValuePickupAddress;
    private javax.swing.JLabel labelValueTotalPrice;
    private javax.swing.JLabel labelValueTransactionID;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel operationPane;
    private javax.swing.JPanel panelBody1;
    private javax.swing.JPanel panelFooter;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelOperation;
    private javax.swing.JPanel panelOrderData;
    private javax.swing.JPanel panelPaymentData;
    private javax.swing.JPasswordField passAccountField;
    private javax.swing.JTabbedPane tabbedPanel;
    private javax.swing.JTextField textBankAccount;
    private javax.swing.JTextField textTotalPayment;
    // End of variables declaration//GEN-END:variables
}

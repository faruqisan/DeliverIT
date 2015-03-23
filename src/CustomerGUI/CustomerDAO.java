package CustomerGUI;

import Classess.Customer;
import Classess.OrderForm;
import static CustomerGUI.OrderMenu.userSession;
import KonekDB.KoneksiMySQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class CustomerDAO {

    public void setTableData(String newUserSession, JTable orderTable) throws SQLException {
        KoneksiMySQL k = new KoneksiMySQL();
        Connection c = k.getConnection();
        Statement st = c.createStatement();
        String sql = "SELECT `order_id`, `order_date`, `oder_type`, `order_pickup_address`, `oder_destination_address`, `order_goods_type`, `order_goods_amount`, `order_status` FROM `deliverit_main`.`order_data` WHERE `userOrdered` LIKE '" + newUserSession + "'";
        ResultSet rs = st.executeQuery(sql);

        orderTable.setModel(DbUtils.resultSetToTableModel(rs));
        orderTable.getColumnModel().getColumn(0).setHeaderValue("Order ID");
        orderTable.getColumnModel().getColumn(1).setHeaderValue("Order Date");
        orderTable.getColumnModel().getColumn(2).setHeaderValue("Order Type");
        orderTable.getColumnModel().getColumn(3).setHeaderValue("Pickup Address");
        orderTable.getColumnModel().getColumn(4).setHeaderValue("Destination Address");
        orderTable.getColumnModel().getColumn(5).setHeaderValue("Goods Type");
        orderTable.getColumnModel().getColumn(6).setHeaderValue("Goods Amount");
        orderTable.getColumnModel().getColumn(7).setHeaderValue("Order Status");

    }

    public Customer setCustomerData(String newUserSession) throws SQLException {
        KoneksiMySQL k = new KoneksiMySQL();
        Connection c = k.getConnection();
        Statement st = c.createStatement();

        Customer cust = new Customer();
        String sql = "SELECT * FROM `customer_data` WHERE `username` LIKE '" + newUserSession + "' ORDER BY `customer_id` ASC";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            cust.setName(rs.getString("nama"));
            cust.seteMail(rs.getString("email"));
            cust.setAddress(rs.getString("address"));
            cust.setPhoneNumber(rs.getString("phoneNumber"));

        }

        return cust;

    }
    
}

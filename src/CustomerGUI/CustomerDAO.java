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

    public void setTableData(String newUserSession, JTable orderTable) {
        try {
            Connection c = new KoneksiMySQL().getConnection();
            Statement st = c.createStatement();
            String sql = "SELECT `order_id`, `order_date`, `oder_type`, `order_pickup_address`, `oder_destination_address`, `order_goods_type`, `order_goods_amount`, `unit`,`order_status` FROM `deliverit_main`.`order_data` WHERE `userOrdered` LIKE '" + newUserSession + "'";
            ResultSet rs = st.executeQuery(sql);
            String columnHeader[]={"Order ID","Order Date","Order Type","Pickup Address","Destination Address","Goods Type","Goods Amount","Unit","Order Status"};
            orderTable.setModel(DbUtils.resultSetToTableModel(rs));
            
            for (int i = 0; i < orderTable.getColumnModel().getColumnCount(); i++) {
                orderTable.getColumnModel().getColumn(i).setHeaderValue(columnHeader[i]);
            }
            for(int i=0;i<orderTable.getRowCount();i++){
                if(orderTable.getValueAt(i,8).toString().equalsIgnoreCase("false")){
                    orderTable.setValueAt("Pending", i, 8);
                }else{
                    orderTable.setValueAt("Confirmed", i, 8);
                }
            }
            
            
        } catch (SQLException ex) {
            
        } catch (NullPointerException e){
            
        }

    }

    public Customer setCustomerData(String newUserSession) {
        Customer cust = new Customer();
        try {
            KoneksiMySQL k = new KoneksiMySQL();
            Connection c = k.getConnection();
            Statement st = c.createStatement();
            String sql = "SELECT * FROM `user_data` WHERE `username` LIKE '" + newUserSession+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cust.setName(rs.getString("name"));
                cust.seteMail(rs.getString("email"));
                cust.setAddress(rs.getString("address"));
                cust.setPhoneNumber(rs.getString("phoneNumber"));
            }
        } catch (SQLException ex) {
            
        } catch (NullPointerException e){
            
        }
        return cust;
    }
    
}

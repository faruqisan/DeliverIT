package Classess;

public class Bill extends Form {
    private String cashierName;
    private int orderID;
    private int transactionId;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    private String orderCheckerName;
    private int billAmount;

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getOrderCheckerName() {
        return orderCheckerName;
    }

    public void setOrderCheckerName(String orderCheckerName) {
        this.orderCheckerName = orderCheckerName;
    }

    public int getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }

}
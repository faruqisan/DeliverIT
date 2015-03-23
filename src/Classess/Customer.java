package Classess;

import java.util.LinkedList;
import java.util.Scanner;

public class Customer extends User{

    private String companyName;
    private String companyAddress;
    private boolean orderStatus;
    private LinkedList<OrderForm> orderList = new LinkedList<>();

    public void makeOrder(Scanner s) {
        OrderForm of = new OrderForm();
        of.setCusName(getName());
        of.setCompName(companyName);
        System.out.print("Deliver Type : ");
        of.deliverType = s.nextLine();
        System.out.print("Pickup Address : ");
        of.pickupAddress = s.nextLine();
        System.out.print("Deliver Address : ");
        of.deliverAddress = s.nextLine();
        System.out.print("Goods Type : ");
        of.goodsType = s.nextLine();
        System.out.print("Goods Amount : ");
        of.goodsAmount = s.nextInt();

        System.out.println("Order submited,waiting for approve , thankyou " + getName());

        this.orderList.push(of);
    }

    public void cancelOrder(OrderForm of) {

    }

    public void pay() {

    }

    public void setCompanyName(String newCompName) {
        this.companyName = newCompName;
    }

    public void setCompanyAddress(String newCompAdd) {
        this.companyAddress = newCompAdd;
    }

    public String getCompName() {
        return this.companyName;
    }

    public String getCompAdd() {
        return this.companyAddress;
    }

    public LinkedList<OrderForm> getOrderList() {
        return this.orderList;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }


}

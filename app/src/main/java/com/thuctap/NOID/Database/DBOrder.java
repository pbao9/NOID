package com.thuctap.NOID.Database;

import java.util.ArrayList;

public class DBOrder {
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private ArrayList<DBCart> cartItems;

    public DBOrder() {
    }

    public DBOrder(String customerName, String customerAddress, String customerPhone, ArrayList<DBCart> cartItems) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.cartItems = cartItems;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public ArrayList<DBCart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<DBCart> cartItems) {
        this.cartItems = cartItems;
    }
}

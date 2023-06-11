package com.thuctap.NOID.Database;

public class DBCart {

    private String cartId, customerId, productId, productName, productPrice, productTotalPrice, productNoteCart, productTimeOrder;
    private int productCount;

    public DBCart() {
    }

    public DBCart(String cartId, String customerId, String productId, String productName, String productPrice, String productTotalPrice, String productNoteCart, String productTimeOrder, int productCount) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productTotalPrice = productTotalPrice;
        this.productNoteCart = productNoteCart;
        this.productTimeOrder = productTimeOrder;
        this.productCount = productCount;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getProductNoteCart() {
        return productNoteCart;
    }

    public void setProductNoteCart(String productNoteCart) {
        this.productNoteCart = productNoteCart;
    }

    public String getProductTimeOrder() {
        return productTimeOrder;
    }

    public void setProductTimeOrder(String productTimeOrder) {
        this.productTimeOrder = productTimeOrder;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}

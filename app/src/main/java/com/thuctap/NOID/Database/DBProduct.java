package com.thuctap.NOID.Database;

public class DBProduct {
    String name;
    String desc;
    String price;
/*    int image;*/

    public DBProduct(String name, String desc, String price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

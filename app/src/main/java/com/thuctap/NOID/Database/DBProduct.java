package com.thuctap.NOID.Database;

public class DBProduct {

    private String id,name, desc, price, imageURl;

    public DBProduct(String id, String name, String desc, String price, String imageURl) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.imageURl = imageURl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }
}

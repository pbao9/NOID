package com.thuctap.NOID.Database;

import java.util.Map;

public class DBOrder {
    String makh, thoigiandh, ghichudh, tinhtrang;
    int soluongdh, tongtiendh;
    private Map<String, DBCart> sanpham;

    public DBOrder() {
    }

    public DBOrder(String makh, String thoigiandh, String ghichudh, String tinhtrang, int soluongdh, int tongtiendh, Map<String, DBCart> sanpham) {
        this.makh = makh;
        this.thoigiandh = thoigiandh;
        this.ghichudh = ghichudh;
        this.tinhtrang = tinhtrang;
        this.soluongdh = soluongdh;
        this.tongtiendh = tongtiendh;
        this.sanpham = sanpham;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getThoigiandh() {
        return thoigiandh;
    }

    public void setThoigiandh(String thoigiandh) {
        this.thoigiandh = thoigiandh;
    }

    public String getGhichudh() {
        return ghichudh;
    }

    public void setGhichudh(String ghichudh) {
        this.ghichudh = ghichudh;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public int getSoluongdh() {
        return soluongdh;
    }

    public void setSoluongdh(int soluongdh) {
        this.soluongdh = soluongdh;
    }

    public int getTongtiendh() {
        return tongtiendh;
    }

    public void setTongtiendh(int tongtiendh) {
        this.tongtiendh = tongtiendh;
    }

    public Map<String, DBCart> getSanpham() {
        return sanpham;
    }

    public void setSanpham(Map<String, DBCart> sanpham) {
        this.sanpham = sanpham;
    }
}

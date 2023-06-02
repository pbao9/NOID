package com.thuctap.NOID.Database;

public class DBUser {
    String email, hoten, tendangnhap, matkhau, phanquyen;

    public DBUser(String email, String hoten, String tendangnhap, String matkhau, String phanquyen) {
        this.email = email;
        this.hoten = hoten;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.phanquyen = phanquyen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getPhanquyen() {
        return phanquyen;
    }

    public void setPhanquyen(String phanquyen) {
        this.phanquyen = phanquyen;
    }

    public DBUser() {
    }
}

package com.shinhan.gridactivity;

/**
 * Created by IC-INTPC-087102 on 2017-03-24.
 */

public class ImageItem {
    String hwnno;   String name;    String mobile;
    String date;    String ctnt;
    int cmnty;      int br_grp_g;   int resId;

    public ImageItem(String hwnno, String name) {
        this.hwnno = hwnno;
        this.name = name;
    }

    public ImageItem(String hwnno, String name, String mobile,
                     String date, String ctnt, int cmnty, int br_grp_g, int resId) {
        this.hwnno = hwnno;
        this.name = name;
        this.mobile = mobile;
        this.date = date;
        this.ctnt = ctnt;
        this.cmnty = cmnty;
        this.br_grp_g = br_grp_g;
        this.resId = resId;
    }

    public ImageItem(String hwnno, String name, String mobile, int cmnty, int br_grp_g) {
        this.hwnno = hwnno;
        this.name = name;
        this.mobile = mobile;
        this.cmnty = cmnty;
        this.br_grp_g = br_grp_g;
    }

    public String getHwnno() {
        return hwnno;
    }

    public void setHwnno(String hwnno) {
        this.hwnno = hwnno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCtnt() {
        return ctnt;
    }

    public void setCtnt(String ctnt) {
        this.ctnt = ctnt;
    }

    public int getCmnty() {
        return cmnty;
    }

    public void setCmnty(int cmnty) {
        this.cmnty = cmnty;
    }

    public int getBr_grp_g() {
        return br_grp_g;
    }

    public void setBr_grp_g(int br_grp_g) {
        this.br_grp_g = br_grp_g;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}

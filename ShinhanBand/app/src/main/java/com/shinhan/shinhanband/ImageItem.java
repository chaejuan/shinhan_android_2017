package com.shinhan.shinhanband;

import java.util.ArrayList;

/**
 * Created by IC-INTPC-087102 on 2017-03-25.
 */

public class ImageItem {
    String grpco_c; String hwnno; String name;
    String mobile; String date; String location;
    //String hwnno; String name; String mobile; Date date; String ctnt; int cmnty; int br_grp_g; int resId;

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

    int latitude; int longditude;
    String ctnt;int cmnty;int br_grp_g;
    ArrayList<String> hashTag = new ArrayList<String>();
    String drdt; String drHwnno;
    String lstdt; String lstHwnno;
    int resId;
    //     ("15101193", "채주안", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon6));

    public ImageItem(String hwnno, String name, String mobile, String date, String location, int cmnty, int br_grp_g, int resId) {
        this.hwnno = hwnno;
        this.name = name;
        this.mobile = mobile;
        this.date = date;
        this.location = location;
        this.cmnty = cmnty;
        this.br_grp_g = br_grp_g;
        this.resId = resId;
    }


    public String getGrpco_c() {
        return grpco_c;
    }

    public void setGrpco_c(String grpco_c) {
        this.grpco_c = grpco_c;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongditude() {
        return longditude;
    }

    public void setLongditude(int longditude) {
        this.longditude = longditude;
    }

    public String getCtnt() {
        return ctnt;
    }

    public void setCtnt(String ctnt) {
        this.ctnt = ctnt;
    }

    public ArrayList<String> getHashTag() {
        return hashTag;
    }

    public void setHashTag(ArrayList<String> hashTag) {
        this.hashTag = hashTag;
    }

    public String getDrdt() {
        return drdt;
    }

    public void setDrdt(String drdt) {
        this.drdt = drdt;
    }

    public String getDrHwnno() {
        return drHwnno;
    }

    public void setDrHwnno(String drHwnno) {
        this.drHwnno = drHwnno;
    }

    public String getLstdt() {
        return lstdt;
    }

    public void setLstdt(String lstdt) {
        this.lstdt = lstdt;
    }

    public String getLstHwnno() {
        return lstHwnno;
    }

    public void setLstHwnno(String lstHwnno) {
        this.lstHwnno = lstHwnno;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}

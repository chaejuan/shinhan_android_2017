package com.shinhan.servlet;

import java.util.ArrayList;

/**
 * Created by IC-INTPC-087102 on 2017-03-25.
 */

public class markerDto {
    String grpco_c; String hwnno; String img_key;;
    int location, mas_s, cmnty, br_grp_g, latitude, longditude;
    String ctnt;
    ArrayList<String> hashTag;
    String drdt;
    String drHwnno;
    String lstdt;
    String lstHwnno;
    int resId;
    ArrayList<markerDto> items = new ArrayList<markerDto>();
    public markerDto(String grpco_c, String hwnno, String img_key, int location,
                     int mas_s, int cmnty, int br_grp_g, int latitude, int longditude,
                     String ctnt, ArrayList<String> hashTag, String drdt, String drHwnno,
                     String lstdt, String lstHwnno, int resId)
    {
        this.grpco_c = grpco_c;
        this.hwnno = hwnno;
        this.img_key = img_key;
        this.location = location;
        this.mas_s = mas_s;
        this.cmnty = cmnty;
        this.br_grp_g = br_grp_g;
        this.latitude = latitude;
        this.longditude = longditude;
        this.ctnt = ctnt;
        this.hashTag = hashTag;
        this.drdt = drdt;
        this.drHwnno = drHwnno;
        this.lstdt = lstdt;
        this.lstHwnno = lstHwnno;
        this.resId = resId;
    }

    public markerDto(String grpco_c, String hwnno, String img_key, int location,
                     int mas_s, int cmnty, int br_grp_g, int latitude, int longditude, String ctnt, ArrayList<String> hashTag)
    {
        this.grpco_c = grpco_c;
        this.hwnno = hwnno;
        this.img_key = img_key;
        this.location = location;
        this.mas_s = mas_s;
        this.cmnty = cmnty;
        this.br_grp_g = br_grp_g;
        this.latitude = latitude;
        this.longditude = longditude;
        this.ctnt = ctnt;
        this.hashTag = hashTag;
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

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getMas_s() {
        return mas_s;
    }

    public void setMas_s(int mas_s) {
        this.mas_s = mas_s;
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

    public void addItem(markerDto marker) {
        items.add(marker);
    }
}

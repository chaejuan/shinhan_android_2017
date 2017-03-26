package com.shinhan.servlet;

/**
 * Created by IC-INTPC-087102 on 2017-03-26.
 */

public class codevalDto {
    String c_eng_nm; int mas_s; int cval; String ctnt;

    public codevalDto(String c_eng_nm, int mas_s, int cval, String ctnt) {
        this.c_eng_nm = c_eng_nm; this.mas_s = mas_s; this.cval = cval; this.ctnt = ctnt;
    }

    public String getC_eng_nm() {
        return c_eng_nm;
    }

    public int getMas_s() {
        return mas_s;
    }

    public int getCval() {
        return cval;
    }

    public String getCtnt() {
        return ctnt;
    }
}

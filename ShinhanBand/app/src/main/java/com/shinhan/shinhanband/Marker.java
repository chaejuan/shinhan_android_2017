package com.shinhan.shinhanband;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IC-INTPC-087102 on 2017-03-25.
 */

public class Marker  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shb_marker.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "shb_marker";

    public Marker(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // 내장 메모리에 만든다. 아무나 접근 못한다. 내 app에서만 접근 가능한 데이타베이스
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // DB 파일이 존재하지 않을 때 최초 생성
        // 장메모리에 데이타베이스 파일 생성할 때
        // 테이블 생성 쿼리 문
        String query = "create table " + TABLE_NAME + " (" +        // integer autoincrement
                "grpco_c text PRIMARY KEY, hwnno text PRIMARY KEY, img_key text PRIMARY KEY, " +
                "location text, mas_s integer, cmnty integer, latitude integer, longditude integer, " +
                "ctnt text, tag_ctnt1 text, tag_ctnt2 text, tag_ctnt3 text, tag_ctnt4 text, " +
                "tag_ctnt5 text, tag_ctnt6 text, tag_ctnt7 text, tag_ctnt8 text, tag_ctnt9 text, tag_ctnt10)";
        try {
            db.execSQL(query);  // 쿼리실행
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  // VERSION이 변경되었을 때
        try { db.execSQL("drop table if exists " +  TABLE_NAME); }
        catch(Exception e) { e.printStackTrace();}
        onCreate(db);   // DB파일 생성
    }
}
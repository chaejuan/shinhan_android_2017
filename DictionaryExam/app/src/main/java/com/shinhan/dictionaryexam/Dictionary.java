package com.shinhan.dictionaryexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IC-INTPC-087102 on 2017-03-24.
 */
public class Dictionary extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dictionary.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "voca";

    public Dictionary(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // 내장 메모리에 만든다. 아무나 접근 못한다. 내 app에서만 접근 가능한 데이타베이스
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // DB 파일이 존재하지 않을 때 최초 생성
        //     내장메모리에 데이타베이스 파일 생성할 때
        String query = "create table " + TABLE_NAME + " (" +
                "_id integer PRIMARY KEY autoincrement, " +
                "word text, definition text)";  // 테이블 생성 쿼리 문
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

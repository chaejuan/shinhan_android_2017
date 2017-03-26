package com.shinhan.servlet;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shinhan.shinhanband.R;
import com.shinhan.shinhanband.RecordWrite;

import java.util.ArrayList;

/**
 * Created by IC-INTPC-087102 on 2017-03-25.
 */

public class markerDao {
    markerDto marker;
    public static final String DATABASE_NAME = "shb.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "shb_marker";

    public markerDao() {

    }

    public void insertMarker(markerDto dto) {

    }

    public ArrayList<markerDto> readMarker(int imgKey, int imgser) {
        ArrayList<markerDto> list = new ArrayList<markerDto>();

        return list;
    }


}

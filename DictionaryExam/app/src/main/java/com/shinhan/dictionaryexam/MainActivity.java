package com.shinhan.dictionaryexam;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readDatabase(); // DB 내용 읽기
    }

    public void onButtonClicked(View view) {
        EditText word = (EditText)findViewById(R.id.word);
        EditText definition = (EditText)findViewById(R.id.definition);
        String wordString = word.getText().toString();
        String definitionString = definition.getText().toString();

        if(! wordString.isEmpty() && ! definitionString.isEmpty()) {
            // 단어, 뜻이 입력했으면 DB에 저장을 해라.
            writeDatabase(wordString, definitionString);    // DB에 저장
            readDatabase();  // DB의 내용 읽기 -> 읽은 후 리스트 뷰 출력 하는 부분
        }
    }
    // 저장하기
    public void writeDatabase(String word, String definition) {
        Dictionary dictionary = new Dictionary (MainActivity.this); // DATABASE 파일이 없으면 생성된다.
        SQLiteDatabase database = dictionary.getWritableDatabase();  // 쓰기용 SQLiteDatabase 열기

        ContentValues values = new ContentValues(); // content vlues에 담기
        values.put("word", word);                   // word 컬럼에 데이타 저장
        values.put("definition", definition);      // definition 컬럼에 데이타 저장

        database.insert(Dictionary.TABLE_NAME, null, values);   // db에 데이타 insert

        // 에러 안나면 데이타 저장된 것
    }

    // 리스트 데이타 출력하기

    public void readDatabase () {
        Log.i("dsfasdfdsfasd", "readDatabase");
        Dictionary dictionary = new Dictionary (MainActivity.this);
        SQLiteDatabase database = dictionary.getReadableDatabase();
        Cursor cursor =
                database.rawQuery("select * from " + Dictionary.TABLE_NAME, null);
        Log.i("count", cursor.getCount() + ""); // toString

        String[] words = new String[cursor.getCount()];
        for (int i=0; i < cursor.getCount(); i++) { // DB내용을 문자형배열로 변환
            cursor.moveToNext();
            words[i] = cursor.getString(1) + " (" + cursor.getString(2) + ")";  // 2번째 3번째 컬럼을 넣어라
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, words);
        ListView listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }
}

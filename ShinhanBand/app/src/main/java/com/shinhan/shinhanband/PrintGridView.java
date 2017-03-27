package com.shinhan.shinhanband;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrintGridView extends AppCompatActivity {
    EditText editText, hashText;
    Button printButton;
    GridView gridView;
    ImageAdapter adapter;
    ArrayList<String> tagList;
    String drawbleImage;
    private String hwnno;   private int cmnty;  private int br_grp_g; private String ctnt;
    private double pointX, pointY; String hashtags;
    String img_key;
    Intent pintent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_grid_view);

        gridView = (GridView) findViewById(R.id.gridView);
        printButton = (Button) findViewById(R.id.listButton);
        hashText = (EditText) findViewById(R.id.hashText);

        intent = getIntent();    // 처음 실행될 때 인텐트 수신
        processIntent(intent);

        if (intent != null) {
            hwnno = intent.getStringExtra("HWNNO");
            cmnty = intent.getIntExtra("CMNTY", 0);
            br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);


            Log.d("TAG", "## hwnno : " + hwnno);
            Log.d("TAG", "## cmty : " + cmnty);
            Log.d("TAG", "## BR_GRP_G : " + br_grp_g);
        }
        adapter = new ImageAdapter(this);
        /*
        this.grpco_c = grpco_c;this.hwnno = hwnno; this.location = location;this.mas_s = mas_s;
        this.cmnty = cmnty;this.br_grp_g = br_grp_g;this.latitude = latitude;this.longditude = longditude;
        this.ctnt = ctnt;this.hashTag = hashTag;this.drdt = drdt;this.drHwnno = drHwnno;this.lstdt = lstdt;
        this.lstHwnno = lstHwnno;this.resId = resId;
        */
        /*
            여기서 select * from shb_marker where location =: location (예. leondon)
         */
        //adapter.addItem(new makerDto
        //        ("S001", hwnno, "5-8001", "", 1, cmnty, br_grp_g, R.drawable.leondon1));

        /*this.grpco_c = grpco_c;this.hwnno = hwnno;this.img_key = img_key;this.mas_s = mas_s;this.location = location;
        this.cmnty = cmnty;this.br_grp_g = br_grp_g;this.latitude = latitude;this.longditude = longditude;
        this.hashtags = hashtags;this.resId = resId;*/
        ctnt = "영국에서의 웰프로 여행";
        drawbleImage = "leondon2";
        adapter.addItem(new ImageItem
                ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, ctnt ,"#영국#신한은행", R.drawable.leondon1));
        adapter.addItem(new ImageItem
                ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, ctnt ,"#영국#신한은행", R.drawable.leondon2));
        adapter.addItem(new ImageItem
                ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, ctnt ,"#영국#신한은행", R.drawable.leondon3));
        adapter.addItem(new ImageItem
                ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, ctnt ,"#영국#신한은행", R.drawable.leondon4));
        adapter.addItem(new ImageItem
                ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, ctnt,"#영국#신한은행", R.drawable.leondon5));

        gridView.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.editText);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();

                img_key = item.getImg_key();

                Toast.makeText(PrintGridView.this, img_key + " 로 이동 중..", Toast.LENGTH_SHORT).show();
                Bundle intent = getIntent().getExtras();
                Intent pintent = new Intent(PrintGridView.this, ReadItem.class);
                pintent.putExtra("IMG", drawbleImage);
                pintent.putExtra("HWNNO", intent.getString("HWNNO")); // putExtra hwnno를 넣은 값으로 HWNNO를 intent 객체로 전달한다.
                pintent.putExtra("CMNTY", cmnty); pintent.putExtra("BR_GRP_G", br_grp_g);
                pintent.putExtra("XPOINT", pointX); pintent.putExtra("YPOINT", pointY);
                pintent.putExtra("IMGKEY", img_key);
                pintent.putExtra("HASHTAGS", hashtags);
                pintent.putExtra("CTNT", ctnt);
//                Log.d("TAG", "onItemClick img : " + );
                Log.d("TAG", "onItemClick hwnno : " + intent.getString("HWNNO"));
                Log.d("TAG", "onItemClick cmty : " + cmnty);
                Log.d("TAG", "onItemClick br_grp_g : " + br_grp_g);
                Log.d("TAG", "onItemClick pointx : " + pointX);
                Log.d("TAG", "onItemClick pointy : " + pointY);
                Log.d("TAG", "onItemClick imgkey : " + img_key);
                Log.d("TAG", "onItemClick hashtags : " + hashtags);
                Log.d("TAG", "onItemClick ctnt : " + ctnt);

                startActivity(pintent);
            }
        });

    }

    public void onHashButtonClicked(View view) {
//        Bundle intent = getIntent().getExtras();
//        Intent pintent = new Intent(PrintGridView.this, HashListView.class);
//
//        pintent.putExtra("HWNNO", intent.getString("HWNNO")); // putExtra hwnno를 넣은 값으로 HWNNO를 intent 객체로 전달한다.
//        pintent.putExtra("CMNTY", cmnty);   pintent.putExtra("BR_GRP_G", br_grp_g);
//        pintent.putExtra("XPOINT", pointX); pintent.putExtra("YPOINT", pointY);
//        pintent.putExtra("HASH", hashText.getText().toString());

        Log.d("TAG", "map view hwnno : " + hwnno);
        Log.d("TAG", "map view cmty : " + cmnty);
        Log.d("TAG", "map view BR_GRP_G : " + br_grp_g);
        Log.d("TAG", "map view xpoint : " + pointX);
        Log.d("TAG", "map view ypoint : " + pointY);
        Log.d("TAG", "map view hash call search name is " + hashText.getText().toString());

        String searchName = hashText.getText().toString();
        /**
         * 여기 수정
         */
        adapter = new ImageAdapter(this);

        if (searchName.equals("부행장님")) {
            drawbleImage = "itbanker2";
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#부행장", R.drawable.itbanker));
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#부행장", R.drawable.itbanker2));
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#부행장", R.drawable.itbanker3));
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#부행장", R.drawable.itbanker4));
        } else if (searchName.equals("조용병행장님")) {
            drawbleImage = "jobanker5";
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#행장", R.drawable.jobanker6));
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#행장", R.drawable.jobanker5));
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#행장", R.drawable.jobanker2));
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#행장", R.drawable.jobanker1));
//            adapter.addItem(new ImageItem
//                    ("15101193", "20160210151011930063", "5-2001", "20160213", "#은행장#신한은행", 7, 0, R.drawable.jobanker2));
//            adapter.addItem(new ImageItem
//                    ("15101193", "20160210151011930063", "5-2001", "20160213", "#은행장#신한은행", 7, 0, R.drawable.jobanker3));
//            adapter.addItem(new ImageItem
//                    ("15101193", "20160210151011930063", "5-2001", "20160213", "#은행장#신한은행", 7, 0, R.drawable.jobanker4));
//            adapter.addItem(new ImageItem
//                    ("15101193", "20160210151011930063", "5-2001", "20150213", "#은행장#신한은행", 7, 0, R.drawable.jobanker5));
//            adapter.addItem(new ImageItem
//                    ("15101193", "20160210151011930063", "5-2001", "20150213", "#은행장#신한은행", 7, 0, R.drawable.jobanker6));
        } else if (searchName.equals("위성호행장님")) {
            drawbleImage = "wibanker2";
//            adapter.addItem(new ImageItem
//                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#취임", R.drawable.wibanker));
//            adapter.addItem(new ImageItem
//                    ("15101193", "20170210151011930063", "5-2100", "20150213", "#부행장#신한은행", 7, 0, R.drawable.wibanker5));
            adapter.addItem(new ImageItem
                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#취임", R.drawable.wibanker2));
//            adapter.addItem(new ImageItem
//                    ("S001", "15101193", "201502131015001510119300", 1, 1, 0, 5, 10, 50, "" ,"#신한은행#취임", R.drawable.wibanker3));
        }
        gridView.setAdapter(adapter);
    }


    public void onListButtonClicked(View view) {
        //Toast.makeText(getApplicationContext(), "선택 : LIST VIEW" + printButton.getText().toString(), Toast.LENGTH_LONG).show();
        if(printButton.getText().toString().equals("LIST VIEW")) {
            //Toast.makeText(getApplicationContext(), "선택 : LIST VIEW" , Toast.LENGTH_LONG).show();
            //adapter = new ImageAdapter(this);
            gridView.setNumColumns(2);
            printButton.setText("GRID VIEW");
            //gridView.setAdapter(adapter);
        }
        else if(printButton.getText().toString().equals("GRID VIEW")) {
            //Toast.makeText(getApplicationContext(), "선택 : GRID VIEW" , Toast.LENGTH_LONG).show();
            //adapter = new ImageAdapter(this);
            gridView.setNumColumns(1);
            printButton.setText("LIST VIEW");
            //gridView.setAdapter(adapter);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {     // 이미 실행중일때 인텐트 수신
        //processIntent()
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null){
            hwnno = intent.getStringExtra("HWNNO"); cmnty = intent.getIntExtra("CMNTY", 0); br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);
            pointX = intent.getDoubleExtra("XPOINT", 0); pointY = intent.getDoubleExtra("YPOINT", 0);

            long x = (long)pointX;  long y = (long)pointY;

            Log.d("TAG", "hwnno : " + hwnno); Log.d("TAG", "cmty : " + cmnty); Log.d("TAG", "br_grp_g" + br_grp_g);
            Log.d("TAG", "xPoint : " + pointX); Log.d("TAG", "yPoint : " + pointY);
        }
        else {}
    }
}

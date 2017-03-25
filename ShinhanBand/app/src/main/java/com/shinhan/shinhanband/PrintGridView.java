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

public class PrintGridView extends AppCompatActivity {
    EditText editText;
    Button printButton;
    GridView gridView;
    ImageAdapter adapter;
    private String hwnno;   private int cmnty;  private int br_grp_g;
    private double pointX, pointY;
    String img_key;
    Intent pintent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_grid_view);

        gridView = (GridView) findViewById(R.id.gridView);
        printButton = (Button) findViewById(R.id.listButton);

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
        adapter.addItem(new ImageItem
                ("15101193", "20150513151011930063", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon2));
        adapter.addItem(new ImageItem
                ("15101193", "20150513151011930063", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon3));
        adapter.addItem(new ImageItem
                ("15101193", "20150513151011930063", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon4));
        adapter.addItem(new ImageItem
                ("15101193", "20150513151011930063", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon5));
        adapter.addItem(new ImageItem
                ("15101193", "20150513151011930063", "5-8001", "20150213", "#런던지점#신한은행", 7, 0, R.drawable.leondon6));

        gridView.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.editText);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();

                img_key = item.getName();


                Toast.makeText(PrintGridView.this, "작성 페이지 이동 중", Toast.LENGTH_SHORT).show();
                Bundle intent = getIntent().getExtras();
                Intent pintent = new Intent(PrintGridView.this, ReadItem.class);

                pintent.putExtra("HWNNO", intent.getString("HWNNO")); // putExtra hwnno를 넣은 값으로 HWNNO를 intent 객체로 전달한다.
                pintent.putExtra("CMNTY", cmnty); pintent.putExtra("BR_GRP_G", br_grp_g);
                //pintent.putExtra("XPOINT", pointX); pintent.putExtra("YPOINT", pointY);
                pintent.putExtra("IMGKEY", img_key);

                Log.d("TAG", "hwnno : " + intent.getString("HWNNO"));
                Log.d("TAG", "cmty : " + cmnty);
                Log.d("TAG", "BR_GRP_G : " + br_grp_g);
                //Log.d("TAG", "XPOINT : " + pointX);
                //Log.d("TAG", "YPOINT : " + pointY);
                Log.d("TAG", "imgkey : " + img_key);

                startActivity(pintent);

            }
        });

    }
    public void onButtonDrClicked(View view) {

    }

    public void onListButtonClicked(View view) {
        //Toast.makeText(getApplicationContext(), "선택 : LIST VIEW" + printButton.getText().toString(), Toast.LENGTH_LONG).show();
        if(printButton.getText().toString().equals("LIST VIEW")) {
            //Toast.makeText(getApplicationContext(), "선택 : LIST VIEW" , Toast.LENGTH_LONG).show();
            //adapter = new ImageAdapter(this);
            gridView.setNumColumns(1);
            printButton.setText("GRID VIEW");
            //gridView.setAdapter(adapter);
        }
        else if(printButton.getText().toString().equals("GRID VIEW")) {
            //Toast.makeText(getApplicationContext(), "선택 : GRID VIEW" , Toast.LENGTH_LONG).show();
            //adapter = new ImageAdapter(this);
            gridView.setNumColumns(2);
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

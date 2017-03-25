package com.shinhan.shinhanband;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.*;

import java.util.ArrayList;
import java.util.List;

public class PrivateActivity extends AppCompatActivity {
    private String hwnno;
    private int cmnty;
    private int br_grp_g;

    List<String> listview_items;
    ArrayAdapter<String> listview_adapter;

    TextView cmntTextView;
    TextView brgrpgTextView;
    String[] cmnyItems = {"미사용", "압구정 커뮤니티", "도산대로 커뮤니티", "코엑스 커뮤니티", "선릉 커뮤니티", "테헤란로 커뮤니티"
                    , "대치 커뮤니티", "도곡 커뮤니티", "논현 커뮤니티", "반포 커뮤니티", "서초 커뮤니티"};
    String[] brGrpgItems = {"그룹외소속", "경영기획그룹", "경영지원그룹", "여신심사그룹", "리스크관리그룹", "ICT그룹",
                    "글로벌사업그룹", "개인그룹", "WM그룹", "기업그룹", "기관그룹", "CIB그룹", "디지털뱅킹그룹",
                    "소비자그룹브랜드그룹", "영업기획그룹", "영업추진1그룹", "영업추진2그룹", "신탁연금그룹"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);

        cmntTextView = (TextView)findViewById(R.id.cmntTextView);

        Spinner spinner = (Spinner) findViewById(R.id.cmnty_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, cmnyItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // 아이템 선택 시 이벤트
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cmntTextView.setText(cmnyItems[position]);
                cmnty = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cmntTextView.setText("");
            }
        });

        brgrpgTextView = (TextView)findViewById(R.id.brgrpgTextView);

        Spinner spinner2 = (Spinner) findViewById(R.id.brgrpg_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, brGrpgItems);


        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        // 아이템 선택 시 이벤트
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brgrpgTextView.setText(brGrpgItems[position]);
                br_grp_g = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cmntTextView.setText("");
            }
        });
        Intent intent = getIntent();    // 처음 실행될 때 인텐트 수신
        processIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {     // 이미 실행중일때 인텐트 수신
        //processIntent()
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null){
            hwnno = intent.getStringExtra("HWNNO");
            cmnty = intent.getIntExtra("CMNTY", 0);
            br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);

            Toast.makeText(PrivateActivity.this, "" + hwnno +"님 환영합니다.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(PrivateActivity.this, "intent is null..",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void onButtonDrClicked(View view) {
        Bundle intent = getIntent().getExtras();

        Toast.makeText(PrivateActivity.this, "map view call!",
                Toast.LENGTH_SHORT).show();
        //EditText editText1 = (EditText) findViewById(R.id.editText1);
        //EditText editText2 = (EditText) findViewById(R.id.editText2);
        Intent pintent = new Intent(PrivateActivity.this, MapView.class);
        // editText의 toString
        // getText는 String으로 변환하여 행원번호를 받아온다.
        //String hwnno = editText1.getText().toString();
        //cmnty = Integer.parseInt(editText1.getText().toString());
        //br_grp_g = Integer.parseInt(editText2.getText().toString());

        //Intent intent = new Intent(MainActivity.this, SubActivity.class);// Main이 Sub를 실행해라.
        // subactivity에 실행시키면서 동시에 자기가 넘겨받은 intent를 subactivity에 전달하고
        // 실행될 때 이미 intent를 받고 있다.
        pintent.putExtra("HWNNO", intent.getString("HWNNO")); // putExtra hwnno를 넣은 값으로 HWNNO를 intent 객체로 전달한다.
        pintent.putExtra("CMNTY", cmnty);
        pintent.putExtra("BR_GRP_G", br_grp_g);

        //intent.putExtra("CMNTY", cmnty)
        Log.d("TAG", "hwnno : " + intent.getString("HWNNO"));
        Log.d("TAG", "cmty : " + cmnty);
        Log.d("TAG", "BR_GRP_G" + br_grp_g);

        //startActivityForResult(intent, 0);     // startActivity
        //if (cmnty == 0 || )
        startActivity(pintent);
    }
}

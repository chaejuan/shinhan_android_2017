package com.shinhan.shinhanband;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shinhan.servlet.MarkerDB;


public class MapView extends AppCompatActivity implements GoogleMap.OnMapClickListener {
    public static final String TAG = "MapView";
    public static LatLng PERTH;

    private LatLng MELBOURNE;           // marker choice
    private long idx;
    //SupportMapFragment mapFragment;
    private String hwnno;   private int cmnty;  private int br_grp_g;
    private double pointX, pointY;

    TextView mapText;
    Button hButton, vButton, tButton, pButton, mButton; /* button */

    SupportMapFragment mapFragment; GoogleMap map;
    public int currentPos;

    public void setup() {
        hButton = (Button)findViewById(R.id.hButton);
        vButton = (Button)findViewById(R.id.vButton);
        tButton = (Button)findViewById(R.id.tButton);
        pButton = (Button)findViewById(R.id.pButton);
        mButton = (Button)findViewById(R.id.mButton);
        idx = 19;
    }

    class MyMarker {        // Marker 정보 저장용 클래스
        String name;
        LatLng latLng;

        MyMarker(String name, LatLng latLng) {
            this.name = name;   this.latLng = latLng;
        }
    }

    MyMarker[] markers = {
            new MyMarker("신한은행 런던지점", new LatLng(52.328503, -0.240398)),
            new MyMarker("신한은행 뉴델리", new LatLng(28.583165, 77.222703)),
            new MyMarker("신한은행 베트남지점", new LatLng(10.784652, 106.696701)),
            new MyMarker("워커힐", new LatLng(37.555272, 127.110946)),
            new MyMarker("신한은행 일산금융센터", new LatLng(37.662563, 126.770731)),
            new MyMarker("문촌마을 17단지", new LatLng(37.667995, 126.757251)),
            new MyMarker("신한은행 본점", new LatLng(37.561419, 126.975516)),
            new MyMarker("신한은행 연수원", new LatLng(37.226066, 127.115673)),
            new MyMarker("서울대학교", new LatLng(37.460044, 126.951862)),
            new MyMarker("신한은행 오사카", new LatLng(34.676315, 135.499803)),
            new MyMarker("신한은행 뉴옥", new LatLng(40.749467, -73.975967))
    };

    public MarkerOptions marker;

    /*
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        Intent intent = getIntent();    // 처음 실행될 때 인텐트 수신
        processIntent(intent);

        setup();

        // 터치이벤트 설정
        //map.setOnMapClickListener(this);

        if (intent != null){
            hwnno = intent.getStringExtra("HWNNO");
            cmnty = intent.getIntExtra("CMNTY", 0);
            br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);

            mapText = (TextView)findViewById(R.id.location);
            String str = hwnno + "님 환영니다.";
            mapText.setText(str);
        }

        readDatabase();     /* 일어 와서 마커에 저장 한다.. 마커 표시를 이런 식으로 한다. */

        mapFragment =
                (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;        // 비동기 방식으로 Google 지도 객체 얻기
                PolylineOptions rectOptions = new PolylineOptions();
                rectOptions.color(Color.RED);
                // 마커 춫력
                for(int i=0; i<markers.length; i++) {
                    marker = new MarkerOptions();
                    marker.position(markers[i].latLng);
                    marker.title(markers[i].name);

                    map.addMarker(marker);
                    // 확대 기능
                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            /*
                            Toast.makeText(MainActivity.this, marker.getTitle() + "-" + marker.getId() + "-" + marker.getTitle(),
                                    Toast.LENGTH_SHORT).show();
                            */


                            for(int j = 0 ; j < markers.length; j++) {      // 마커 클릭 시 현재 마커 index 저장
                                if (markers[j].name.equals(marker.getTitle())) {
                                    Log.d(TAG, "-------------------------------------- index is " + j);
                                    //Log.d("Zoom Level " + String.valueOf(c))
                                    currentPos = j + 1;
                                    if (currentPos >= markers.length - 1)
                                        currentPos = 0;
                                    break;
                                }
                                marker.getPosition();
                            }
                            ////////////////////////////////


                            ////////////////////////////////
                            /*
                             * 위성으로 변경하여 당기는 것인데........ !!!
                             */
                            /*
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);       // 위성으로 변경 후 당기는 것
                            */
                            //map.animateCamera(  // 위성사진으로 확대
                            //        CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));

                            //map.animateCamera(CameraUpdateFactory.zoomTo(18));

                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), idx));

                            ImageView imageView = new ImageView(MapView.this);
                            imageView.setImageResource(R.drawable.sunny);
                            // Layer
                            // 팝업 띄우기
                            /*
                            private int cmnty;
                            private int br_grp_g;
                            */
                            AlertDialog.Builder dialog =
                                        new AlertDialog.Builder(MapView.this);
                            dialog.setTitle(marker.getTitle());
                            dialog.setMessage(hwnno + "(" + cmnty + "," + br_grp_g + ")"  + "님은" + marker.getTitle()+"을(를) 방문 했습니다.");
                            // 단순한 글은 여기서
                            dialog.setView(imageView);      // xml로 넣을 수도 있다.
                            /*
                             *  영국에 있었던 리스트가 나온다.
                             *  영국의 리스트 사진들이 나오며 해당사진을 클릭하면
                             *  태그들과 이미지가 출력된다.
                             */
                            dialog.setPositiveButton("보기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(MapView.this, "보기 클릭", Toast.LENGTH_SHORT).show();
                                    Bundle intent = getIntent().getExtras();
                                    Intent pintent = new Intent(MapView.this, PrintGridView.class);

                                    pintent.putExtra("HWNNO", intent.getString("HWNNO")); // putExtra hwnno를 넣은 값으로 HWNNO를 intent 객체로 전달한다.
                                    pintent.putExtra("CMNTY", cmnty);   pintent.putExtra("BR_GRP_G", br_grp_g);
                                    pintent.putExtra("XPOINT", pointX); pintent.putExtra("YPOINT", pointY);

                                    Log.d("TAG", "map view hwnno : " + intent.getString("HWNNO"));
                                    Log.d("TAG", "map view cmty : " + cmnty);
                                    Log.d("TAG", "map view BR_GRP_G : " + br_grp_g);
                                    Log.d("TAG", "map view xpoint : " + pointX);
                                    Log.d("TAG", "map view ypoint : " + pointY);

                                    //startActivityForResult(intent, 0);     // startActivity
                                    //if (cmnty == 0 || )
                                    startActivity(pintent);
                                }
                            });
                            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MapView.this, "취소 클릭", Toast.LENGTH_SHORT).show();;
                                }
                            });
                            dialog.show();

                            return false;
                        }
                    }) ;
                    // 이 marker가 클릭 되었을 때 이 marker의 클릭 이벤트를 받는 부분이다.
                    // 10개의 marker마다 각각 등록하자.

                    rectOptions.add(markers[i].latLng);
                }
                Polyline polyline = map.addPolyline(rectOptions);

                // 클릭이벤트
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    public void onMapClick(LatLng point) {
                        String text = "[단시간 클릭시 이벤트] latitude =" + point.latitude + ", longitude ="
                                + point.longitude;
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                                .show();
                    }
                });
                // click text 등록하기
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                    public void onMapLongClick(LatLng point) {
                        Bundle intent = getIntent().getExtras();

                        String text = "[" + hwnno + "]님 latitude =" + point.latitude + ", longitude ="
                                + point.longitude + "에 무슨 일이 있었나요?";
                        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        //        .show();
                        pointX = point.latitude;
                        pointY = point.longitude;

                        MELBOURNE = new LatLng(pointX, pointY);
                        Marker melbourne;

                        //Toast.makeText(MapView.this, "취소 클릭", Toast.LENGTH_SHORT).show();

                        melbourne = map.addMarker(new MarkerOptions()
                                .position(MELBOURNE)
                                .title("Melbourne")
                                .snippet("Population: 4,137,400"));

                        AlertDialog.Builder dialog =
                                new AlertDialog.Builder(MapView.this);
                        dialog.setMessage(text);
                        dialog.setPositiveButton("글 작성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MapView.this, "작성 페이지 이동 중", Toast.LENGTH_SHORT).show();
                                Bundle intent = getIntent().getExtras();
                                Intent pintent = new Intent(MapView.this, RecordWrite.class);

                                pintent.putExtra("HWNNO", intent.getString("HWNNO")); // putExtra hwnno를 넣은 값으로 HWNNO를 intent 객체로 전달한다.
                                pintent.putExtra("CMNTY", cmnty); pintent.putExtra("BR_GRP_G", br_grp_g);
                                pintent.putExtra("XPOINT", pointX); pintent.putExtra("YPOINT", pointY);

                                Log.d("TAG", "map hwnno : " + intent.getString("HWNNO"));
                                Log.d("TAG", "map cmty : " + cmnty);
                                Log.d("TAG", "map br_grp_g : " + br_grp_g);
                                Log.d("TAG", "map xpoint : " + pointX);
                                Log.d("TAG", "map ypoint : " + pointY);

                                startActivityForResult(pintent, 0);
                            }
                        });
                        dialog.setNegativeButton("작성 취소", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MapView.this, "취소 클릭", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.show();
                    }
                });
            }
        });



    }

    /*
     * Map 클릭시 터치 이벤트
     *
     * @see
     * com.google.android.gms.maps.GoogleMap.OnMapClickListener#onMapClick(com
     * .google.android.gms.maps.model.LatLng)
     */
    public void onMapClick(LatLng point) {

        // 현재 위도와 경도에서 화면 포인트를 알려준다
        Point screenPt = map.getProjection().toScreenLocation(point);

        // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
        LatLng latLng = map.getProjection().fromScreenLocation(screenPt);

        // Log.DEBUG(this, "좌표: 위도(" + point.latitude + "), 경도(" +
        // point.longitude + ")", Toast.LENGTH_LONG);
        // Log.DEBUG(this, "화면좌표: X(" + screenPt.x + "), Y(" + screenPt.y + ")",
        // Toast.LENGTH_LONG);

        Log.d("맵좌표", "좌표: 위도(" + String.valueOf(point.latitude) + "), 경도("
                + String.valueOf(point.longitude) + ")");
        Log.d("화면좌표", "화면좌표: X(" + String.valueOf(screenPt.x) + "), Y("
                + String.valueOf(screenPt.y) + ")");
    }

    // 리스트 데이타 출력하기
    public void readDatabase () {
        Log.i("RecordWrite", "readDatabase");
        MarkerDB markerdb = new MarkerDB (MapView.this);
        SQLiteDatabase database = markerdb.getReadableDatabase();
        Cursor cursor =
                database.rawQuery("select * from " + MarkerDB.TABLE_NAME, null);
        Log.i("count", cursor.getCount() + ""); // toString

//        String[] words = new String[cursor.getCount()];       //Marker 형식으로 한다.
//        for (int i=0; i < cursor.getCount(); i++) { // DB내용을 문자형배열로 변환
//            cursor.moveToNext();
//            words[i] = cursor.getString(1) + " (" + cursor.getString(2) + ")";  // 2번째 3번째 컬럼을 넣어라
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecordWrite.this,
//                android.R.layout.simple_list_item_1, words);
//        ListView listview = (ListView)findViewById(R.id.listview);
//        listview.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {     // 이미 실행중일때 인텐트 수신
        //processIntent()
        processIntent(intent);
        super.onNewIntent(intent);
    }
    private void processIntent(Intent intent) {
        if (intent != null){
            String hwnno = intent.getStringExtra("HWNNO");
            int cmnty = intent.getIntExtra("CMNTY", 0);
            int br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);
        }
        else {

        }
    }

    /*
     * @param requestCode
     * @param permissions
     * @param grantResults
     * 권한 승인, 권한 거부
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GPS 권한 승인!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS 권한 거부!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class GPSListener implements LocationListener {
        // 걸어다니면서 좌표가 바뀌는 것이다..
        @Override
        public void onLocationChanged(Location location) {  // 변경된 좌표 (위도와 경도가 들어가 있다)
            double latitude = location.getLatitude();   //위도
            double longitude = location.getLongitude(); //경도
            //내가 수신한 좌표 GPS가 알려준것
            TextView textView = (TextView)findViewById(R.id.location);
            textView.setText("내위치 : " + latitude + ", " + longitude);
            Toast.makeText(MapView.this, "위도:" + latitude + ",경도:"+longitude, Toast.LENGTH_SHORT).show();

            /* 이때 지도상의 위치로 가자 */
            // 위도와 경도를 가질 수 있는 클래스
            LatLng curPoint = new LatLng(latitude, longitude);

            if(map != null) {       // 현재 맵 객체가 null이 아니면 현재 위치로 맵 이동시키기
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                // 0에서부터 15까지 zoom 초기는 zoom level이 0 또는 1이다.
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {   //

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    }

    public void startLocationService (View view) {
        // 위치를 알아보는 방법이 두가지가 있는데, APP을 실행하자마자 좌표를 알 수 있는 방법은 없다.
        // 실내에 있다가 실외로 나가면 좌표를 바로 알 수 없다.
        // 이동하다가 기록된 좌표가 있다면 그 건물을 나왔을 때 입구부터 시작 Location을 얻어온다.
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissonCheck == PackageManager.PERMISSION_GRANTED) {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null) {
                TextView textView = (TextView) findViewById(R.id.location);
                textView.setText("startLocationService 내 위치 :" + location.getLatitude() + "," + location.getLongitude());
                Toast.makeText(this, "Last Known Location 위도 : " + location.getLatitude() +", 경도:" + location.getLongitude()
                        , Toast.LENGTH_SHORT).show();

                 /* 이때 지도상의 위치로 가자 */
                // 위도와 경도를 가질 수 있는 클래스
                LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

                if(map != null) {       // 현재 맵 객체가 null이 아니면 현재 위치로 맵 이동시키기
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
                    // 0에서부터 15까지 zoom 초기는 zoom level이 0 또는 1이다.
                }
            }
            GPSListener gpsListener = new GPSListener();
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, gpsListener);
        }
    }

    /*
     * @param view 현재 위치로 가는 이벤트
     */
    public void hButtonClicked(View view) {
        map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(37.555744, 126.970431)   // 위도, 경도
        ));
    }

    /*
     * @param view 시간을 변경한다. 위성, 거리 등 뷰 변경
     */
    public void vButtonClicked(View view) {
        if (map.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        else if (map.getMapType() == GoogleMap.MAP_TYPE_HYBRID)
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        else if (map.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    /*
     * @param view 자신이 다녀간 곳들을 이동한다. (x,y)의 사진을 볼 수 있다.
     */
    public void tButtonClicked(View view) {
        if (currentPos >= markers.length) {
            currentPos = 0;
        } else {
            currentPos ++;
        }
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(markers[currentPos].latLng, 19));
        }

    }
    /*
     * @param view zoom
     */
    public void pButtonClicked(View view) {
        //map.animateCamera(CameraUpdateFactory.zoomTo(18));
        idx ++;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), idx));
    }
    /*
     * @param view out
     */
    public void mButtonClicked(View view) {
        idx --;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), idx));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 0) {
            // finish 또는 back button
            if(resultCode == RESULT_OK)
            {
                String result = data.getStringExtra("Result");
                readDatabase();
            }
        }
    }
}

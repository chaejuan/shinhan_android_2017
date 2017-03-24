package com.shinhan.shinhanband;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapView extends AppCompatActivity {
    public static final String TAG = "MapView";
    //SupportMapFragment mapFragment;
    private String hwnno;
    private int cmnty;
    private int br_grp_g;

    SupportMapFragment mapFragment;
    GoogleMap map;
    int currentPos;

    class MyMarker {        // Marker 정보 저장용 클래스
        String name;    LatLng latLng;
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

        if (intent != null){
            hwnno = intent.getStringExtra("HWNNO");
            cmnty = intent.getIntExtra("CMNTY", 0);
            br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);

            Toast.makeText(MapView.this, "hwnno:" + hwnno +", cmnty : " + cmnty + ", br_grp_g : " + br_grp_g,
                    Toast.LENGTH_LONG).show();
        }

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
                    MarkerOptions marker = new MarkerOptions();
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
                            }

                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);       // 위성으로 변경 후 당기는 것

                            //map.animateCamera(  // 위성사진으로 확대
                            //        CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));

                            //map.animateCamera(CameraUpdateFactory.zoomTo(18));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 19));

                            ImageView imageView = new ImageView(MapView.this);
                            imageView.setImageResource(R.mipmap.ic_launcher);
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
                            dialog.setPositiveButton("보기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(MapView.this, "보기 클릭", Toast.LENGTH_SHORT).show();
                                    Bundle intent = getIntent().getExtras();
                                    Intent pintent = new Intent(MapView.this, ImageListActivity.class);

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
            }
        });

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

            Toast.makeText(MapView.this, "hwnno:" + hwnno +", cmnty : " + cmnty + ", br_grp_g : " + br_grp_g,
                    Toast.LENGTH_LONG).show();
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
}

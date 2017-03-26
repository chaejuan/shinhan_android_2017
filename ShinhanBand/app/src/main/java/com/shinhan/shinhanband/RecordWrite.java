package com.shinhan.shinhanband;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shinhan.servlet.MarkerDB;
import com.shinhan.servlet.markerDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class RecordWrite extends AppCompatActivity {
    public static String IMAGE_FILE = "";
    public static String imagePath = "";
    EditText writeText = null;  Button cameraBtn = null; EditText hashText;
    Button upBtn = null;    Button galBtn = null;
    private String hwnno;   private int cmnty;  private int br_grp_g;
    private double xPoint;  private double yPoint; int location;
    private String imgkey; private String ctnt;
    private String filename;
    markerDto dto;

    private ArrayList<String> hashTags = new ArrayList<String>();
    private String hashTag;
    TextView pointText;  TextView locationText;
    ImageView iv = null;
    //Bundle intent;
    Intent pintent;         Intent intent;

    String[] locationItems = {"영업부", "하노이", "시드니", "폴란드", "미얀마", "충칭분행",
            "멕시코", "나고야", "고베지점", "캐나다", "우주베키스탄", "달라스", "뉴델리",
            "홍콩", "맨하탄", "캘리포니아", "동경", "버지니아"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_write);
        readDatabase();
        //readDatabase(); // DB 내용 읽기
        setup();

        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissonCheck != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                Toast.makeText(this, "카메라 연동 권한 필요합니다.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, 1);
            }
        }


        locationText = (TextView)findViewById(R.id.locationText);

        Spinner spinner = (Spinner) findViewById(R.id.location_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, locationItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // 아이템 선택 시 이벤트
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationText.setText(locationItems[position]);
                location = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                locationText.setText("");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "카메라 권한 승인", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "카메라 권한 거부", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setup() {
        Log.d("TAG", "record write setup..");
        //writeText = (EditText)findViewById(R.id.writeText);
        writeText = (EditText) findViewById(R.id.editText);
        cameraBtn = (Button) findViewById(R.id.cameraButton);
        upBtn = (Button) findViewById(R.id.uploadButton);
        galBtn = (Button) findViewById(R.id.galleryButton);
        iv = (ImageView) findViewById(R.id.iv);
        pointText = (TextView)findViewById(R.id.pointText);
        hashText = (EditText)findViewById(R.id.hashText);


        intent = getIntent();    // 처음 실행될 때 인텐트 수신
        processIntent(intent);

        if (intent != null) {
            pintent = new Intent(RecordWrite.this, RecordWrite.class);
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
            hwnno = intent.getStringExtra("HWNNO");
            cmnty = intent.getIntExtra("CMNTY", 0);
            br_grp_g =  intent.getIntExtra("BR_GRP_G", 0);
            xPoint = intent.getDoubleExtra("XPOINT", 0);
            yPoint = intent.getDoubleExtra("YPOINT", 0);

            long x = (long)xPoint;
            long y = (long)yPoint;

            Log.d("TAG", "hwnno : " + hwnno);
            Log.d("TAG", "cmty : " + cmnty);
            Log.d("TAG", "br_grp_g" + br_grp_g);
            Log.d("TAG", "xPoint : " + xPoint);
            Log.d("TAG", "yPoint : " + yPoint);

            String pointXY = "좌표 : ( " + x + ", " + " " + y + ")";
            pointText.setText(pointXY);
        }
        else {

        }
    }

    /*
     * @param upload
     */
    public void onUploadButtonClicked(View view) {
        Uri mImageCaptureUri;
//        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
//        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
//        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        savePictureFile();
        Log.d("TAG", "SavePicture 완료");
//        Date f = new Date();
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dImagkey = date.format(f);       /* from date to string */
//        String keyResult;
//        String timeStamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
//        keyResult = dImagkey + hwnno + String.format("%04d", cmnty);  /* img key 조립 (24) */
//        String file_name = timeStamp + hwnno + String.format("02d", cmnty) + ".jpg";  /* 22자리 */
        Log.d("TAG", "imgkey bitmap convert before : " + imgkey);
        String file_path = "/sdcard/Images/" + imgkey + ".jpg";
//
        Log.d("LOG", "FILE_PATH : " + file_path);
//        Log.d("LOG", "FILE_NAME : " + file_name);
//        Log.d("LOG", "IMG_KEY : " + keyResult);

        File file = new File(file_path);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ImageView imageView = (ImageView) findViewById(R.id.iv);
            imageView.setImageBitmap(bitmap);
            Log.d("TAG", "FILE 저장후 비트맵 이미지 저장");
        }

        /*-------------------------------------------------------------------------------
         * imgkey, location, latitude, longditude, ctnt, tag_ctnt@
         * dbio insert
         *------------------------------------------------------------------------------- */

        ctnt = writeText.getText().toString();       /* 글 내용 */
        //@param private ArrayList<String> hashTags = new ArrayList<String>();
        //@param private String hashTag;
        hashTag = hashText.getText().toString();        // hash태그 전체를 내려받는다.
        Log.d("D", "HASH TAG CTNT : " + hashTag);
        hashTokenizer(hashTag);

        Log.d("TAG", "hwnno : " + hwnno); Log.d("TAG", "cmty : " + cmnty); Log.d("TAG", "imgkey : " + imgkey);
        Log.d("TAG", "br_grp_g" + br_grp_g); Log.d("TAG", "xPoint : " + xPoint); Log.d("TAG", "yPoint : " + yPoint);
        Log.d("TAG", "ctnt : " + ctnt); Log.d("TAG", "location : " + location);
        for (int idx=0; idx < hashTags.size(); idx++)  Log.d("TAG", "Arraylist ; " + hashTags.get(idx));
        /* image dto insert .. */
        /*
        public markerDto(String grpco_c, String hwnno, String img_key, String location,
        int mas_s, int cmnty, int br_grp_g, int latitude, int longditude,
        String ctnt, ArrayList<String> hashTag, String drdt, String drHwnno,
                String lstdt, String lstHwnno, int resId)
        */
        dto = new markerDto ("S001", hwnno, imgkey, location, 1, cmnty, br_grp_g, (int)xPoint, (int)yPoint, ctnt, hashTags);
        //writeDatabase(dto);

        try {
            Thread.sleep(3000);
        }catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(RecordWrite.this, imgkey + " 등록 후 이전 페이지로 돌아갑니다.",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("Result", 1);
        setResult(RESULT_OK, intent);
        finish();                   /* back button 과 같은 것이다. 다시 종료하고 돌아갈때는 */
    }

    // 저장하기
    public void writeDatabase(markerDto dto) {
        MarkerDB markerdb = new MarkerDB(RecordWrite.this); // DATABASE 파일이 없으면 생성된다.
        SQLiteDatabase database = markerdb.getWritableDatabase();  // 쓰기용 SQLiteDatabase 열기

        ContentValues values = new ContentValues(); // content vlues에 담기
        values.put("grpco_c", "S001");values.put("hwnno", hwnno);values.put("img_key", imgkey);values.put("mas_s", 1);
        values.put("location", location);values.put("cmnty", cmnty);values.put("br_grp_g", br_grp_g);values.put("latitude", xPoint);
        values.put("longditude", yPoint);values.put("ctnt", ctnt);
        Log.d("TAG", "db insert ctnt : " + ctnt);
        for(int idx = 0; idx < hashTags.size(); idx++) {
            if(idx==0) {values.put("tag_ctnt1", hashTags.get(idx)); Log.d("TAG", "tag_ctnt1 : " + hashTags.get(idx));}
            if(idx==1) {values.put("tag_ctnt1", hashTags.get(idx)); Log.d("TAG", "tag_ctnt2 : " + hashTags.get(idx));}
            if(idx==2) {values.put("tag_ctnt1", hashTags.get(idx)); Log.d("TAG", "tag_ctnt3 : " + hashTags.get(idx));}
            if(idx==3) {values.put("tag_ctnt1", hashTags.get(idx)); Log.d("TAG", "tag_ctnt4 : " + hashTags.get(idx));}
            if(idx==4) {values.put("tag_ctnt1", hashTags.get(idx)); Log.d("TAG", "tag_ctnt5 : " + hashTags.get(idx));}
        }
        Date d = new Date();
        values.put("drdt", d.toString()); values.put("dr_hwnno", hwnno);
        values.put("lstdt", d.toString());values.put("lst_hwnno", hwnno);

        database.insert(MarkerDB.TABLE_NAME, null, values);   // db에 데이타 insert

        Toast.makeText(RecordWrite.this, "이미지 " + imgkey + " 등록 성공",
                Toast.LENGTH_SHORT).show();
    }

    // 리스트 데이타 출력하기
    public void readDatabase () {
        Log.i("RecordWrite", "readDatabase");
        MarkerDB markerdb = new MarkerDB (RecordWrite.this);
        SQLiteDatabase database = markerdb.getReadableDatabase();
        Cursor cursor =
                database.rawQuery("select * from " + MarkerDB.TABLE_NAME, null);
        Log.i("count", cursor.getCount() + ""); // toString

        String[] words = new String[cursor.getCount()];
//        for (int i=0; i < cursor.getCount(); i++) { // DB내용을 문자형배열로 변환
//            cursor.moveToNext();
//            words[i] = cursor.getString(1) + " (" + cursor.getString(2) + ")";  // 2번째 3번째 컬럼을 넣어라
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecordWrite.this,
//                android.R.layout.simple_list_item_1, words);
//        ListView listview = (ListView)findViewById(R.id.listview);
//        listview.setAdapter(adapter);
    }

    /*
     * @hash tag로 분리한다.
     */
    private void hashTokenizer(String hashTag) {
        StringTokenizer st = new StringTokenizer(hashTag, "#");
        int count = st.countTokens();
        Log.d("TAG", "hash tag count : " + count);

        while(st.hasMoreTokens()) {
            String tmp = st.nextToken();
            hashTags.add(tmp);       /* hashTags items addition */
            Log.d("TAG", "# tag......" + tmp);
        }
    }

    public void onCameraGalleryButtonClicked(View view) {
        /*
         * 갤러리에서 이미지를 취득하는 방법은 1. ACTION_PICK
         * 2. ACTION_GET_CONTENT 2가지 방법이 존재한다. 하지만 Android 4.4 에서 ACTION_GET_CONTENT가 Storage access framework에 통합되어
         * 4.4에서 추가된 Storage access framework에서 제공하는 UI가 나타나고 여기에서 사용할 이미지를 클릭해도 해당 미지를 가지고 올 수 없게 된다.
         * 또한 이 현상은 해당앱의 타겟버전이 API 19보다 낮아도 발생하는 현상이므로 꼭 체크해야한다.
         *
         */
    }

    public void onCameraButtonClicked(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 임시로 사용할 파일의 경로를 생성
        startActivityForResult(intent, 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            iv.setImageURI(data.getData());

            BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            Log.d("TAG", "hwnno : ");
            Log.i("Creating cw", "");
            ContextWrapper cw = new ContextWrapper(this.getApplicationContext());
            Log.i("Creating dir", "Creating");
            File directory = cw.getDir("ImagesDir", Context.MODE_PRIVATE);
            Log.i("Created dir" + directory, "");

            Uri uri = createImageFile();
            filename = uri + ".jpg";

            File mypath = new File(directory, "filename");
            Log.i("path is" + mypath, "");

            FileOutputStream fos = null;
            try {
                Log.i("creating fos", "");
                fos = new FileOutputStream(mypath);
                Log.i("Compressing bitmap", "");
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                    Log.i("fos closed", "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Uri createImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        imgkey = timeStamp + hwnno + String.format("%02d", br_grp_g);
        filename = timeStamp + hwnno + String.format("%02d", br_grp_g) + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("TAG", "저장된 파일명 : " + filename);
        Uri uri = Uri.fromFile(new File(storageDir, filename));
        return uri;
    }

    private File savePictureFile()
    {
        String saveKey = imgkey + ".jpg";
        Log.i("savePictureFile", "");
        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissonCheck == PackageManager.PERMISSION_GRANTED && cPermissionCheck == PackageManager.PERMISSION_GRANTED) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
            Log.d("TAG", "savePictureFile : " + saveKey);

            // 사진 파일이 저장될 장소를 구한다.
            File pictureStorage = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "MYAPP/");

            if (!pictureStorage.exists()) {
                /** * mkdir은 폴더를 하나만 만들고, * mkdirs는 경로상에 존재하는 모든 폴더를 만들어준다. */
                pictureStorage.mkdirs();
            }

            try {
                File file = File.createTempFile(imgkey, ".jpg", pictureStorage);
                // 사진 파일의 절대경로를 얻어온다.
                // 나중에 ImageView에 보여줄 때 필요하다.
                // ImageView에 보여주기위해 사진파일의 절대 경로를 얻어온다.
                imagePath = file.getAbsolutePath();
                Log.d("TAG", "imagePath 확인 : " + imagePath);    // 생성된 파일의 실제경로...
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                File f = new File(imagePath);
                Uri contentUri = Uri.fromFile(f);
                Log.d("TAG", "contentUri : " + contentUri);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                Toast.makeText(RecordWrite.this, imagePath + " save success..",
                        Toast.LENGTH_SHORT).show();

                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }   // 사용자 권한 거부 케이스
        else {}

        return null;
    }


}

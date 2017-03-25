package com.shinhan.shinhanband;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordWrite extends AppCompatActivity {
    public static String IMAGE_FILE = "";
    public static String imagePath = "";
    EditText writeText = null;  Button cameraBtn = null;
    Button upBtn = null;    Button galBtn = null;
    private String hwnno;   private int cmnty;  private int br_grp_g;
    private double xPoint;  private double yPoint;

    SimpleDateFormat imgkey;            /* img key */

    TextView pointText;  TextView locationText;
    ImageView iv = null;
    //Bundle intent;
    Intent pintent;         Intent intent;

    String[] locationItems = {"영업부", "하노이", "시드니", "폴란드", "미얀마", "충칭분행",
            "멕시코", "나고야", "고베지점", "캐나다", "우주베키스탄", "달라스", "뉴델리",
            "홍콩", "맨하탄", "캘리포니아", "동경", "버지니아"};
    int location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_write);

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
        //writeText = (EditText)findViewById(R.id.writeText);
        writeText = (EditText) findViewById(R.id.editText);
        cameraBtn = (Button) findViewById(R.id.cameraButton);
        upBtn = (Button) findViewById(R.id.uploadButton);
        galBtn = (Button) findViewById(R.id.galleryButton);
        iv = (ImageView) findViewById(R.id.iv);
        pointText = (TextView)findViewById(R.id.pointText);

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
        imgkey = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");       /* imgkey 채번 */
        String file_name = imgkey + hwnno + String.format("%04d", cmnty) + ".jpg";
        String file_path = "/sdcard/Images/" + file_name;
        File file = new File(file_path);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);
        }
        /*-------------------------------------------------------------------------------
         * imgkey, location, latitude, longditude, ctnt, tag_ctnt@
         * dbio insert
         *------------------------------------------------------------------------------- */
        String ctnt = writeText.getText().toString();
        Log.d("TAG", "" + ctnt);
    }

    public void onCameraGalleryButtonClicked(View view) {

    }

    public void onCameraButtonClicked(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 임시로 사용할 파일의 경로를 생성
        startActivityForResult(intent, 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        iv.setImageURI(data.getData());

        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        Log.d("TAG", "hwnno : " );
        Log.i("Creating cw", "");
        ContextWrapper cw = new ContextWrapper(this.getApplicationContext());
        Log.i("Creating dir", "Creating");
        File directory = cw.getDir("ImagesDir", Context.MODE_PRIVATE);
        Log.i("Created dir" + directory, "");

        Uri uri = createImageFile();
        String filename = uri + ".jpg";

        File mypath=new File(directory,"filename");
        Log.i("path is"+mypath, "");

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

    private Uri createImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        Uri uri = Uri.fromFile(new File(storageDir, imageFileName));
        return uri;
    }

    private File savePictureFile()
    {
        Log.i("savePictureFile", "");
        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissonCheck == PackageManager.PERMISSION_GRANTED && cPermissionCheck == PackageManager.PERMISSION_GRANTED) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
            String fileName = "IMG" + timeStamp;

            // 사진 파일이 저장될 장소를 구한다.
            File pictureStorage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MYAPP/");

            if (!pictureStorage.exists()) {
                pictureStorage.mkdirs();
            }

            try {
                File file = File.createTempFile(fileName, ".jpg", pictureStorage);
                // 사진 파일의 절대경로를 얻어온다.
                // 나중에 ImageView에 보여줄 때 필요하다.
                imagePath = file.getAbsolutePath();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                File f = new File(imagePath);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

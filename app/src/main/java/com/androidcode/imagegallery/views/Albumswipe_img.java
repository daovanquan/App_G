package com.androidcode.imagegallery.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.androidcode.imagegallery.models.ImageGallery;
import com.androidcode.imagegallery.viewmodels.MoveFile;
import com.androidcode.imagegallery.viewmodels.OnSwipeTouchListener;
import com.androidcode.imagegallery.R;
import com.androidcode.imagegallery.viewmodels.pictureFacer;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Albumswipe_img extends AppCompatActivity implements Custom_dialog.Custom_DialogInterface {

    ArrayList<pictureFacer> pics;
    ImageView imgV;
    private int position;
    private String linkpath,tenpath;
    private Button btnmove;
    private Button btncopy,btnshare;
    private String temppath;
    private String pathnewfold;
    public void openDialog(View view)
    {
        Custom_dialog custom_dialog = new Custom_dialog();
        custom_dialog.show(getSupportFragmentManager(),null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumswipe_img);

        btnmove = findViewById(R.id.btn_move_pic_album);
        btncopy = findViewById(R.id.btncopy_pic_album);
        btnshare = findViewById(R.id.btn_share);
        imgV = findViewById(R.id.album_swipe_img);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("posi");
            linkpath = bundle.getString("path");
            tenpath = bundle.getString("Tenfold");

        }
           char []s = linkpath.toCharArray();


        temppath = String.copyValueOf(s,0,linkpath.length()-tenpath.length()-1);
        //System.out.println(position);
        pics = ImageGallery.getAllImagesByFolder(Albumswipe_img.this,linkpath);
        pictureFacer facer = pics.get(position);
        String uri_img = facer.getPicturePath();
       // System.out.println("Uri chinh la " + uri_img);
        Glide.with(this).load(uri_img).into(imgV);
        //pathnewfold = linkpath;
        File file = new File(uri_img);
        btncopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
                if(pathnewfold!=null) {
                    System.out.println("Path la + " + pathnewfold);
                    File fileC = new File(pathnewfold);
                    try {
                        MoveFile.copyOrMoveFile(file, fileC, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
                if(pathnewfold!=null) {
                    System.out.println("Path la + " + pathnewfold);
                    File fileC = new File(pathnewfold);
                    try {
                        MoveFile.copyOrMoveFile(file, fileC, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image();
            }
        });

        imgV.setOnTouchListener(new OnSwipeTouchListener(Albumswipe_img.this) {

            public void onSwipeRight() {
                if (position >= 0) {
                    position = position - 1;

                    pictureFacer facer = pics.get(position);
                    imgV.setImageURI(Uri.parse(facer.getPicturePath()));

                    Toast.makeText(Albumswipe_img.this, "Đường dẫn :" + facer.getPicturePath() + "\n" +
                            "Kích cỡ :" + facer.getPictureSize() + "\n" + "Tên ảnh :" + facer.getPictureName(), Toast.LENGTH_LONG).show();
                }
            }

            public void onSwipeLeft() {
                if (position <= pics.size()) {
                    position = position + 1;

                    pictureFacer facer = pics.get(position);
                    imgV.setImageURI(Uri.parse(facer.getPicturePath()));
                    Toast.makeText(Albumswipe_img.this, "Đường dẫn :" + facer.getPicturePath() + "\n" +
                            "Kích cỡ :" + facer.getPictureSize() + "\n" + "Tên ảnh :" + facer.getPictureName(), Toast.LENGTH_LONG).show();

                }
            }

        });
    }

    private void image()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        BitmapDrawable drawable = (BitmapDrawable) imgV.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File f = new File(getExternalCacheDir()+ "/" + getResources().
                getString(R.string.app_name) + "png");

        Intent intentshare;

        try {

            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

            outputStream.flush();
            outputStream.close();

            intentshare = new Intent(Intent.ACTION_SEND);

            intentshare.setType("image/*");
            intentshare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            intentshare.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        startActivity(Intent.createChooser(intentshare,"share image"));
    }


    @Override
    public void applyText(String text) {
        pathnewfold = temppath + text;
    }
}

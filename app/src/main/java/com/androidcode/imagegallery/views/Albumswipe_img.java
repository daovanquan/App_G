package com.androidcode.imagegallery.views;

import android.net.Uri;
import android.os.Bundle;

import com.androidcode.imagegallery.models.ImageGallery;
import com.androidcode.imagegallery.viewmodels.MoveFile;
import com.androidcode.imagegallery.viewmodels.OnSwipeTouchListener;
import com.androidcode.imagegallery.R;
import com.androidcode.imagegallery.viewmodels.pictureFacer;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Albumswipe_img extends AppCompatActivity implements Custom_dialog.Custom_DialogInterface {

    ArrayList<pictureFacer> pics;
    ImageView imgV;
    private int position;
    private String linkpath,tenpath;
    private Button btnmove;
    private Button btncopy;
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

    @Override
    public void applyText(String text) {
        pathnewfold = temppath + text;
    }
}

package com.androidcode.imagegallery;

import android.net.Uri;
import android.os.Bundle;

import com.androidcode.imagegallery.utils.pictureFacer;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;

public class Albumswipe_img extends AppCompatActivity {

    ArrayList<pictureFacer> pics;
    ImageView imgV;
    private int position;
    private String linkpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumswipe_img);

        imgV = findViewById(R.id.album_swipe_img);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("posi");
            linkpath = bundle.getString("path");
        }
       // System.out.println(linkpath);
        //System.out.println(position);
        pics = ImageGallery.getAllImagesByFolder(Albumswipe_img.this,linkpath);
        pictureFacer facer = pics.get(position);
        String uri_img = facer.getPicturePath();
       // System.out.println("Uri chinh la " + uri_img);
        Glide.with(this).load(uri_img).into(imgV);
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
}

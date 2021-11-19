package com.androidcode.imagegallery.views;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidcode.imagegallery.models.ImageGallery;
import com.androidcode.imagegallery.viewmodels.MoveFile;
import com.androidcode.imagegallery.viewmodels.OnSwipeTouchListener;
import com.androidcode.imagegallery.R;

import java.io.File;
import java.util.List;

public class FullScreen extends AppCompatActivity {

    int position;
    ImageView imageV;
    String imageLink;
    //private Button btnmove_copy;
    private List<String> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        imageV = findViewById(R.id.image_full);
       // btnmove_copy = findViewById(R.id.btn_share_pic);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("pos");
            imageLink = bundle.getString("image");
        }
        //System.out.println("path la " +imageLink);
        images = ImageGallery.listOfImage(this);
        //imageV.setImageBitmap(BitmapFactory.decodeFile(imageLink));

        //Glide.with(this).load(imageLink).into(imageV);
        imageV.setImageURI(Uri.parse(imageLink));

        /*btnmove_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(imageLink);
                Toast.makeText(FullScreen.this, "Da click button", Toast.LENGTH_SHORT).show();
                //MoveFile.copyOrMoveFile();

            }
        });*/

        imageV.setOnTouchListener(new OnSwipeTouchListener(FullScreen.this) {

            public void onSwipeRight() {
                if (position >= 0) {
                    position = position - 1;

                    imageV.setImageURI(Uri.parse(images.get(position)));

                    Toast.makeText(FullScreen.this, images.get(position), Toast.LENGTH_SHORT).show();
                }
            }
            public void onSwipeLeft() {
                if (position <= images.size()) {
                    position = position + 1;

                    imageV.setImageURI(Uri.parse(images.get(position)));
                    Toast.makeText(FullScreen.this, images.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
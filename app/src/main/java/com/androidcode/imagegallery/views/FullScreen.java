package com.androidcode.imagegallery.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
import java.io.FileOutputStream;
import java.util.List;

public class FullScreen extends AppCompatActivity {

    int position;
    ImageView imageV;
    String imageLink;
    private Button btnshare;
    private List<String> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        imageV = findViewById(R.id.image_full);
        btnshare = findViewById(R.id.btn_share_pic);
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

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image();
            }
        });



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
    private void image()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        BitmapDrawable drawable = (BitmapDrawable) imageV.getDrawable();
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
}
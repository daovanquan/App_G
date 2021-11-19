package com.androidcode.imagegallery.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.androidcode.imagegallery.models.ImageGallery;
import com.androidcode.imagegallery.R;
import com.androidcode.imagegallery.viewmodels.MarginDecoration;
import com.androidcode.imagegallery.viewmodels.PicHolder;
import com.androidcode.imagegallery.viewmodels.itemClickListener;
import com.androidcode.imagegallery.viewmodels.pictureFacer;
import com.androidcode.imagegallery.viewmodels.picture_Adapter;

import java.io.File;
import java.util.ArrayList;



public class ImageDisplay extends AppCompatActivity implements itemClickListener {

    RecyclerView imageRecycler;
    ArrayList<pictureFacer> allpictures;
    ProgressBar load;
    String foldePath,foldName;
    TextView folderName;

    picture_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        folderName = findViewById(R.id.foldername);
        foldName = getIntent().getStringExtra("folderName");
        folderName.setText(foldName);
        foldePath =  getIntent().getStringExtra("folderPath");
        allpictures = new ArrayList<>();
        imageRecycler = findViewById(R.id.recycler);
        imageRecycler.addItemDecoration(new MarginDecoration(this));
        imageRecycler.hasFixedSize();
        load = findViewById(R.id.loader);

        if(allpictures.isEmpty()){
            load.setVisibility(View.VISIBLE);
            allpictures = ImageGallery.getAllImagesByFolder(this,foldePath);
            adapter = new picture_Adapter(allpictures,ImageDisplay.this,this);
            imageRecycler.setAdapter(adapter);
            load.setVisibility(View.GONE);
        }else{

        }
    }

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {
        //pictureBrowserFragment browser = pictureBrowserFragment.newInstance(pics,position,ImageDisplay.this);
        pictureFacer facer = pics.get(position);
        Toast.makeText(this, "Đường dẫn :" + facer.getPicturePath() +"\n" +
                "Kích cỡ :" + facer.getPictureSize() + "\n" + " Tên ảnh :" + facer.getPictureName()   , Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Albumswipe_img.class);
        intent.putExtra("posi",position);
        intent.putExtra("path",foldePath);
        intent.putExtra("Tenfold",foldName);
        startActivity(intent);
    }

    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {

    }

    @Override
    public void onPicClickLong(int position) {
        //Toast.makeText(this, "Da click dai tai anh", Toast.LENGTH_SHORT).show();
        Xacnhanxoa(position);
    }

    private void Xacnhanxoa(int position){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Bạn có muốn xoá thư mục này hay không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pictureFacer facer = allpictures.get(position);
                File file = new File(facer.getPicturePath());
                file.delete();
                allpictures.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create().show();
    }
    /**
     * This Method gets all the images in the folder paths passed as a String to the method and returns
     * and ArrayList of pictureFacer a custom object that holds data of a given image
     * @param path a String corresponding to a folder path on the device external storage
     */


}

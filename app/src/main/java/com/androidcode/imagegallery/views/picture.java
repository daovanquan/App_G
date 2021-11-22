package com.androidcode.imagegallery.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidcode.imagegallery.viewmodels.GalleryAdapter;
import com.androidcode.imagegallery.models.ImageGallery;
import com.androidcode.imagegallery.R;
import com.androidcode.imagegallery.models.pictureFacer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class picture extends Fragment {



    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    private List<String> images;

    ArrayList<pictureFacer> pics;
    private View mview;
    private MainActivity mMainActivity;
    private static final int MY_READ_PERMISSION_CODE = 1;

    public picture() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_picture, container, false);

        mMainActivity = (MainActivity) getActivity();

        //gallery_number = mview.findViewById(R.id.gallery_number);
        recyclerView = mview.findViewById(R.id.recycleview_gallery_images);

        if(ContextCompat.checkSelfPermission(mMainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(mMainActivity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_READ_PERMISSION_CODE);}

        if(ContextCompat.checkSelfPermission(mMainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(mMainActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_READ_PERMISSION_CODE);
        }
        loadImage();
        return mview;
    }

    private void loadImage()
    {
        mMainActivity = (MainActivity) getActivity();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mMainActivity,4));
        images = ImageGallery.listOfImage(mMainActivity);
        galleryAdapter = new GalleryAdapter(mMainActivity, images, new GalleryAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(int position) {
                //Toast.makeText(mMainActivity, ""+position, Toast.LENGTH_SHORT).show();
                Toast.makeText(mMainActivity, String.valueOf(images.get(position)), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mMainActivity, FullScreen.class);
                intent.putExtra("pos",position);
                intent.putExtra("image",String.valueOf(images.get(position)));
                startActivity(intent);
            }

            @Override
            public void OnItemClickLongListener(View view, int position) {
                //Toast.makeText(mMainActivity, "Da nghe duoc click dai", Toast.LENGTH_SHORT).show();
                Xacnhanxoa(position);
            }

        });

        recyclerView.setAdapter(galleryAdapter);
        // gallery_number.setText("Photos (" + images.size() +")");
    }
    private void Xacnhanxoa(int position){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mMainActivity);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Bạn có muốn xoá thư mục này hay không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMainActivity.deleteDatabase(images.get(position));
                File file = new File(images.get(position));
                file.delete();
                images.remove(position);
                galleryAdapter.notifyDataSetChanged();

            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create().show();
    }
}
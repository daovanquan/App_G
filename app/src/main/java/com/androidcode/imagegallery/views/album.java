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
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcode.imagegallery.models.ImageGallery;
import com.androidcode.imagegallery.R;
import com.androidcode.imagegallery.viewmodels.MarginDecoration;
import com.androidcode.imagegallery.viewmodels.PicHolder;
import com.androidcode.imagegallery.models.imageFolder;
import com.androidcode.imagegallery.viewmodels.itemClickListener;
import com.androidcode.imagegallery.models.pictureFacer;
import com.androidcode.imagegallery.viewmodels.pictureFolderAdapter;

import java.io.File;
import java.util.ArrayList;

public class album extends Fragment implements itemClickListener {
    RecyclerView folderRecycler;
    TextView empty;
    ArrayList<imageFolder> folds;
    ImageButton imgbtn;
    View mview;
    MainActivity mainActivity;
    RecyclerView.Adapter folderAdapter;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_album, container, false);

        mainActivity = (MainActivity) getActivity();
        if(ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        if(ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        imgbtn = mview.findViewById(R.id.img_button_Album);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mainActivity, "Da click button", Toast.LENGTH_SHORT).show();

            }
        });

        folderRecycler = mview.findViewById(R.id.folderRecycler);
        folderRecycler.addItemDecoration(new MarginDecoration(mainActivity));
        folderRecycler.hasFixedSize();

        folds = ImageGallery.getPicturePaths(mainActivity);

        if(folds.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        }else{
            folderAdapter = new pictureFolderAdapter(folds,mainActivity,this);
            folderRecycler.setAdapter(folderAdapter);
        }

        return mview;
    }

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {
        Intent move = new Intent(mainActivity, ImageDisplay.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName",folderName);

        Toast.makeText(mainActivity, pictureFolderPath, Toast.LENGTH_SHORT).show();
        //move.putExtra("recyclerItemSize",getCardsOptimalWidth(4));
        startActivity(move);
    }

    @Override
    public void onPicClickLong(int position ) {
       // Toast.makeText(mainActivity, "Da click dai", Toast.LENGTH_SHORT).show();
        Xacnhanxoa(position);
    }

    private void Xacnhanxoa(int position){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainActivity);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Bạn có muốn xoá thư mục này hay không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //mMainActivity.deleteDatabase(images.get(position));
                File file = new File(folds.get(position).getPath());
                file.delete();
                folds.remove(position);
                folderAdapter.notifyDataSetChanged();

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
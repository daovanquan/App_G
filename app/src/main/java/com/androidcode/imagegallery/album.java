package com.androidcode.imagegallery;

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
import android.widget.TextView;

import com.androidcode.imagegallery.utils.MarginDecoration;
import com.androidcode.imagegallery.utils.PicHolder;
import com.androidcode.imagegallery.utils.imageFolder;
import com.androidcode.imagegallery.utils.itemClickListener;
import com.androidcode.imagegallery.utils.pictureFacer;
import com.androidcode.imagegallery.utils.pictureFolderAdapter;

import java.util.ArrayList;

public class album extends Fragment implements itemClickListener {
    RecyclerView folderRecycler;
    TextView empty;
    ArrayList<imageFolder> folds;
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

        //changeStatusBarColor();
        return mview;
    }

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {
        Intent move = new Intent(mainActivity,ImageDisplay.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName",folderName);

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
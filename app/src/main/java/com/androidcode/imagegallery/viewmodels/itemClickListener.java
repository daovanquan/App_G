package com.androidcode.imagegallery.viewmodels;

import com.androidcode.imagegallery.models.pictureFacer;

import java.util.ArrayList;


public interface itemClickListener {

    /**
     * Called when a picture is clicked
     * @param holder The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     */
    void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics);
    void onPicClicked(String pictureFolderPath,String folderName);
    void onPicClickLong(int position);
}

package com.androidcode.imagegallery.viewmodels;


public interface imageIndicatorListener {

    /**
     *
     * @param ImagePosition position of an item in the RecyclerView Adapter
     */
    void onImageIndicatorClicked(int ImagePosition);
}

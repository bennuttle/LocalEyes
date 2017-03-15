package com.example.android.camera2basic;

import android.os.AsyncTask;

/**
 * Created by NUB1BUV on 3/15/2017.
 */

public class PictureTask extends AsyncTask {

    private Camera2BasicFragment cameraFragment;
    public PictureTask(Camera2BasicFragment cameraFragment) {
        this.cameraFragment = cameraFragment;
    }
        @Override
    protected Object doInBackground(Object[] params) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
        cameraFragment.takePicture();
            return null;
    }
}

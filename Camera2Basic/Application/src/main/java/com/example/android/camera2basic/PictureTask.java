package com.example.android.camera2basic;

import android.os.AsyncTask;

/**
 * Created by NUB1BUV on 3/15/2017.
 */

public class PictureTask implements Runnable {

    private Camera2BasicFragment cameraFragment;
    public PictureTask(Camera2BasicFragment cameraFragment) {
        this.cameraFragment = cameraFragment;
    }

    @Override
    public void run() {
        cameraFragment.takePicture();
        cameraFragment.mBackgroundHandler.postDelayed(this, 1000);
    }
    // @Override
    /*protected Object doInBackground(Object[] params) {
            /*while(true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                cameraFragment.takePicture();
            }
            //cameraFragment.takePicture();
            //return null;
    }*/
}

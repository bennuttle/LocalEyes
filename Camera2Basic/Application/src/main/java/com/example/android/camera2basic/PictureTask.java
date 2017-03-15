package com.example.android.camera2basic;

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
        new FTPTask(cameraFragment).execute();
    }
}

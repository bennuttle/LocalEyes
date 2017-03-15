package com.example.android.camera2basic;

/**
 * Created by NUB1BUV on 3/15/2017.
 */

public class PictureTask implements Runnable {

    private Camera2BasicFragment cameraFragment;
    int collisionCounter;
    public PictureTask(Camera2BasicFragment cameraFragment) {
        this.cameraFragment = cameraFragment;
        collisionCounter = 0;
    }

    @Override
    public void run() {

        /*if(collisionCounter > 0) {
            collisionCounter--;
            new FTPTask(cameraFragment, "fuckery").execute();
        }*/
        cameraFragment.takePicture();
        new FTPTask(cameraFragment, "fuckery").execute();
        //new FTPTask(cameraFragment).execute();
    }
}

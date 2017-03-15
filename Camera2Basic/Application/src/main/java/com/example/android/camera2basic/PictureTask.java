package com.example.android.camera2basic;

/**
 * Created by NUB1BUV on 3/15/2017.
 */

public class PictureTask implements Runnable {

    private Camera2BasicFragment cameraFragment;
    int collisionCounter;

    private FTPTask ftpTask;
    public PictureTask(Camera2BasicFragment cameraFragment) {
        this.cameraFragment = cameraFragment;
        collisionCounter = 0;

        //ftpTask = new FTPTask(cameraFragment);
    }

    @Override
    public void run() {

        /*if(collisionCounter > 0) {
            collisionCounter--;
            ftpTask.execute();
            //new FTPTask(cameraFragment).execute();
        }*/
        cameraFragment.takePicture();
        //new FTPTask(cameraFragment).execute();
        //new FTPTask(cameraFragment).execute();
    }

    public void onShutDown() {
        ftpTask.onShutdown();
    }
}

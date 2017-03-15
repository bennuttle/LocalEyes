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

        //ftpTask = new FTPTask(cameraFragment);
    }

    @Override
    public void run() {

        if(collisionCounter >= cameraFragment.getBufferSize()) {
            cameraFragment.startFTP();
            collisionCounter = 0;
            cameraFragment.setCollisionEvent(false);
            return;
        }

        if(cameraFragment.getCollisionEvent()) {
            collisionCounter++;
        }

        //TODO need to check a collision event from CameraFragment, implement a counter equal to bufferSize. After counter has been filled, launch FTP transfer event.
        //TODO clear the collision event after the counter has expired (new buffer is fully filled)
        //TODO need to change FTP logic to empty both buffers on a single method run
        /*if(collisionCounter > 0) {
            collisionCounter--;
            ftpTask.execute();
            //new FTPTask(cameraFragment).execute();
        }*/
        cameraFragment.takePicture();
    }

    //TODO need to call onshutdown from CameraFragment.
    /*public void onShutDown() {
        ftpTask.onShutdown();
    }*/
}

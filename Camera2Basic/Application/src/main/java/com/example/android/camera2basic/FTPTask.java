package com.example.android.camera2basic;

import android.content.Context;
import android.graphics.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.SecureRandom;


/**
 * Created by BEJ2PLY on 3/15/2017.
 */

public class FTPTask extends AsyncTask {

    Camera2BasicFragment fragment;
    public FTPTask(Camera2BasicFragment cameraFragment) {
        super();
        fragment = cameraFragment;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        System.out.println("ftp task started");

        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("52.168.21.141", 22);

            if (ftpClient.login("4chan", "#hackthegibs0n")) {

                ftpClient.changeWorkingDirectory("~/bcw2017/imgs/");

                ftpClient.enterLocalPassiveMode(); // important!
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                String Location = fragment.getActivity().getExternalFilesDir(null).toString();
                Log.v("DIRECTORY", Location);

                SecureRandom random = new SecureRandom();

                String nextSessionId = new BigInteger(130, random).toString(32);
                Log.v("RANDOM", nextSessionId);
                String fileName = "CrashPicture0.jpg";
                String data = Location + File.separator + fileName;
                FileInputStream in = new FileInputStream(new File(data));
                boolean result = ftpClient.storeFile(fileName, in);
                in.close();
                if (result)
                    Log.v("upload result", "succeeded");
                else
                    Log.v("upload result", "failed");
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            Log.v("count", "error");
            e.printStackTrace();
            //Log.v("MACADDRESS", MACAddress);
            SecureRandom random = new SecureRandom();
            String nextSessionId = new BigInteger(130, random).toString(32);
            Log.v("RANDOM", nextSessionId);
        }

        return null;
    }
}

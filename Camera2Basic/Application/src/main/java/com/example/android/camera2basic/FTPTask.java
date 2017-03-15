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

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


/**
 * Created by BEJ2PLY on 3/15/2017.
 */

public class FTPTask extends AsyncTask {

    Camera2BasicFragment fragment;
    private int index;
    public FTPTask(Camera2BasicFragment cameraFragment) {
        super();
        fragment = cameraFragment;
        index = 0;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        System.out.println("ftp task started");


        try{

            JSch ssh = new JSch();
            Session session = ssh.getSession("4chan", "52.168.21.141", 22);
            // Remember that this is just for testing and we need a quick access, you can add an identity and known_hosts file to prevent
            // Man In the Middle attacks
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword("#hackthegibs0n");

            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;

            //sftp.cd(directory);
            // If you need to display the progress of the upload, read how to do it in the end of the article

            // use the put method , if you are using android remember to remove "file://" and use only the relative path

            String Location = fragment.getActivity().getExternalFilesDir(null).toString();
            Log.v("DIRECTORY", Location);
            SecureRandom random = new SecureRandom();
            String nextSessionId = new BigInteger(130, random).toString(32);
            Log.v("RANDOM", nextSessionId);
            String fileName = nextSessionId + ".jpg";

            File test = new File(Location + File.separator + "CrashPicture" + index + ".jpg");
            if(test.exists()) {
                Log.v("FILE", "OK");
            } else {
                Log.v("FILE", "NOTOK");
            }
            sftp.put(Location + File.separator + "CrashPicture" + index + ".jpg", "/home/4chan/bcw2017/imgs/" + fileName +".jpg");
            index = ++index % 10;

            Boolean success = true;

            if (success) {
                // The file has been uploaded succesfully
            }

            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        } catch (SftpException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        }
        return null;
    }
}

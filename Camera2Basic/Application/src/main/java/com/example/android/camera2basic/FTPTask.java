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

    private Camera2BasicFragment fragment;
    private ChannelSftp sftp;
    private Channel channel;
    private Session session;
    private int index;
    private String collisionDir;
    public FTPTask(Camera2BasicFragment cameraFragment) {
        super();
        fragment = cameraFragment;
        index = 0;
        //this.collisionDir = collisionDir;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        System.out.println("ftp task started");


        if(sftp == null) {
            connect();
        }

        try{

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
            if(sftp != null) {
                //System.out.println("/home/4chan/bcw2017/imgs/" + new String(collisionDir) + File.separator + fileName);
                //sftp.put(Location + File.separator + "CrashPicture" + index + ".jpg", "/home/4chan/bcw2017/imgs/" + new String(collisionDir) + File.separator + fileName);
                sftp.put(Location + File.separator + "CrashPicture" + index + ".jpg", "/home/4chan/bcw2017/imgs/" + collisionDir + File.separator + fileName);
            }
            index = ++index % 10;

            Boolean success = true;

            if (success) {
                // The file has been uploaded succesfully
            }

            //channel.disconnect();
            //session.disconnect();
        } catch (SftpException e) {
            System.out.println(e.getMessage().toString());
            e.printStackTrace();
        }
        return null;
    }

    private void connect() {
        try {
            JSch ssh = new JSch();
            session = ssh.getSession("4chan", "52.168.21.141", 22);
            // Remember that this is just for testing and we need a quick access, you can add an identity and known_hosts file to prevent
            // Man In the Middle attacks
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword("#hackthegibs0n");

            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeDir(String dir) {
        collisionDir = dir;
    }

    public void onShutdown() {
        if(channel != null)
            channel.disconnect();
        if(session != null)
            session.disconnect();
    }
}

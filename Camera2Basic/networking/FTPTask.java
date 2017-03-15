package com.bej2ply.worksmart;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;


/**
 * Created by BEJ2PLY on 3/15/2017.
 */

public class FTPTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        System.out.println("ftp task started");

        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("52.168.21.141", 1883);

            if (ftpClient.login("4chan", "#hackthegibs0n")) {

                ftpClient.enterLocalPassiveMode(); // important!
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                String Location = Environment.getExternalStorageDirectory().toString();
                String data = Location + File.separator + "DICKPICHERE.jpg";
                FileInputStream in = new FileInputStream(new File(data));
                boolean result = ftpClient.storeFile("DICKPICHERE.jpg", in);
                in.close();
                if (result)
                    Log.v("upload result", "succeeded");
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            Log.v("count", "error");
            e.printStackTrace();
        }

        return null;
    }
}

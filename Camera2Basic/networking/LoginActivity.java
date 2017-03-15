package com.bej2ply.worksmart;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private Context context = this;
    private static XMPPTCPConnection connection;
    private static final String SA_SERVICE = "sensor.andrew.cmu.edu";
    private static final String HOST = "sensor.andrew.cmu.edu";
    private static final int PORT = 5222;
    private static final String TAG = "BCWHACCKZZZZZZ";

    private FTPTask ftpTask;

    private class SAConn{
        boolean exists;
        String code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = this;
        //final FTPTask ftpTask = new FTPTask();

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String clientId = MqttClient.generateClientId();
                final MqttAndroidClient client = new MqttAndroidClient(getApplicationContext(),"tcp://52.168.21.141:1883", clientId);

                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            Log.d(TAG, "onSuccess");


                            //SUBSCRIBE TO MQTT TOPIC
                            client.setCallback(new MqttCallback() {

                                @Override
                                public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                                }

                                @Override
                                public void messageArrived(String topic, MqttMessage message) throws Exception {
                                    String triggermebaby = Arrays.toString(message.getPayload());
                                    System.out.println(topic + ": " + Arrays.toString(message.getPayload()));

                                    if(triggermebaby.equals("[84, 114, 105, 103, 103, 101, 114, 101, 100]")){
                                        System.out.println("I AM SO TRIGGERED");


                                        //SEND CAMERA PICTURES VIA FTP TO AZURE SERVER
                                        new FTPTask().execute();
                                    }
                                }

                                @Override
                                public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                                }
                            });
                            try {
                                client.subscribe("mqtt", 1);
                            } catch (MqttException e){
                                //
                            }
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            Log.d(TAG, "onFailure");

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                //fetch username and password
//                EditText user_field = (EditText) findViewById(R.id.username);
//                String jid = user_field.getText().toString();
//                EditText pass_field = (EditText) findViewById(R.id.password);
//                String password = pass_field.getText().toString();

            }
        });
    }

}

package com.example.android.camera2basic;

import android.os.AsyncTask;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;


/**
 * Created by NUB1BUV on 3/15/2017.
 */

public class MQTTListener extends AsyncTask {

    private Camera2BasicFragment fragment;
    //private FTPTask ftpTask;
    public MQTTListener(Camera2BasicFragment fragment) {

        this.fragment = fragment;
        //this.ftpTask = new FTPTask(fragment);

    }

    private void connect() {
        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client = new MqttAndroidClient(fragment.getActivity().getApplicationContext(),"tcp://52.170.39.130:1883", clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {


                    //SUBSCRIBE TO MQTT TOPIC
                    client.setCallback(new MqttCallback() {

                        @Override
                        public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            fragment.setCollisionEvent(true);
                            String triggermebaby = new String(message.getPayload());
                            System.out.println(topic + ": " + Arrays.toString(message.getPayload()));

                                System.out.println("I AM SO TRIGGERED");
                                fragment.changeServerDir(triggermebaby);
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                        }
                    });
                    try {
                        client.subscribe("cameras", 1);
                    } catch (MqttException e){
                        //
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    //Log.d(TAG, "onFailure");

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

    @Override
    protected Object doInBackground(Object[] params) {
        connect();
        return null;
    }


/*loginBtn.setOnClickListener(new View.OnClickListener() {
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
        });*/
}

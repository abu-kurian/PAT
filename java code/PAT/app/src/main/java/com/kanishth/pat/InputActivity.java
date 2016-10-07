package com.kanishth.pat;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputActivity extends Activity {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader input;

    private TextView log_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //TODO: remove
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        log_msg = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.editText);

                String reply = sendToServer(editText.getText().toString());
                ArrayList<LatLng> dataset = convert(reply);

                //ArrayList<LatLng> dataset = Downloader.getLatLong(editText.getText().toString());

                Intent intent = new Intent(InputActivity.this, HeatMapsActivity.class);
                intent.putExtra("dataset",dataset);

                startActivity(intent);
            }
        });
    }

    private String sendToServer(String queryCode)
    {
        try {
            Log.d("****","in");
            socket = new Socket("192.168.43.143", 5268);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.print(queryCode+"\n");
            out.flush();

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String dataset="a";
            dataset = input.readLine();
                if(dataset==null) {
                    Log.d("****", "null");
                    Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT);
                }
            else {
                    Log.d("****", dataset);
                    Toast.makeText(getApplicationContext(),dataset,Toast.LENGTH_SHORT);

                }


            out.close();
            socket.close();
            input.close();

            return dataset;

        }catch (UnknownHostException e) {
            log_msg.setText("Don't know about host: hostname");
        } catch (IOException e){
            log_msg.setText("Couldn't get I/O for the connection to: hostname");
        }
        return null;
    }
    private ArrayList<LatLng> convert(String s)
    {
        ArrayList<LatLng> list = new ArrayList<LatLng>();

        for(int i=0; i<s.length();)
        {
            if(i<100)
            Log.d("****",s.substring(i, s.indexOf('x', i))+"   "+s.substring(s.indexOf('x')+1, s.indexOf('y',i)));

            double lat = Double.parseDouble(s.substring(i, s.indexOf('x', i)));
            double lng = Double.parseDouble(s.substring(s.indexOf('x',i)+1, s.indexOf('y',i)));
            i=s.indexOf('y',i)+1;
            list.add(new LatLng(lat, lng));
        }

        return list;
    }

    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }
}

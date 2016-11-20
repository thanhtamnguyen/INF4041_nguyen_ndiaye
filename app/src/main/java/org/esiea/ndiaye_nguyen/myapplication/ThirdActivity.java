package org.esiea.ndiaye_nguyen.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketPermission;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        String[] s = {"A","B","C"};
        ArrayAdapter a = new ArrayAdapter<String>(this, R.layout.list_adapter, s);
        ListView l = (ListView) findViewById(R.id.list_view_id);
        //l.setAdapter(a);
        JSONArray j = getJsonFromURL();
    }

    public static JSONArray getJsonFromURL(){
        /*
         */
        try{
            Log.d(TAG, new String("try"));
            URL url = new URL("https://jsonplaceholder.typicode.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            Log.d(TAG, new String("connect ok"));

            InputStream input = connection.getInputStream();
            Log.d(TAG, new String("InputStream ok"));

            byte[] buffer = new byte[input.available()];
            Log.d(TAG, new String(" buffer ok"));
            input.read(buffer);
            input.close();
            Log.d(TAG, new String("close"));
            JSONArray j = new JSONArray(new String(buffer, "UTF-8"));
            Log.d(TAG, new String("json done"));
            return j;
        }catch(IOException e){
            e.printStackTrace();
            Log.d(TAG, new String("ioexception"));
            return new JSONArray();
        }catch(Exception e){
            e.printStackTrace();
            Log.d(TAG, new String("exception"));
            return new JSONArray();
        }
    }
}

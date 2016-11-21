package org.esiea.ndiaye_nguyen.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class SecondActivity extends AppCompatActivity {
    TextView detail_caption_view;
    TextView detail_date_view;
    ImageView detail_image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /*String[] s = {"A","B", "C"};
        ArrayAdapter a = new ArrayAdapter<String>(this, R.layout.list_adapter, s);
        ListView l = (ListView) findViewById(R.id.list_view_id);
        //l.setAdapter(a);
        */
        Intent intent = getIntent();

        //JSONObject detail = (JSONObject) intent.getSerializableExtra("jobj");
        String caption = (String) intent.getStringExtra("caption");
        String date = (String) intent.getStringExtra("date");
        String url_img = (String) intent.getStringExtra("url_img");

        //Get View
        detail_caption_view = (TextView) findViewById(R.id.detail_caption);
        detail_date_view = (TextView) findViewById(R.id.detail_date);
        detail_image_view = (ImageView) findViewById(R.id.detail_img);

        //set Element
        detail_caption_view.setText(caption);
        detail_date_view.setText(date);
        Picasso.with(getBaseContext()).load(url_img).into(detail_image_view);
        /*try {
            detail_caption_upd.setText(detail.getString("caption"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.choice1:
                Toast.makeText(getApplicationContext(),getString(R.string.choice1), Toast.LENGTH_LONG).show();
                return true;
            case R.id.choice2:
                Toast.makeText(getApplicationContext(),getString(R.string.choice2), Toast.LENGTH_LONG).show();
                return true;
            case R.id.choice3:
                Toast.makeText(getApplicationContext(),getString(R.string.choice3), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}

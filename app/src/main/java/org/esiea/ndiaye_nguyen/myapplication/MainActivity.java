package org.esiea.ndiaye_nguyen.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    public static final String BIERES_UPDATE = "com.octip.cours.inf4042_11.BIERE_UPDATE";
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        final Button bList = (Button) findViewById(R.id.buttonNew);
        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });*/

        /*
         * Download File service
         */
        GetBiersService.startActionBiers(this);
        IntentFilter intentFilter = new IntentFilter(BIERES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(), intentFilter);

        JSONArray jsonArray = getBiersFromFile();
        /*
         * RecyclerView Definition
         */
        rv = (RecyclerView) findViewById(R.id.rv_obj);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        // create ObjectAdapter and fill RecyclerView
        rv.setAdapter(new ObjectAdapter(jsonArray));

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

    public JSONArray getBiersFromFile(){
        try{
            InputStream input = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();

            JSONArray jsonArray = new JSONArray(new String(buffer, "UTF-8"));
            //Log.d(TAG, new String("reading done"));
            Toast.makeText(this,"Reading Done in getBiersFromFile", Toast.LENGTH_LONG).show();

            try {
                Log.i(MainActivity.class.getName(),
                        "Number of entries " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //Log.i(MainActivity.class.getName(), jsonObject.getString("name"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this,"Return JsonArray", Toast.LENGTH_LONG).show();
            return jsonArray;
        }catch(IOException e){
            e.printStackTrace();
            Log.d(TAG, "ioexception");
            return new JSONArray();
        }catch(Exception e) {
            e.printStackTrace();
            Log.d(TAG, "exception");
            return new JSONArray();

        }
    }

    private class BierUpdate extends BroadcastReceiver {
        /*
         * Sub-Class displaying notification
         */
        private static final String TAG = "MyActivity";
        @Override
        public void onReceive(Context context, Intent intent){
            //Log.d(TAG, getIntent().getAction());
            Toast.makeText(context,"Bier Update Done", Toast.LENGTH_LONG).show();
        }

    }

    private class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.ObjectHolder>{
        private JSONArray obj;

        public ObjectAdapter(JSONArray jsonArray){
            this.obj = jsonArray;
        }
        @Override
        public ObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*
             * Get context from parent
             * Inflate the instance with rv_obj_element layout ressource
             */
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_obj_element, parent, false);
            /*
             * Return new ObjectHolder with view as input
             */
            return new ObjectHolder(view);
        }

        @Override
        public void onBindViewHolder(ObjectHolder holder, int position) {
            /*
             * Fill data view element
             */
            try {
                JSONObject j = this.obj.getJSONObject(position);
                holder.bind(j);
                Log.d(TAG, new String("\nSetText done"));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, new String("\nNo Elem for this key"));
            }
        }

        @Override
        public int getItemCount() {
            /*
             * return length of jsonArray
             */
            return this.obj.length();
        }

        public void setNewObject(JSONArray j){

        }

        public class ObjectHolder extends RecyclerView.ViewHolder {
            /*
             * Translate view element into java object
             */
            public TextView textViewView;

            public ObjectHolder(View itemView) {
                /*
                 * Input : inflated view (from onCreateViewHolder method)
                 */
                super(itemView);

                // update the graphic element
                textViewView = (TextView) itemView.findViewById(R.id.rv_obj_element_name);
            }
            public void bind(JSONObject json_obj){
                try {
                    textViewView.setText(json_obj.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

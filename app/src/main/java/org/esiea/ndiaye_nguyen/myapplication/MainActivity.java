package org.esiea.ndiaye_nguyen.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private static final String TAG_JSON = "JSON File";
    private static final String TAG_RV = "RecyclerView Holder";
    public static final String BIERES_UPDATE = "com.octip.cours.inf4042_11.BIERE_UPDATE";
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Download File service
        */
        GetBiersService.startActionBiers(this);

        //throw an intent to signal an update
        //and filter the intent
        IntentFilter intentFilter = new IntentFilter(BIERES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new DataUpdate(), intentFilter);


        JSONArray jsonArray = getBiersFromFile();

        /*
         * RecyclerView Definition
        */
        /*
        rv = (RecyclerView) findViewById(R.id.rv_obj);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // create ObjectAdapter and fill RecyclerView
        rv.setAdapter(new ObjectAdapter(jsonArray));
        */
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
            case R.id.site_url:
                String url = "http://epic.gsfc.nasa.gov/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            case R.id.box:
                AlertDialog.Builder my_box;
                my_box = new AlertDialog.Builder(this);
                my_box.setTitle(R.string.box_title);

                my_box.setMessage(R.string.box_msg);
                my_box.setCancelable(true);
                my_box.setNegativeButton(R.string.box_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Lorsque l'on cliquera sur yes, on quittera l'application
                        MainActivity.this.finish();
                    } });
                my_box.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public JSONArray getBiersFromFile(){
        try{
            InputStream input = new FileInputStream(getCacheDir()+"/"+"my_data.json");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            JSONArray jsonArray = new JSONArray(new String(buffer, "UTF-8"));
            Log.d(TAG_JSON, "Convert to Json Array done");
            try {
                Log.i(MainActivity.class.getName(),"Number of entries " + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArray;
        }catch(IOException e){
            e.printStackTrace();
            Log.d(TAG_JSON, "ioexception");
            return new JSONArray();
        }catch(Exception e) {
            e.printStackTrace();
            Log.d(TAG_JSON, "exception");
            return new JSONArray();

        }
    }

    private class DataUpdate extends BroadcastReceiver {
        /*
         * Sub-Class displaying notification
         */
        private static final String TAG = "DL Activity";
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context,"Data Update Done", Toast.LENGTH_LONG).show();

            /*
             * RecyclerView Definition
            */
            rv = (RecyclerView) findViewById(R.id.rv_obj);
            rv.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));

            // reads file and puts datas into jsonArray
            JSONArray jsonArray = getBiersFromFile();

            // create ObjectAdapter and fill RecyclerView
            rv.setAdapter(new ObjectAdapter(jsonArray));
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
                // call bind() function
                holder.bind(j);
            } catch (JSONException e) {
                e.printStackTrace();
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
            public ImageView imageViewView;

            public ObjectHolder(View itemView) {
                /*
                 * Input : inflated view (from onCreateViewHolder method)
                 */
                super(itemView);

                // update the graphic element
                textViewView = (TextView) itemView.findViewById(R.id.rv_obj_element_name);
                imageViewView = (ImageView) itemView.findViewById(R.id.rv_obj_element_image);
            }
            public void bind(final JSONObject json_obj){
                /*
                 * Set all elements in view
                 */
                try {
                    //Log.d(TAG_RV, "https://epic.gsfc.nasa.gov/archive/natural/2015/12/20/png/"+json_obj.getString("image")+".png");
                    textViewView.setText(json_obj.getString("date"));

                    //final String urlImage = "https://epic.gsfc.nasa.gov/epic-archive/jpg/"+json_obj.getString("image")+".jpg";
                    final String urlImage = "http://epic.gsfc.nasa.gov/archive/natural/2015/12/20/jpg/"+json_obj.getString("image")+".jpg";
                    //final String urlImage = "http://apod.nasa.gov/apod/image/1512/spritenight_iss_4256.jpg";
                    Log.d(TAG_RV, urlImage);
                    Picasso.with(imageViewView.getContext()).load(urlImage).into(imageViewView);
                    imageViewView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, DetailActivity.class);
                            try {
                                i.putExtra("caption", String.valueOf(json_obj.getString("caption")));
                                i.putExtra("url_img", String.valueOf(urlImage));
                                i.putExtra("date", String.valueOf(json_obj.getString("date")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(i);
                        }
                    });
                    //imageViewView.setImageBitmap(bitmap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package org.esiea.ndiaye_nguyen.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView detail_caption_view;
    TextView detail_date_view;
    ImageView detail_image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        String caption = intent.getStringExtra("caption");
        String date = intent.getStringExtra("date");
        String url_img = intent.getStringExtra("url_img");

        //Get View
        detail_caption_view = (TextView) findViewById(R.id.detail_caption);
        detail_date_view = (TextView) findViewById(R.id.detail_date);
        detail_image_view = (ImageView) findViewById(R.id.detail_img);

        //set Element
        detail_caption_view.setText(caption);
        detail_date_view.setText(date);
        Picasso.with(getBaseContext()).load(url_img).into(detail_image_view);
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
                my_box.setNegativeButton(R.string.box_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Lorsque l'on cliquera sur annuler on quittera l'application
                        DetailActivity.this.finish();
                    } });
                my_box.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

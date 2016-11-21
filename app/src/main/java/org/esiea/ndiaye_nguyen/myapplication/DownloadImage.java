package org.esiea.ndiaye_nguyen.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by tnguyen on 21/11/2016.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    Bitmap bmImg;

    public DownloadImage(String url){
        doInBackground(url);
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        Log.d("DL image", new String("\ndownload imag in background"));
        String urldisplay = urls[0];
        this.bmImg = null;
        try {
            //not working
            InputStream in = new java.net.URL(urldisplay).openStream();
            this.bmImg = BitmapFactory.decodeStream(in);

            //
        } catch (Exception e) {
            Log.e("BIG ERROR", e.getMessage());
            e.printStackTrace();
        }
        return this.bmImg;
    }
    public Bitmap getBitmap(){
        return this.bmImg;
    }
}

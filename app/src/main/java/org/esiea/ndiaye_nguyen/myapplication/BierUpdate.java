package org.esiea.ndiaye_nguyen.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static android.content.Intent.getIntent;

/**
 * Created by tnguyen on 08/11/2016.
 */

public class BierUpdate extends BroadcastReceiver {

    private static final String TAG = "MyActivity";
    @Override
    public void onReceive(Context context, Intent intent){
        //Log.d(TAG, getIntent().getAction());
        Toast.makeText(context,"Bier Update Done", Toast.LENGTH_LONG).show();
    }

}

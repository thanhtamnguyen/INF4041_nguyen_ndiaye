package org.esiea.ndiaye_nguyen.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String[] s = {"A","B", "C"};
        ArrayAdapter a = new ArrayAdapter<String>(this, R.layout.list_adapter, s);
        ListView l = (ListView) findViewById(R.id.list_view_id);
        //l.setAdapter(a);

        final Button bNext = (Button) findViewById(R.id.buttonNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(i);
            }
        });
    }

}

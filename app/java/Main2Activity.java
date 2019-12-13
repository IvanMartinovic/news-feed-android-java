package com.example.vesti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView imageStart=(ImageView) findViewById(R.id.imageStart);
        TextView textStart=(TextView) findViewById(R.id.textStart);
        imageStart.setOnClickListener(this);
        textStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent= new Intent(Main2Activity.this,MainActivity.class);
        startActivity(intent);
    }
}

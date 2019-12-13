package com.example.vesti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class VestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vest);
        ImageView imageVest = (ImageView) findViewById(R.id.imageVest);
        TextView textNaslov_vest = (TextView) findViewById(R.id.textNaslov_vest);
        TextView textDatum_vest = (TextView) findViewById(R.id.textDatum_vest);
        TextView textVest = (TextView) findViewById(R.id.textVest);


        Glide.with(this)
                .asBitmap()
                .load(getIntent().getStringExtra("imageVest"))
                .into(imageVest);
        textNaslov_vest.setText(getIntent().getStringExtra("textNaslov_vest"));
        textDatum_vest.setText(getIntent().getStringExtra("textDatum_vest"));
        textVest.setText(getIntent().getStringExtra("textVest"));


    }
    }


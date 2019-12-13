package com.example.vesti;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    private static final String SHARED_PREFERENCES_PREFIX="MainActivitySharedPreferencesPrefix";
    private static final String SHARED_PREFERENCES_KATEGORIJA="kategorija";
    private static final String SHARED_PREFERENCES_ENG="eng";

    public static ApiInterface apiInterface;
    public static  ArrayList<Category> categories=new ArrayList<>();
    public static  ArrayList<Post> posts=new ArrayList<>();
    public static int category;
    public static boolean eng;
    private View divider;
    private RecyclerView listView;
    private RecyclerView listView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readPreferences();
        Switch switch_eng=(Switch) findViewById(R.id.switch1);
        divider=findViewById(R.id.divider2);
        apiInterface=ApiClient.getClient(ApiInterface.class);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        listView=(RecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        listView2=(RecyclerView) findViewById(R.id.listView2);
        listView2.setLayoutManager(layoutManager2);


        switch_eng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b==false){
                    eng=false;
                    getCategories(apiInterface,MainActivity.this);
                    getPosts(apiInterface,MainActivity.this,category);

                    Runnable r=new Runnable() {
                        @Override
                        public void run() {
                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories,listView2,divider,apiInterface);
                    listView.setAdapter(categoryAdapter);
                    VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts);
                    listView2.setAdapter(vestAdapter);
                        }
                    };
                    Handler h=new Handler();
                    h.postDelayed(r,1500);

                }
                else{
                eng=true;
                    getCategoriesENG(apiInterface, MainActivity.this);
                    getPostsENG(apiInterface,MainActivity.this,category);
                    Runnable r=new Runnable() {
                        @Override
                        public void run() {
                    divider.setBackgroundColor(Color.parseColor(categories.get(category).getColor()));
                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories,listView2,divider,apiInterface);
                    listView.setAdapter(categoryAdapter);
                    VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts);
                    listView2.setAdapter(vestAdapter);
                }
            };
            Handler h=new Handler();
            h.postDelayed(r,1500);


        }


            }
        });



        if(eng==true){switch_eng.setChecked(true);}
        else{
            getCategories(apiInterface,MainActivity.this);

            getPosts(apiInterface,this,category);

            Runnable r=new Runnable() {
                @Override
                public void run() {
                    divider.setBackgroundColor(Color.parseColor(categories.get(category).getColor()));
                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories,listView2,divider,apiInterface);
                    listView.setAdapter(categoryAdapter);
                    VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts);
                    listView2.setAdapter(vestAdapter);
                }
            };
            Handler h=new Handler();
            h.postDelayed(r,1500);

        }

        }






    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }


    private void savePreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_PREFIX,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCES_KATEGORIJA,category);
        editor.putBoolean(SHARED_PREFERENCES_ENG,eng);
        editor.commit();
    }

    private void readPreferences(){

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_PREFIX,0);
        category=sharedPreferences.getInt(SHARED_PREFERENCES_KATEGORIJA,0);
        eng=sharedPreferences.getBoolean(SHARED_PREFERENCES_ENG,false);
    }


    protected  void getCategories(ApiInterface apiInterface,Context mcontext) {



        Call<ArrayList<Category>> callLanguage = apiInterface.getCategories();
        final Dialog dialog = LoadDialog.loadDialog(MainActivity.this);
        dialog.show();

         callLanguage.enqueue(new Callback<ArrayList<Category>>()

        {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    categories=response.body();
                }
                else{
                    Toast.makeText(MainActivity.this, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });

    }







    protected  void getCategoriesENG(ApiInterface apiInterface, final Context mcontext) {




        Call<ArrayList<Category>> callLanguage = apiInterface.getCategoriesENG();
        final Dialog dialog = LoadDialog.loadDialog(MainActivity.this);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Category>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){

                    categories=response.body();
                }
                else{
                    Toast.makeText(mcontext, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(mcontext, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });


    }




    protected static  void getPosts(ApiInterface apiInterface, final Context mcontext,int category_id) {



        Call<ArrayList<Post>> callLanguage = apiInterface.getPosts(category_id);
        final Dialog dialog = LoadDialog.loadDialog(mcontext);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Post>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    posts=response.body();
                }
                else{
                    Toast.makeText(mcontext, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(mcontext, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });

    }





    protected static void getPostsENG(ApiInterface apiInterface, final Context mcontext,int category_id) {



        Call<ArrayList<Post>> callLanguage = apiInterface.getPostsENG(category_id);
        final Dialog dialog = LoadDialog.loadDialog(mcontext);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Post>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    posts=response.body();
                }
                else{
                    Toast.makeText(mcontext, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(mcontext, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });

    }




    }








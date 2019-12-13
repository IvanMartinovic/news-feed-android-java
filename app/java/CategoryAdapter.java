package com.example.vesti;




import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;




public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Category> categories=new ArrayList<>() ;
    private RecyclerView listView2;
    private View divider;
    private ApiInterface apiInterface;



    public CategoryAdapter(Context context, ArrayList<Category> categories,RecyclerView listView2,View divider,ApiInterface apiInterface) {
        this.context=context;
        this.categories=categories;
        this.listView2=listView2;
        this.divider=divider;
        this.apiInterface=apiInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategorija, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
    // position kreće od 0 !!!

        Glide.with(context)
                .asBitmap()
                .load(categories.get(position).getImg_url())
                .placeholder(R.drawable.kkcrvenazvezda)
                .error(R.drawable.kkcrvenazvezda)
                .fallback(R.drawable.kkcrvenazvezda)
                .into(holder.imageKategorija);

        holder.textKategorija.setText(categories.get(position).getName());
        holder.textKategorija.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));
        holder.imageKategorija.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));

        holder.kategorijaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.category=position;
              if(MainActivity.eng==false){  MainActivity.getPosts(apiInterface,context,MainActivity.category);}
              else{MainActivity.getPostsENG(apiInterface,context,MainActivity.category);}
                Runnable r=new Runnable() {
                    @Override
                    public void run() {
                divider.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));
                VestAdapter vestAdapter=new VestAdapter(context,MainActivity.posts);
                listView2.setAdapter(vestAdapter);
                    }
                };
                Handler h=new Handler();
                h.postDelayed(r,1500);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textKategorija;
        ImageView imageKategorija;
        ConstraintLayout kategorijaLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textKategorija = itemView.findViewById(R.id.textKategorija);
            imageKategorija=itemView.findViewById(R.id.imageKat);
            kategorijaLayout=itemView.findViewById(R.id.kategorijaLayout);
        }
    }
}








//package com.example.vesti;
//
//
//
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//
//
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
//
//
//    private Context context;
//    private ArrayList<Category> categories ;
//    private RecyclerView listView2;
//    private View divider;
//    private ApiInterface apiInterface;
//    private ArrayList<Post> posts;
//
//
//    public CategoryAdapter(Context context, ArrayList<Category> categories,RecyclerView listView2,View divider,ApiInterface apiInterface) {
//        this.context=context;
//        this.categories=categories;
//        this.listView2=listView2;
//        this.divider=divider;
//        this.apiInterface=apiInterface;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategorija, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        // position kreće od 0 !!!
//
//        Glide.with(context)
//                .asBitmap()
//                .load(categories.get(position).getImg_url())
//                .into(holder.imageKategorija);
//
//        holder.textKategorija.setText(categories.get(position).getName());
//        holder.textKategorija.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));
//        holder.imageKategorija.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));
//
//        holder.kategorijaLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity.category=position;
//                if(MainActivity.eng==false){ if(position==0) posts=MainActivity.posts1; else posts=MainActivity.posts3;}
//                else{if(position==0)posts=MainActivity.posts2; else posts=MainActivity.posts4; }
//
//                divider.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));
//                VestAdapter vestAdapter=new VestAdapter(context,posts);
//                listView2.setAdapter(vestAdapter);
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return categories.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        TextView textKategorija;
//        ImageView imageKategorija;
//        ConstraintLayout kategorijaLayout;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            textKategorija = itemView.findViewById(R.id.textKategorija);
//            imageKategorija=itemView.findViewById(R.id.imageKat);
//            kategorijaLayout=itemView.findViewById(R.id.kategorijaLayout);
//        }
//    }
//}
//
//
//
//
//

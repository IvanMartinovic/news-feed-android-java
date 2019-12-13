package com.example.vesti;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VestAdapter extends RecyclerView.Adapter<VestAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> posts=new ArrayList<>();

    public VestAdapter(Context context,ArrayList<Post> posts ) {

        this.context = context;
        this.posts=posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.glavna_vest, parent, false);
        return new ViewHolder1(view);
        }
        else{
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.mala_vest,parent,false);
            return new ViewHolder2(view);}
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // position kreće od 0 !!!

        if(position==0){

            Glide.with(context)
                    .asBitmap()
                    .load(posts.get(position).getImage_url())
                    .into(((ViewHolder1) holder).imageGlavna);
            ((ViewHolder1) holder).textNaslov_glavna.setText(posts.get(position).getTitle());
            ((ViewHolder1) holder).textDatum_glavna.setText(posts.get(position).getDate());


        ((ViewHolder1)holder).glavnaVestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,VestActivity.class);
                intent.putExtra("imageVest",posts.get(position).getImage_url());
                intent.putExtra("textNaslov_vest",posts.get(position).getTitle());
                intent.putExtra("textDatum_vest",posts.get(position).getDate());
                intent.putExtra("textVest",posts.get(position).getContent());

                context.startActivity(intent);


            }
        });}
        else{
            Glide.with(context)
                    .asBitmap()
                    .load(posts.get(position).getImage_url())
                    .into(((ViewHolder2) holder).imageMala);
            ((ViewHolder2) holder).textNaslov_mala.setText(posts.get(position).getTitle());
            ((ViewHolder2) holder).textDatum_mala.setText(posts.get(position).getDate());

            ((ViewHolder2)holder).malaVestLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,VestActivity.class);
                    intent.putExtra("imageVest",posts.get(position).getImage_url());
                    intent.putExtra("textNaslov_vest",posts.get(position).getTitle());
                    intent.putExtra("textDatum_vest",posts.get(position).getDate());
                    intent.putExtra("textVest",posts.get(position).getContent());
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
       public ViewHolder(View itemView){
            super(itemView);
        }
    }

    public class ViewHolder1 extends ViewHolder{

        ImageView imageGlavna;
        TextView textNaslov_glavna;
        TextView textDatum_glavna;
        ConstraintLayout glavnaVestLayout;

        public ViewHolder1(View itemView) {
            super(itemView);
            imageGlavna = itemView.findViewById(R.id.imageGlavna);
            textNaslov_glavna=itemView.findViewById(R.id.textNaslov_glavna);
            textDatum_glavna=itemView.findViewById(R.id.textDatum_glavna);
            glavnaVestLayout=itemView.findViewById(R.id.glavnaVestLayout);
        }
    }

    public class ViewHolder2 extends ViewHolder{

        ImageView imageMala;
        TextView textNaslov_mala;
        TextView textDatum_mala;
        ConstraintLayout malaVestLayout;

        public ViewHolder2(View itemView) {
            super(itemView);
            imageMala = itemView.findViewById(R.id.imageMala);
            textNaslov_mala=itemView.findViewById(R.id.textNaslov_mala);
            textDatum_mala=itemView.findViewById(R.id.textDatum_mala);
            malaVestLayout=itemView.findViewById(R.id.malaVestLayout);
        }
    }


}



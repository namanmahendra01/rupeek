package com.naman.myplace.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naman.myplace.MapsActivity;
import com.naman.myplace.ModelPlace;
import com.naman.myplace.R;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class AdapterPlaces extends RecyclerView.Adapter<AdapterPlaces.ViewHolder> {

    private final Context mContext;
    private ViewHolder prevHolder;

    private final List<ModelPlace> modelPlaces;

    public AdapterPlaces(Context mContext, List<ModelPlace> modelPlaces) {
        this.mContext = mContext;
        this.modelPlaces = modelPlaces;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_places_card, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {



        Display display = ((Activity)mContext).getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // ((display.getWidth()*20)/100)
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width/2,width/2);
        holder.img.setLayoutParams(parms);
        ModelPlace modelPlace = modelPlaces.get(i);

        SharedPreferences sp = mContext.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json =sp.getString("task list",null);
        Type type= new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList votelist=gson.fromJson(json,type);
        if (votelist==null){
            votelist=new ArrayList();
        }

        if(votelist.contains(modelPlace.getId())){
            holder.upVote.setVisibility(View.GONE);
            holder.downVote.setVisibility(View.VISIBLE);
        }
        holder.name.setText(modelPlace.getName());
        holder.address.setText(modelPlace.getAddress());

        Glide.with(mContext.getApplicationContext())
                .load(modelPlace.getImage())
                .thumbnail(0.5f)
                .into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MapsActivity.class);
                i.putExtra("origin1",Double.parseDouble(modelPlace.getLatitude().substring(0,7)));
                i.putExtra("dest1",Double.parseDouble(modelPlace.getLongitude().substring(0,7)));

               mContext.startActivity(i);


            }
        });

        ArrayList finalVotelist = votelist;
        holder.upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if(!finalVotelist.contains(modelPlace.getId())) {
               holder.upVote.setVisibility(View.GONE);
               holder.downVote.setVisibility(View.VISIBLE);

               finalVotelist.add(modelPlace.getId());
               SharedPreferences sp = mContext.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sp.edit();
               Gson gson = new Gson();
               String json = gson.toJson(finalVotelist);
               editor.putString("task list", json);
               editor.apply();
           }

            }
        });

        ArrayList finalVotelist1 = votelist;
        holder.downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalVotelist1.contains(modelPlace.getId())) {

                    holder.downVote.setVisibility(View.GONE);
                holder.upVote.setVisibility(View.VISIBLE);
                    finalVotelist1.add(modelPlace.getId());
                    SharedPreferences sp = mContext.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(finalVotelist1);
                    editor.putString("task list", json);
                    editor.apply();
                }

            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public long getItemId(int position) {

        return modelPlaces.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return modelPlaces.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name,address;
        private final ImageView img,upVote,downVote;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.image);
            address = itemView.findViewById(R.id.adress);
            upVote = itemView.findViewById(R.id.upvote);
            downVote = itemView.findViewById(R.id.downVote);


        }
    }


}
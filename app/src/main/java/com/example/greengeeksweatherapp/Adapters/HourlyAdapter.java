package com.example.greengeeksweatherapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greengeeksweatherapp.Domains.Hourly;
import com.example.greengeeksweatherapp.R;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    ArrayList<Hourly> items;
    Context context;

    public HourlyAdapter(ArrayList<Hourly> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hourly, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.ViewHolder holder, int position) {
        holder.hourTextView.setText(items.get(position).getHour());
        //holder.tempTextView.setText(items.get(position).getTemp());
        holder.tempTextView.setText(String.valueOf(items.get(position).getTemp()));

        int drawableResourceId = holder.itemView.getResources().getIdentifier(
                items.get(position).getPicPath(),
                "drawable",
                holder.itemView.getContext().getPackageName()
        );

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView hourTextView, tempTextView;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTextView = itemView.findViewById(R.id.hourTxt);
            tempTextView = itemView.findViewById(R.id.tempTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}

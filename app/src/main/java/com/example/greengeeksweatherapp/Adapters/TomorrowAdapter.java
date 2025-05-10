package com.example.greengeeksweatherapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greengeeksweatherapp.Domains.TomorrowDomain;
import com.example.greengeeksweatherapp.R;

import java.util.ArrayList;

public class TomorrowAdapter extends RecyclerView.Adapter<TomorrowAdapter.ViewHolder> {
    ArrayList<TomorrowDomain> items;
    Context context;

    public TomorrowAdapter(ArrayList<TomorrowDomain> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public TomorrowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tomorrow, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TomorrowAdapter.ViewHolder holder, int position) {
        //holder.hourTextView.setText(items.get(position).getHour());
        //holder.tempTextView.setText(items.get(position).getTemp());
        //holder.tempTextView.setText(String.valueOf(items.get(position).getTemp()));
        holder.dayTextView.setText(items.get(position).getDay());
        holder.statusTextView.setText(items.get(position).getStatus());
        holder.lowTextView.setText(String.valueOf(items.get(position).getLowTemp()));
        holder.highTextView.setText(String.valueOf(items.get(position).getHighTemp()));

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
        TextView dayTextView, statusTextView, lowTextView, highTextView;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTxt);
            statusTextView = itemView.findViewById(R.id.statusTxt);
            lowTextView = itemView.findViewById(R.id.lowTxt);
            highTextView = itemView.findViewById(R.id.highTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}

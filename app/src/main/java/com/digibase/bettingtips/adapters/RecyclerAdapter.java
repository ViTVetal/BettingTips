package com.digibase.bettingtips.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digibase.bettingtips.models.Event;

import java.util.List;

import digibase.com.bettingtips.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Event> eventList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView tvTeam1;
        protected TextView tvTeam2;
        protected TextView tvTip;
        protected TextView tvDate;

        public ViewHolder(View v) {
            super(v);
            tvTeam1 =  (TextView) v.findViewById(R.id.tvTeam1);
            tvTeam2 = (TextView)  v.findViewById(R.id.tvTeam2);
            tvTip = (TextView)  v.findViewById(R.id.tvTip);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Event event = eventList.get(position);
        holder.tvTeam1.setText(event.team1);
        holder.tvTeam2.setText(event.team2);
        holder.tvTip.setText(event.tip);
        holder.tvDate.setText(event.date);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
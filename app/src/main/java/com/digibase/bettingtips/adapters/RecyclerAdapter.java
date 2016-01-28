package com.digibase.bettingtips.adapters;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.digibase.bettingtips.models.Event;
import com.digibase.bettingtips.network.API;

import java.util.List;

import digibase.com.bettingtips.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Event> eventList;
    private Context context;
    ImageLoader mImageLoader;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView tvTeam1;
        protected TextView tvTeam2;
        protected TextView tvTip;
        protected TextView tvDate;
        protected TextView tvScore1;
        protected TextView tvScore2;
        protected TextView tvOdds;
        protected ImageView ivSuccess;
        protected NetworkImageView ivCountry;
        protected CardView cardView;

        public ViewHolder(View v) {
            super(v);
            tvTeam1 =  (TextView) v.findViewById(R.id.tvTeam1);
            tvTeam2 = (TextView)  v.findViewById(R.id.tvTeam2);
            tvTip = (TextView)  v.findViewById(R.id.tvTip);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvScore1 = (TextView)  v.findViewById(R.id.tvScore1);
            tvScore2 = (TextView) v.findViewById(R.id.tvScore2);
            tvOdds = (TextView) v.findViewById(R.id.tvOdds);
            ivSuccess = (ImageView) v.findViewById(R.id.ivSuccess);
            ivCountry = (NetworkImageView) v.findViewById(R.id.ivCountry);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
        mImageLoader = API.getInstance(context).getImageLoader();
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

        boolean isScore1 = false, isScore2 = false;

        if(position % 2 == 0)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        else
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));

        Event event = eventList.get(position);
        holder.tvTeam1.setText(event.team1);
        holder.tvTeam2.setText(event.team2);
        holder.tvTip.setText(event.tip);
        holder.tvDate.setText(event.date);
        holder.tvOdds.setText(event.odds);

        holder.ivCountry.setImageUrl(event.imageURL, mImageLoader);

        if(event.score1 != null && !TextUtils.isEmpty(event.score1)) {
            holder.tvScore1.setText(event.score1);
            isScore1 = true;
        } else {
            holder.tvScore1.setText("");
            holder.tvScore2.setText("");
        }

        if(event.score2 != null && !TextUtils.isEmpty(event.score2)) {
            holder.tvScore2.setText(event.score2);
            isScore2 = true;
        } else {
            holder.tvScore1.setText("");
            holder.tvScore2.setText("");
        }

        if(isScore1 && isScore2) {
            if(event.success)
                holder.ivSuccess.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_check));
            else
                holder.ivSuccess.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cross));
        } else {
            holder.ivSuccess.setImageDrawable(null);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
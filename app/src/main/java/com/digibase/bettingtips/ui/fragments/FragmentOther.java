package com.digibase.bettingtips.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.digibase.bettingtips.adapters.RecyclerAdapter;
import com.digibase.bettingtips.models.Event;
import com.digibase.bettingtips.network.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import digibase.com.bettingtips.BuildConfig;
import digibase.com.bettingtips.R;

public class FragmentOther extends Fragment {

    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_other,container,false);
        ButterKnife.inject(this, v);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        if(getResources().getBoolean(R.bool.isTablet)) {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
        } else
            mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);


        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getInfo();
                    }
                }
        );

        mySwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mySwipeRefreshLayout.setRefreshing(true);
                getInfo();
            }
        });

        return v;
    }

    private void getInfo() {
        if(ConnectionDetector.isConnection(getActivity())) {
            JsonArrayRequest stringRequest = new JsonArrayRequest("http://bettingtips.herokuapp.com/api/events/by_category/" + 2,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("myLogs", response.toString());

                            try {
                                // Parsing json array response
                                // loop through each json object
                                List<Event> eventList = new ArrayList<Event>();
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject eventJson = (JSONObject) response
                                            .get(i);

                                    String team1 = eventJson.getString("team1");
                                    String team2 = eventJson.getString("team2");
                                    String tip = eventJson.getString("tip");
                                    String date = eventJson.getString("date");
                                    String odds = eventJson.getString("odds");
                                    String score1 = eventJson.getString("score1");
                                    String score2 = eventJson.getString("score2");
                                    boolean success = eventJson.getBoolean("success");
                                    String imageURL = eventJson.getString("image_url");

                                    Event event = new Event();
                                    event.team1 = team1;
                                    event.team2 = team2;
                                    event.tip = tip;
                                    event.date = date;
                                    event.odds = odds;
                                    if(score1 != null && !TextUtils.isEmpty(score1) && !score1.equals("null"))
                                        event.score1 = score1;
                                    if(score2 != null && !TextUtils.isEmpty(score2) && !score2.equals("null"))
                                        event.score2 = score2;
                                    event.success = success;
                                    event.imageURL = imageURL;

                                    eventList.add(event);
                                }


                                // specify an adapter (see also next example)
                                mAdapter = new RecyclerAdapter(eventList, getActivity());
                                recyclerView.setAdapter(mAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(),
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }

                            mySwipeRefreshLayout.setRefreshing(false);

                            // Toast toast = Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT);
                            // toast.show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mySwipeRefreshLayout.setRefreshing(false);

                            Toast toast = Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }) {

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } else {
            Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
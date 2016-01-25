package com.digibase.bettingtips.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digibase.bettingtips.network.ConnectionDetector;

import butterknife.ButterKnife;
import butterknife.InjectView;
import digibase.com.bettingtips.BuildConfig;
import digibase.com.bettingtips.R;

public class FragmentFootball extends Fragment {

    @InjectView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_football,container,false);

        ButterKnife.inject(this, v);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getInfo();
                    }
                }
        );

        return v;
    }

    private void getInfo() {
        if(ConnectionDetector.isConnection(getActivity())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, BuildConfig.API_URL + "/by_category/" + 1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            mySwipeRefreshLayout.setRefreshing(false);

                            Toast toast = Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT);
                            toast.show();
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
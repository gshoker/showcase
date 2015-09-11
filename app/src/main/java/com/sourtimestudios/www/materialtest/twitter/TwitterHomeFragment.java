package com.sourtimestudios.www.materialtest.twitter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.sourtimestudios.www.materialtest.R;
import com.squareup.okhttp.OkHttpClient;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 10/08/15.
 */
public class TwitterHomeFragment extends Fragment {

    RecyclerView recyclerView;
    TweetListAdapter adapter;

    private List<TweetListAdapter.TweetModel> micropostList;
    private static final String TAG = "TwitterHomeFragment";
    private String httpResponse;
    private static final String ENDPOINT = "https://thawing-peak-6741.herokuapp.com";

    MicropostsAPI api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        micropostList = Collections.emptyList();

        OkHttpClient okHttpClient = OKSingleton.getInstance().getOKClient();

//        RequestInterceptor interceptor = new RequestInterceptor() {
//            @Override
//            public void intercept(RequestFacade request) {
//                String cookie = OKSingleton.getInstance().getSessionCookie();
//                if (cookie != null){
//                    request.addHeader("Cookie","_sample_app_session="+cookie);
//                }
//            }
//        };
//
//        RestAdapter adapter = new RestAdapter.Builder()
//                .setClient(new OkClient(okHttpClient))
//                .setRequestInterceptor(interceptor)
//                .setEndpoint(ENDPOINT)
//                .build();



        api = OKSingleton.getInstance().getMicropostsAPI();
//        api = adapter.create(MicropostsAPI.class);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_twitterhome,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rvtweetfeed);
        getData();
//        adapter = new TweetListAdapter(getActivity(),micropostList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }



    public void getData(){
        api.getPosts(new Callback<List<TweetListAdapter.TweetModel>>() {
            @Override
            public void success(List<TweetListAdapter.TweetModel> microposts, Response response) {
                micropostList = microposts;
                setupAdapter();
                TweetListAdapter.TweetModel post = micropostList.get(0);
                Log.i(TAG, "user: " + post.getUser() + "gravatar: " + post.getGravatar());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG,error.toString());
            }
        });

    }

    private void setupAdapter() {

        adapter = new TweetListAdapter(getActivity(),micropostList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }



}

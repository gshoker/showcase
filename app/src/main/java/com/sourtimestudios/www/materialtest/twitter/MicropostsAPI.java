package com.sourtimestudios.www.materialtest.twitter;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by user on 12/08/15.
 */
public interface MicropostsAPI {

    @POST("/api/login")
    public void login(Callback<String> response);

    @GET("/api/users")
    public void getPosts(Callback<List<TweetListAdapter.TweetModel>> response);

    @FormUrlEncoded
    @POST("/api/microposts")
    public void submitPost(@Field("micropost[content]") String content, Callback<List<TweetListAdapter.TweetModel>> response);

    @DELETE("/logout")
    public void logout(Callback<Response> response);
}
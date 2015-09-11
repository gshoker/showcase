package com.sourtimestudios.www.materialtest.twitter;

import com.sourtimestudios.www.materialtest.CustomCookieManager;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by user on 15/08/15.
 */
public class OKSingleton {
    private static OKSingleton mInstance = null;

    private OkHttpClient mOkHttpClient;
    private String mSessionCookie;
    private MicropostsAPI micropostsAPI;
    private CustomCookieManager cookieManager;

    private final String ENDPOINT = "https://thawing-peak-6741.herokuapp.com";

    private OKSingleton(){
        mOkHttpClient = new OkHttpClient();
        cookieManager = new CustomCookieManager();
        mOkHttpClient.setCookieHandler(cookieManager);


        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String cookie = OKSingleton.getInstance().getSessionCookie();
                if (cookie != null){
                    request.addHeader("Cookie","_sample_app_session="+cookie);
                }
            }
        };

        RestAdapter adapter = new RestAdapter.Builder()
                .setClient(new OkClient(mOkHttpClient))
                .setRequestInterceptor(interceptor)
                .setEndpoint(ENDPOINT)
                .build();


        micropostsAPI = adapter.create(MicropostsAPI.class);
    }

    public static OKSingleton getInstance(){
        if (mInstance == null){
            mInstance = new OKSingleton();
        }
        return mInstance;
    }

    public OkHttpClient getOKClient(){
        return this.mOkHttpClient;
    }

    public String getSessionCookie() {
        return mSessionCookie;
    }

    public void setSessionCookie(String mSessionCookie) {
        this.mSessionCookie = mSessionCookie;
    }

    public MicropostsAPI getMicropostsAPI() {
        return micropostsAPI;
    }

    public void setMicropostsAPI(MicropostsAPI micropostsAPI) {
        this.micropostsAPI = micropostsAPI;
    }

    public void deleteCookies(){
        cookieManager.getCookieStore().removeAll();
    }
}

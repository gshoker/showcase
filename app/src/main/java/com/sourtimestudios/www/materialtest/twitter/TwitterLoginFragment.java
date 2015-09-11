package com.sourtimestudios.www.materialtest.twitter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sourtimestudios.www.materialtest.CustomCookieManager;
import com.sourtimestudios.www.materialtest.R;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import tslamic.fancybg.FancyBackground;

/**
 * Created by user on 09/08/15.
 */
public class TwitterLoginFragment extends Fragment {

    private static final String TAG = "TwitterFragment2";
    private static final String XMLLogout = "Log out";

    private EditText etEmail;
    private EditText etPassword;
    private Button bLogin;
    private Button bSkip;
    private ProgressBar progressBar;
    private String httpResponse;
    private MicropostsAPI apiClient;

    String cookieKey;
    String cookieValue;

    OkHttpClient okHttpClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_twitterlogin,container,false);

//        final View view = v.findViewById(R.id.parent);
//
//        final int[] drawables = {
//                R.drawable.fbg_fst,
//                R.drawable.fbg_snd,
//                R.drawable.fbg_trd
//        };

//        FancyBackground.on(view)
//                .set(drawables)
//                .inAnimation(R.anim.fade_in)
//                .outAnimation(R.anim.fade_out)
//                .interval(2500)
//                .scale(ImageView.ScaleType.CENTER_CROP)
//                .listener(this)
//                .start();

        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etPassword = (EditText) v.findViewById(R.id.etPassword);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar2);

        setProgressBarVisible(false);

        bLogin = (Button)v.findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(email == null || password == null)
                    Toast.makeText(getActivity(), "Fields can't be blank. Skip if you're not registered.", Toast.LENGTH_LONG).show();
                else {
                    setProgressBarVisible(true);
//                    requestData("https://thawing-peak-6741.herokuapp.com/login", "POST",email,password);
                    hideKeyboard();

                    try {
                        run("https://thawing-peak-6741.herokuapp.com/login", "POST",email,password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });


        bSkip = (Button)v.findViewById(R.id.bSkip);
        bSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                requestData("https://thawing-peak-6741.herokuapp.com/login", "POST",null,null);
                setProgressBarVisible(true);

                try {
                    run("https://thawing-peak-6741.herokuapp.com/login", "POST", "sourtimestudios@gmail.com", "mhartl");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        okHttpClient = OKSingleton.getInstance().getOKClient();
        CustomCookieManager manager = new CustomCookieManager();
        okHttpClient.setCookieHandler(manager);

//        RestAdapter adapter = new RestAdapter.Builder()
//                .setClient(new OkClient(client))
//                .setRequestInterceptor(new RequestInterceptor() {
//                    @Override
//                    public void intercept(RequestInterceptor.RequestFacade request) {
//                        // assuming `cookieKey` and `cookieValue` are not null
//                        if (cookieKey != null && cookieValue != null) {
//                            request.addHeader("Cookie", cookieKey + "=" + cookieValue);
//                        }
//                    }
//                })
//                .build();
//
//
//        MicropostsAPI api = adapter.create(MicropostsAPI.class);
//
//        api.

        return v;

    }

    @Override
    public void onPause() {
        super.onPause();
        setProgressBarVisible(false);
    }

    private void requestData(String uri, String method, String email, String password) {

//        client = ServiceGenerator.createService(MicropostsAPI.class,"https://thawing-peak-6741.herokuapp.com/login",
//                 "sourtimestudios@gmail.com","mhartl");
//        client.login(new Callback<String>() {
//            @Override
//            public void success(String s, Response response) {
//                Log.i(TAG,"Success: "+s);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e(TAG,error.toString());
//            }
//        });
//        MyTask.execute();
//        parseXml();

//        okClient.
//        RequestPackage okRequestPackage = new RequestPackage();
//        okRequestPackage.setMethod("POST");
//        okRequestPackage.setParam("session[email]", email);
//        okRequestPackage.setParam("session[password]", password);
//        okRequestPackage.setParam("session[remember_me]", "1");
//        String encodedParams = okRequestPackage.getEncodedParams();




        if(isOnline()) {
            RequestPackage p = new RequestPackage();
            p.setMethod(method);
            p.setUri(uri);

            if (method == "POST" && uri == "https://thawing-peak-6741.herokuapp.com/login") {
                if (email != null && password != null) {
                    p.setParam("session[email]", email);
                    p.setParam("session[password]", password);
                    p.setParam("session[remember_me]", "1");
                } else {
                    p.setParam("session[email]", "sourtimestudios@gmail.com");
                    p.setParam("session[password]", "mhartl");
                    p.setParam("session[remember_me]", "1");
                }
            }

            MyTask task = new MyTask();
            task.execute(p);
        }else{
            Toast.makeText(getActivity(), "Network isn't available", Toast.LENGTH_LONG).show();
        }
    }

    public void run(String uri, String method, String email, String password) throws Exception {
        RequestBody formBody = new FormEncodingBuilder()
                .add("session[email]", email)
                .add("session[password]", password)
                .add("session[remember_me]", "1")
                .build();

        Request request = new Request.Builder()
                .url(uri)
                .post(formBody)
                .build();

//        com.squareup.okhttp.Response response = okClient.newCall(request).execute();
//        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);


        okHttpClient.newCall(request).enqueue(new com.squareup.okhttp.Callback() {


            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
//                progressBar.setVisibility(View.INVISIBLE);
//                setProgressBarVisible(false);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
//                setProgressBarVisible(false);

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.i("OKHttpReq",responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                Log.i("OKHttp Response",response.body().string());
//                response.;
                openHomeScreen();

            }
        });




    }


        protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void parseXml(){
        if(httpResponse == null) return;

        try{
            Log.i(TAG, "Parsing xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(httpResponse));

            int eventType = parser.next();

            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.TEXT && XMLLogout.equals(parser.getText())) {
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_LONG).show();
                    break;
                }
                eventType = parser.next();
            }
            openHomeScreen();
        }catch (XmlPullParserException e){
            Log.e(TAG, "XML Parser exception: " + e);
        }catch(IOException e){
            Log.e(TAG, "IOException " + e);
        }
    }

    private void openHomeScreen(){
        startActivity(new Intent(getActivity(),TwitterHomeActivity.class));
    }

    private class MyTask extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, " Started: ");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
//            httpResponse = client.login();
            Log.i(TAG, "Fetched data: " + content);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "Finished");
            progressBar.setVisibility(View.INVISIBLE);
            httpResponse = result;
            parseXml();
//            return result;
        }
    }



    private void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    private void setProgressBarVisible(boolean value){
        if (value == true){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);

        }
    }
}


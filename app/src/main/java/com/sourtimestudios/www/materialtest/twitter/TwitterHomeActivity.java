package com.sourtimestudios.www.materialtest.twitter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sourtimestudios.www.materialtest.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 10/08/15.
 */
public class TwitterHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText editText;
    private Button submitButton;

    private TwitterHomeFragment homeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_twitterhome);

        toolbar = (Toolbar) findViewById(R.id.appbar_twitter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Window w = getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.setStatusBarColor(getResources().getColor(R.color.primaryColorDarkTwit));
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new TwitterHomeFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
            homeFragment = (TwitterHomeFragment) fragment;
        }

        editText = (EditText) findViewById(R.id.etnewmessage);
        submitButton = (Button) findViewById(R.id.bsubmitpost);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                editText.setText("");
                hideKeyboard();
                OKSingleton.getInstance().getMicropostsAPI().submitPost(content,new Callback<List<TweetListAdapter.TweetModel>>() {
                    @Override
                    public void success(List<TweetListAdapter.TweetModel> tweetModels, Response response) {
                        homeFragment.getData();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.navigate) {
//            startActivity(new Intent(this, SubActivity.class));

            OKSingleton.getInstance().setSessionCookie("");
            OKSingleton.getInstance().deleteCookies();


            OKSingleton.getInstance().getMicropostsAPI().logout(new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Toast.makeText(getApplicationContext(), "Logout Successful.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),TwitterHomeActivity.class));
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });

//            OKSingleton.getInstance().setSessionCookie("");
//            OKSingleton.getInstance().deleteCookies();
//            {
//                @Override
//                public void success(String s, Response response) {
//                    Toast.makeText(getApplicationContext(),"Logout Successful.",Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    error.printStackTrace();
//                }
//            });
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

    private void logout(){}

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}



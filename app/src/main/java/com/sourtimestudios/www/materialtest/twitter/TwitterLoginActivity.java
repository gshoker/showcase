package com.sourtimestudios.www.materialtest.twitter;

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
import android.widget.ImageView;

import com.sourtimestudios.www.materialtest.R;
import com.sourtimestudios.www.materialtest.SubActivity;

import tslamic.fancybg.FancyBackground;

//import tslamic.fancybg.FancyBackground;

/**
 * Created by user on 09/08/15.
 */


public class TwitterLoginActivity extends AppCompatActivity    {

    private Toolbar toolbar;
    private static final String TAG = "FancyBackground";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_twitterlogin);



        toolbar = (Toolbar)findViewById(R.id.appbar_twitter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Window w = getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.setStatusBarColor(getResources().getColor(R.color.primaryColorDarkTwit));
        }



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null){
            fragment = new TwitterLoginFragment();
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
        }
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

        if(id == R.id.navigate){
            startActivity(new Intent(this,SubActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onStarted(FancyBackground fancyBackground) {
//
//    }
//
//    @Override
//    public void onNew(FancyBackground fancyBackground) {
//
//    }
//
//    @Override
//    public void onLoopDone(FancyBackground fancyBackground) {
//
//    }
//
//    @Override
//    public void onStopped(FancyBackground fancyBackground) {
//
//    }
}

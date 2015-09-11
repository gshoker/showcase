package com.sourtimestudios.www.materialtest;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.sourtimestudios.www.materialtest.flickr.PhotoGalleryFragment;

/**
 * Created by user on 19/08/15.
 */
public class Flickr extends SingleFragmentActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar)findViewById(R.id.appbar_flickr);
        setSupportActionBar(toolbar);

        Window w = getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.setStatusBarColor(getResources().getColor(R.color.primaryColorDarkFlickr));
        }
    }

    @Override
    protected Fragment createFragment() {
        return new PhotoGalleryFragment();
    }
}

package com.sourtimestudios.www.materialtest;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sourtimestudios.www.materialtest.restaurant.SlidingTabsBasicFragment;

/**
 * Created by user on 19/08/15.
 */
public class Restaurant extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static String TAG = "Restaurant";

//    private Toolbar toolbar;
    protected GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private GoogleMap mMap;
    private static final LatLng SYDNEY = new LatLng(51.45723220,-0.56426390);
    private static final LatLng REST1 = new LatLng(51.45423222,-0.56426390);
    private static final LatLng REST2 = new LatLng(51.45723222,-0.56826390);




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map);


        setContentView(R.layout.mapandtabs);

//        toolbar = (Toolbar)findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);

        setUpMapIfNeeded();

        buildGoogleApiClient();

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = fm.beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

//        try{
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                    mGoogleApiClient);
//            if (mLastLocation != null) {
//
//                LatLng last = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
//                double newlat = last.latitude - 0.003;
//                double newlong = last.longitude + 0.004;
//
//                mMap.addMarker(new MarkerOptions()
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.you2))
//                        .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
//                        .position(last));
//
//                mMap.addMarker(new MarkerOptions()
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.us2))
//                        .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
//                        .position(new LatLng(newlat,last.longitude)));
//
//                mMap.addMarker(new MarkerOptions()
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.us2))
//                        .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
//                        .position(new LatLng(last.latitude,newlong)));
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(last, 50));
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        mMap.addMarker(new MarkerOptions()
//                .position(SYDNEY)
//                .title("Hello world"));
//
//        mMap.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.you2))
//                .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
//                .position(SYDNEY));
//
//        mMap.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.us2))
//                .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
//                .position(REST1));
//
//        mMap.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.us2))
//                .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
//                .position(REST2));


//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(SYDNEY)      // Sets the center of the map to Mountain View
//                .zoom(14)                   // Sets the zoom
//                .bearing(90)                // Sets the orientation of the camera to east
//                .tilt(60)                   // Sets the tilt of the camera to 30 degrees
//                .build();                   // Creates a CameraPosition from the builder
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),5000, null);

    }



//    @Override
//    public void onMapLoaded() {
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                new LatLng(41.889, -87.622), 16));
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);
        if (mMap == null) {
            return;
        }


        // Initialize map options. For example:
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Log.i(TAG,"build apiclient.");

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG,"onConnected called.");

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            Log.i(TAG,"mLastLocation: " + mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());


            LatLng last = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            double newlat = last.latitude - 0.003;
            double newlong = last.longitude + 0.004;

            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.you2))
                    .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
                    .position(last));

            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.us2))
                    .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
                    .position(new LatLng(newlat,last.longitude)));

            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.us2))
                    .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
                    .position(new LatLng(last.latitude,newlong)));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(last, 14));

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }

}
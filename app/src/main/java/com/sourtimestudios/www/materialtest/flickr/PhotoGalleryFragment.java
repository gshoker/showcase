package com.sourtimestudios.www.materialtest.flickr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sourtimestudios.www.materialtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 19/08/15.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "PhotoGalleryFragment";

    GridView mGridView;
    ArrayList<GalleryItem> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        updateItems();

        Intent i = new Intent(getActivity(), PollService.class);
        getActivity().startService(i);
//        PollService.setServiceAlarm(getActivity(),true);
        PollService.setServiceAlarm(getActivity(),false);
//        getActivity().getActionBar().setTitle("Photo Gallery");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery,container,false);

        mGridView = (GridView)v.findViewById(R.id.gridView);

        setupAdapter();

//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                GalleryItem item = mItems.get(position);
//
//                Uri photoPageUri = Uri.parse(item.getPhotoPageUrl());
////                Intent i = new Intent(Intent.ACTION_VIEW, photoPageUri);
//                Intent i = new Intent(getActivity(),PhotoPageActivity.class);
//                i.setData(photoPageUri);
//
//                startActivity(i);
//            }
//        });


//        LayoutTransition transition = container.getLayoutTransition();
//        transition.enableTransitionType(LayoutTransition.CHANGING);

        return v;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
////        inflater.inflate(R.menu.fragment_photo_gallery, menu);
//        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
//        SearchView searchView = (SearchView)searchItem.getActionView();
//
//        SearchManager searchManager = (SearchManager)getActivity()
//                .getSystemService(Context.SEARCH_SERVICE);
//
//        ComponentName name = getActivity().getComponentName();
//        SearchableInfo info = searchManager.getSearchableInfo(name);
//
//        searchView.setSearchableInfo(info);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.menu_item_search:
//                getActivity().onSearchRequested();
//                return true;
//            case R.id.menu_item_clear:
//                PreferenceManager.getDefaultSharedPreferences(getActivity())
//                        .edit()
//                        .putString(FlickrFetchr.PREF_SEARCH_QUERY,null)
//                        .commit();
//                updateItems();
//                return true;
//            case R.id.menu_item_toggle_polling:
//                boolean shouldStartAlarm = !PollService.isServiceAlarmOn(getActivity());
//                PollService.setServiceAlarm(getActivity(),shouldStartAlarm);
//
//                getActivity().invalidateOptionsMenu();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//
//        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_polling);
//        if(PollService.isServiceAlarmOn(getActivity())){
//            toggleItem.setTitle(R.string.stop_polling);
//        }else{
//            toggleItem.setTitle(R.string.start_polling);
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... params) {
            Activity activity = getActivity();
            if(activity == null)
                return new ArrayList<GalleryItem>();

            String query = PreferenceManager.getDefaultSharedPreferences(activity)
                    .getString(FlickrFetchr.PREF_SEARCH_QUERY,null);

            if(query != null){
                return new FlickrFetchr().search(query);
            }else{
                return new FlickrFetchr().fetchItems();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> galleryItems) {
            mItems = galleryItems;
            setupAdapter();
        }
    }

    void setupAdapter(){
        if(getActivity() == null || mGridView == null) return;

        if(mItems != null){
//            mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),android.R.layout.simple_gallery_item, mItems));
            mGridView.setAdapter(new GalleryItemAdapter(mItems));
        }else{
            mGridView.setAdapter(null);
        }
    }

    public void updateItems(){
        new FetchItemsTask().execute();
    }

    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {
        public GalleryItemAdapter(ArrayList<GalleryItem> items){
            super(getActivity(),0,items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item2,parent,false);
            }

            ImageView imageView = (ImageView)convertView.findViewById(R.id.gallery_item_imageView);
//            imageView.setImageResource(R.drawable.blank);
            GalleryItem item = getItem(position);

            Picasso.with(getActivity())
                    .load(item.getUrl())
//                    .placeholder(R.drawable.blank)
//                    .centerCrop()
//                    .noFade()
                    .into(imageView);

            return convertView;
        }
    }
}

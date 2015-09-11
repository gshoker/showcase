package com.sourtimestudios.www.materialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by user on 19/08/15.
 */
public class MenuFragment extends ListFragment {
    String classes[] = {"Restaurant","Flickr", "Twitter"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, classes));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String clicked = classes[position];

        try{
            Class ourClass = Class.forName("com.sourtimestudios.www.materialtest." + clicked);
            Intent ourIntent = new Intent(getActivity(),ourClass);
            startActivity(ourIntent);

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}

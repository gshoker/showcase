package com.sourtimestudios.www.materialtest.restaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//import com.sourtime.exhibits.R;
import com.sourtimestudios.www.materialtest.R;
import com.sourtimestudios.www.materialtest.restaurant.Meal;

import java.util.ArrayList;

/**
 * Created by user on 05/12/14.
 */
public class PagerFragment1 extends ListFragment {

    private ArrayList<Meal> mMeals;
    private static final String TAG = "MenuFragment";

    String menuTitles[] = {"Prawn Spring Rolls","Egg Roll", "Crispy Chicken Wings", "Onion Rings","Spring Roll","Chicken balls"};

    String menuDescriptions[] = {"Prawn spring rolls with cucumber-yoghurt dip.",
            "Chicken and vegetables in egg pastry.",
            "Minced pork, shredded carrot, bean sprouts and other vegetables in pastry.",
    "Chicken wings cooked in special sauce til crispy.",
    "Fried onion segments.",
    "Deep friend balls of chicken."};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeals = new ArrayList<Meal>();
        setupMealsList();
        MenuAdapter adapter = new MenuAdapter(mMeals);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = super.onCreateView(inflater, container, savedInstanceState);
//        v.setBackgroundColor(Color.DKGRAY);
        ListView listView = (ListView) v.findViewById(android.R.id.list);

        return v;
    }


    private void setupMealsList(){
        for(int i = 0; i<menuTitles.length; i++){
            Meal m = new Meal(menuTitles[i],menuDescriptions[i]);
            mMeals.add(m);
        }
    }


    private class MenuAdapter extends ArrayAdapter<Meal> {
        public MenuAdapter(ArrayList<Meal> meals) {
            super(getActivity(), 0, meals);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.meal_item, null);
            }
            Meal m = getItem(position);
            TextView titleTextView = (TextView) convertView
                    .findViewById(R.id.mealTitle);
            titleTextView.setText(m.getmTitle());
            TextView descriptionTextView = (TextView) convertView
                    .findViewById(R.id.mealContent);
            descriptionTextView.setText(m.getmDescription());

            try{
                Bitmap bitmap;
                if(position % 3 == 0) {
                    bitmap = BitmapFactory.decodeResource(parent.getResources(), R.drawable.side3);
                }else if(position % 2 == 0){
                    bitmap = BitmapFactory.decodeResource(parent.getResources(), R.drawable.side2);
                }else if(position == 1){
                    bitmap = BitmapFactory.decodeResource(parent.getResources(),R.drawable.side1);
                }else{
                    bitmap = BitmapFactory.decodeResource(parent.getResources(),R.drawable.side0)   ;
                }

                ImageView iv = (ImageView) convertView.findViewById(R.id.mealImageView);
                iv.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
                Log.e("Restaurant", "Meal: " + position + " not loaded");
            }


            return convertView;
        }

    }
}

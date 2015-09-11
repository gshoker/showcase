package com.sourtimestudios.www.materialtest.restaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sourtimestudios.www.materialtest.R;

import java.util.ArrayList;

/**
 * Created by user on 13/08/15.
 */
public class PagerFragment0 extends ListFragment {


    private ArrayList<Meal> mMeals;
    private static final String TAG = "MenuFragment";

    String menuTitles[] = {"Beef Chow Mein","Kung Pao Chicken","Special Fried Rice","Shredded Crispy Beef",
            "Sweet & Sour Chicken","Chop Suey","Stir-fried Mixed Vegetables","Sweet & Sour Pork",
            "BBQ Spare Ribs","Crispy Aromatic Duck"};

    String menuDescriptions[] = {"Shredded beef with noodles and vegetables.",
            "A spicy stir-fry dish made with chicken, peanuts, vegetables, and chili peppers.",
            "Prawns, ham, chicken and vegetables with fried rice.",
            "Chinese flash-fried steak with a sweet gingery sauce and red peppers.",
            "Chicken, chillies, peppers, spring onions, pineapple.",
            "Pork, eggs, bean sprouts, cabbage, and celery and bound in a starch-thickened sauce.",
            "Mixed vegetables cooked in soy sauce.",
            "Pork, peppers, plum sauce, oyster sauce.",
            "Pork ribs cooked in BBQ sauce",
            "Duck, garlic, rosemary, potatoes."};


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
//                int resID = getActivity().getResources().getIdentifier("0.jpg","drawable","com.sourtime.exhibits");
//                String imgName = "meal" + position + ".jpg";
//                int resID = getActivity().getResources().getIdentifier(imgName,"drawable",getActivity()
//                                                        .getApplicationContext().getPackageName());
//                int resID = getActivity().getResources().getIdentifier("@drawable/" + imgName, "drawable", "com.sourtime.exhibits");

//                Resources res = getActivity().getResources();

//                int resID = getActivity().getApplicationContext().getResources().getIdentifier(imgName,"drawable",getActivity()
//                        .getApplicationContext().getPackageName());
//                Log.i("Restaurant","Meal " +position+" id: " + resID);

//                int resID = parent.getResources().getIdentifier(imgName, "drawable", "com.sourtime.exhibits");
//                Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(), resID);
//
//                ImageView iv = (ImageView) convertView.findViewById(R.id.mealImageView);
//                iv.setImageBitmap(bitmap);
//                int id = R.drawable.class.getField(imgName).getInt(null);
//                Log.i("Restaurant", position +" : " + id);
//                Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(), id);
//                iv.setImageResource(id);
//                iv.setImageBitmap(bitmap);

                Bitmap bitmap;
                if(position % 3 == 0){
                    bitmap = BitmapFactory.decodeResource(parent.getResources(),R.drawable.meal2);
                }else if(position % 2 == 0){
                    bitmap = BitmapFactory.decodeResource(parent.getResources(),R.drawable.meal1);
                }else{
                    bitmap = BitmapFactory.decodeResource(parent.getResources(), R.drawable.meal0);
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

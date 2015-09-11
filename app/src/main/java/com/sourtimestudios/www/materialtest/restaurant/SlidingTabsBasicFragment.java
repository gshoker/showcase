package com.sourtimestudios.www.materialtest.restaurant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourtimestudios.www.materialtest.R;
import com.sourtimestudios.www.materialtest.restaurant.PagerFragment0;

import tabs.SlidingTabLayout;

/**
 * Created by user on 03/12/14.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    /**
     * A custom {@link android.support.v4.view.ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link android.support.v4.view.ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    private FragmentManager fragmentManager;

    /**
     * Inflates the {@link android.view.View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */

//    public SlidingTabsBasicFragment(FragmentManager fm){
//        fragmentManager = fm;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        getActivity().getActionBar().setTitle("Restaurant");
        fragmentManager = getActivity().getSupportFragmentManager();
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(fragmentManager));
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);

        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends FragmentStatePagerAdapter {

        public SamplePagerAdapter(FragmentManager fm){
            super(fm);
        }

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int i) {
            CharSequence pagerFrag = "PagerFragment" + i;
            final Fragment fragment;
            Log.i(LOG_TAG,""+i);

            if(i == 0){
//                Log.i(LOG_TAG,pagerFrag.toString());
                ListFragment fragment0 = new PagerFragment0();
                return fragment0;
            }else if(i == 1){
//                Log.i(LOG_TAG,pagerFrag.toString());

                fragment = new PagerFragment1();
            }else{
//                Log.i(LOG_TAG,pagerFrag.toString());

                fragment = new PagerFragment2();
            }

            return fragment;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
        //         */
//        @Override
//        public boolean isViewFromObject(View view, Object o) {
//            return o == view;
//        }
//
//        // BEGIN_INCLUDE (pageradapter_getpagetitle)
//        /**
//         * Return the title of the item at {@code position}. This is important as what this method
//         * returns is what is displayed in the {@link SlidingTabLayout}.
//         * <p>
//         * Here we construct one using the position value, but for real application the title should
//         * refer to the item's contents.
//         */
        @Override
        public CharSequence getPageTitle(int position) {
//            return "Item " + (position + 1);
            CharSequence title;
            if(position == 0){
//                Log.i(LOG_TAG,pagerFrag.toString());
                title = "Meals";
            }else if(position == 1){
//                Log.i(LOG_TAG,pagerFrag.toString());

                title = "Sides";
            }else{
//                Log.i(LOG_TAG,pagerFrag.toString());

                title = "Chat";
            }

            return title;
        }
//        // END_INCLUDE (pageradapter_getpagetitle)
//
//        /**
//         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
//         * inflate a layout from the apps resources and then change the text view to signify the position.
//         */
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            // Inflate a new layout from our resources
////            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
////                    container, false);
//            String pagerItem = "pager"+position;
//            int resID = getResources().getIdentifier(pagerItem, "layout", getActivity().getPackageName());
//            View view = getActivity().getLayoutInflater().inflate(resID,
//                    container, false);
//
//            // Add the newly created View to the ViewPager
//            container.addView(view);
//
//            // Retrieve a TextView from the inflated View, and update it's text
////            TextView title = (TextView) view.findViewById(R.id.item_title);
////            title.setText(String.valueOf(position + 1));
//
//            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");
//
//            // Return the View
//            return view;
//        }
//
//        /**
//         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
//         * {@link View}.
//         */
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
//        }

    }
}
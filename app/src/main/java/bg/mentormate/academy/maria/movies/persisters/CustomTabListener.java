package bg.mentormate.academy.maria.movies.persisters;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

import bg.mentormate.academy.maria.movies.R;

/**
 * Created by Rado on 1/25/2015.
 */
public class CustomTabListener implements ActionBar.TabListener {
    Fragment fragment;
    public CustomTabListener(Fragment fragment) {
        this.fragment = fragment;
    }

    // When a tab is tapped, the FragmentTransaction replaces
    // the content of our main layout with the specified fragment;
    // that's why we declared an id for the main layout.
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("TAB", "onTabSelected");
       // ft.replace(R.id.movie_list, fragment);
    }

    // When a tab is unselected, we have to hide it from the user's view.
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("TAB", "onTabUnselected");
       // ft.remove(fragment);
    }

    // Nothing special here. Fragments already did the job.
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.d("TAB", "onTabReselected");

    }

}

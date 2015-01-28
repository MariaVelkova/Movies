package bg.mentormate.academy.maria.movies.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import bg.mentormate.academy.maria.movies.R;
import bg.mentormate.academy.maria.movies.persisters.Constants;
import bg.mentormate.academy.maria.movies.persisters.DownloadTask;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MovieListFragment} and the item details
 * (if present) is a {@link MovieDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link MovieListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MovieListActivity extends Activity
        implements MovieListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);



        SharedPreferences sp = getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);

        String savedString = "";
        if (sp.contains(Constants.LAST_MODIFIED)) {
            savedString = sp.getString(Constants.LAST_MODIFIED, "");
        }



        Date date = new Date(); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT); // the format of your date
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
        String dateString = dateFormat.format(date);

//        Log.d("NOW", dateString);
//        Log.d("LAST_MODIFIED", savedString);
//        Toast.makeText(this, dateString,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, savedString,Toast.LENGTH_SHORT).show();

        if (!dateString.equals(savedString)) {
            Log.d("UPDATE", dateString);
            new DownloadTask(this).execute(Constants.ROTTEN_TOMATOES_UPCOMING_MOVIES_URL);
            new DownloadTask(this).execute(Constants.ROTTEN_TOMATOES_OPENING_MOVIES_URL);
            new DownloadTask(this).execute(Constants.ROTTEN_TOMATOES_IN_THEATERS_MOVIES_URL);
            new DownloadTask(this).execute(Constants.ROTTEN_TOMATOES_BOX_OFFICE_MOVIES_URL);
        }

        //FragmentManager fragmentManager = getFragmentManager();
        //MovieListFragment movieListFragment = (MovieListFragment) fragmentManager.findFragmentById(R.id.movie_list);
        //ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //ActionBar.Tab tab = actionBar.newTab();
        //tab.setText("Upcoming");
        //tab.setTabListener(new CustomTabListener());
        //actionBar.addTab(tab);

        //get information about the screen.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float scaleFactor = metrics.density;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float widthDpi = metrics.xdpi;
        float heightDpi = metrics.ydpi;
        float widthInches = widthPixels / widthDpi;
        float heightInches = heightPixels / heightDpi;
        double diagonalInches = Math.sqrt(
                (widthInches * widthInches) +
                (heightInches * heightInches)
        );
        if (diagonalInches >= 10) {
            //Device is a 10" tablet
            mTwoPane = true;
        } else if (diagonalInches >= 7) {
            //Device is a 7" tablet
            mTwoPane = true;
        } else {
            //Device is a phone
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mTwoPane = true;
            } else {
                mTwoPane = false;
            }
        }

        if (mTwoPane == true) {
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MovieListFragment) getFragmentManager()
                    .findFragmentById(R.id.movie_list))
                    .setActivateOnItemClick(true);
        }
/*
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MovieListFragment) getFragmentManager()
                    .findFragmentById(R.id.movie_list))
                    .setActivateOnItemClick(true);
        }
*/
        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link MovieListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(MovieDetailFragment.ARG_ITEM_ID, id);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MovieDetailActivity.class);
            detailIntent.putExtra(MovieDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}

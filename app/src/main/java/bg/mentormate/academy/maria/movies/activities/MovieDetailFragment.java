package bg.mentormate.academy.maria.movies.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bg.mentormate.academy.maria.movies.R;

import bg.mentormate.academy.maria.movies.models.Movie;
import bg.mentormate.academy.maria.movies.persisters.Constants;
import bg.mentormate.academy.maria.movies.persisters.DummyContent;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private Movie movie;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Cursor movieCursor = getActivity().getContentResolver().query(Constants.CONTENT_URI_MOVIES,null,"id = ?", new String[] {getArguments().getString(ARG_ITEM_ID)}, Constants.DB_TABLE_MOVIES_ID);
            if (movieCursor.moveToFirst()) {
                //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
                movie = new Movie(
                        movieCursor.getInt(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_ID)),
                        movieCursor.getString(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_TITLE)),
                        movieCursor.getInt(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_YEAR)),
                        movieCursor.getInt(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_RUNTIME)),
                        movieCursor.getInt(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_RELEASE_DATE)),
                        movieCursor.getInt(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_RATING)),
                        movieCursor.getString(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_DESCRIPTION)),
                        movieCursor.getString(movieCursor.getColumnIndex(Constants.DB_TABLE_MOVIES_STATUS)));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.movie_detail)).setText(mItem.content);
//        }

        if (movie != null) {
            ((TextView) rootView.findViewById(R.id.movie_title)).setText(movie.getTitle());
            ((TextView) rootView.findViewById(R.id.movie_release_date)).setText(movie.getTitle());
            ((TextView) rootView.findViewById(R.id.movie_release_date)).setText(
                    String.format(getActivity().getString(R.string.release), movie.getReleaseDate()));
            ((TextView) rootView.findViewById(R.id.movie_rating)).setText(
                    String.format(getActivity().getString(R.string.rating), movie.getRating()));
            ((TextView) rootView.findViewById(R.id.movie_description)).setText(movie.getDescription());
        }
        return rootView;
    }
}

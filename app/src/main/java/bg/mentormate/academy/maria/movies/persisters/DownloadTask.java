package bg.mentormate.academy.maria.movies.persisters;

import android.accounts.NetworkErrorException;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Maria on 1/25/2015.
 */
public class DownloadTask extends AsyncTask<String,Void,String> {

    private Context context;
    private String status = "";

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("DOWNLOAD", "Starting now...");
    }

    @Override
    protected String doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        String result = "";
        try {
            URL url = new URL(params[0]);
            Log.d("URL", params[0]);


            switch (params[0]) {
                case Constants.ROTTEN_TOMATOES_UPCOMING_MOVIES_URL:
                    status = "upcoming";
                    break;
                case Constants.ROTTEN_TOMATOES_IN_THEATERS_MOVIES_URL:
                    status = "in theatre";
                    break;
                case Constants.ROTTEN_TOMATOES_BOX_OFFICE_MOVIES_URL:
                    status = "box office";
                    break;
                case Constants.ROTTEN_TOMATOES_OPENING_MOVIES_URL:
                    status = "open";
                    break;
            }
            result =  downloadFromUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Log.d("DOWNLOAD", "Just finished - " + result);
    }

    private String downloadFromUrl(URL url) throws NetworkErrorException, IOException {

        String result = "";
        HttpResponse response = null;
        InputStream inputStream = null;
        if (hasNetworkConnection()) {
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpGet httpGet = new HttpGet(String.valueOf(url));

            response = httpClient.execute(httpGet);
        } else {
            throw new NetworkErrorException("No network connection available.");
        }

        if (response != null) {
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            InputStreamReader byteReader = new InputStreamReader(inputStream, "UTF-8");

            result = createString(byteReader);
        }

        if(inputStream != null && inputStream.available() != 0) {
            inputStream.close();
        }


        updateDataBase(result);

        return result;
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = (networkInfo != null && networkInfo.isConnected());
        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = (networkInfo != null && networkInfo.isConnected());
        Log.d("CONNECTED", "Wifi connected: " + isWifiConn);
        Log.d("CONNECTED", "Mobile connected: " + isMobileConn);

        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private String createString(InputStreamReader byteReader) throws IOException {
        String resultString = "";
        BufferedReader reader = new BufferedReader(byteReader, 8);
        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        resultString = sb.toString();
        return resultString;
    }

    // Reads an InputStream and converts it to a String.
    public String readInputStream(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public void writeToFile(File file, String contentString) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);

            /** Saving the contents to the file*/
            writer.write(contentString);

            /** Closing the writer object */
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logDataBaseUpdate() {
        Date date = new Date(); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT); // the format of your date
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
        String dateString = dateFormat.format(date);

        SharedPreferences sp = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.LAST_MODIFIED, dateString);
        editor.commit();
    }

    private void updateDataBase(String result) {
        try {
            JSONObject resultJSON = new JSONObject(result);
            JSONArray movies = resultJSON.getJSONArray("movies");
            for (int i = 0; i < movies.length(); i++) {
                JSONObject movie = movies.getJSONObject(i);
                int movie_id = movie.getInt("id");
                Cursor cursor = context.getContentResolver().query(Constants.CONTENT_URI_MOVIES,null,"id = ?",new String[] {Integer.toString(movie_id) },Constants.DB_TABLE_MOVIES_ID);
                ContentValues values = new ContentValues();
                values.put(Constants.DB_TABLE_MOVIES_STATUS, status);
                if (!cursor.moveToFirst()) {
                    values.put(Constants.DB_TABLE_MOVIES_ID, movie_id);
                    values.put(Constants.DB_TABLE_MOVIES_TITLE, movie.getString("title"));
                    values.put(Constants.DB_TABLE_MOVIES_YEAR, movie.getInt("year"));
                    values.put(Constants.DB_TABLE_MOVIES_RUNTIME, Integer.valueOf(movie.getString("runtime")));
                    values.put(Constants.DB_TABLE_MOVIES_DESCRIPTION, movie.getString("synopsis"));
                    JSONObject ratings = movie.getJSONObject("ratings");
                    values.put(Constants.DB_TABLE_MOVIES_RATING, ratings.getInt("critics_score"));
                    JSONObject release_dates = movie.getJSONObject("release_dates");
                    String release_date_string = release_dates.getString("theater");
                    DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
                    Date release_date = format.parse(release_date_string);
                    int release_time = (int) (release_date.getTime() / 1000);
                    values.put(Constants.DB_TABLE_MOVIES_RELEASE_DATE, release_time);
                    //JSONArray genres = movies.getJSONArray("genres");
                    Uri uri = context.getContentResolver().insert(Constants.CONTENT_URI_MOVIES, values);
                } else {
                    int count = context.getContentResolver().update(Constants.CONTENT_URI_MOVIES, values,"id = ?", new String[] { Integer.toString(movie_id)});
                }
            }
            logDataBaseUpdate();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
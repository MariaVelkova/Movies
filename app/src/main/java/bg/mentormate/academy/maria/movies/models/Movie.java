package bg.mentormate.academy.maria.movies.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import bg.mentormate.academy.maria.movies.persisters.Constants;

/**
 * Created by Maria on 1/25/2015.
 */
public class Movie {
    private int id;
    private String title;
    private int year;
    //private ArrayList<String> genres;
    private int runtime;
    private int release_date;
    private int rating;
    private String description;
    private String status;
    //private Thumbnail thumbnail;
    //private ArrayList<Character> cast;
    //private ArrayList<Review> reviews;
    //private ArrayList<Movie> similar;


    public Movie(int id, String title, int year, int runtime, int release_date, int rating, String description, String status) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.release_date = release_date;
        this.rating = rating;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getReleaseDate() {
        Date date = new Date(this.release_date*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT); // the format of your date
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
        String dateString = dateFormat.format(date);
        return dateString;
    }

    public int getRelease_date() {
        return release_date;
    }

    public void setRelease_date(int release_date) {
        this.release_date = release_date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}

package bg.mentormate.academy.maria.movies.persisters;

import android.content.UriMatcher;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.mentormate.academy.maria.movies.models.*;
import bg.mentormate.academy.maria.movies.models.Character;

/**
 * Created by Maria on 1/25/2015.
 */
public class Constants {
    public static final String LAST_MODIFIED = "LAST_MODIFIED";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String PREFERENCES = "PREFERENCES";

    private static final String ROTTEN_TOMATOES_KEY = "n8fbrnyv2bpfuz2wp7m2c3mj";
    //public static final String ROTTEN_TOMATOES_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=" + ROTTEN_TOMATOES_KEY;
    //public static final String ROTTEN_TOMATOES_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey=" + ROTTEN_TOMATOES_KEY;
    public static final String ROTTEN_TOMATOES_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
    public static final String ROTTEN_TOMATOES_UPCOMING_MOVIES_URL = ROTTEN_TOMATOES_URL + "lists/movies/upcoming.json?apikey=" + ROTTEN_TOMATOES_KEY;
    public static final String ROTTEN_TOMATOES_IN_THEATERS_MOVIES_URL = ROTTEN_TOMATOES_URL + "lists/movies/in_theaters.json?apikey=" + ROTTEN_TOMATOES_KEY;
    public static final String ROTTEN_TOMATOES_OPENING_MOVIES_URL = ROTTEN_TOMATOES_URL + "lists/movies/opening.json?apikey=" + ROTTEN_TOMATOES_KEY;
    public static final String ROTTEN_TOMATOES_BOX_OFFICE_MOVIES_URL = ROTTEN_TOMATOES_URL + "lists/movies/box_office.json?apikey=" + ROTTEN_TOMATOES_KEY;


    // Constants for the content provider
    public static final String DB_NAME = "Movies.sql";
    public static final int DB_VERSION = 3;
    public static final String AUTHORITY  = "bg.mentormate.academy.maria.movies.persisters.CustomContentProvider";

    // ------- define tables
    public static final String DB_TABLE_MOVIES = "movies";
    public static final String DB_TABLE_REVIEWS = "reviews";
    public static final String DB_TABLE_CHARACTERS = "characters";
    public static final String DB_TABLE_ACTORS = "actors";
    public static final String DB_TABLE_GENRES = "genres";

    public static final String[] DB_TABLES = new String[]{DB_TABLE_MOVIES, DB_TABLE_REVIEWS, DB_TABLE_CHARACTERS, DB_TABLE_ACTORS, DB_TABLE_GENRES};
    public static final String[] MOVIE_STATUSES = new String[]{"upcoming", "opening", "in theaters", "box office"};
    // ------- define some Uris

    public static final Uri CONTENT_URI_MOVIES = Uri.parse("content://" + AUTHORITY
            + "/" + DB_TABLE_MOVIES);
    public static final Uri CONTENT_URI_REVIEWS = Uri.parse("content://" + AUTHORITY
            + "/" + DB_TABLE_REVIEWS);
    public static final Uri CONTENT_URI_CHARACTERS = Uri.parse("content://" + AUTHORITY
            + "/" + DB_TABLE_CHARACTERS);
    public static final Uri CONTENT_URI_ACTORS = Uri.parse("content://" + AUTHORITY
            + "/" + DB_TABLE_ACTORS);
    public static final Uri CONTENT_URI_GENRES = Uri.parse("content://" + AUTHORITY
            + "/" + DB_TABLE_GENRES);

// ------- maybe also define CONTENT_TYPE for each

    //public static final String URL                  = "content://" + AUTHORITY + "/" + DATABASE_NAME;
    //public static final Uri CONTENT_URI             = Uri.parse(URL);

    // ------- setup UriMatcher
    public static final int TAG_MOVIES = 10;
    public static final int TAG_REVIEWS = 20;
    public static final int TAG_CHARACTERS = 30;
    public static final int TAG_ACTORS = 40;
    public static final int TAG_GENRES = 50;

    public static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, DB_TABLE_MOVIES, TAG_MOVIES);
        //uriMatcher.addURI(AUTHORITY, DB_TABLE_MOVIES + "/#", TAG_MOVIE_ID);
        uriMatcher.addURI(AUTHORITY, DB_TABLE_REVIEWS, TAG_REVIEWS);
        uriMatcher.addURI(AUTHORITY, DB_TABLE_CHARACTERS, TAG_CHARACTERS);
        uriMatcher.addURI(AUTHORITY, DB_TABLE_ACTORS, TAG_ACTORS);
        uriMatcher.addURI(AUTHORITY, DB_TABLE_GENRES, TAG_GENRES);
    }


    public static final String DB_TABLE_MOVIES_ID           = "id";
    public static final String DB_TABLE_MOVIES_TITLE        = "title";
    public static final String DB_TABLE_MOVIES_YEAR         = "year";
    public static final String DB_TABLE_MOVIES_RUNTIME      = "runtime";
    public static final String DB_TABLE_MOVIES_RELEASE_DATE = "release_date";
    public static final String DB_TABLE_MOVIES_RATING       = "rating";
    public static final String DB_TABLE_MOVIES_DESCRIPTION  = "description";
    public static final String DB_TABLE_MOVIES_STATUS       = "status";
    private static String DB_TABLE_MOVIES_CREATE = " CREATE TABLE " + DB_TABLE_MOVIES + " (" +
            DB_TABLE_MOVIES_ID + " INTEGER NOT NULL PRIMARY KEY UNIQUE, " +
            DB_TABLE_MOVIES_TITLE + " TEXT NOT NULL, " +
            DB_TABLE_MOVIES_YEAR + " INTEGER NOT NULL, " +
            DB_TABLE_MOVIES_RUNTIME + " INTEGER NOT NULL, " +
            DB_TABLE_MOVIES_RELEASE_DATE + " INTEGER NOT NULL, " +
            DB_TABLE_MOVIES_RATING + " INTEGER, " +
            DB_TABLE_MOVIES_DESCRIPTION + " TEXT," +
            DB_TABLE_MOVIES_STATUS + " TEXT NOT NULL);";

    public static final String DB_TABLE_REVIEWS_ID           = "id";
    public static final String DB_TABLE_REVIEWS_MOVIE_ID     = "movie_id";
    public static final String DB_TABLE_REVIEWS_DATE         = "date";
    public static final String DB_TABLE_REVIEWS_AUTHOR       = "author";
    public static final String DB_TABLE_REVIEWS_QUOTE        = "qoute";
    private static String DB_TABLE_REVIEWS_CREATE = " CREATE TABLE " + DB_TABLE_REVIEWS + " (" +
            DB_TABLE_REVIEWS_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            DB_TABLE_REVIEWS_MOVIE_ID + " INTEGER NOT NULL, " +
            DB_TABLE_REVIEWS_DATE + " INTEGER NOT NULL, " +
            DB_TABLE_REVIEWS_AUTHOR + " TEXT NOT NULL, " +
            DB_TABLE_REVIEWS_QUOTE + " TEXT NOT NULL);";

    public static final String DB_TABLE_CHARACTERS_ID           = "id";
    public static final String DB_TABLE_CHARACTERS_MOVIE_ID     = "movie_id";
    public static final String DB_TABLE_CHARACTERS_ACTOR_ID     = "actor_id";
    public static final String DB_TABLE_CHARACTERS_CHARACTER    = "character";
    private static String DB_TABLE_CHARACTERS_CREATE = " CREATE TABLE " + DB_TABLE_CHARACTERS + " (" +
            DB_TABLE_CHARACTERS_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            DB_TABLE_CHARACTERS_MOVIE_ID + " INTEGER NOT NULL, " +
            DB_TABLE_CHARACTERS_ACTOR_ID + " INTEGER NOT NULL, " +
            DB_TABLE_CHARACTERS_CHARACTER + " TEXT NOT NULL);";

    public static final String DB_TABLE_ACTORS_ID           = "id";
    public static final String DB_TABLE_ACTORS_NAME         = "name";
    private static String DB_TABLE_ACTORS_CREATE = " CREATE TABLE " + DB_TABLE_ACTORS + " (" +
            DB_TABLE_ACTORS_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            DB_TABLE_ACTORS_NAME + " TEXT NOT NULL);";

    public static final String DB_TABLE_GENRES_ID           = "id";
    public static final String DB_TABLE_GENRES_NAME         = "name";
    private static String DB_TABLE_GENRES_CREATE = " CREATE TABLE " + DB_TABLE_GENRES + " (" +
            DB_TABLE_GENRES_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            DB_TABLE_GENRES_NAME + " TEXT NOT NULL);";

    public static Map<String, String> create_tables = new HashMap<String, String>();
    public static void init() {
        create_tables.put(DB_TABLE_MOVIES, DB_TABLE_MOVIES_CREATE);
        create_tables.put(DB_TABLE_REVIEWS, DB_TABLE_REVIEWS_CREATE);
        create_tables.put(DB_TABLE_CHARACTERS, DB_TABLE_CHARACTERS_CREATE);
        create_tables.put(DB_TABLE_ACTORS, DB_TABLE_ACTORS_CREATE);
        create_tables.put(DB_TABLE_GENRES, DB_TABLE_GENRES_CREATE);
        //create_tables.get("name");
    }

}
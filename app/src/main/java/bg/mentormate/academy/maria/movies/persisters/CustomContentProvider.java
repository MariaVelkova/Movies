package bg.mentormate.academy.maria.movies.persisters;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Maria on 1/25/2015.
 */
public class CustomContentProvider extends ContentProvider {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();

        if(database == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = Constants.uriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriType){
            case Constants.TAG_MOVIES:
                queryBuilder.setTables(Constants.DB_TABLE_MOVIES);
                if (sortOrder == null || sortOrder == ""){
                    // No sorting-> sort on names by default
                    sortOrder = Constants.DB_TABLE_MOVIES_ID;
                }
                break;
            case Constants.TAG_REVIEWS:
                queryBuilder.setTables(Constants.DB_TABLE_REVIEWS);
                if (sortOrder == null || sortOrder == ""){
                    // No sorting-> sort on names by default
                    sortOrder = Constants.DB_TABLE_REVIEWS_ID;
                }
                break;
            case Constants.TAG_CHARACTERS:
                queryBuilder.setTables(Constants.DB_TABLE_CHARACTERS);
                if (sortOrder == null || sortOrder == ""){
                    // No sorting-> sort on names by default
                    sortOrder = Constants.DB_TABLE_CHARACTERS_ID;
                }
                break;
            case Constants.TAG_ACTORS:
                queryBuilder.setTables(Constants.DB_TABLE_ACTORS);
                if (sortOrder == null || sortOrder == ""){
                    // No sorting-> sort on names by default
                    sortOrder = Constants.DB_TABLE_ACTORS_ID;
                }
                break;
            case Constants.TAG_GENRES:
                queryBuilder.setTables(Constants.DB_TABLE_GENRES);
                if (sortOrder == null || sortOrder == ""){
                    // No sorting-> sort on names by default
                    sortOrder = Constants.DB_TABLE_GENRES_ID;
                }
                break;
            default:
                throw new SQLException("Failed to extract rows from " + uri);
        }

        /*
        SQLiteDatabase db = dbName, the table name to compile the query against.
        String[] projection = columnNames, a list of which table columns to return. Passing "null" will return all.
        String selection = whereClause, filter for the selection of data, null will select all data.
        String[] selectionArgs = you may include ?s in the "whereClause"". These placeholders will get replaced by the values from the selectionArgs array.
        String[] groupBy = a filter declaring how to group rows, null will cause the rows to not be grouped.
        String[] having	= filter for the groups, null means no filter.
        String sortOrder = orderBy, table columns which will be used to order the data, null means no ordering.
        */
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        // Register to watch a content URI for changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = null;
        String tableName = getTable(uri);
        long _ID = database.insert(tableName, "", values);
        if (_ID > 0) {
            _uri = ContentUris.withAppendedId(uri, _ID);
            getContext().getContentResolver().notifyChange(_uri, null);
        }
        /*
        switch (Constants.uriMatcher.match(uri)){
            case Constants.TAG_MOVIES:
                long _ID1 = database.insert(Constants.DB_TABLE_MOVIES, "", values);
                //---if added successfully---
                if (_ID1 > 0) {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_MOVIES, _ID1);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case Constants.TAG_REVIEWS:
                long _ID2 = database.insert(Constants.DB_TABLE_REVIEWS, "", values);
                //---if added successfully---
                if (_ID2 > 0) {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_REVIEWS, _ID2);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case Constants.TAG_CHARACTERS:
                long _ID3 = database.insert(Constants.DB_TABLE_CHARACTERS, "", values);
                //---if added successfully---
                if (_ID3 > 0) {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_CHARACTERS, _ID3);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case Constants.TAG_ACTORS:
                long _ID4 = database.insert(Constants.DB_TABLE_ACTORS, "", values);
                //---if added successfully---
                if (_ID4 > 0) {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_ACTORS, _ID4);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case Constants.TAG_GENRES:
                long _ID5 = database.insert(Constants.DB_TABLE_GENRES, "", values);
                //---if added successfully---
                if (_ID5 > 0) {
                    _uri = ContentUris.withAppendedId(Constants.CONTENT_URI_GENRES, _ID5);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            default: throw new SQLException("Failed to insert row into " + uri);
        }
        */
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String tableName = getTable(uri);
        count = database.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        String tableName = getTable(uri);
        count = database.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private String getTable(Uri uri) throws SQLException {
        String tableName;
        switch (Constants.uriMatcher.match(uri)){
            case Constants.TAG_MOVIES:
                tableName = Constants.DB_TABLE_MOVIES;
                break;
            case Constants.TAG_REVIEWS:
                tableName = Constants.DB_TABLE_REVIEWS;
                break;
            case Constants.TAG_CHARACTERS:
                tableName = Constants.DB_TABLE_CHARACTERS;
                break;
            case Constants.TAG_ACTORS:
                tableName = Constants.DB_TABLE_ACTORS;
                break;
            case Constants.TAG_GENRES:
                tableName = Constants.DB_TABLE_GENRES;
                break;
            default: throw new SQLException("Failed to define table name row from " + uri);
        }
        return tableName;
    }
}

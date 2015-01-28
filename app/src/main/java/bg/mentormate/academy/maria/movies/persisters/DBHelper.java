package bg.mentormate.academy.maria.movies.persisters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Maria on 1/25/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        Constants.init();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < Constants.DB_TABLES.length; i++) {
            Log.d("table",Constants.DB_TABLES[i]);
            Log.d("query",Constants.create_tables.get(Constants.DB_TABLES[i]));
            db.execSQL(Constants.create_tables.get(Constants.DB_TABLES[i]));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ". Old data will be destroyed");
        for (int i = 0; i < Constants.DB_TABLES.length; i++) {
            db.execSQL("DROP TABLE IF EXISTS " +  Constants.DB_TABLES[i]);
        }
        onCreate(db);
    }
}

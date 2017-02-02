package com.mcnedward.fittime.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Edward on 2/7/2016.
 * <p>
 * Some help taken from: http://www.androiddesignpatterns.com/2012/05/correctly-managing-your-sqlite-database.html
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper sInstance;

    // Database title
    public static String DB_NAME = "FitTime.db";
    // Database version - increment this number to upgrade the database
    public static final int DB_VERSION = 1;

    // Tables
    public static final String EXERCISE_TABLE = "Exercises";
    // Id column, which should be the same across all tables
    public static final String ID = "Id";
    // Exercises table
    public static final String E_NAME = "Name";
    public static final String E_TYPE = "Type";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    /*****
     * CREATE STATEMENTS
     */
    private static final String createExerciseTable = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "%s TEXT, %s TEXT)", EXERCISE_TABLE, ID, E_NAME, E_TYPE);

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createExerciseTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "Update database tables...");
        dropTables(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    /*****
     * DROP Statements
     *****/
    private static final String DROP_EXERCISE_TABLE = "DROP TABLE IF EXISTS " + EXERCISE_TABLE;

    public void dropTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_EXERCISE_TABLE);
    }
}

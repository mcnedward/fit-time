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
class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper sInstance;

    // Database title
    private static String DB_NAME = "FitTime.db";
    // Database version - increment this number to upgrade the database
    private static final int DB_VERSION = 4;

    // Tables
    static final String EXERCISE_TABLE = "Exercises";
    static final String SETS_TABLE = "Sets";
    // Id column, which should be the same across all tables
    static final String ID = "Id";
    // Exercises table
    static final String E_NAME = "Name";
    static final String E_TYPE = "Type";
    //Sets table
    static final String S_EXERCISE_ID = "ExerciseId";
    static final String S_NUMBER = "Number";
    static final String S_TYPE = "Type";
    static final String S_VALUE = "Value";

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    static synchronized DatabaseHelper getInstance(Context context) {
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
    private static final String createExercisesTable = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "%s TEXT, %s TEXT)", EXERCISE_TABLE, ID, E_NAME, E_TYPE);

    private static final String createSetsTable = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT)",
            SETS_TABLE, ID, S_EXERCISE_ID, S_NUMBER, S_TYPE, S_VALUE);


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createExercisesTable);
        sqLiteDatabase.execSQL(createSetsTable);
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
    private static final String DROP_SETS_TABLE = "DROP TABLE IF EXISTS " + SETS_TABLE;

    public void dropTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_EXERCISE_TABLE);
        sqLiteDatabase.execSQL(DROP_SETS_TABLE);
    }
}

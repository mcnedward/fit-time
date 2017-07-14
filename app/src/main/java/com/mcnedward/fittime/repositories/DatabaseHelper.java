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
    private static String DB_NAME = "FitTime.db";
    // Database version - increment this number to upgrade the database
    private static final int DB_VERSION = 9;

    // Tables
    public static final String EXERCISE_TABLE = "Exercises";
    public static final String WORK_SETS_TABLE = "WorkSets";
    public static final String HISTORY_TABLE = "History";
    // Id column, which should be the same across all tables
    public static final String ID = "Id";
    // Exercises table
    public static final String E_NAME = "Name";
    public static final String E_TYPE = "Type";
    // WorkSets table
    public static final String W_EXERCISE_ID = "ExerciseId";
    public static final String W_NUMBER = "Number";
    public static final String W_TYPE = "Type";
    public static final String W_VALUE = "Value";
    public static final String W_WORK_DATE = "WorkDate";
    // History table
    public static final String H_HISTORY_DATE = "HistoryDate";
    public static final String H_EXERCISE_ID = "ExerciseId";

    private DatabaseHelper(Context context) {
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
    private static final String createExercisesTable = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT)", EXERCISE_TABLE, ID, E_NAME, E_TYPE);

    private static final String createWorkSetsTable = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s))", WORK_SETS_TABLE, ID, W_EXERCISE_ID, W_NUMBER, W_TYPE, W_VALUE, W_WORK_DATE, W_EXERCISE_ID, EXERCISE_TABLE, ID);

    private static final String createHistoryTable = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s))",
            HISTORY_TABLE, ID, H_HISTORY_DATE, H_EXERCISE_ID, H_EXERCISE_ID, EXERCISE_TABLE, ID);

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createExercisesTable);
        sqLiteDatabase.execSQL(createWorkSetsTable);
        sqLiteDatabase.execSQL(createHistoryTable);
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
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public void dropTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE + EXERCISE_TABLE);
        sqLiteDatabase.execSQL(DROP_TABLE + WORK_SETS_TABLE);
        sqLiteDatabase.execSQL(DROP_TABLE + HISTORY_TABLE);
    }
}

package com.mcnedward.fittime.repositories.workSet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mcnedward.fittime.models.WorkSet;
import com.mcnedward.fittime.repositories.DatabaseHelper;
import com.mcnedward.fittime.repositories.IRepository;
import com.mcnedward.fittime.repositories.Repository;

import java.util.List;

import static com.mcnedward.fittime.repositories.DatabaseHelper.ID;
import static com.mcnedward.fittime.repositories.DatabaseHelper.W_EXERCISE_ID;
import static com.mcnedward.fittime.repositories.DatabaseHelper.W_LOGGED;
import static com.mcnedward.fittime.repositories.DatabaseHelper.W_NUMBER;
import static com.mcnedward.fittime.repositories.DatabaseHelper.W_TYPE;
import static com.mcnedward.fittime.repositories.DatabaseHelper.W_VALUE;
import static com.mcnedward.fittime.repositories.DatabaseHelper.W_WORK_DATE;
import static com.mcnedward.fittime.repositories.DatabaseHelper.W_WORK_DATE_TIME;

/**
 * Created by Edward on 2/5/2017.
 */
public class WorkSetRepository extends Repository<WorkSet> implements IRepository<WorkSet> {

    public WorkSetRepository(Context context) {
        super(context);
    }

    public List<WorkSet> getSetsForDay(String dateStamp) {
        return query(String.format("%s = ? AND %s > 0", W_WORK_DATE, W_LOGGED), new String[]{dateStamp}, null, null, W_WORK_DATE_TIME);
    }

    public List<WorkSet> getSetsByExerciseId(long exerciseId) {
        return query(String.format("%s = ? AND %s IS 0", W_EXERCISE_ID, W_LOGGED), new String[]{String.valueOf(exerciseId)}, null, null, W_WORK_DATE_TIME);
    }

    @Override
    protected WorkSet generateObjectFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
        int exerciseId = cursor.getInt(cursor.getColumnIndexOrThrow(W_EXERCISE_ID));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(W_TYPE));
        String value = cursor.getString(cursor.getColumnIndexOrThrow(W_VALUE));
        String workDate = cursor.getString(cursor.getColumnIndexOrThrow(W_WORK_DATE));
        String workDateTime = cursor.getString(cursor.getColumnIndexOrThrow(W_WORK_DATE_TIME));
        boolean logged = cursor.getInt(cursor.getColumnIndexOrThrow(W_LOGGED)) > 0;
        return new WorkSet(id, exerciseId, type, value, workDate, workDateTime, logged);
    }

    @Override
    protected ContentValues generateContentValuesFromEntity(WorkSet entity) {
        ContentValues values = new ContentValues();
        values.put(ID, entity.getId());
        values.put(W_EXERCISE_ID, entity.getExerciseId());
        values.put(W_TYPE, entity.getType());
        values.put(W_VALUE, entity.getValue());
        values.put(W_WORK_DATE, entity.getWorkDate());
        values.put(W_WORK_DATE_TIME, entity.getWorkDateTime());
        values.put(W_LOGGED, entity.isLogged());
        return values;
    }

    @Override
    protected String getTableName() {
        return DatabaseHelper.WORK_SETS_TABLE;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{
                ID,
                W_EXERCISE_ID,
                W_TYPE,
                W_VALUE,
                W_WORK_DATE,
                W_WORK_DATE_TIME,
                W_LOGGED
        };
    }
}

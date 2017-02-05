package com.mcnedward.fittime.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mcnedward.fittime.models.Set;

import java.util.List;

import static com.mcnedward.fittime.repositories.DatabaseHelper.*;

/**
 * Created by Edward on 2/5/2017.
 */
public class SetRepository extends Repository<Set> implements ISetRepository {

    public SetRepository(Context context) {
        super(context);
    }

    @Override
    public List<Set> getSetsByExerciseId(long exerciseId) {
        return query(String.format("%s = ?", S_EXERCISE_ID), new String[]{String.valueOf(exerciseId)}, null, null, S_NUMBER);
    }

    @Override
    protected Set generateObjectFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(ID));
        int exerciseId = cursor.getInt(cursor.getColumnIndexOrThrow(S_EXERCISE_ID));
        int number = cursor.getInt(cursor.getColumnIndexOrThrow(S_NUMBER));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(S_TYPE));
        String value = cursor.getString(cursor.getColumnIndexOrThrow(S_VALUE));
        return new Set(id, exerciseId, number, type, value);
    }

    @Override
    protected ContentValues generateContentValuesFromEntity(Set entity) {
        ContentValues values = new ContentValues();
        values.put(ID, entity.getId());
        values.put(S_EXERCISE_ID, entity.getExerciseId());
        values.put(S_NUMBER, entity.getNumber());
        values.put(S_TYPE, entity.getType());
        values.put(S_VALUE, entity.getValue());
        return values;
    }

    @Override
    protected String getTableName() {
        return SETS_TABLE;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{
                ID,
                S_EXERCISE_ID,
                S_NUMBER,
                S_TYPE,
                S_VALUE
        };
    }
}

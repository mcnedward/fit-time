package com.mcnedward.fittime.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mcnedward.fittime.exceptions.EntityAlreadyExistsException;
import com.mcnedward.fittime.exceptions.EntityDoesNotExistException;
import com.mcnedward.fittime.models.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Edward on 2/8/2016.
 */
abstract class Repository<T extends BaseEntity> implements IRepository<T> {
    private static final String TAG = "Repository";

    private static final String WHERE_ID_CLAUSE = "Id = ?";
    private DatabaseHelper helper;
    private SQLiteDatabase mDatabase;

    Repository(Context context) {
        this(DatabaseHelper.getInstance(context));
        open();
    }

    private Repository(DatabaseHelper helper) {
        this.helper = helper;
        open();
    }

    public T get(long id) {
        List<T> dataList = query(WHERE_ID_CLAUSE, new String[]{String.valueOf(id)}, null, null, null);
        return !dataList.isEmpty() ? dataList.get(0) : null;
    }

    public T get(String... args) {
        List<String> selectionArgs = new ArrayList<>();
        Collections.addAll(selectionArgs, args);
        List<T> dataList = query(WHERE_ID_CLAUSE, selectionArgs.toArray(new String[selectionArgs.size()]), null, null, null);
        return dataList.get(0);
    }

    /**
     * Save an entity in the mDatabase.
     *
     * @param entity The entity to save to the mDatabase.
     * @return True if the entity was saved, false otherwise.
     * @throws EntityAlreadyExistsException If the entity already exists in the mDatabase.
     */
    public T save(T entity) throws EntityAlreadyExistsException {
        if (entity == null && entityExists(entity.getId()))
            throw new EntityAlreadyExistsException(entity.getId());
        return insert(entity);
    }

    /**
     * Update an existing entity.
     *
     * @param entity The entity to update.
     * @return True if the entity was updated, false otherwise
     * @throws EntityDoesNotExistException If the entity does not exist.
     */
    public boolean update(T entity) throws EntityDoesNotExistException {
        if (!entityExists(entity.getId()))
            throw new EntityDoesNotExistException(entity.getId());
        return change(entity);
    }

    /**
     * Delete an existing entity.
     *
     * @param entity The entity to delete.
     * @return True if the entity was deleted, false otherwise.
     * @throws EntityDoesNotExistException If the entity does not exist.
     */
    public boolean delete(T entity) throws EntityDoesNotExistException {
        if (!entityExists(entity.getId()))
            throw new EntityDoesNotExistException(entity.getId());
        return remove(entity);
    }

    /**
     * Attempts to insert an entity into the mDatabase. This does not check if an
     * entity already exists.
     *
     * @param entity The entity to insert into the mDatabase.
     * @return True if the entity was inserted, false otherwise.
     */
    private T insert(T entity) {
        try {
            mDatabase.beginTransaction();
            long id = mDatabase.insert(getTableName(), null,
                    generateContentValuesFromEntity(entity));
            entity.setId(id);
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error when trying to insert " + entity, e);
        } finally {
            mDatabase.endTransaction();
        }
        return entity;
    }

    private boolean change(T entity) {
        int rowsUpdated = 0;
        try {
            mDatabase.beginTransaction();
            ContentValues values = generateContentValuesFromEntity(entity);
            rowsUpdated = mDatabase.update(getTableName(), values, WHERE_ID_CLAUSE, new String[]{String.valueOf(entity.getId())});
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error when trying to insert " + entity, e);
        } finally {
            mDatabase.endTransaction();
        }
        return rowsUpdated != 0;
    }

    /**
     * Delete an entity from the mDatabase.
     *
     * @param entity The entity to delete.
     * @return True if the entity was deleted, false otherwise.
     */
    private boolean remove(T entity) {
        int rowsDeleted = 0;
        try {
            mDatabase.beginTransaction();
            rowsDeleted = mDatabase.delete(getTableName(), WHERE_ID_CLAUSE, new String[]{String.valueOf(entity.getId())});
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error when trying to insert " + entity, e);
        } finally {
            mDatabase.endTransaction();
        }
        return rowsDeleted != 0;
    }

    /**
     * Retrieve all data for this source.
     *
     * @return A list of all entities in this data source.
     */
    @Override
    public List<T> getAll() {
        return query(null, null, null, null, null);
    }

    /**
     * Retrieve all data for this source.
     * @param groupBy
     * @param having
     * @param orderBy
     * @return A list of all entities in this data source.
     */
    @Override
    public List<T> getAll(String groupBy, String having, String orderBy) {
        return query(null, null, groupBy, having, orderBy);
    }

    /**
     * Queries the data table based on the options passed in.
     *
     * @param whereClause   The filter of which rows to return (WHERE clause), with arguments passed in as "?"
     * @param whereArgs     The arguments for the WHERE clause
     * @param groupBy       How to group rows
     * @param having        Which rows to include in the cursor results
     * @param orderBy       The order of the results to return
     * @return A list of data from the table
     */
    protected List<T> query(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy) {
        if (!mDatabase.isOpen()) return new ArrayList<>();
        List<T> data = new ArrayList<>();
        Cursor cursor = null;
        try {
            mDatabase.beginTransaction();
            cursor = mDatabase.query(getTableName(), getAllColumns(), whereClause, whereArgs, groupBy, having, orderBy);
            while (cursor.moveToNext()) {
                data.add(generateObjectFromCursor(cursor));
            }
        } finally {
            if (cursor != null)
                cursor.close();
            mDatabase.endTransaction();
        }
        return data;
    }

    protected List<T> queryDistinct(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy, String limit) {
        List<T> data = new ArrayList<>();
        Cursor cursor = null;
        try {
            mDatabase.beginTransaction();
            cursor = mDatabase.query(true, getTableName(), getAllColumns(), whereClause, whereArgs, groupBy, having, orderBy, limit);
            while (cursor.moveToNext()) {
                data.add(generateObjectFromCursor(cursor));
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            if (cursor != null)
                cursor.close();
            mDatabase.endTransaction();
        }
        return data;
    }

    protected abstract T generateObjectFromCursor(Cursor cursor);

    protected abstract ContentValues generateContentValuesFromEntity(T entity);

    protected abstract String getTableName();

    protected abstract String[] getAllColumns();

    /**
     * This checks if an entity with a certain id already exists in the
     * mDatabase.
     *
     * @param id The id of the entity of check.
     * @return True if the entity already exists, false otherwise.
     */
    private boolean entityExists(long id) {
        Cursor cursor = null;
        try {
            cursor = mDatabase.query(getTableName(), new String[]{DatabaseHelper.ID},
                    DatabaseHelper.ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            cursor.moveToFirst();
            return cursor.getCount() > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error checking if entity exists with id: " + id, e);
            return false;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private SQLiteDatabase openToRead() throws android.database.SQLException {
        mDatabase = helper.getReadableDatabase();
        return mDatabase;
    }

    private SQLiteDatabase open() throws android.database.SQLException {
        mDatabase = helper.getWritableDatabase();
        return mDatabase;
    }

    @Override
    public void close() {
        if (helper != null)
            helper.close();
    }
}

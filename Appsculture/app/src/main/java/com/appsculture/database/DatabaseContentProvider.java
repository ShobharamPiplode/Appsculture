package com.appsculture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class for providing methods to perform CRUD Operation on SQLite database.
 *
 * Created by shobharam.piplode on 11/10/2015.
 */
public abstract class DatabaseContentProvider
{
    // SQLiteDatabase object for performing CRUD Operation
    private SQLiteDatabase mDb;

    //DatabaseHelper class object for accessing database.
    private DatabaseHelper mDbHelper;

    //
    private Context mContext;

    public DatabaseContentProvider(Context context)
    {
        this.mContext = context;
        this.mDbHelper = new DatabaseHelper(mContext);
        this.mDb = mDbHelper.getWritableDatabase();
    }

    /**
     * Method to close Database.
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * Abstract method to Convert Cursor object to Entity.
     *
     * @param cursor  - Database Cursor ojbect
     * @param <T> - Type of Entity object
     * @return -  Entuty Object
     */
    protected abstract <T> T cursorToEntity(Cursor cursor);

    /**
     * Method to perform Delete operation.
     *
     * @param tableName - Name of Table
     * @param selection - Selection String
     * @param selectionArgs - Selection Arguments
     * @return - No of Deleted Rows in Integer
     */
    public int delete(String tableName, String selection,
                      String[] selectionArgs) {
        return mDb.delete(tableName, selection, selectionArgs);
    }

    /**
     * Method to insert values to table.
     *
     * @param tableName -  Name of Table
     * @param values - ContentValues object
     * @return - Row ID
     */
    public long insert(String tableName, ContentValues values)
    {

        try {
             return mDb.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Method to perform Query operation.
     *
     * @param tableName - Name of Table
     * @param columns - Columns of table which need to Select
     * @param selection - Selection String
     * @param selectionArgs - Selection Arguments
     * @param sortOrder - Type of Sort Order
     * @return - Cursor Object
     */
    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String sortOrder) {

        final Cursor cursor = mDb.query(tableName, columns,
                selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }
}
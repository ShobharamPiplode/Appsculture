package com.appsculture.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appsculture.database.dao.IVocabularySchema;

/**
 * Database Helper Class for Creating SqLite Database.
 *
 * Created by shobharam.piplode on 3/16/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    // Database name
    public static String DATABASE_NAME="vocabularies.db";

    // Database Version
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            db.execSQL(IVocabularySchema.CREATE_TABLE_VOCABULARY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + IVocabularySchema.CREATE_TABLE_VOCABULARY);
        // create new tables
        onCreate(db);
    }
}

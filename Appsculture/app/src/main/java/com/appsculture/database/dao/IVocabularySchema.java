package com.appsculture.database.dao;

/**
 * Interface to define Vocabulary Schema and Constants.
 *
 * Created by shobharam.piplode on 11/10/2015.
 */
public interface IVocabularySchema
{
    // Table Name
    String TABLE_VOCABULARY="vocabulary";

    // Name of the Rows
    String ID="id";
    String WORD="word";
    String VARIANT="variant";
    String MEANING="meaning";
    String RATIO="ratio";
    String URL="url";

    // Table Create VOCABULARY
    String CREATE_TABLE_VOCABULARY = "CREATE TABLE " + TABLE_VOCABULARY + "("
            + ID + " INTEGER PRIMARY KEY,"
            + WORD + " TEXT,"
            + VARIANT + " INTEGER,"
            + MEANING  + " TEXT,"
            + RATIO + " DOUBLE,"
            + URL  + " TEXT" + ")";

    String[] USER_COLUMNS = new String[] { ID,
            WORD, VARIANT, MEANING, RATIO,URL};
}

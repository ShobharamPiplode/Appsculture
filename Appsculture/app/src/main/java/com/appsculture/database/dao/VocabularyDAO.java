package com.appsculture.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;

import com.appsculture.database.DatabaseContentProvider;
import com.appsculture.models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to implemet DAO pattern for performing CRUD operation on Databse.
 *
 * Created by shobharam.piplode on 11/10/2015.
 */
public class VocabularyDAO extends DatabaseContentProvider implements IVocabularySchema,IVocabularyDAO
{
    private Cursor cursor;
    private ContentValues initialValues;

    public VocabularyDAO(Context context) {
        super(context);
    }

    @Override
    public Word fetchWordById(int id)
    {
        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = ID + " = ?";
        Word word = new Word();
        cursor = super.query(TABLE_VOCABULARY, USER_COLUMNS, selection,selectionArgs, ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                word = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return word;
    }

    @Override
    public List<Word> fetchWords()
    {
        // Constructs a selection clause with a replaceable parameter
        String mSelectionClause =  RATIO+">?";
        // Defines an array to contain the selection arguments
        String[] selectionArgs = {"0"};

        List<Word> wordList = new ArrayList<>();
        cursor = super.query(TABLE_VOCABULARY, USER_COLUMNS, mSelectionClause,selectionArgs, ID);

        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Word word = cursorToEntity(cursor);
                wordList.add(word);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return wordList;
    }

    @Override
    public boolean addWord(Word word) {
        try {
            return super.insert(TABLE_VOCABULARY, getContentValue(word)) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    @Override
    public boolean addWords(List<Word> words) {
        return false;
    }

    @Override
    public int deleteAllWords() {

        try {
            return super.delete(TABLE_VOCABULARY,null,null);
        } catch (SQLiteConstraintException ex){
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    protected Word cursorToEntity(Cursor cursor){

        Word word = new Word();

        int idIndex;
        int wordIndex;
        int variantIndex;
        int meaningIndex;
        int ratioIndex;
        int urlIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(ID);
                word.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(WORD) != -1)
            {
                wordIndex = cursor.getColumnIndexOrThrow(WORD);
                word.setWord(cursor.getString(wordIndex));
            }
            if (cursor.getColumnIndex(VARIANT) != -1)
            {
                variantIndex = cursor.getColumnIndexOrThrow(VARIANT);
                word.setVarient(cursor.getInt(variantIndex));
            }
            if (cursor.getColumnIndex(MEANING) != -1) {
                meaningIndex = cursor.getColumnIndexOrThrow(MEANING);
                word.setMeaning(cursor.getString(meaningIndex));
            }
            if (cursor.getColumnIndex(RATIO) != -1) {
                ratioIndex = cursor.getColumnIndexOrThrow(RATIO);
                word.setRatio(cursor.getDouble(ratioIndex));
            }
            if (cursor.getColumnIndex(URL) != -1) {
                urlIndex = cursor.getColumnIndexOrThrow(URL);
                word.setImageUrl(cursor.getString(urlIndex));
            }
        }
        return word;

    }

    private ContentValues getContentValue(Word word)
    {
        initialValues = new ContentValues();

        initialValues.put(ID, word.getId());
        initialValues.put(WORD, word.getWord());
        initialValues.put(VARIANT, word.getVarient());
        initialValues.put(MEANING, word.getMeaning());
        initialValues.put(RATIO, word.getRatio());
        initialValues.put(URL, word.getImageUrl());

        return initialValues;
    }


}

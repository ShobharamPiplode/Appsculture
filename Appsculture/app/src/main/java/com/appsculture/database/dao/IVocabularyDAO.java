package com.appsculture.database.dao;

import com.appsculture.models.Word;

import java.util.List;

/**
 * Interface to define Vocabulary Database Access object
 *
 * Created by shobharam.piplode on 11/10/2015.
 */
public interface IVocabularyDAO
{
    public Word fetchWordById(int id);
    public List<Word> fetchWords();
    public boolean addWord(Word word);
    public boolean addWords(List<Word> words);
    public int deleteAllWords();
}

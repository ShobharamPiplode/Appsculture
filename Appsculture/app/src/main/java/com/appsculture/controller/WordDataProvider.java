
package com.appsculture.controller;

import android.content.Context;
import android.util.Log;

import com.appsculture.database.dao.VocabularyDAO;
import com.appsculture.models.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;

/**
 * Class to download JSON data , parse and store it to SQLite Database.
 *
 * Created by shobharam.piplode on 3/16/2016.
 */
public class WordDataProvider
{
    private static final String TAG = "WordDataProvider";

    private static String TAG_WORDS = "words";
    private static String TAG_ID = "id";
    private static String TAG_WORD = "word";
    private static String TAG_VARIANT = "variant";
    private static String TAG_MEANING= "meaning";
    private static String TAG_RATIO = "ratio";

    /**
     * Method to make URLConnection for Download JSON Data.
     *
     * @param urlString - String URL
     * @return - JSONObject Response object
     */
    protected JSONObject parseUrl(String urlString)
    {
        InputStream is = null;
        try {
            java.net.URL url = new java.net.URL(urlString);
            URLConnection urlConnection = url.openConnection();
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            return new JSONObject(json);
        } catch (Exception e) {
            Log.d(TAG, "Failed to parse the json for media list", e);
            return null;
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     *Method to parse JSON data and store it to SQLite database.
     *
     * @param context
     * @param url - String URL
     * @return - Boolean object true if Parse and Store succeed
     * @throws JSONException
     */
    public static Boolean buildVocabulary(Context context,String url) throws JSONException
    {
        JSONObject jsonObj = new WordDataProvider().parseUrl(url);
        JSONArray words = jsonObj.getJSONArray(TAG_WORDS);

        VocabularyDAO vocabularyDAO = null;
        if (null != words)
        {
            try {
                vocabularyDAO = new VocabularyDAO(context);
                vocabularyDAO.deleteAllWords();

                for (int i = 0; i < words.length(); i++)
                {
                    Word wordObject = new Word();

                    JSONObject object = words.getJSONObject(i);
                    int id = object.getInt(TAG_ID);
                    String  word = object.getString(TAG_WORD);
                    int variant = object.getInt(TAG_VARIANT);
                    String  meaning = object.getString(TAG_MEANING);
                    double ratio = object.getDouble(TAG_RATIO);
                    String  imageUrl = "http://appsculture.com/vocab/images/"+id+".png";

                    wordObject.setId(id);
                    wordObject.setWord(word);
                    wordObject.setVarient(variant);
                    wordObject.setMeaning(meaning);
                    wordObject.setRatio(ratio);
                    wordObject.setImageUrl(imageUrl);

                    vocabularyDAO.addWord(wordObject);
                }
            } finally
            {
                vocabularyDAO.close();
            }
            return true;
        }
        return false;
    }
}

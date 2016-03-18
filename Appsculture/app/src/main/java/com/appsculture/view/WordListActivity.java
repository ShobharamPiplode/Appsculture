package com.appsculture.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.appsculture.R;
import com.appsculture.database.dao.VocabularyDAO;
import com.appsculture.models.Word;

import java.util.List;


/**
 * Activity class to display list of Words and their meanings.
 */

public class WordListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
    }

    private void setupRecyclerView(List<Word> wordList)
    {
        if(wordList!=null && wordList.size()>0)
        {
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.word_list);
            assert recyclerView != null;
            recyclerView.setAdapter(new WordItemRecyclerViewAdapter(wordList));
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // Start Asynktask to load data from Sqlite Database.
        new DataLoaderTask().execute();
    }

    /**
     * Adapter class to display list of Words.
     */
    public class WordItemRecyclerViewAdapter
            extends RecyclerView.Adapter<WordItemRecyclerViewAdapter.ViewHolder> {

        private final List<Word> mValues;
        private final float mAspectRatio = 9f / 16f;

        public WordItemRecyclerViewAdapter(List<Word> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.word_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            holder.mItem = mValues.get(position);

            holder.titleView.setText(holder.mItem.getWord().toUpperCase());
            holder.descrView.setText(holder.mItem.getMeaning());

            AQuery aq = new AQuery(holder.mView);
                aq.id(holder.imgView).width(150).image( holder.mItem.getImageUrl(),
                        true, true, 0, R.drawable.default_video, null, 0, (float)holder.mItem.getRatio());


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public Word mItem;

            TextView titleView;
            TextView descrView;
            ImageView imgView;

            public ViewHolder(View view)
            {
                super(view);

                mView = view;
                imgView = (ImageView) view.findViewById(R.id.imageView_video_thumbnail);
                titleView = (TextView) view.findViewById(R.id.textView_title);
                descrView = (TextView) view.findViewById(R.id.textView_text);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + titleView.getText() + "'";
            }
        }
    }

    /**
     * AsyncTask class to load data from Sqlite Database
     */
  class DataLoaderTask extends AsyncTask<Void,Void,List<Word>>
  {
      @Override
      protected List<Word> doInBackground(Void... params) {

          VocabularyDAO vocabularyDAO =null;
          try{
              vocabularyDAO =  new VocabularyDAO(WordListActivity.this);
              if(vocabularyDAO!=null)
              {
                  return vocabularyDAO.fetchWords();
              }
          }catch (Exception e){
              e.printStackTrace();
          }finally {
              if(vocabularyDAO!=null)
                  vocabularyDAO.close();
          }
          return null;
      }

      @Override
      protected void onPostExecute(List<Word> words)
      {
          super.onPostExecute(words);

          if(words!=null && words.size()>0)
          {
              setupRecyclerView(words);
          }
      }
  }
}

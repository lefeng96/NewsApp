package com.example.rkjc.news_app_2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.MenuInflater;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private TextView mSearchResultsTextView;
    private RecyclerView rView;
    private NewsRecyclerViewAdapter adapter;
    private ArrayList<NewsItem> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = findViewById(R.id.news_recyclerview);
        adapter=new NewsRecyclerViewAdapter(news, this);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));

    }


    public class NewsQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String results= null;
            try {
                results =  NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);
//            if (results != null && !results.equals("")) {
//                mSearchResultsTextView.setText(results);
//            }
            news = JsonUtils.parseNews(results);
            adapter.newsItemList.addAll(news);
            adapter.notifyDataSetChanged();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.get_news) {
//            news = JsonUtils.parseNews(results);
//            adapter.newsItemList = news;
//            adapter.notifyDataSetChanged();
            getNews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getNews() {
        URL newsUrl = NetworkUtils.buildURL();
        // COMPLETED (4) Create a new GithubQueryTask and call its execute method, passing in the url to query
        new NewsQueryTask().execute(newsUrl);
    }




}

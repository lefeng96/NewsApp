package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.ListItemClickListener {

    private RecyclerView rView;
    private NewsRecyclerViewAdapter adapter;
    private ArrayList<NewsItem> news = new ArrayList<>();
    NewsItemViewModel newsItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        rView = findViewById(R.id.news_recyclerview);

        adapter=new NewsRecyclerViewAdapter(newsItemViewModel, MainActivity.this);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
        newsItemViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable final List<NewsItem> newsItems) {
                adapter.setNewsItems(newsItems);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rView.setLayoutManager(layoutManager);

        rView.setHasFixedSize(true);

        UpdateUtilities.scheduleUpdate(this);
    }

//
//    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
//
//        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(URL... params) {
//            URL searchUrl = params[0];
//            String results= null;
//            try {
//                results =  NetworkUtils.getResponseFromHttpUrl(searchUrl);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return results;
//        }
//
//        // COMPLETED (3) Override onPostExecute to display the results in the TextView
//        @Override
//        protected void onPostExecute(String results) {
//            super.onPostExecute(results);
////            if (results != null && !results.equals("")) {
////                mSearchResultsTextView.setText(results);
////            }
//            news = JsonUtils.parseNews(results);
//            adapter.newsItemList.addAll(news);
//            adapter.notifyDataSetChanged();
//        }
//
//
//    }

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
            newsItemViewModel.update();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void getNews() {
//        URL newsUrl = NetworkUtils.buildURL();
//        // COMPLETED (4) Create a new GithubQueryTask and call its execute method, passing in the url to query
//        new NewsQueryTask().execute(newsUrl);
//    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Uri web_page = Uri.parse(newsItemViewModel.getAllNewsItems().getValue().get(clickedItemIndex).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, web_page);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }




}

package com.example.rkjc.news_app_2;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String JSONString){
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try{
            JSONObject main = new JSONObject(JSONString);
            JSONArray list = main.getJSONArray("articles");

            for(int i = 0; i < list.length(); i++){
                JSONObject newsItem = list.getJSONObject(i);
                newsList.add(new NewsItem(newsItem.getString("author"), newsItem.getString("title"), newsItem.getString("description"), newsItem.getString("url"), newsItem.getString("urlToImage"), newsItem.getString("publishedAt")));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }

}



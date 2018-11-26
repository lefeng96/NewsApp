package com.example.rkjc.news_app_2.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsItemDAO {

    @Insert
    void insert(List<NewsItem> newsItem);

    //If conflicts are possible, you can use @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Delete
    void delete(List<NewsItem> newsItem);

    @Query("DELETE FROM news_item")
    void deleteAll();

    @Query("SELECT * FROM news_item ORDER BY id ASC")
    LiveData<List<NewsItem>> getAllNewsItems();

}

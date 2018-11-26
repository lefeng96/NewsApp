package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemRepository mRepository;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemViewModel(Application application) {
        super(application);
        mRepository = new NewsItemRepository(application);
        mAllNewsItems = mRepository.getAllWords();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public void update(){mRepository.makeNewsSearchQuery();}

}

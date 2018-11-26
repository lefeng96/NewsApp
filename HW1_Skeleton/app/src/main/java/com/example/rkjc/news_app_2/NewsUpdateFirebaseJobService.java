package com.example.rkjc.news_app_2;

import com.example.rkjc.news_app_2.data.NewsItemRepository;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.RetryStrategy;
import android.content.Context;
import com.firebase.jobdispatcher.JobService;
import android.os.AsyncTask;
import android.util.Log;

public class NewsUpdateFirebaseJobService extends JobService {
    private AsyncTask mBackgroundTask;
    NewsItemRepository mNewsItemRepo;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = NewsUpdateFirebaseJobService.this;
                UpdateTask.executeTask(context, UpdateTask.UPDATE_NEWS);
                mNewsItemRepo = new NewsItemRepository(getApplication());
                mNewsItemRepo.makeNewsSearchQuery();
                Log.d("ASYNCTASK", "doInBackground: HIIII");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false );
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}

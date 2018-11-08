package com.example.rkjc.news_app_2;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.Intent;
import android.net.Uri;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    private static final String TAG = NewsRecyclerViewAdapter.class.getSimpleName();

    ArrayList<NewsItem> newsItemList;
    Context context;

    public NewsRecyclerViewAdapter(ArrayList<NewsItem> newsItemList, Context context) {
        this.newsItemList= newsItemList;
        this.context=context;
    }
    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context1 = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context1);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( NewsItemViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public TextView publishedAt;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            publishedAt = itemView.findViewById(R.id.date);
        }


        void bind(final int listIndex) {
            title.setText("Title: " +newsItemList.get(listIndex).getTitle());
            description.setText("Description: " +newsItemList.get(listIndex).getDescription());
            publishedAt.setText("Date: " +newsItemList.get(listIndex).getPublishedAt());
            final String url =newsItemList.get(listIndex).getUrl();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                }
            });
        }
    }

}

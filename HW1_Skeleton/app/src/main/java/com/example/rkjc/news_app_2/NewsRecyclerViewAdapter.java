package com.example.rkjc.news_app_2;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.Intent;
import android.net.Uri;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    private static final String TAG = NewsRecyclerViewAdapterAdapter.class.getSimpleName();

    ArrayList<NewsItem> newsItemList;
    Context context;

    public NewsAdapter(ArrayList<NewsItem> newsItemList, Context context) {
        this.newsItemList= newsItemList;
        this.context=context;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
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
            view.setOnClickListener(this);
        }


        void bind(final int listIndex) {
            title.setText(newsItemList.get(listIndex).getTitle());
            description.setText(newsItemList.get(listIndex).getDescription());
            publishedAt.setText(newsItemList.get(listIndex).getPublishedAt());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(newsItemList.get(listIndex).getUrl().getText().toString()));
                    context.startActivity(intent);
                }
            });
        }
    }

}

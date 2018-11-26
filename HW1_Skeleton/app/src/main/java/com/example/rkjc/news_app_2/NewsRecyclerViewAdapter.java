package com.example.rkjc.news_app_2;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private List<NewsItem> articles;
    final private ListItemClickListener mOnClickListener;

    private NewsItemViewModel viewModel;

    public NewsRecyclerViewAdapter( NewsItemViewModel viewModel, ListItemClickListener mOnClickListener){
        this.viewModel = viewModel;
        this.mOnClickListener = mOnClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( NewsViewHolder articleViewHolder, final int i) {
        articleViewHolder.bind(i);
    }

    void setNewsItems(List<NewsItem> newsItems){
        articles = newsItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(articles != null){
            return articles.size();
        } else {
            return 0;
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mArticleImage;
        TextView mArticleTitle;
        TextView mArticleDescription;


        public NewsViewHolder(View itemView) {
            super(itemView);

            mArticleImage = itemView.findViewById(R.id.image);
            mArticleTitle = itemView.findViewById(R.id.title);
            mArticleDescription = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);
        }

        void bind(int position){
            Uri imageLink = Uri.parse(articles.get(position).getUrlToImage());

            if(imageLink != null){
                Picasso.get().load(imageLink).into(mArticleImage);
            }

            mArticleTitle.setText(articles.get(position).getTitle());
            mArticleDescription.setText(articles.get(position).getPublishedAt() + " . " +articles.get(position).getDescription());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}

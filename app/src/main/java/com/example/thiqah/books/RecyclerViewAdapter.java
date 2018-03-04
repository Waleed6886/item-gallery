package com.example.thiqah.books;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import api.Author;
import api.Book;
import api.CoverPhotos;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DummyDataViewHolder> {

    private List<Book> dummyDataList = new ArrayList<>();
    private List<Author> authorsDataList = new ArrayList<>();
    private List<CoverPhotos> photosDataList = new ArrayList<>();
    private SimpleArrayMap<Integer, String> photoArrayMap = new SimpleArrayMap<>();
    private SimpleArrayMap<Integer, List<Author>> authorArrayMap = new SimpleArrayMap<>();
    List<Author> list;


    public static class DummyDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.book_name)
        TextView bookName;
        @BindView(R.id.author_name)
        TextView authorName;
        @BindView(R.id.cover_photo)
        ImageView coverPhoto;

        View view;

        DummyDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            view = itemView;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(DetailActivity.KEY_BOOKS, (Book) view.getTag(R.id.Books));
            bundle.putParcelable(DetailActivity.KEY_AUTHOR, (Author) view.getTag(R.id.Authors));
            bundle.putParcelable(DetailActivity.KEY_PHOTOS, (CoverPhotos) view.getTag(R.id.Photos));
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }

    }

    RecyclerViewAdapter() {
    }

    @Override
    public DummyDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        view.setClickable(true);
        return new DummyDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DummyDataViewHolder holder, int position) {
        if (position < photosDataList.size()) {
            Glide.with(holder.view.getContext()).load(photoArrayMap.get(dummyDataList.get(position).getID())).into(holder.coverPhoto);
            holder.view.setTag(R.id.Photos, photosDataList.get(position));
        }
        if (position < dummyDataList.size()) {
            holder.bookName.setText(dummyDataList.get(position).getTitle());
            holder.view.setTag(R.id.Books, dummyDataList.get(position));
        }
        if (position < authorsDataList.size()) {
            String authorsCo = "";
            List<Author> authorsList = authorArrayMap.get(dummyDataList.get(position).getID());
            for (int i = 0; i < authorsList.size(); i++) {
                authorsCo +=" "+authorsList.get(i).getFirstName();
            }
            holder.authorName.setText(authorsCo);
            holder.view.setTag(R.id.Authors, authorsDataList.get(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return dummyDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setDataList(List<Book> dummyDataList) {
        this.dummyDataList = dummyDataList;
        notifyDataSetChanged();
    }

    void setPhotosDataList(List<CoverPhotos> photosDataList) {
        this.photosDataList = photosDataList;
        if (photosDataList.size() != 0) {
            for (int i = 0; i < photosDataList.size(); i++) {
                photoArrayMap.put(photosDataList.get(i).getIDBook(), photosDataList.get(i).getUrl());
            }
        }
        notifyDataSetChanged();
    }

    void setAuthorsDataList(List<Author> authorsDataList) {
        this.authorsDataList = authorsDataList;
        if (authorsDataList.size() == 0) {
            return;
        }
        for (int i = 0; i < authorsDataList.size(); i++) {
            if (authorArrayMap.indexOfKey(authorsDataList.get(i).getIDBook()) > -1)
                list = authorArrayMap.get(authorsDataList.get(i).getIDBook());
            else list = new ArrayList<>();

            list.add(authorsDataList.get(i));
            authorArrayMap.put(authorsDataList.get(i).getIDBook(), list);
        }
        notifyDataSetChanged();
    }
}

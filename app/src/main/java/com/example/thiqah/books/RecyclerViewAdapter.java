package com.example.thiqah.books;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thiqah.Model.Author;
import com.example.thiqah.Model.Book;
import com.example.thiqah.Model.CoverPhotos;
import com.ramotion.foldingcell.FoldingCell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DummyDataViewHolder> {

    private List<Book> booksDataList = new ArrayList<>();
    private List<Author> authorsDataList = new ArrayList<>();
    private List<CoverPhotos> photosDataList = new ArrayList<>();
    private SimpleArrayMap<Integer, String> photoArrayMap = new SimpleArrayMap<>();
    private SimpleArrayMap<Integer, List<Author>> authorArrayMap = new SimpleArrayMap<>();
    List<Author> list;


    public static class DummyDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.folding_cell)
        FoldingCell folding_cell;
        @BindView(R.id.book_name_left)
        TextView bookNameL;
        @BindView(R.id.book_name_right)
        TextView bookNameR;
        @BindView(R.id.number_pages)
        TextView pageNumber;
        @BindView(R.id.number_author)
        TextView authorNumber;
        @BindView(R.id.cover_photo)
        ImageView coverPhoto;
        @BindView(R.id.header_bookTitle)
        TextView header_title;
        @BindView(R.id.head_image_Tile_text)
        TextView headImageTitleText;
        @BindView(R.id.head_image)
        ImageView headImage;
        @BindView(R.id.list_authors)
        TextView authorList;
        @Nullable
        @BindView(R.id.description_detail)
        TextView descriptionDetail;
        @BindView(R.id.content_Excerpt)
        TextView excerptTextView;
        @BindView(R.id.publish_date)
        TextView publish_date;
        View view;

        DummyDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    folding_cell.toggle(false);
                    excerptTextView.setMovementMethod(new ScrollingMovementMethod());
                    descriptionDetail.setMovementMethod(new ScrollingMovementMethod());

                }
            });
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(DummyDataViewHolder holder, int position) {
        if (position < photosDataList.size()) {
            holder.folding_cell.fold(true);
            String image = photoArrayMap.get(booksDataList.get(position).getID());
            Glide.with(holder.view.getContext()).load(image).into(holder.coverPhoto);
            Glide.with(holder.view.getContext()).load(image).into(holder.headImage);
            holder.view.setTag(R.id.Photos, photosDataList.get(position));
        }
        if (position < booksDataList.size()) {
            holder.folding_cell.fold(true);
            holder.bookNameL.setText(booksDataList.get(position).getTitle());
            holder.bookNameR.setText(booksDataList.get(position).getTitle());
            holder.pageNumber.setText(booksDataList.get(position).getPageCount() + "");
            holder.header_title.setText(booksDataList.get(position).getTitle());
            holder.headImageTitleText.setText(booksDataList.get(position).getTitle());
            holder.descriptionDetail.setText(booksDataList.get(position).getDescription() + "");
            holder.excerptTextView.setText(booksDataList.get(position).getExcerpt() + "");
            holder.view.setTag(R.id.Books, booksDataList.get(position));


            String d = booksDataList.get(position).getPublishDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            try {
                Date date = sdf.parse(d);
                sdf = new SimpleDateFormat("dd MMM yyyy");
                d = sdf.format(date);
                holder.publish_date.setText(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        if (position < authorsDataList.size()) {
            String authorsCo;
            holder.folding_cell.fold(true);
            authorsCo = holder.authorList.getText().toString();
            if (Objects.equals(authorsCo, "Authors")) {
                authorsCo = "";
            }
            List<Author> authorsList = authorArrayMap.get(booksDataList.get(position).getID());
            for (int i = 0; i < authorsList.size(); i++) {
                if(!authorsCo.contains(authorsList.get(i).getFirstName())) {
                    authorsCo += authorsList.get(i).getFirstName() + "\n";
                }
            }
            holder.authorList.setText(authorsCo);
            holder.authorNumber.setText(countline(authorsCo) + "");
            holder.view.setTag(R.id.Authors, authorsDataList.get(position));
        }
    }

    private int countline(String text) {
        int numberOfLine = text.split("\n").length;
        return numberOfLine;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return booksDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setDataList(List<Book> booksDataList) {
        this.booksDataList = booksDataList;
        notifyDataSetChanged();
    }

    void setPhotosDataList(List<CoverPhotos> photosDataList) {
        this.photosDataList = photosDataList;
        if(photosDataList == null){
            return;
        }
        else  {
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

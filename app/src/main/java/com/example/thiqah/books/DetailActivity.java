package com.example.thiqah.books;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.thiqah.api.Author;
import com.example.thiqah.api.Book;
import com.example.thiqah.api.CoverPhotos;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static final String KEY_PHOTOS = "Photos";
    public static final String KEY_AUTHOR = "Author";
    public static final String KEY_BOOKS = "Books";
    @BindView(R.id.book_name_detail)
    TextView bookNameDetail;
    @BindView(R.id.author_name_detail)
    TextView authorNameDetail;
    @BindView(R.id.cover_photo_detail)
    ImageView coverPhotoDetail;
    @BindView(R.id.page_count_detail)
    TextView pageCountDetail;
    @BindView(R.id.description_detail)
    TextView descriptionDetail;
    @BindView(R.id.publish_date_detail)
    TextView publishDateDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
    }

    private void init() {
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Book bookData = extras.getParcelable(KEY_BOOKS);
            Author authorData = extras.getParcelable(KEY_AUTHOR);
            CoverPhotos coverPhotos = extras.getParcelable(KEY_PHOTOS);

            bookNameDetail.setText(bookData.getTitle());
            descriptionDetail.setText(bookData.getDescription());
            pageCountDetail.setText(String.valueOf(bookData.getPageCount()));
            publishDateDetail.setText(bookData.getPublishDate());


            authorNameDetail.setText(authorData.getFirstName());


            Glide.with(this).load(coverPhotos.getUrl()).into(coverPhotoDetail);


        }
    }
}
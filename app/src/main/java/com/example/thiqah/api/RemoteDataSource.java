package com.example.thiqah.api;

import android.util.Log;

import com.example.thiqah.Model.Author;
import com.example.thiqah.Model.Book;
import com.example.thiqah.Model.CoverPhotos;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {

    private static final String TAG = "RemoteDataSource";
    // Get a Realm instance
    Realm realm = Realm.getDefaultInstance();

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor);

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://fakerestapi.azurewebsites.net/")
            .client(okBuilder.build())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    BookClient bookClient = retrofit.create(BookClient.class);

    Call<List<Book>> bookListCall = bookClient.allBooks();
    Call<List<Author>> authorListCall = bookClient.allAuthors();
    Call<List<CoverPhotos>> coverPhotosListCall = bookClient.allPhotos();


    public void getBookListCall(final DataSource dataSource) {
        bookListCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> books = response.body();
                RealmResults<Book> data = realm.where(Book.class).findAll();
                if (data.size() == 0)
                    save_books_to_database(books);

                dataSource.passBookList(realm.copyToRealm(data));

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.getStackTraceString(t);
                Log.d(TAG, "No book");
            }
        });
    }

    public void getAuthorListCall(final DataSource dataSource) {
        authorListCall.enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                List<Author> authors = response.body();
                RealmResults<Author> data = realm.where(Author.class).findAll();
                if (data.size() == 0)
                    save_authors_to_database(authors);
                dataSource.passAuthorList(realm.copyToRealm(data));

            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
                Log.getStackTraceString(t);
                Log.d("fail", "No author");

            }
        });
    }

    public void getCoverPhotosListCall(final DataSource dataSource) {
        coverPhotosListCall.enqueue(new Callback<List<CoverPhotos>>() {
            @Override
            public void onResponse(Call<List<CoverPhotos>> call, Response<List<CoverPhotos>> response) {
                List<CoverPhotos> photos = response.body();
                RealmResults<CoverPhotos> data = realm.where(CoverPhotos.class).findAll();
                if (data.size() == 0)
                    save_photos_to_database(photos);
                dataSource.passCoverPhotoList(realm.copyToRealm(data));


            }

            @Override
            public void onFailure(Call<List<CoverPhotos>> call, Throwable t) {
                Log.getStackTraceString(t);
                Log.d("fail", "No photo");
            }
        });
    }

    private void save_books_to_database(final List<Book> books) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(books);
            }
        });
        Log.d("database", String.valueOf(realm.getSchema()));
    }

    private void save_authors_to_database(final List<Author> authors) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(authors);
            }
        });
    }

    private void save_photos_to_database(final List<CoverPhotos> photos) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(photos);
            }
        });
    }

}

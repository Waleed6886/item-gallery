package api;

import android.os.AsyncTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thiqah on 2/18/2018.
 */

public class RemoteDataSource {


    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://fakerestapi.azurewebsites.net/")
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
                new LoadListTask().execute(books);
                dataSource.passBookList(books);

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    public void getAuthorListCall(final DataSource dataSource) {
        authorListCall.enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                List<Author> authorList = response.body();
                new LoadListTask().execute(authorList);
                dataSource.passAuthorList(authorList);

            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {

            }
        });
    }

    public void getCoverPhotosListCall(final DataSource dataSource) {
        coverPhotosListCall.enqueue(new Callback<List<CoverPhotos>>() {
            @Override
            public void onResponse(Call<List<CoverPhotos>> call, Response<List<CoverPhotos>> response) {
                List<CoverPhotos> photosList = response.body();
                new LoadListTask().execute(photosList);
                dataSource.passCoverPhotoList(photosList);
            }

            @Override
            public void onFailure(Call<List<CoverPhotos>> call, Throwable t) {

            }
        });
    }

    private class LoadListTask extends AsyncTask<Void,List,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            for(List list:b)
            return null;
        }

        @Override
        protected void onProgressUpdate(List[] values) {
            super.onProgressUpdate(values);
        }


    }

}

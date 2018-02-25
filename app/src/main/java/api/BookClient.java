package api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Thiqah on 2/15/2018.
 */

public interface BookClient {

    @GET("/api/books/")
    Call<List<Book>> allBooks ();

    @GET("/api/authors/")
    Call<List<Author>> allAuthors ();

    @GET("/api/CoverPhotos")
    Call<List<CoverPhotos>> allPhotos ();


}

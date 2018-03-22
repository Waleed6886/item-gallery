package com.example.thiqah.books;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.thiqah.Model.Author;
import com.example.thiqah.Model.Book;
import com.example.thiqah.Model.CoverPhotos;
import com.example.thiqah.api.DataSource;
import com.example.thiqah.api.RemoteDataSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements DataSource {

    static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST = 100;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mySwipeRefreshLayout;
    RecyclerViewAdapter recyclerViewAdapter;
    Realm realm;
    RealmResults<Book> bookRealmResults;
    RealmResults<Author> authorRealmResults;
    RealmResults<CoverPhotos> photosRealmResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // opens "books.realm"
        realm = Realm.getDefaultInstance();


        init();
    }

    private void init() {
        setView();

        requestStoragePermission();

        initializeLayoutManager();

        initializeAdapter();

        initializeData();

        swipeRefresh();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Check if user triggered a refresh:
            case R.id.menu_refresh:
                Log.i(LOG_TAG, "Refresh menu item selected");

                // Signal SwipeRefreshLayout to start the progress indicator
                mySwipeRefreshLayout.setRefreshing(true);

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.
                myUpdateOperation();
                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);

    }

    private void swipeRefresh() {

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        myUpdateOperation();
                    }
                }
        );
    }

    //Inject the views
    private void setView() {
        ButterKnife.bind(this);
    }

    //ask for permission to read and write
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
    }

    //initialize Layout Manger for the adapter
    private void initializeLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    //initialize the adapter
    private void initializeAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    //bring the data from the remote source
    private void initializeData() {
        //Read the database for offline data
        bookRealmResults = realm.where(Book.class).findAll();
        authorRealmResults = realm.where(Author.class).findAll();
        photosRealmResults = realm.where(CoverPhotos.class).findAll();

        passBookList(bookRealmResults);
        passAuthorList(authorRealmResults);
        passCoverPhotoList(photosRealmResults);
    }

    private void myUpdateOperation() {
        //take data from the remote source
        RemoteDataSource remoteDataSource = new RemoteDataSource();

        remoteDataSource.getCoverPhotosListCall(MainActivity.this);
        remoteDataSource.getBookListCall(MainActivity.this);
        remoteDataSource.getAuthorListCall(MainActivity.this);
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // file-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void passBookList(final List list) {
        if (bookRealmResults.size() == 0) {
            myUpdateOperation();
        } else {
            if (list == null) {
                Log.w("error", "book list is empty");
                return;
            }
            else {
                recyclerViewAdapter.setDataList(list);
            }
        }
    }

    @Override
    public void passAuthorList(final List list) {
        if (authorRealmResults.size() == 0) {
            myUpdateOperation();
        } else {
            if (list == null) {
                Log.w("error", "author list is empty");
                return;
            }
            else {
                recyclerViewAdapter.setAuthorsDataList(list);

            }
        }
    }

    @Override
    public void passCoverPhotoList(final List list) {
        if (photosRealmResults.size() == 0) {
            myUpdateOperation();
        } else {
            if (list == null) {
                Log.w("error", "photos list is empty");
                return;
            }
            else {
                recyclerViewAdapter.setPhotosDataList(list);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

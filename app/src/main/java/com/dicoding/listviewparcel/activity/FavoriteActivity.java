package com.dicoding.listviewparcel.activity;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.listviewparcel.R;
import com.dicoding.listviewparcel.adapter.FavoriteAdapter;
import com.dicoding.listviewparcel.db.DatabaseContract;
import com.dicoding.listviewparcel.helper.MappingHelper;
import com.dicoding.listviewparcel.modelsettergetter.Detail;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements LoadNotesCallback {
    private RecyclerView rvFav;
    private FavoriteAdapter adapter;
//    private UserHelper userHelper;
    private static final String TAG = FavoriteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Favorites");

        rvFav = findViewById(R.id.rv_userfav);
        rvFav.setLayoutManager(new LinearLayoutManager(this));
        rvFav.setHasFixedSize(true);
        adapter = new FavoriteAdapter(this);
        rvFav.setAdapter(adapter);

        adapter.setOnItemClickCallback(new FavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Detail data) {
                Intent detailIntent = new Intent(FavoriteActivity.this, DetailUserActivity.class);

                detailIntent.putExtra(DetailUserActivity.EXTRA_USER, data);
                startActivity(detailIntent);
            }
        });


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler= new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.FavColumns.CONTENT_URI, true, myObserver);

//        userHelper = UserHelper.getInstance(getApplicationContext());
//        userHelper.open();

//        new LoadNotesAsync(userHelper, this).execute();
        new LoadNotesAsync(this, this).execute();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        userHelper.close();
//    }

    @Override
    public void preExecute() {
    }

    @Override
    public void postExecute(ArrayList<Detail> details) {
        Log.d(TAG,"tes ini ada"+ details);
        if(details.size() > 0){
            adapter.setListUsers(details);
        }
        else {
            adapter.setListUsers(new ArrayList<Detail>());
        }
    }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Detail>>{
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallBack;

        private LoadNotesAsync(Context context, LoadNotesCallback callback){
            weakContext = new WeakReference<>(context);
            weakCallBack = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallBack.get().preExecute();
        }

        @Override
        protected ArrayList<Detail> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.FavColumns.CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Detail> details) {
            super.onPostExecute(details);
            weakCallBack.get().postExecute(details);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadNotesAsync(context, (LoadNotesCallback) context).execute();
        }
    }

}

interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Detail> details);
}

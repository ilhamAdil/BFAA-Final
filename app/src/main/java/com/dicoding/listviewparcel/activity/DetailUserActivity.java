package com.dicoding.listviewparcel.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dicoding.listviewparcel.BuildConfig;
import com.dicoding.listviewparcel.R;
import com.dicoding.listviewparcel.db.UserHelper;
import com.dicoding.listviewparcel.modelsettergetter.Detail;
import com.dicoding.listviewparcel.fragment.FragmentSectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.dicoding.listviewparcel.db.DatabaseContract.FavColumns.AVATAR;
import static com.dicoding.listviewparcel.db.DatabaseContract.FavColumns.CONTENT_URI;
import static com.dicoding.listviewparcel.db.DatabaseContract.FavColumns.FOLLOWERS;
import static com.dicoding.listviewparcel.db.DatabaseContract.FavColumns.FOLLOWING;
import static com.dicoding.listviewparcel.db.DatabaseContract.FavColumns.REPO;
import static com.dicoding.listviewparcel.db.DatabaseContract.FavColumns.USERNAME;

public class DetailUserActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_POSITION = "extra_position";
    private static final String TAG = DetailUserActivity.class.getSimpleName();
    TextView tvName, tvFollower, tvFollowing, tvRepository;
    ImageView imgUser;
    AppCompatImageButton btnFav;
    AppCompatImageButton btnUnfav;

    private boolean isEdit = false;
    private int position;
    private UserHelper userHelper;
    public static final int RESULT_DELETE = 301;


    private Detail detail = new Detail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        Detail detail = getIntent().getParcelableExtra(EXTRA_USER);

        btnFav = findViewById(R.id.btn_favorite);
        btnFav.setOnClickListener(this);

        btnUnfav = findViewById(R.id.btn_unfavorite);
        btnUnfav.setOnClickListener(this);

        getListUsernames(detail.getName());
        Log.d(TAG, "dapat" +detail.getName());

        Log.d(TAG, "manggil " +detail);


        FragmentSectionPagerAdapter fragmentSectionPagerAdapter = new FragmentSectionPagerAdapter(this, getSupportFragmentManager());
        fragmentSectionPagerAdapter.username = detail.getName();

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(fragmentSectionPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

        tvName = findViewById(R.id.tv_username);
        imgUser = findViewById(R.id.img_user);
        tvFollower = findViewById(R.id.tv_follower);
        tvFollowing = findViewById(R.id.tv_following);
        tvRepository = findViewById(R.id.tv_repository);

        userHelper = UserHelper.getInstance(getApplicationContext());
        userHelper.open();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userHelper.close();
    }

    private void getListUsernames(String username){
        AsyncHttpClient client = new AsyncHttpClient();
        String url ="https://api.github.com/users/"+username;
        client.addHeader("Authorization", "token "+ BuildConfig.GITHUB_TOKEN);
        client.addHeader("User-Agent","request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    Log.d(TAG, "Detail sukses");
                    JSONObject jsonObject = new JSONObject(result);

                    detail.setName(jsonObject.getString("login"));
                    detail.setRepo(jsonObject.getString("public_repos"));
                    detail.setFollowers(jsonObject.getString("followers"));
                    detail.setFollowing(jsonObject.getString("following"));
                    detail.setAvatar(jsonObject.getString("avatar_url"));

                    tvName.setText(detail.getName());
                    Log.d(TAG, "ini contoh "+detail.getName());
                    tvRepository.setText(detail.getRepo());
                    tvFollower.setText(detail.getFollowers());
                    tvFollowing.setText(detail.getFollowing());
                    Glide.with(getApplicationContext()).load(detail.getAvatar()).into(imgUser);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String respBody = new String(responseBody);
                Log.d(TAG, "onFailure: gagal api "+respBody);
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbiden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Foundddd";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
                Toast.makeText(DetailUserActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER, detail);
        intent.putExtra(EXTRA_POSITION, position);

        String username = detail.getName();
        Log.d(TAG, "ini adalah detail " + username);
        String avatar = detail.getAvatar();
        Log.d(TAG, "ini adalah avatar " + avatar);
        String followers = detail.getFollowers();
        String following = detail.getFollowing();
        String repo = detail.getRepo();

        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(AVATAR, avatar);
        values.put(FOLLOWERS, followers);
        values.put(FOLLOWING, following);
        values.put(REPO, repo);
        Log.d(TAG, "ini adalah nilai " + values);

        if (view.getId() == R.id.btn_favorite) {
            /**
             * insert menggunakan content resolver
             */
            getContentResolver().insert(CONTENT_URI, values);
            Log.d(TAG, "kita menambahkan "+getContentResolver().insert(CONTENT_URI, values));
            Toast.makeText(DetailUserActivity.this, "Berhasil Add Data", Toast.LENGTH_SHORT).show();
            Intent back = new Intent(DetailUserActivity.this, MainActivity.class);
            startActivity(back);
//            long result = userHelper.insert(values);

//            Log.d(TAG, "ini adalah banyak " + result);
//            if (result > 0) {
//                setResult(RESULT_ADD, intent);
//                Toast.makeText(DetailUserActivity.this, "berhasil add data", Toast.LENGTH_SHORT).show();
//                finish();
//            } else {
//                Toast.makeText(DetailUserActivity.this, "telah di add sebelumnya", Toast.LENGTH_SHORT).show();
//            }
        }

        if (view.getId() == R.id.btn_unfavorite){
            /**
             * Primary key yang saya gunakan bukanlah id melainkan username (String), maka saya tidak dapat menggunakan content resolver untuk delete by idnya. Hal ini
             * karena uri matcher yang digunakan membandingkan dengan int (integer). Sehingga saya menggunakan userhelper biasa.
             */
            long result = userHelper.deleteById(detail.getName());
            if(result > 0){
                setResult(RESULT_DELETE, intent);
                Toast.makeText(DetailUserActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
                Intent back = new Intent(DetailUserActivity.this, MainActivity.class);
                startActivity(back);
            } else {
                Toast.makeText(DetailUserActivity.this, "Belum Ditambahkan ke Favorie",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
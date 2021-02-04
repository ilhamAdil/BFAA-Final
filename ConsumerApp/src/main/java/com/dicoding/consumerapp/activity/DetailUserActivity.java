package com.dicoding.consumerapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dicoding.consumerapp.BuildConfig;
import com.dicoding.consumerapp.R;
import com.dicoding.consumerapp.db.UserHelper;
import com.dicoding.consumerapp.modelsettergetter.Detail;
import com.dicoding.consumerapp.sectionAdapter.FragmentSectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class DetailUserActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_POSITION = "extra_position";
    private static final String TAG = DetailUserActivity.class.getSimpleName();
    TextView tvName, tvFollower, tvFollowing, tvRepository;
    ImageView imgUser;
    private boolean isEdit = false;
    private int position;
    private UserHelper userHelper;

    private Detail detail = new Detail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        Detail detail = getIntent().getParcelableExtra(EXTRA_USER);

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

}
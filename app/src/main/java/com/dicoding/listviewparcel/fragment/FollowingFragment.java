package com.dicoding.listviewparcel.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.listviewparcel.BuildConfig;
import com.dicoding.listviewparcel.R;
import com.dicoding.listviewparcel.activity.DetailUserActivity;
import com.dicoding.listviewparcel.adapter.FragmentRecyclerAdapter;
import com.dicoding.listviewparcel.modelsettergetter.Detail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class FollowingFragment extends Fragment {
    private ArrayList<Detail> Users = new ArrayList<>();
    private static final String TAG = DetailUserActivity.class.getSimpleName();
    private static final String ARG_SECTION_USERNAME = "username";
    public String username;
    private RecyclerView rvFollowingFragment;
    private ProgressBar progressBarFollowing;

    public FollowingFragment() {
    }

    public static FollowingFragment newInstance(String username) {
       FollowingFragment followingFragment = new FollowingFragment();
       Bundle bundle = new Bundle();
       bundle.putString(ARG_SECTION_USERNAME, username);
       followingFragment.setArguments(bundle);
       return followingFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFollowingFragment = view.findViewById(R.id.rv_fragment_following);
        rvFollowingFragment.setHasFixedSize(true);
        progressBarFollowing = view.findViewById(R.id.progressBarFollowing);
        getListUsers(username);
        Log.d(TAG, "onViewCreated following ada"+username);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = this.getArguments().getString(ARG_SECTION_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    public void getListUsers(String username){
        progressBarFollowing.setVisibility(View.VISIBLE);
        Log.d(TAG, "getListUser: fragment "+username);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/"+username+"/following";
        client.addHeader("Authorization", "token "+ BuildConfig.GITHUB_TOKEN);
        client.addHeader("User-Agent","request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBarFollowing.setVisibility(View.INVISIBLE);
                String result = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Detail detail = new Detail();
                        detail.setName(jsonObject.getString("login"));
                        detail.setAvatar(jsonObject.getString("avatar_url"));
                        Users.add(detail);
                    }
                    showRecyclerListFollowing();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBarFollowing.setVisibility(View.INVISIBLE);
                String response = new String(responseBody);
                Log.d(TAG, "onFailure - gagal " + response);
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbiden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRecyclerListFollowing(){
        Log.d(TAG, "showRecylerListfollower");
        rvFollowingFragment.setLayoutManager(new LinearLayoutManager(getActivity()));
        FragmentRecyclerAdapter fragmentRecyclerAdapter = new FragmentRecyclerAdapter(Users);
        rvFollowingFragment.setAdapter(fragmentRecyclerAdapter);
    }
}
package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static String NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=60c4779d3f8c405235ecfb211223bb16";
    public static String TAG = "MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //String size = getSize(3);
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        rvMovies.setAdapter(movieAdapter);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Result: "+ results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "movies: "+ movies.get(0).getPosterPath());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }



    /*public String getSize(int pos){
        List<String> sizes = new ArrayList<>();

        String MOVIE_IMAGES = "https://api.themoviedb.org/3/configuration?api_key=60c4779d3f8c405235ecfb211223bb16";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_IMAGES, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject image = jsonObject.getJSONObject("images");
                    JSONArray posters = image.getJSONArray("poster_sizes");
                    for(int j = 0; j < posters.length(); j++){
                        sizes.add(posters.getString(j));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
                Log.i(TAG, "size: "+ sizes.toString());
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
                //sizes = new ArrayList<>();
            }
        });

        return "w342";//sizes.get(pos);
    }*/
}


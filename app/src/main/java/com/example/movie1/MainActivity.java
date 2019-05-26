package com.example.movie1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final static String popular = "https://api.themoviedb.org/3/movie/popular?api_key=d4949efbdefb715b85f08989622b9cfd ";
    final static String top_rated = "https://api.themoviedb.org/3/movie/top_rated?api_key=d4949efbdefb715b85f08989622b9cfd ";
    ArrayList<JsonData> jsonData;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Test","Main ");
        recyclerView = (RecyclerView) findViewById(R.id.id_recycler_view);
        jsonData = new ArrayList<JsonData>();
        if (isNetworkAvailable()) {
            JsonAsync jsonAsync = new JsonAsync(popular);
            jsonAsync.execute();
        } else {
            Log.i("Test","Main No internet");
            displayDialog();
        }
    }

    public void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.create();
        builder.setMessage(R.string.no_interet);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.show();
    }

    private boolean isNetworkAvailable() {

        //Log.i("Test","Main check internet");
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private Activity getActivity() {
        Context c = MainActivity.this;
        while (c instanceof ContextWrapper) {
            if (c instanceof Activity) {
                return (Activity) c;
            }
            c = ((ContextWrapper) c).getBaseContext();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_popular:
                jsonData = new ArrayList<JsonData>();
                if (isNetworkAvailable()) {
                    JsonAsync jsonAsync = new JsonAsync(popular);
                    jsonAsync.execute();
                    setTitle("popular Movies");
                } else {
                    displayDialog();
                }
                break;
            case R.id.id_top_rated:
                jsonData = new ArrayList<JsonData>();
                if (isNetworkAvailable()) {
                    JsonAsync jsonAsync = new JsonAsync(top_rated);
                    jsonAsync.execute();
                    setTitle("Top Rated Movies");
                } else {
                    displayDialog();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class JsonAsync extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        String url_string;

        public JsonAsync(String url_string) {
            this.url_string = url_string;
            //Log.i("Test","Main URLString"+url_string);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getString(R.string.wait));
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... s) {
            JsonUtils connection = new JsonUtils();
            //Log.i("Test","Main URLString"+url_string);
            URL url = connection.buildUrl(url_string);
            //Log.i("Test","Main URL"+url_string);
            String response = null;
            try {
                response = connection.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.optJSONArray(getString(R.string.results));
                Log.i("Test","Main length "+jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject movie = jsonArray.optJSONObject(i);
                    int vote_count = movie.optInt(getString(R.string.votecount), 0);
                    int id = movie.optInt(getString(R.string.id), 0);
                    boolean video = movie.optBoolean(getString(R.string.video), false);
                    long vote_average = movie.optLong(getString(R.string.voteaverage), 0);
                    String title = movie.optString(getString(R.string.title), null);
                    long popularity = movie.optLong(getString(R.string.popularity), 0);
                    String poster_path = movie.optString(getString(R.string.posterpath), null);
                    String original_language = movie.optString(getString(R.string.originallanguage), null);
                    String original_title = movie.optString(getString(R.string.originaltitle), null);
                    String backdrop_path = movie.optString(getString(R.string.bckdrop), null);
                    boolean adult = movie.optBoolean(getString(R.string.adult), false);
                    String overview = movie.optString(getString(R.string.overview), null);
                    String release_date = movie.optString(getString(R.string.releasedate), null);
                    JsonData jData = new JsonData(vote_count, id, video, vote_average, title, popularity, poster_path,
                            original_language, original_title, backdrop_path, adult, overview, release_date);
                    //response = jsonData.getPoster_path();
                    jsonData.add(jData);


                }
            } catch (JSONException e) {
                Log.i("catch:", e.toString());
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //pDialog.dismiss();
           movieAdapter = new MovieAdapter(MainActivity.this, jsonData);
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Toast.makeText(MainActivity.this, jsonData.get(0).getPoster_path(), Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                recyclerView.setAdapter(movieAdapter);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                recyclerView.setAdapter(movieAdapter);
            }

        }
    }

}

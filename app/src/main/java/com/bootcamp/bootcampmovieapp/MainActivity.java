package com.bootcamp.bootcampmovieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bootcamp.bootcampmovieapp.data.EndlessRecyclerViewScrollListener;
import com.bootcamp.bootcampmovieapp.data.MovieItem;
import com.bootcamp.bootcampmovieapp.data.MovieList;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdaptor adaptor;
    EditText editText;
    ProgressDialog dialog;
    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.movieTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);



        adaptor = new MovieAdaptor(getApplicationContext());
        recyclerView.setAdapter(adaptor);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("검색 중...");

        Button button = (Button) findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query;
                String urlStr;
                try{
                    adaptor.removeAll();
                    query = URLEncoder.encode(editText.getText().toString(),"UTF8");
                    urlStr = "https://openapi.naver.com/v1/search/movie.json?query=" + query;
                    sendRequest(urlStr);
                }catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });



        adaptor.setOnItemClickListener(new MovieAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(MovieAdaptor.ViewHolder holder, View view, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(adaptor.getMovieItem(position).link));
                startActivity(intent);
            }

        });
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        };

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


    }

    public void processRequest(String res){
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(res, MovieList.class);

        if (movieList != null){
            Iterator<MovieItem> iterator = movieList.items.iterator();
            while(iterator.hasNext()){
                adaptor.addItem(iterator.next());
            }
        }
        adaptor.notifyDataSetChanged();
    }

    public void sendRequest(String url){
        dialog.show();
        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("응답 ->",response);
                        processRequest(response);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        dialog.dismiss();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-Naver-Client-Id", getApplicationContext().getString(R.string.CLIENT_ID));
                params.put("X-Naver-Client-Secret", getApplicationContext().getString(R.string.CLIENT_SECRET));
                return params;
            }
        };
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }


}

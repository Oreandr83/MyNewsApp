package com.example.mynewsapp.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mynewsapp.ApiClient;
import com.example.mynewsapp.Model.Articles;
import com.example.mynewsapp.Model.HeadLines;
import com.example.mynewsapp.MyAdapter;
import com.example.mynewsapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Articles> list = new ArrayList<>();
    final String API_KEY = "cc031b3da180464a82c3d811fed19550";

    private SwipeRefreshLayout refreshLayout;

    private EditText search;
    private Button btnSearch, btnFav;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = view.findViewById(R.id.id_swipeRefresh);
        recyclerView = view.findViewById(R.id.id_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final String country = getCountry();
        btnFav = view.findViewById(R.id.btn_fav);

        refreshLayout.setColorSchemeColors(Color.BLACK);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(country,API_KEY);
            }
        });
        retrieveJson(country,API_KEY);

 //       search = view.findViewById(R.id.search_text);
   //     btnSearch = view.findViewById(R.id.search_button);
// that third
      /*  btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!search.getText().toString().equals("")){
                  refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                      @Override
                      public void onRefresh() {
                          retrieveJson(search.getText().toString(),country,API_KEY);
                      }
                  });
                  retrieveJson(search.getText().toString(),country, API_KEY);
              }else {
                  refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                      @Override
                      public void onRefresh() {
                          retrieveJson("",country,API_KEY);
                      }
                  });
                  retrieveJson("",country,API_KEY);
              }
            }
        });
*/

    }
//String query
    public void retrieveJson(String country, String apiKey){

       refreshLayout.setRefreshing(true);

       //create Search
        //that second
       /* Call<HeadLines> call;
        if(!search.getText().toString().equals("")){
            call = ApiClient.getInstance().getApi().getSpecificData(query,apiKey);
        }else {
            call = ApiClient.getInstance().getApi().getHeadLines(country,apiKey);
        }*/

       Call<HeadLines> call =  call = ApiClient.getInstance().getApi().getHeadLines(country,apiKey); //that first

        call.enqueue(new Callback<HeadLines>() {
            @Override
            public void onResponse(Call<HeadLines> call, Response<HeadLines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    refreshLayout.setRefreshing(false);
                    list.clear();
                    list = response.body().getArticles();
                    myAdapter = new MyAdapter(getActivity(),list);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<HeadLines> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}

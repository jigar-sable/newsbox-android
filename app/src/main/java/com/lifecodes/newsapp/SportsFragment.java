package com.lifecodes.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.lifecodes.newsapp.models.Article;
import com.lifecodes.newsapp.models.News;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsFragment extends Fragment {

    String country = "in";
    String apiKey;
    private ArrayList<Article> articlesList;
    private MainAdapter adapter;
    private RecyclerView recyclerView;
    String category = "sports";
    private ShimmerFrameLayout shimmerFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports, container, false);
        apiKey = view.getContext().getResources().getString(R.string.api_key);

        recyclerView = view.findViewById(R.id.recyclerViewSports);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayoutSports);
        shimmerFrameLayout.startShimmer();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        articlesList = new ArrayList<>();
        adapter = new MainAdapter(articlesList, view.getContext());
        recyclerView.setAdapter(adapter);

        fetchNews();

        return view;
    }

    private void fetchNews() {

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<News> call = apiService.getCategoryNews(country, category, 100, apiKey);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                articlesList.addAll(response.body().getArticles());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
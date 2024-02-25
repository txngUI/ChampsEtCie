package com.example.champscie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ChampionClickListener {
    SearchView searchView;
    RecyclerView recyclerView;
    ChampionAdapter championAdapter;
    ImageView errorNotFound;
    ProgressBar progressBar;
    Button sort;
    Dialog mDialog;
    private RiotAPI championApi;
    private List<MinChampion> originalChampionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        errorNotFound = findViewById(R.id.imageView);
        errorNotFound.setVisibility(View.GONE);
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ddragon.leagueoflegends.com/cdn/14.3.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        championApi = retrofit.create(RiotAPI.class);

        fetchChampions();

        sort = findViewById(R.id.sort);
        mDialog = new Dialog(this);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortFilterPopup popup = new SortFilterPopup(MainActivity.this);
                popup.show();
            }
        });
    }

    private void filterList(String newText) {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(champion);
            }
        }
        if (filteredList.isEmpty()) {
            errorNotFound.setVisibility(View.VISIBLE);
        } else {
            errorNotFound.setVisibility(View.GONE);
        }

        championAdapter.setFilteredList(filteredList);
    }

    private void fetchChampions() {
        championApi.getChampions().enqueue(new Callback<MinChampionResponse>() {
            @Override
            public void onResponse(Call<MinChampionResponse> call, Response<MinChampionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                MinChampionResponse minChampionResponse = response.body();
                if (minChampionResponse != null) {
                    originalChampionList.addAll(minChampionResponse.getData().values());
                    championAdapter = new ChampionAdapter(MainActivity.this, originalChampionList, MainActivity.this);
                    recyclerView.setAdapter(championAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MinChampionResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onChampionClick(MinChampion champion) {
        Intent intent = new Intent(MainActivity.this, ChampionDetailsActivity.class);
        intent.putExtra("championId", champion.getId());
        startActivity(intent);
    }
}

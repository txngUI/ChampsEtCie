package com.example.champscie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    ChampionAdapter championAdapter;
    ImageView errorNotFound;
    private RiotAPI championApi;
    private List<Champion> originalChampionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        errorNotFound = findViewById(R.id.imageView);
        errorNotFound.setVisibility(View.GONE);
        searchView = findViewById(R.id.searchView);
        //searchView.clearFocus();
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
    }

    private void filterList(String newText) {
        List<Champion> filteredList = new ArrayList<>();
        for (Champion champion : originalChampionList) {
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
        championApi.getChampions().enqueue(new Callback<ChampionResponse>() {
            @Override
            public void onResponse(Call<ChampionResponse> call, Response<ChampionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ChampionResponse championResponse = response.body();
                if (championResponse != null) {
                    originalChampionList.addAll(championResponse.getData().values());
                    championAdapter = new ChampionAdapter(MainActivity.this, originalChampionList);
                    recyclerView.setAdapter(championAdapter);
                }
            }

            @Override
            public void onFailure(Call<ChampionResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

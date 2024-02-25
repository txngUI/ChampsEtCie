package com.example.champscie;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChampionDetailsActivity extends AppCompatActivity {
    TextView name;
    TextView title;
    private RiotAPI championApi;
    ImageView backBtn;
    private Champion champion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.champion_details_activity);

        // Initialisation des TextView
        name = findViewById(R.id.name);
        title = findViewById(R.id.title);

        // Vérification pour éviter les NullPointerException
        if (name == null || title == null) {
            Log.e("ChampionDetailsActivity", "TextViews name or title is null");
            return;
        }

        String championId = getIntent().getStringExtra("championId");

        // Verify that the championId is being passed from the MainActivity
        Log.d("ChampionDetailsActivity", "Champion ID: " + championId);

        backBtn = findViewById(R.id.back);

        backBtn.setOnClickListener(v -> {
            finish();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ddragon.leagueoflegends.com/cdn/14.3.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        championApi = retrofit.create(RiotAPI.class);

        Call<ChampionResponse> call = championApi.getChampionDetails(championId);
        call.enqueue(new Callback<ChampionResponse>() {
            @Override
            public void onResponse(Call<ChampionResponse> call, Response<ChampionResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ChampionDetailsActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ChampionResponse championResponse = response.body();
                if (championResponse != null && championResponse.getData() != null) {
                    champion = championResponse.getData().get(championId);
                    if (champion != null) {
                        name.setText(champion.getName());
                        title.setText(champion.getTitle());
                    } else {
                        Log.e("ChampionDetailsActivity", "Champion details not found for ID: " + championId);
                    }
                } else {
                    Log.e("ChampionDetailsActivity", "Champion response or data is null");
                }
            }

            @Override
            public void onFailure(Call<ChampionResponse> call, Throwable t) {
                Toast.makeText(ChampionDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
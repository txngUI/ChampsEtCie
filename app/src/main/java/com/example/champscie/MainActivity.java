package com.example.champscie;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listChamps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listChamps = findViewById(R.id.listChamps);

        // Configuration de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ddragon.leagueoflegends.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Création du service
        YourApiService apiService = retrofit.create(YourApiService.class);

        // Appel à l'API
        Call<List<Champion>> call = apiService.getChampions();
        call.enqueue(new Callback<List<Champion>>() {
            @Override
            public void onResponse(Call<List<Champion>> call, Response<List<Champion>> response) {
                if (response.isSuccessful()) {
                    List<Champion> dataList = response.body();
                    if (dataList != null && !dataList.isEmpty()) {
                        // Assurez-vous que dataList contient des données valides.
                        ArrayAdapter<Champion> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataList);
                        listChamps.setAdapter(adapter);
                    } else {
                        // Aucune donnée valide à afficher, gérer cela en conséquence.
                    }
                } else {
                    // Gérer l'erreur ici
                }
            }

            @Override
            public void onFailure(Call<List<Champion>> call, Throwable t) {
                // Gérer l'erreur ici
            }
        });

        listChamps.setVisibility(View.VISIBLE);
    }
}

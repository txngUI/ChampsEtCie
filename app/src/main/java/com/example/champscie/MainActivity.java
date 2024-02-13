package com.example.champscie;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listChamps;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        listChamps = findViewById(R.id.listChamps);

        arrayList = new ArrayList<>();
        arrayList.add("Lundi");
        arrayList.add("Mardi");
        arrayList.add("Mercredi");
        arrayList.add("Jeudi");
        arrayList.add("Vendredi");
        arrayList.add("Samedi");
        arrayList.add("Dimanche");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        listChamps.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}

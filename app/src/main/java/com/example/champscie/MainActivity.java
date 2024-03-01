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
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ChampionClickListener, SortListener, PartypeFilterListener, SortRoleListener {
    SearchView searchView;
    RecyclerView recyclerView;
    ChampionAdapter championAdapter;
    ImageView errorNotFound;
    ProgressBar progressBar;
    Button sort;
    Button kinds;
    Button roles;
    Dialog mDialog;
    private RiotAPI championApi;
    private List<MinChampion> originalChampionList = new ArrayList<>();
    private List<MinChampion> currentFilteredList = new ArrayList<>();
    private SortFilterPopup sortPopup;
    private PartypeFilterPopup partypePopup;
    private SortRoleFilter rolePopup;
    private RadioButton selectedRadioButton;
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

        sortPopup = new SortFilterPopup(MainActivity.this, MainActivity.this);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortPopup.setSelectedRadioButton(selectedRadioButton);
                sortPopup.show();
            }
        });

        kinds = findViewById(R.id.kinds);
        partypePopup = new PartypeFilterPopup(MainActivity.this, MainActivity.this);
        kinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partypePopup.setSelectedRadioButton(selectedRadioButton);
                partypePopup.show();
            }
        });

        roles = findViewById(R.id.roles);
        rolePopup = new SortRoleFilter(MainActivity.this, MainActivity.this);
        roles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rolePopup.show();
            }
        });

    }

    private void filterList(String newText) {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : currentFilteredList.isEmpty() ? originalChampionList : currentFilteredList) {
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

    @Override
    public void onAlphaAscClicked() {
        Collections.sort(originalChampionList, new Comparator<MinChampion>() {
            @Override
            public int compare(MinChampion o1, MinChampion o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        if (!currentFilteredList.isEmpty()) {
            Collections.sort(currentFilteredList, new Comparator<MinChampion>() {
                @Override
                public int compare(MinChampion o1, MinChampion o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        }

        championAdapter.setFilteredList(currentFilteredList.isEmpty() ? originalChampionList : currentFilteredList);
    }

    @Override
    public void onAlphaDescClicked() {
        Collections.sort(originalChampionList, new Comparator<MinChampion>() {
            @Override
            public int compare(MinChampion o1, MinChampion o2) {
                return o2.getName().compareToIgnoreCase(o1.getName());
            }
        });

        if (!currentFilteredList.isEmpty()) {
            Collections.sort(currentFilteredList, new Comparator<MinChampion>() {
                @Override
                public int compare(MinChampion o1, MinChampion o2) {
                    return o2.getName().compareToIgnoreCase(o1.getName());
                }
            });
        }

        championAdapter.setFilteredList(currentFilteredList.isEmpty() ? originalChampionList : currentFilteredList);
    }

    @Override
    public void onManaClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Mana")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onEnergyClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Énergie")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onBloodyWellClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Puits de sang")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onRageClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Rage")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onCourageClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Courage")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onShieldClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Bouclier")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onFuryClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Fureur")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onFerocityClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Férocité")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onVaporClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Vapeur")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onAgressivityClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Agressivité")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onBloodFlowClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Afflux sanguin")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onImpulseClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getPartype().equals("Impulsion")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onNoOneClicked() {
        championAdapter.setFilteredList(originalChampionList);
    }

    @Override
    public void onMageClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getTags().contains("Mage")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onAssassinClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getTags().contains("Assassin")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onMarksmanClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getTags().contains("Marksman")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onTankClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getTags().contains("Tank")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onSupportClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getTags().contains("Support")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onFighterClicked() {
        List<MinChampion> filteredList = new ArrayList<>();
        for (MinChampion champion : originalChampionList) {
            if (champion.getTags().contains("Fighter")) {
                filteredList.add(champion);
            }
        }
        currentFilteredList.clear();
        currentFilteredList.addAll(filteredList);
        championAdapter.setFilteredList(currentFilteredList);
    }

    @Override
    public void onAllClicked() {
        championAdapter.setFilteredList(originalChampionList);
    }
}
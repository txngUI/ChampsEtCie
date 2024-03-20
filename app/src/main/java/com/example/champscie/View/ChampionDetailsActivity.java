package com.example.champscie.View;

import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.champscie.Models.ChampionResponse;
import com.example.champscie.Models.Champion;
import com.example.champscie.Models.RiotAPI;
import com.example.champscie.R;
import com.example.champscie.Models.Skin;
import com.example.champscie.Models.Spell;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChampionDetailsActivity extends AppCompatActivity {
    TextView name;
    TextView title;
    TextView lore;
    TextView tags;
    TextView partType;
    TextView difficulty;
    ImageView imageViewQ;
    TextView abilitieQTitle;
    TextView abilitieQDescription;
    ImageView imageViewW;
    TextView abilitieWTitle;
    TextView abilitieWDescription;
    ImageView imageViewE;
    TextView abilitieETitle;
    TextView abilitieEDescription;
    ImageView imageViewR;
    TextView abilitieRTitle;
    TextView abilitieRDescription;
    ImageView imageViewP;
    TextView abilitiePTitle;
    TextView abilitiePDescription;
    LinearLayout allyTips;
    LinearLayout enemyTips;
    LinearLayout skinsLayout;
    ImageView image;
    private RiotAPI championApi;
    ImageView backBtn;
    private Champion champion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.champion_details_activity);

        name = findViewById(R.id.name);
        title = findViewById(R.id.title);
        image = findViewById(R.id.imageChamp);
        lore = findViewById(R.id.lore);
        tags = findViewById(R.id.tags);
        partType = findViewById(R.id.parType);
        difficulty = findViewById(R.id.difficulty);
        abilitieQTitle = findViewById(R.id.abilitieQTitle);
        imageViewQ = findViewById(R.id.imageViewQ);
        abilitieQDescription = findViewById(R.id.abilitieQDescription);
        abilitieWTitle = findViewById(R.id.abilitieWTitle);
        imageViewW = findViewById(R.id.imageViewW);
        abilitieWDescription = findViewById(R.id.abilitieWDescription);
        abilitieETitle = findViewById(R.id.abilitieETitle);
        imageViewE = findViewById(R.id.imageViewE);
        abilitieEDescription = findViewById(R.id.abilitieEDescription);
        abilitieRTitle = findViewById(R.id.abilitieRTitle);
        imageViewR = findViewById(R.id.imageViewR);
        abilitieRDescription = findViewById(R.id.abilitieRDescription);
        imageViewP = findViewById(R.id.imageViewP);
        abilitiePTitle = findViewById(R.id.abilitiePTitle);
        abilitiePDescription = findViewById(R.id.abilitiePDescription);
        allyTips = findViewById(R.id.allyTipsLayout);
        enemyTips = findViewById(R.id.enemyTipsLayout);
        skinsLayout = findViewById(R.id.skinsLayout);

        if (name == null || title == null) {
            Log.e("ChampionDetailsActivity", "TextViews name or title is null");
            return;
        }

        String championId = getIntent().getStringExtra("championId");
        String version = getIntent().getStringExtra("version");

        // Verify that the championId is being passed from the MainActivity
        Log.d("ChampionDetailsActivity", "Champion ID: " + championId);

        backBtn = findViewById(R.id.back);

        backBtn.setOnClickListener(v -> {
            finish();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ddragon.leagueoflegends.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        championApi = retrofit.create(RiotAPI.class);

        Call<ChampionResponse> call = championApi.getChampionDetails(version, championId);
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
                        List<Spell> spells = champion.getSpells();
                        name.setText(champion.getName());
                        title.setText(champion.getTitle());
                        Glide.with(ChampionDetailsActivity.this)
                                .load(champion.getImageUrl())
                                .into(image);
                        lore.setText(champion.getLore());
                        tags.setText(champion.getTags());
                        partType.setText(champion.getPartType());
                        difficulty.setText(champion.getDifficulty());
                        abilitieQTitle.setText("Q - " + spells.get(0).getName());
                        abilitieQDescription.setText(spells.get(0).getDescription());

                        Glide.with(ChampionDetailsActivity.this)
                                .load(spells.get(0).getImageUrl())
                                .into(imageViewQ);

                        abilitieWTitle.setText("W - " + spells.get(1).getName());
                        abilitieWDescription.setText(spells.get(1).getDescription());

                        Glide.with(ChampionDetailsActivity.this)
                                .load(spells.get(1).getImageUrl())
                                .into(imageViewW);

                        abilitieETitle.setText("E - " + spells.get(2).getName());
                        abilitieEDescription.setText(spells.get(2).getDescription());

                        Glide.with(ChampionDetailsActivity.this)
                                .load(spells.get(2).getImageUrl())
                                .into(imageViewE);

                        abilitieRTitle.setText("R - " + spells.get(3).getName());
                        abilitieRDescription.setText(spells.get(3).getDescription());

                        Glide.with(ChampionDetailsActivity.this)
                                .load(spells.get(3).getImageUrl())
                                .into(imageViewR);

                        abilitiePTitle.setText("Passif - " + champion.getPassive().getName());
                        abilitiePDescription.setText(champion.getPassive().getDescription());

                        Glide.with(ChampionDetailsActivity.this)
                                .load(champion.getPassive().getImageUrl())
                                .into(imageViewP);

                        int i = 1;
                        for (String tip : champion.getAllyTips()) {
                            TextView textView = new TextView(ChampionDetailsActivity.this);
                            textView.setTextColor(getResources().getColor(R.color.blurb));
                            textView.setPadding(32,16,32,0);
                            textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                            textView.setText(i + " - " + tip);
                            allyTips.addView(textView);
                            i++;
                        }

                        i = 1;
                        for (String tip : champion.getEnemyTips()) {
                            TextView textView = new TextView(ChampionDetailsActivity.this);
                            textView.setTextColor(getResources().getColor(R.color.blurb));
                            textView.setPadding(32,16,32,0);
                            textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                            textView.setText(i + " - " + tip);
                            enemyTips.addView(textView);
                            i++;
                        }

                        for (Skin skin : champion.getSkins()) {
                            LinearLayout layoutSkin = new LinearLayout(ChampionDetailsActivity.this);
                            layoutSkin.setOrientation(LinearLayout.VERTICAL);

                            ImageView imageView = new ImageView(ChampionDetailsActivity.this);
                            Glide.with(ChampionDetailsActivity.this)
                                    .load(skin.getImageUrl() + champion.getId() + "_" + skin.getNum() + ".jpg")
                                    .into(imageView);

                            TextView nameTextView = new TextView(ChampionDetailsActivity.this);
                            nameTextView.setText(skin.getName());
                            nameTextView.setTextSize(16);
                            nameTextView.setTypeface(null, Typeface.BOLD);
                            nameTextView.setGravity(Gravity.CENTER);

                            layoutSkin.addView(imageView);
                            layoutSkin.addView(nameTextView);
                            layoutSkin.setPadding(16, 16, 16, 64);

                            skinsLayout.addView(layoutSkin);
                        }



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
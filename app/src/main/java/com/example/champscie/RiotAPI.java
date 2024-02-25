package com.example.champscie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RiotAPI {
    @GET("data/fr_FR/champion.json")
    Call<MinChampionResponse> getChampions();

    @GET("data/fr_FR/champion/{id}.json")
    Call<ChampionResponse> getChampionDetails(@Path("id") String championId);
}
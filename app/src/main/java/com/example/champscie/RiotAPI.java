package com.example.champscie;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RiotAPI {
    @GET("data/fr_FR/champion.json")
    Call<ChampionResponse> getChampions();
}
package com.example.champscie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RiotAPI {
    @GET("api/versions.json")
    Call<List<String>> getVersions();

    @GET("cdn/{version}/data/fr_FR/champion.json")
    Call<MinChampionResponse> getChampions(@Path("version") String version);

    @GET("cdn/{version}/data/fr_FR/champion/{id}.json")
    Call<ChampionResponse> getChampionDetails(@Path("version") String version, @Path("id") String championId);
}
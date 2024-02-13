package com.example.champscie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RiotAPI {

    @GET("api/versions.json")
    Call<List<String>> getVersions();

    @GET("cdn/11.6.1/data/fr_FR/champion.json")
    Call<List<Champion>> getChampions();
}
package com.example.champscie.Models;

import com.example.champscie.Models.Champion;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ChampionResponse {
    @SerializedName("data")
    private Map<String, Champion> data;

    public Map<String, Champion> getData() {
        return data;
    }
}

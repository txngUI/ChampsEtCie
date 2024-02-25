package com.example.champscie;

import java.util.Map;

public class MinChampionResponse {
    private Map<String, MinChampion> data;
    private Map<String, Champion> dataChamp;

    public Map<String, MinChampion> getData() {
        return data;
    }

    public Map<String, Champion> getDataChamp() {
        return dataChamp;
    }
}
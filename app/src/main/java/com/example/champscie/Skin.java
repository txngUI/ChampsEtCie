package com.example.champscie;

import com.google.gson.annotations.SerializedName;

class Skin {
    @SerializedName("num")
    private int num;
    @SerializedName("name")
    private String name;
    public int getNum() {
        return num;
    }
    public String getName() {
        if (name.equals("default")) {
            return "Skin par défaut";
        }
        return name;
    }
    public String getImageUrl() {
        return "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/";
    }
}
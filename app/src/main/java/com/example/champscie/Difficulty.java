package com.example.champscie;

import com.google.gson.annotations.SerializedName;

public class Difficulty {
    @SerializedName("difficulty")
    private int difficulty;

    public int getDifficulty() {
        return difficulty;
    }
}
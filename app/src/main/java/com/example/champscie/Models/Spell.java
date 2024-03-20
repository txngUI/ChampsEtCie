package com.example.champscie.Models;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

public class Spell {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return "https://ddragon.leagueoflegends.com/cdn/14.3.1/img/spell/" + id + ".png";
    }
}

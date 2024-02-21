package com.example.champscie;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;


public class Champion {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    @SerializedName("title")
    private String title;

    @SerializedName("blurb")
    private String blurb;

    @SerializedName("tags")
    private LinkedList<String> tags;

    public String getImageUrl() {
        return "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + id + "_0.jpg";
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getBlurb() {
        return blurb;
    }

    public LinkedList<String> getTags() {
        return tags;
    }
}

package com.example.champscie.Models;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;


public class MinChampion {
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
    @SerializedName("key")
    private String key;
    @SerializedName("partype")
    private String partype;

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

    public String getId() {
        return id;
    }
    public String getKey() {
        return key;
    }
    public String getPartype() {
        return partype;
    }
}

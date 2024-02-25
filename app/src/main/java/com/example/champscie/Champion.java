package com.example.champscie;

import android.media.Image;
import com.google.gson.annotations.SerializedName;

public class Champion {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("title")
    private String title;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

}

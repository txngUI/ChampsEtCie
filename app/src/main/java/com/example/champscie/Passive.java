package com.example.champscie;

import com.google.gson.annotations.SerializedName;

class Passive {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private Image image;

    class Image {
        @SerializedName("full")
        private String full;

        public String getFull() {
            return full;
        }
    }


    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return "https://ddragon.leagueoflegends.com/cdn/14.3.1/img/passive/" + this.image.getFull();
    }
}
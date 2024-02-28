package com.example.champscie;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;
import java.util.List;

public class Champion {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("title")
    private String title;
    @SerializedName("lore")
    private String lore;
    @SerializedName("tags")
    private String[] tags;
    @SerializedName("partype")
    private String partType;
    @SerializedName("info")
    private Difficulty difficulty;
    @SerializedName("spells")
    private List<Spell> spells;
    @SerializedName("passive")
    private Passive passive;
    @SerializedName("allytips")
    private String[] allyTips;
    @SerializedName("enemytips")
    private String[] enemyTips;
    @SerializedName("skins")
    private List<Skin> skins;

    private List<String> imageUrls;

    public String getImageUrl() {
        return "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/" + id + "_0.jpg";
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getTitle() {
        return title;
    }
    public String getLore() {
        return lore;
    }
    public String getTags() {
        StringBuilder tagsString = new StringBuilder();
        for (int i = 0; i < tags.length; i++) {
            tagsString.append(tags[i]);
            if (i < tags.length - 1) {
                tagsString.append(", ");
            }
        }
        return tagsString.toString();
    }
    public String getPartType() {
        return partType;
    }

    public String getDifficulty() {
        return difficulty.getDifficulty() + "/10";
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public Passive getPassive() {
        return passive;
    }

    public String[] getAllyTips() {
        return allyTips;
    }
    public String[] getEnemyTips() {
        return enemyTips;
    }
    public List<Skin> getSkins() {
        return skins;
    }

}

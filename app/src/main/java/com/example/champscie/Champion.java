package com.example.champscie;

import java.util.LinkedList;

public class Champion {
    private String id;
    private String name;
    private String title;
    private String image;
    private String lore;
    private LinkedList<String> tags;
    private String partype;
    private int difficulty;
    private LinkedList<Spell> spellsSet;
    private LinkedList<String> allyTips;
    private LinkedList<String> enemyTips;
    private LinkedList<Skin> skins;

    public Champion(String id) {
        this.id = id;
    }
}

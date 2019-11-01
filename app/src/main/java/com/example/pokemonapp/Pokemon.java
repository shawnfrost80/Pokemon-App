package com.example.pokemonapp;

import org.json.JSONArray;

import java.util.ArrayList;

public class Pokemon {

    int id;
    String name;
    String image;
    ArrayList<String> type;
    String height;
    String weight;
    ArrayList<String> weakness;

    public Pokemon(int id, String name, String image, ArrayList<String> type, String height, String weight, ArrayList<String> weakness) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.height = height;
        this.weight = weight;
        this.weakness = weakness;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public ArrayList<String> getWeakness() {
        return weakness;
    }
}

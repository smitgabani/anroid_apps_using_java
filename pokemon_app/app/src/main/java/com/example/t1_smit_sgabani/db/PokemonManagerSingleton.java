package com.example.t1_smit_sgabani.db;

import android.util.Log;

import com.example.t1_smit_sgabani.models.Pokemon;

import java.util.ArrayList;


public class PokemonManagerSingleton {
    private PokemonManagerSingleton() {
        loadProductsDataSet();
    }
    private static PokemonManagerSingleton instance = null;
    public static PokemonManagerSingleton getInstance() {
        if (instance == null) {
            instance = new PokemonManagerSingleton();
        }
        return instance;
    }

    private ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();


    private void loadProductsDataSet() {
        this.pokemonList.add(new Pokemon(12, "Caterpie"));
        this.pokemonList.add(new Pokemon(19, "Rattata"));
        this.pokemonList.add(new Pokemon(25, "Pikachu"));
        this.pokemonList.add(new Pokemon(147, "Dratini"));
    }

    public ArrayList<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public Pokemon getPokemonByID(int pokemonNumber) {
        for (int i = 0; i < this.pokemonList.size(); i++) {
            Pokemon curPokemon = this.pokemonList.get(i);
            if (curPokemon.getNumber() == pokemonNumber) {
                return curPokemon;
            }
        }
        return null;
    }
}

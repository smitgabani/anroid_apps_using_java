package com.example.t1_smit_sgabani;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.t1_smit_sgabani.databinding.ListBinding;
import com.example.t1_smit_sgabani.models.Pokemon;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PokemonAdapter extends ArrayAdapter {
    private ArrayList<Pokemon> pokemons;
    private int favourite;
    public PokemonAdapter(@NonNull Context context, int list, ArrayList<Pokemon> pokemons, int favourite) {
        super(context, 0);
        this.pokemons = pokemons;
        this.favourite = favourite;

    }
    public int getCount() {
        return pokemons.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }

        Pokemon currPokemon = pokemons.get(position);

        ListBinding binding = ListBinding.bind(convertView);

        if(currPokemon.getNumber() == favourite) {
            binding.name.setBackgroundColor();
        }
        binding.name.setText(currPokemon.getName());
        binding.score.setText("Win: "+ currPokemon.getWins()+" - "+ currPokemon.getLosses());

        return convertView;
    }
}

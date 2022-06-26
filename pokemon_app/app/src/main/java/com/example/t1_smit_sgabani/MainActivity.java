package com.example.t1_smit_sgabani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.t1_smit_sgabani.databinding.ActivityMainBinding;
import com.example.t1_smit_sgabani.databinding.ListBinding;
import com.example.t1_smit_sgabani.db.PokemonManagerSingleton;
import com.example.t1_smit_sgabani.models.Pokemon;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private SharedPreferences sp;
    int favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.sp = getSharedPreferences("MY-SP", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString("KEY_POKEDEX_NUMBER", "19");
        spEditor.apply();

        if (this.sp.contains("KEY_CAR_COLOR") && this.sp.contains("KEY_CAR_MODEL"))  {
            String indexString = this.sp.getString("KEY_POKEDEX_NUMBER", "19");
            favourite = Integer.parseInt(indexString);
            favourite=19;

        }
        else {
            // output an error
        }

        PokemonManagerSingleton pokemonSingelton = PokemonManagerSingleton.getInstance();
        ArrayList<Pokemon> pokemondata = pokemonSingelton.getPokemonList();
        PokemonAdapter pokemon = new PokemonAdapter(this, R.layout.list, pokemondata, favourite);




        binding.list.setAdapter(pokemon);



        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Details.class);
                intent.putExtra("POSITION", position);
                startActivity(intent);
            }
        });



    }

}
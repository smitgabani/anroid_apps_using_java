package com.example.t1_smit_sgabani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.t1_smit_sgabani.databinding.ActivityDetailsBinding;
import com.example.t1_smit_sgabani.db.PokemonManagerSingleton;
import com.example.t1_smit_sgabani.models.Pokemon;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Details extends AppCompatActivity {
    ActivityDetailsBinding binding;
    private SharedPreferences sp;

    int position;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.sp = getSharedPreferences("MY-SP", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION",0 );
        PokemonManagerSingleton pokemonSingelton = PokemonManagerSingleton.getInstance();
        ArrayList<Pokemon> pokemondata = pokemonSingelton.getPokemonList();
        Pokemon cur = pokemondata.get(position);
        //Log.i(cur, "DETAILS OF POKEMON");
        String name = cur.getName();

        int imageid = getResources().getIdentifier(cur.getName().toLowerCase(Locale.ROOT), "drawable",
                getPackageName());
        binding.ivImage.setImageResource(imageid);

        Log.i(name, "DETAILS OF POKEMON");
        binding.pokemonName.setText(cur.getName());
        number = cur.getNumber();
        binding.pokemonId.setText("Pokemon ID: "+ number);

        binding.fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomNumber1 = random.nextInt(5) + 0;
                int randomNumber2 = random.nextInt(5) + 0;
                String res = "LOSS";
                if (randomNumber1 > randomNumber2) {
                    res = "WIN";
                    int newWin = cur.getWins() +1;
                    cur.setWins(newWin);
                }
                if (randomNumber1 <= randomNumber2) {
                    res = "LOSSES";
                    int newLosses = cur.getLosses() +1;
                    cur.setLosses(newLosses);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                Snackbar result = Snackbar.make(binding.getRoot(), res, 3000);

                result.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 6000);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_options, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setFav:
                setFavourite();
                return true;
            case R.id.reset:
                reset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setFavourite() {

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("KEY_POKEDEX_NUMBER", String.valueOf(number));
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);

    }
    private void reset() {
        PokemonManagerSingleton pokemonSingelton = PokemonManagerSingleton.getInstance();
        ArrayList<Pokemon> pokemondata = pokemonSingelton.getPokemonList();
        Pokemon cur = pokemondata.get(position);
        cur.setLosses(0);
        cur.setWins(0);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
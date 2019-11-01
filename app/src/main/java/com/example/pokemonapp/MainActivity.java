package com.example.pokemonapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Pokemon> clonePokemons = new ArrayList<>();
    ArrayList<Pokemon> pokemons = new ArrayList<>();
    Intent intent;


    public class GetPokemons extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ArrayList<String> list = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("pokemon"));
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    ArrayList<String> type = new ArrayList<>();
                    for(int j = 0; j < jsonObject1.getJSONArray("type").length(); j++) {
                        type.add(jsonObject1.getJSONArray("type").getString(j));
                    }

                    ArrayList<String> weakness = new ArrayList<>();
                    for(int j = 0; j < jsonObject1.getJSONArray("weaknesses").length(); j++) {
                        weakness.add(jsonObject1.getJSONArray("weaknesses").getString(j));
                    }

                    pokemons.add(new Pokemon(jsonObject1.getInt("id"), jsonObject1.getString("name"), jsonObject1.getString("img"), type, jsonObject1.getString("height"), jsonObject1.getString("weight"), weakness));
                }
                setAdapter(pokemons);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setAdapter(final ArrayList<Pokemon> pokemons) {
        MyAdapter myAdapter = new MyAdapter(getBaseContext(), R.layout.grid_view_list, pokemons);
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("name", pokemons.get(position).name);
                intent.putExtra("image", pokemons.get(position).getImage());
                intent.putExtra("height", pokemons.get(position).getHeight());
                intent.putExtra("weight", pokemons.get(position).getWeight());
                intent.putStringArrayListExtra("types", pokemons.get(position).getType());
                intent.putStringArrayListExtra("weaknesses", pokemons.get(position).getWeakness());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    setAdapter(pokemons);
                    return false;
                }
                clonePokemons.clear();
                for (Pokemon p : pokemons) {
                    if (p.name.toLowerCase().startsWith(newText.toLowerCase())) {
                        clonePokemons.add(p);
                    }
                }
                setAdapter(clonePokemons);
                return false;
            }

        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetPokemons task = new GetPokemons();
        task.execute("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json");

        setContentView(R.layout.activity_main);
        Toolbar mytoolBar = findViewById(R.id.app_bar);
        setSupportActionBar(mytoolBar);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimary));

        gridView = (GridView) findViewById(R.id.gridView);
        intent = new Intent(MainActivity.this, detailActivity.class);

    }
}

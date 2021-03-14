package edu.quinnipiac.gadacy.foodcalories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    NutrientHandler nutrientHandler = new NutrientHandler();

    private String siteURL = "https://edamam-food-and-grocery-database.p.rapidapi.com/parser?ingr=";
    private boolean userSelect = false;
    private ShareActionProvider provider;
    String foodFact[] = null;
    private RecyclerView mRecycleView;
    private LinkedList<String> mFoodList = new LinkedList<>();
    private FoodListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < nutrientHandler.foods.length; i++) {
            mFoodList.addLast(nutrientHandler.foods[i]);
        }

        mRecycleView = findViewById(R.id.recyclerView);
        mAdapter = new FoodListAdapter(this, mFoodList, this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    //Search for a given food on the database
    public void lookUpFood(String item) {
        new FetchFoodInfo().execute(item);
    }

    //Set up the toolbar options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.share));
        return super.onCreateOptionsMenu(menu);
    }

    //See what toolbar option is selected and perform the required action
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.share:
                Toast.makeText(this, "Please select a food first!", Toast.LENGTH_LONG).show();
                break;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userSelect = true;
    }

    class FetchFoodInfo extends AsyncTask<String, Void, String> {
        //Array to story food info from JSON
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(siteURL + strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "eb04409a80mshbbb7a990e545dc5p1e3b52jsn357dcfb6743d");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(in));

                if (reader == null) {
                    Log.e("MainActivity", "ERROR: READER RETURNED NULL");
                    return null;
                }
                foodFact = getStringFromReader(reader);


            } catch (Exception e) {
                Log.e("MainActivity", "ERROR " + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return foodFact[0];
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Intent intent = new Intent(MainActivity.this, NutrientInfoActivity.class);
                intent.putExtra("foodName", foodFact[0]);
                intent.putExtra("foodCal", foodFact[1]);
                intent.putExtra("foodFat", foodFact[2]);
                intent.putExtra("foodImage", foodFact[3]);
                startActivity(intent);
            } else {
                Log.e("MainActivity", "ERROR: onPostExecute RESULT IS NULL.");
            }
        }
    }

    private String[] getStringFromReader(BufferedReader reader) {
        StringBuffer buffer = new StringBuffer();
        String line;

        if (reader != null) {
            try {
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + '\n');
                }
                return nutrientHandler.getFoodInfo(buffer.toString());
            } catch (Exception e) {
                Log.e("MainActivity", "ERROR " + e.getMessage());
                return null;
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("MainActivity", "ERROR " + e.getMessage());
                }
            }
        }
        return null;
    }
}

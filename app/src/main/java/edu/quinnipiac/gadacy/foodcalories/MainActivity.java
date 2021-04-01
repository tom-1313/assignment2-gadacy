package edu.quinnipiac.gadacy.foodcalories;

/*
Thomas Gadacy
Professor Ruby ElKharboutly
Assignment 2 Part 2
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener {
    NutrientHandler nutrientHandler = new NutrientHandler();

    private String siteURL = "https://edamam-food-and-grocery-database.p.rapidapi.com/parser?ingr=";
    private ShareActionProvider provider;
    String foodFact[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, mainFragment);
        fragmentTransaction.commit();
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
                HelpActivity help = new HelpActivity();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, help);
                fragmentTransaction.commit();
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
                intent.putExtra("foodFacts", foodFact);
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

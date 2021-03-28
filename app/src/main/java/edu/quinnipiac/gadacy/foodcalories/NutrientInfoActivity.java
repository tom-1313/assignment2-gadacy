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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class NutrientInfoActivity extends AppCompatActivity {
    public String name;
    public String calorie;
    public String fat;
    private ShareActionProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_info);

        Intent intent = getIntent();
        NutrientInfoFragment frag = new NutrientInfoFragment();
        frag.setInfo((String[]) intent.getExtras().get("foodFacts"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.info_container, frag);
        fragmentTransaction.commit();

        //set up the ActionBar
        setSupportActionBar(findViewById(R.id.toolbar2));

    }

    //Set up the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider((MenuItem) menu.findItem(R.id.share));
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.share:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "This food looks good! \n Food: " + name + ", Calories: " + calorie + ", Fat: " + fat);
                provider.setShareIntent(intent);
                break;
            case R.id.help:
                HelpActivity help = new HelpActivity();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.info_container, help);
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
}
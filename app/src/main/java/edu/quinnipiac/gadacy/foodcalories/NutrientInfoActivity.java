package edu.quinnipiac.gadacy.foodcalories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
        TextView foodTextView = (TextView) findViewById(R.id.food_textView);
        TextView calorieTextView = (TextView) findViewById(R.id.calorie_number_textView);
        TextView fatTextView = (TextView) findViewById(R.id.fat_number_textView);

        foodTextView.setText((String) intent.getExtras().get("foodName"));
        calorieTextView.setText((String) intent.getExtras().get("foodCal"));
        fatTextView.setText((String) intent.getExtras().get("foodFat"));

        name = (String) intent.getExtras().get("foodName");
        calorie = (String) intent.getExtras().get("foodCal");
        fat = (String) intent.getExtras().get("foodFat");

        //TODO: bonus: figure out how to use the JSON "image" to set an image to that image.
        ImageView imageView = (ImageView) findViewById(R.id.food_imageView);
        Picasso.get().load((String) intent.getExtras().get("foodImage")).into(imageView);
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
}
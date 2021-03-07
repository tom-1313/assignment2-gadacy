package edu.quinnipiac.gadacy.foodcalories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NutrientInfoActivity extends AppCompatActivity {

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
        //TODO: bonus: figure out how to use the JSON "image" to set an image to that image.

        //set up the ActionBar
        setSupportActionBar(findViewById(R.id.toolbar2));

    }

    //Set up the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
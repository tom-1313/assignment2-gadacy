package edu.quinnipiac.gadacy.foodcalories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class NutrientInfoFragment extends Fragment {

    String info[] = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.fragment_nutrient_info, container, false);
    }

    public void setInfo(String[] info) {
        this.info = info;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null && info != null) {

            TextView foodTextView = (TextView) view.findViewById(R.id.food_textView);
            TextView calorieTextView = (TextView) view.findViewById(R.id.calorie_number_textView);
            TextView fatTextView = (TextView) view.findViewById(R.id.fat_number_textView);
            foodTextView.setText(info[0]);
            calorieTextView.setText(info[1]);
            fatTextView.setText(info[2]);
            ImageView imageView = (ImageView) view.findViewById(R.id.food_imageView);
            Picasso.get().load(info[3]).into(imageView);
        }


    }
}

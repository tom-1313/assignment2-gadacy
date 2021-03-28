package edu.quinnipiac.gadacy.foodcalories;

/*
Thomas Gadacy
Professor Ruby ElKharboutly
Assignment 2 Part 2
 */
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Activity where the help information is displayed
public class HelpActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_help, container, false);
        return view;
    }
}
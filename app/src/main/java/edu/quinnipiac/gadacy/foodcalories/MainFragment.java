package edu.quinnipiac.gadacy.foodcalories;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

public class MainFragment extends Fragment {

    private RecyclerView mRecycleView;
    private LinkedList<String> mFoodList = new LinkedList<>();
    private FoodListAdapter mAdapter;
    private MainFragmentListener mainActivity;
    public String foods[] = { "Cauliflower", "Broccoli", "Cabbage", "Potato", "Eggplant", "Chillies", "Tomato", "Leek", "Corn", "Ginger", "Pumpkin", "Squash", "Cucumber", "Spinach", "Sweet Potato", "Apple", "Apricot", "Avocado", "Banana", "Blueberry", "Blackberry",
            "Orange", "Lime", "Pear", "Passionfruit", "Beef", "Salmon", "Tuna", "Ice cream", "Barley" };

    public interface MainFragmentListener {
        void lookUpFood(String item);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        for (int i = 0; i < foods.length; i++) {
            mFoodList.addLast(foods[i]);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecycleView = view.findViewById(R.id.recyclerView);
        mAdapter = new FoodListAdapter(getContext(), mFoodList, mainActivity);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
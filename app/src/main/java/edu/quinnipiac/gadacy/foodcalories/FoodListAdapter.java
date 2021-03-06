package edu.quinnipiac.gadacy.foodcalories;

/*
Thomas Gadacy
Professor Ruby ElKharboutly
Assignment 2 Part 2
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

//This class and inner class sets up the RecyclerView for the options of food shown in the MainActivity
public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private final LayoutInflater mInflater;
    private final LinkedList<String> mFoodList;
    private Context context;
    private MainFragment.MainFragmentListener mainActivity;


    public FoodListAdapter(Context context, LinkedList<String> foodList, MainFragment.MainFragmentListener mainActivity) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mainActivity = mainActivity;
        this.mFoodList = foodList;
    }

    @NonNull
    @Override
    public FoodListAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.foodlist_item, parent, false);
        return new FoodViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.FoodViewHolder holder, int position) {
        String mCurr = mFoodList.get(position);
        holder.foodItemView.setText(mCurr);

    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final FoodListAdapter mAdapter;
        public TextView foodItemView;

        public FoodViewHolder(View mItemView, FoodListAdapter foodListAdapter) {
            super(mItemView);
            foodItemView = mItemView.findViewById(R.id.food_item);
            this.mAdapter = foodListAdapter;
            mItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String element = mFoodList.get(mPosition);
            mainActivity.lookUpFood(element);
        }
    }

}

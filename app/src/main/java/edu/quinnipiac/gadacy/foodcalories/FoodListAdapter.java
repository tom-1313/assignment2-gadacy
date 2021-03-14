package edu.quinnipiac.gadacy.foodcalories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private final LayoutInflater mInflater;
    private final LinkedList<String> mFoodList;
    private Context context;
    private MainActivity mainActivity;

    public FoodListAdapter(Context context, LinkedList<String> foodList, MainActivity mainActivity) {
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
            //when an item is clicked start the thread in the mainActivity
            int mPosition = getLayoutPosition();
            String element = mFoodList.get(mPosition);
            mainActivity.lookUpFood(element);
        }
    }

}

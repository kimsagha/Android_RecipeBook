package com.example.recipebook;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private ItemClickListener mClickListener;

    public RecipeAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.db_layout_view, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Recipe> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleText;

        RecipeViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text1);
            itemView.setOnClickListener(this);
        }

        void bind(final Recipe recipe) {

            if (recipe != null) {
                titleText.setText(recipe.getName());
            }
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                try {
                    mClickListener.onItemClick(view, getAdapterPosition());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Recipe getItem(int position) {
        return data.get(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position) throws ExecutionException, InterruptedException;
    }

}

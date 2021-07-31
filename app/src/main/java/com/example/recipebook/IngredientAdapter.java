package com.example.recipebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public IngredientAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.db_layout_view, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Ingredient> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;

        IngredientViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.text1);
        }

        void bind(final Ingredient ingredient) {

            if (ingredient != null) {
                nameText.setText(ingredient.getIngredientname());
            }
        }

    }

}

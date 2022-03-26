package com.example.medicalreminder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.add_medication_screen.AdapterClickListener;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.ViewHolder> {
    private Context context;
    private String[] listItems;
    private AdapterClickListener adapterClickListener;

    public SaveListAdapter(Context context, String[] listItems, AdapterClickListener adapterClickListener) {
        this.context = context;
        this.listItems = listItems;
        this.adapterClickListener = adapterClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.on_save_fragment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.listItemTx.setText(listItems[position]);
        holder.listItemImg.setVisibility(View.INVISIBLE);

        holder.listItemTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClickListener.updateUi(String.valueOf(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView listItemTx;
        public ImageView listItemImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listItemTx = itemView.findViewById(R.id.list_item_tx);
            listItemImg = itemView.findViewById(R.id.list_item_img);
        }
    }
}

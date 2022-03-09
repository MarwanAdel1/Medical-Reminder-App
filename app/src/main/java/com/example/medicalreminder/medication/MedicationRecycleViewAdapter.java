package com.example.medicalreminder.medication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;

import java.util.ArrayList;

public class MedicationRecycleViewAdapter extends RecyclerView.Adapter<MedicationRecycleViewAdapter.MedicationViewHolder> {

    private ArrayList<MedicationModel> myItems ;
    private OnMedClickListener listener ;

    public MedicationRecycleViewAdapter( OnMedClickListener listener , ArrayList<MedicationModel>myItems){
        this.myItems=myItems;
        this.listener = listener ;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_custom_row,parent,false);
        MedicationViewHolder  viewHolder = new MedicationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        MedicationModel model = myItems.get(position);
        holder.drugNametext.setText(myItems.get(position).getDrugName());
        holder.numberOfDrugstext.setText(String.valueOf(myItems.get(position).getDrugsNumber()));
        holder.drugImage.setImageResource((myItems.get(position).getImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on item click
                listener.onClick(model);
            }
        });


    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder{

        ImageView drugImage ;
        TextView drugNametext , numberOfDrugstext ;
        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);

            drugImage = itemView.findViewById(R.id.drugImage) ;
            drugNametext = itemView.findViewById(R.id.drugNametext) ;
            numberOfDrugstext = itemView.findViewById(R.id.numberOfDrugstext) ;
        }
    }
}


package com.example.medicalreminder.screens.medication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicationRecycleViewAdapter extends RecyclerView.Adapter<MedicationRecycleViewAdapter.MedicationViewHolder> {

    private List<Medicine> myItems ;
    private OnMedClickListener listener ;

    public MedicationRecycleViewAdapter( OnMedClickListener listener , List<Medicine>myItems){
        this.myItems=myItems;
        this.listener = listener ;
    }

    public void setMyItems(List<Medicine> myItems) {
        this.myItems = myItems;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_custom_row,parent,false);
        MedicationViewHolder  viewHolder = new MedicationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Medicine model = myItems.get(position);
        holder.drugNametext.setText(myItems.get(position).getMedName());
        holder.numberOfDrugstext.setText(String.valueOf(myItems.get(position).getMedForm()));
        //holder.drugImage.setImageResource((myItems.get(position).getImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on item click
                //Toast.makeText(view.getContext(), "kj : "+ model.getMedName(), Toast.LENGTH_SHORT).show();

                listener.onClick(model , position);
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


package com.example.medicalreminder.views.medicine_list_home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MedicineListHomeAdapter extends RecyclerView.Adapter<MedicineListHomeAdapter.ViewHolder>{

    List<MedicineInfoHome> medicinesArray;
    Context context;

    // MedicineListAdapter Constructor
    public MedicineListHomeAdapter(Context _context, List<MedicineInfoHome> medicines){
            context = _context;
            medicinesArray = medicines;
        }


    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        View row;
        ConstraintLayout constraintLayout;
        TextView medicine_time;
        TextView medicine_name;
        TextView medicine_info;
        TextView medicine_missed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            row = itemView;
            constraintLayout = row.findViewById(R.id.main_constaint_layout);
            medicine_time = row.findViewById(R.id.drug_data);
            medicine_name = row.findViewById(R.id.drug_name);
            medicine_info = row.findViewById(R.id.drug_data);
            medicine_missed = row.findViewById(R.id.drug_missed);
        }
        // Getters
        public ConstraintLayout getConstraintLayout() {
            return constraintLayout;
        }

        public TextView getMedicine_time() {
            return medicine_time;
        }

        public TextView getMedicine_name() {
            return medicine_name;
        }

        public TextView getMedicine_info() {
            return medicine_info;
        }

        public TextView getMedicine_missed() {
            return medicine_missed;
        }
    }


    //                Creating a new medicine
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //              Layout inflation
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //                 what i want to parse, parse/ put it in parent view(this view)
        View view = layoutInflater.inflate(R.layout.single_medicine, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    //                Creating a new medicine
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getMedicine_time().setText(medicinesArray.get(position).getMedicineTime());
        holder.getMedicine_name().setText(medicinesArray.get(position).getMedicineName());
        holder.getMedicine_info().setText(medicinesArray.get(position).getMedicineInfo());
        holder.getMedicine_missed().setText(medicinesArray.get(position).getMissed());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Medicine info requested", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicinesArray.size();
    }
}


package com.example.medicalreminder.screens.home_screen.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.pojo.Medicine;
import com.example.medicalreminder.pojo.MedicineTimeList;
import com.example.medicalreminder.screens.medication.OnMedClickListener;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private List<MedicineTimeList> myMedicines ;
    private OnMedClickListener listener ;

    public MedicineAdapter(OnMedClickListener listener , List<MedicineTimeList> myMedicines){
        this.myMedicines = myMedicines;
        this.listener = listener ;
    }

    public void setMyMedicines(List<MedicineTimeList> myMedicines) {
        this.myMedicines = myMedicines;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View row;
        LinearLayout linearLayout;
        TextView timeValue;
        TextView medicineName;
        TextView medicineDetails;
        TextView medicineMissed;
        ImageView medicineImage;

        //              Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //          UI references
            row = itemView;
            linearLayout = row.findViewById(R.id.linear_layout);
            timeValue = row.findViewById(R.id.drug_time_text);
            medicineName = row.findViewById(R.id.drugNametext);
            medicineDetails = row.findViewById(R.id.drug_data);
            medicineMissed = row.findViewById(R.id.drug_missed);
            medicineImage = row.findViewById(R.id.drugImage);
        }

        //          Getters
        public LinearLayout getLinearLayout() {return linearLayout;}

        public TextView getTimeValue() {return timeValue;}

        public TextView getMedicineName() {return medicineName;}

        public TextView getMedicineDetails() {return medicineDetails;}
    }

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = myMedicines.get(position).getMedicine();
        holder.getMedicineName().setText(medicine.getMedName());
        String medicineStrength = String.valueOf(medicine.getMedStrength());
        String medicineStrengthUnit = medicine.getMedStrengthUnit();
        String medicinesNoPerDose = String.valueOf(medicine.getMedNumberOfPillsPerDose());
        holder.getMedicineDetails().setText(medicineStrength + medicineStrengthUnit + ", " + medicinesNoPerDose + " Pill(s)");
        holder.getTimeValue().setText(myMedicines.get(position).getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(medicine,position);
            }
        });
    }

    @Override
    public int getItemCount() {return myMedicines.size();}


}

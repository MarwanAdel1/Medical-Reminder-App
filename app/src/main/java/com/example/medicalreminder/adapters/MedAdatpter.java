package com.example.medicalreminder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;
import com.example.medicalreminder.views.add_medication_screen.AdapterClickListener;
import com.example.medicalreminder.views.add_medication_screen.fragments.AddMedFormFragment;

public class MedAdatpter extends RecyclerView.Adapter<MedAdatpter.ViewHolder> {
    private AddMedFormFragment addMedFormFragment;
    private Context context;
    private String[] medFormList;
    private AdapterClickListener adapterClickListener;

    public MedAdatpter(Context context, String[] medFormList, AdapterClickListener adapterClickListener) {
        this.context = context;
        this.medFormList = medFormList;
        this.adapterClickListener = adapterClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.med_forn_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTx.setText(medFormList[position]);

        holder.titleTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medData = holder.titleTx.getText().toString();
                adapterClickListener.updateUi(medData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medFormList.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTx;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTx = itemView.findViewById(R.id.form_title);
        }
    }
}

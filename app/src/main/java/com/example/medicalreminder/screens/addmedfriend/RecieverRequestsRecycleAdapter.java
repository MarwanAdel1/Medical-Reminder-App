package com.example.medicalreminder.screens.addmedfriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;

import java.util.ArrayList;

public class RecieverRequestsRecycleAdapter extends RecyclerView.Adapter<RecieverRequestsRecycleAdapter.ViewHolder>{

    private ArrayList<RequestModel> myItems ;
    private OnRefuseClickListener refuselistener ;
    private OnAcceptClickListener acceptlistener ;

    public RecieverRequestsRecycleAdapter(ArrayList<RequestModel> arrayList){
        this.myItems = arrayList;
    }

    public RecieverRequestsRecycleAdapter(OnRefuseClickListener listener , OnAcceptClickListener acceptlistener , ArrayList<RequestModel> myItems ){
        this.refuselistener = listener ;
        this.acceptlistener = acceptlistener ;
        this.myItems = myItems ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_requests_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestModel model = myItems.get(position);

        holder.senderName.setText(myItems.get(position).getSenderName());
        holder.sendermail.setText(myItems.get(position).getSenderEmail());

        holder.refusebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuselistener.onRefuseClick(model);
            }
        });

        holder.acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptlistener.onAcceptClick(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sendermail ;
        TextView senderName ;
        Button refusebtn ;
        Button acceptbtn ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sendermail = itemView.findViewById(R.id.sender_mail_text_setting);
            senderName = itemView.findViewById(R.id.sender_name_text_setting);
            refusebtn = itemView.findViewById(R.id.refusebtn);
            acceptbtn = itemView.findViewById(R.id.acceptbtn);
        }
    }
}

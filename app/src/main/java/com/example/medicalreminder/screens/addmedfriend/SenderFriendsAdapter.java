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

public class SenderFriendsAdapter extends RecyclerView.Adapter<SenderFriendsAdapter.ViewHolder>{

    private ArrayList<RequestModel> myItems ;
    private OnClickDeleteSendRequest onMedfriendClickListener ;


    public SenderFriendsAdapter(ArrayList<RequestModel> arrayList){
        this.myItems = arrayList;
    }

    public SenderFriendsAdapter(OnClickDeleteSendRequest listener , ArrayList<RequestModel> myItems ){
        this.onMedfriendClickListener = listener ;
        this.myItems = myItems ;
    }

    public void setMyItems(ArrayList<RequestModel> myItems){
        this.myItems=myItems;
    }

    @NonNull
    @Override
    public SenderFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_requests_custom_row,parent,false);
        SenderFriendsAdapter.ViewHolder viewHolder = new SenderFriendsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SenderFriendsAdapter.ViewHolder holder, int position) {

        RequestModel model = myItems.get(position);
        holder.recieverName.setText(myItems.get(position).getReciverName());
        holder.recieverState.setText(myItems.get(position).getStatus());


        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMedfriendClickListener.OnClickDeleteSendRequest(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView recieverName ;
        TextView recieverState ;
        Button deletebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recieverName = itemView.findViewById(R.id.reciver_name_friends_text);
            recieverState = itemView.findViewById(R.id.reciever_state_text);
            deletebtn = itemView.findViewById(R.id.deleteSendRequestbutton) ;
        }
    }
}

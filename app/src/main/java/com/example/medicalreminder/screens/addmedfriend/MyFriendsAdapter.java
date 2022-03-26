package com.example.medicalreminder.screens.addmedfriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalreminder.R;

import java.util.ArrayList;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder>{

    private ArrayList<RequestModel> myItems ;
    private OnMedfriendClickListener onMedfriendClickListener ;


    public MyFriendsAdapter(ArrayList<RequestModel> arrayList){
        this.myItems = arrayList;
    }

    public MyFriendsAdapter(OnMedfriendClickListener listener , ArrayList<RequestModel> myItems ){
        this.onMedfriendClickListener = listener ;
        this.myItems = myItems ;
    }

    public void setMyItems(ArrayList<RequestModel> myItems){
        this.myItems=myItems;
    }

    @NonNull
    @Override
    public MyFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mymedfriends_custom_row,parent,false);
        MyFriendsAdapter.ViewHolder viewHolder = new MyFriendsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendsAdapter.ViewHolder holder, int position) {

        RequestModel model = myItems.get(position);
        holder.recieverName.setText(myItems.get(position).getReciverName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMedfriendClickListener.onMedFriendClick(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView recieverName ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recieverName = itemView.findViewById(R.id.reciver_name_friends_text);

        }
    }
}



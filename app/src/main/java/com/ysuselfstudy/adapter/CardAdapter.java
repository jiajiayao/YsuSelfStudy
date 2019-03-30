package com.ysuselfstudy.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ysuselfstudy.R;
import com.ysuselfstudy.database.EmptyRoom;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
   private List<EmptyRoom> mEmptyRoom;
    private static final String TAG = "CardAdapter";
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView number;
        TextView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.classname);
            number=(TextView) itemView.findViewById(R.id.number);
            location=(TextView) itemView.findViewById(R.id.location);
        }
    }

    public CardAdapter(List<EmptyRoom> list) {
        super();
        mEmptyRoom=list;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.classroom,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder viewHolder, int i) {
        EmptyRoom emptyRoom=mEmptyRoom.get(i);
        viewHolder.location.setText(emptyRoom.getLocation());
        viewHolder.number.setText(emptyRoom.getNums());
        viewHolder.name.setText(emptyRoom.getRoom());
    }

    @Override
    public int getItemCount() {
        return mEmptyRoom.size();
    }


}

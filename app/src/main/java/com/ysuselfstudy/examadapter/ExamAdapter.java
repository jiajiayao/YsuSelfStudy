package com.ysuselfstudy.examadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ysuselfstudy.R;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    private List<ExamInfor> mList;
        static class ViewHolder extends RecyclerView.ViewHolder
        {
             TextView time;
             TextView name;
             TextView location;
             TextView number;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                time=(TextView) itemView.findViewById(R.id.exam_time);
                location= itemView.findViewById(R.id.exam_positon);
                number=itemView.findViewById(R.id.exam_number);
                name = itemView.findViewById(R.id.exam_name);
            }
        }

    public ExamAdapter(List<ExamInfor> list) {
            mList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.exam_layout,viewGroup,false);
            ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ExamInfor examInfor=mList.get(i);
        viewHolder.time.setText(examInfor.getTime());
        viewHolder.number.setText(examInfor.getNumber());
        viewHolder.location.setText(examInfor.getLocation());
        viewHolder.name.setText(examInfor.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

package com.ysuselfstudy.gradeadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysuselfstudy.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder> {
    public List<GradeBean> list = new ArrayList<>();
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        public AppCompatTextView kemu;
        public AppCompatTextView mark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kemu = itemView.findViewById(R.id.kemu);
            mark = itemView.findViewById(R.id.mark);
        }
    }

    public MarkAdapter(List<GradeBean> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradeBean gradeBean = list.get(position);
        holder.mark.setText(gradeBean.getMark());
        holder.kemu.setText(gradeBean.getKemu());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

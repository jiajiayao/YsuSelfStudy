package com.ysuselfstudy.gradeadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysuselfstudy.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder> {
    public List<GradeBean> list = new ArrayList<>();
    public Context context;
    private static final String TAG = "MarkAdapter";
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

    public MarkAdapter(List<GradeBean> list,Context context) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradeBean gradeBean = list.get(position);
        if (gradeBean.getMark().contains("A"))
        {
            Log.d(TAG, "onBindViewHolder: 有A");
            holder.mark.setTextColor(ContextCompat.getColor(context, R.color.color_light_green));
        }
        holder.mark.setText(gradeBean.getMark());
        holder.kemu.setText(gradeBean.getKemu());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

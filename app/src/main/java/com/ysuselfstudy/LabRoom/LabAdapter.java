package com.ysuselfstudy.LabRoom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysuselfstudy.R;
import com.ysuselfstudy.gradeadapter.MarkAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.ViewHolder> {
    private static final String TAG = "LabAdapter";
    public List<LabBean> list = new ArrayList<>();
    public LabAdapter(List<LabBean> temp) {
        this.list=temp ;

    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        public AppCompatTextView time;
        public AppCompatTextView project;
        public AppCompatTextView location;
        public AppCompatTextView content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.lab_time);
            location = itemView.findViewById(R.id.lab_location);
            content = itemView.findViewById(R.id.lab_content);
            project = itemView.findViewById(R.id.lab_project);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LabBean labBean = list.get(position);
        holder.content.setText("课题："+labBean.getContent());
        holder.location.setText("地点："+labBean.getRoom());
        holder.time.setText("时间："+labBean.getTime());
        holder.project.setText("课程："+labBean.getProject());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_layout, parent, false);
        LabAdapter.ViewHolder holder = new LabAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

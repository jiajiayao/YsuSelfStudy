package com.ysuselfstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysuselfstudy.R;
import com.github.vipulasri.timelineview.TimelineView;
import com.ysuselfstudy.database.MipushInfor;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {
    public List<MipushInfor> mbean;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    static class ViewHolder extends RecyclerView.ViewHolder{
        public TimelineView timelineView;
        public AppCompatTextView date;
        public AppCompatTextView message;
        public ViewHolder(@NonNull View itemView, int viewtype) {
            super(itemView);
            timelineView = itemView.findViewById(R.id.timeline);
            date=itemView.findViewById(R.id.text_timeline_date);
            message=itemView.findViewById(R.id.text_timeline_title);
            timelineView.initLine(viewtype);
        }

    }
    public TimeLineAdapter(List<MipushInfor> list)
    {
        this.mbean=list;
    }
    @NonNull
    @Override
    public TimeLineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View  view = mLayoutInflater.inflate(R.layout.timeline_layout,viewGroup,false);
        return new ViewHolder(view, i);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineAdapter.ViewHolder viewHolder, int i) {
        MipushInfor be = mbean.get(i);

        viewHolder.date.setText(be.getTime());
        viewHolder.message.setText(be.getInformation());
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return mbean.size();
    }
}

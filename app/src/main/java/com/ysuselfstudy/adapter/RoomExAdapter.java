package com.ysuselfstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.ysuselfstudy.R;
import com.ysuselfstudy.database.School;
import com.ysuselfstudy.database.SchoolBuilding;

import java.util.List;

public class RoomExAdapter extends BaseExpandableListAdapter {
    List<School> groupList; //父级列表数据
    List<List> childList;//子级数据
    Context context;//应用上下文
    int groutlayout;//父级列表布局
    int childrenlayout;//子级列表布局


    /**
     *构造方法
     *
     * */
    public RoomExAdapter(Context context,int groutlayoutID,int childrenlayoutID,
                         List<School> groupList, List<List> childList)
    {
        this.childList=childList;
        this.childrenlayout=childrenlayoutID;
        this.groupList=groupList;
        this.groutlayout=groutlayoutID;
        this.context=context.getApplicationContext();
    }


    /**
     * 读取父级数据条数
     * @return 返回父级数据大小，也就是学有多少建筑
     */
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    /**
     * 读取子级数据条数
     * @param groupPosition 指针，镖师当前正在现实的的 X 条父级数据
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        List<SchoolBuilding> itemList=childList.get(groupPosition);
        return itemList.size();
    }

    /**
     *  读取 Group 数据
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return  groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
       List<SchoolBuilding> itemList=childList.get(groupPosition);
        return itemList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null)// 如果布局缓存为空，那么加载布局到 view
            view= LayoutInflater.from(context).inflate(groutlayout,parent,false);
        else
            view=convertView;

        School School=(School) getGroup(groupPosition);

        TextView textView = (TextView) view.findViewById(R.id.view_title);
        textView.setText(School.getWhere());


        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null)// 如果布局缓存为空，那么加载布局到 view
            view= LayoutInflater.from(context).inflate(childrenlayout,parent,false);
        else
            view=convertView;

        SchoolBuilding SchoolBuilding=(SchoolBuilding) getChild(groupPosition,childPosition);


        final TextView textView = (TextView) view.findViewById(R.id.view_content);
        textView.setText(SchoolBuilding.getBuildingName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

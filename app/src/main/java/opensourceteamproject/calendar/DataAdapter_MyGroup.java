package opensourceteamproject.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DataAdapter_MyGroup extends BaseExpandableListAdapter{
    private ArrayList<Data_Group> dataList;
    private Context context;
    private ViewHolder viewHolder;

    public DataAdapter_MyGroup(Context context, ArrayList<Data_Group> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    /* Child List Data */
    @Override
    public int getChildrenCount(int groupPos) {
        return dataList.get(groupPos).data.size();
    }

    @Override
    public Data getChild(int groupPos, int childPos) {
        return dataList.get(groupPos).data.get(childPos);
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public View getChildView (int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v == null) {
            v = getChildGenericView();
            viewHolder=new ViewHolder();
            viewHolder.set_dDay=(TextView) v.findViewById(R.id.mdday);
            viewHolder.set_title = (TextView) v.findViewById(R.id.mtitle);
            viewHolder.set_date = (TextView) v.findViewById(R.id.mdate);
            v.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.set_dDay.setText(dataList.get(groupPos).data.get(childPos).getDDay());
        viewHolder.set_title.setText(dataList.get(groupPos).data.get(childPos).getTitle());
        viewHolder.set_date.setText(dataList.get(groupPos).data.get(childPos).getDate());

        return v;
    }

    /* Paren tList View */
    @Override
    public int getGroupCount() {
        try{
            return dataList.size();
        }catch(NullPointerException e){
            return 0;
        }
    }

    @Override
    public Data_Group getGroup(int groupPos) {
        return dataList.get(groupPos);
    }

    @Override
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = getParentGenericView();
        } else {
            view = convertView;
        }
        TextView set_group = (TextView)view.findViewById(R.id.mgroup);
        set_group.setText(dataList.get(groupPos).getGroup());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int childPos) {
        // false : 선택 불가능
        //  true : 선택 가능
        return true;
    }

    public View getChildGenericView() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.data, null);
        return view;
    }

    public View getParentGenericView() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.data_group, null);
        return view;
    }

    class ViewHolder{
        public TextView set_dDay = null;
        public TextView set_title = null;
        public TextView set_date = null;
    }
}
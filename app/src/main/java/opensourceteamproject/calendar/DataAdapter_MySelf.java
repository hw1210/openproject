package opensourceteamproject.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DataAdapter_MySelf extends BaseAdapter {
    private ArrayList<Data> dataList;
    private Context context;
    private ViewHolder viewHolder;
    private LayoutInflater inflater;


    public DataAdapter_MySelf(Context context, ArrayList<Data> dataList){
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Data getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.data, null);
            viewHolder.set_dDay=(TextView) v.findViewById(R.id.mdday);
            viewHolder.set_title = (TextView) v.findViewById(R.id.mtitle);
            viewHolder.set_date = (TextView) v.findViewById(R.id.mdate);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.set_dDay.setText(dataList.get(position).getDDay());
        viewHolder.set_title.setText(dataList.get(position).getTitle());
        viewHolder.set_date.setText(dataList.get(position).getDate());

        return v;
    }
    class ViewHolder{
        public TextView set_dDay = null;
        public TextView set_title = null;
        public TextView set_date = null;
    }
}
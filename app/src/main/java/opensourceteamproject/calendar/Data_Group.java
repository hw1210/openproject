package opensourceteamproject.calendar;

import java.util.ArrayList;

public class Data_Group {
    private String group;
    ArrayList<Data> data;

    public Data_Group(String group, ArrayList<Data> data){
        this.group=group;
        this.data=data;
    }

    String getGroup(){
        return group;
    }
}

package opensourceteamproject.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MyGroupActivity extends AppCompatActivity {
    Button btn_mySelf;
    FloatingActionButton btn_MakeS;
    Button btn_myHome;
    ToggleButton btn_calendar;

    String phoneNum="";
    Data data_container;
    ArrayList<Data> instanceList=new ArrayList<Data>();;
    Data_Group GroupData;
    ArrayList<Data_Group> instance;
    DataAdapter_MyGroup dataAdapter;
    ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroup);
        Intent rintent=new Intent(this.getIntent());
        phoneNum=rintent.getStringExtra("phoneNum");

        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);

        btn_mySelf=(Button)findViewById(R.id.mySelf);
        btn_mySelf.setOnClickListener(btn_mySelfClickListener);
        btn_MakeS=(FloatingActionButton)findViewById(R.id.MakeS);
        btn_MakeS.setOnClickListener(MakeSClickListener);
        btn_myHome=(Button)findViewById(R.id.myHome);
        btn_myHome.setOnClickListener(btn_myHomeClickListener);
        btn_MakeS.setRippleColor(Color.rgb(93,181,164));

        btn_calendar=(ToggleButton)findViewById(R.id.select_calendar);

        initListView();
    }

    public void initListView() {
        /*data_container=new Data("D-10","오픈소스","2018/06/11");
        instanceList.add(data_container);*/

        /*for(int i=0;data_container[i].equals(null);i++){ //할 일
            data_container[i]=null;//new Data=(dday,할일명,날짜);
            //instanceList[그룹(id).add(data_container[i]);
        }*/
        //GroupData=new Data_Group("",instanceList);
        instance=new ArrayList<Data_Group>();
        //instance.add(GroupData);

        /*for(int j=0;GroupData[j].equals(null);j++){
            GroupData[j]=null;//new Data_Group(그룹명,할일리스트);
            instance.add(GroupData[j]); //리스트뷰에 넣을 그룹리스트 합
        }*/

        dataAdapter = new DataAdapter_MyGroup(this, instance);
        listView = (ExpandableListView) findViewById(R.id.list_myGroup);
        listView.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater=getMenuInflater();
        mInflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.menu_bt1:

                Intent intent1 =new Intent(getApplicationContext(),Group_List.class);
                intent1.putExtra("phoneNum",phoneNum);
                startActivity(intent1);
                finish();
                return true;

            case R.id.menu_bt3:

                Intent intent3 =new Intent(getApplicationContext(),theme.class);
                intent3.putExtra("phoneNum",phoneNum);
                startActivity(intent3);
                finish();
                return true;

            case R.id.menu_bt4:

                Intent intent4 =new Intent(getApplicationContext(),setting.class);
                intent4.putExtra("phoneNum",phoneNum);
                startActivity(intent4);
                finish();
                return true;

        }

        return false;

    }
    View.OnClickListener btn_myHomeClickListener=new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener btn_mySelfClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MySelfActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener MakeSClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),Scheduling_MyGroup.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };
}

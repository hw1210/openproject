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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Scheduling_MySelf extends AppCompatActivity {
    FloatingActionButton btn_RegisterS,btn_CancelS;

    Switch btn_dDay,btn_allDay;
    TimePicker btn_startTime,btn_endTime;
    DatePicker btn_date;
    ToggleButton btn_calendar;
    Spinner btn_group;
    LinearLayout show_allDay,show_startTime,show_endTime,show_calendar,show_group;
    Button btn_mySelf;
    Button btn_myGroup;
    Button btn_myHome;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduling);

        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);

        btn_RegisterS=(FloatingActionButton)findViewById(R.id.RegisterS);
        btn_RegisterS.setOnClickListener(RegisterSClickListener);
        btn_CancelS=(FloatingActionButton)findViewById(R.id.CancelS);
        btn_CancelS.setOnClickListener(CancelSClickListener);

        btn_RegisterS.setRippleColor(Color.rgb(93,181,164));
        btn_CancelS.setRippleColor(Color.rgb(93,181,164));
        btn_myGroup=(Button)findViewById(R.id.myGroup);
        btn_myGroup.setOnClickListener(btn_myGroupClickListener);
        btn_myHome=(Button)findViewById(R.id.myHome);
        btn_myHome.setOnClickListener(btn_myHomeClickListener);
        btn_mySelf=(Button)findViewById(R.id.mySelf);
        btn_mySelf.setOnClickListener(btn_mySelfClickListener);

        //일정 생성
        btn_dDay=(Switch)findViewById(R.id.select_dDay);    btn_dDay.setChecked(true);
        btn_date=(DatePicker)findViewById(R.id.select_date);
        btn_allDay=(Switch)findViewById(R.id.select_allDay);    btn_allDay.setChecked(true);
        btn_startTime=(TimePicker) findViewById(R.id.select_startTime);
        btn_endTime=(TimePicker) findViewById(R.id.select_endTime);
        btn_calendar=(ToggleButton)findViewById(R.id.select_calendar);  btn_calendar.setChecked(true);
        btn_group=(Spinner)findViewById(R.id.select_group);

        show_allDay=(LinearLayout)findViewById(R.id.list_allDay);   show_allDay.setVisibility(View.GONE);
        show_startTime=(LinearLayout)findViewById(R.id.list_startTime); show_startTime.setVisibility(View.GONE);
        show_endTime=(LinearLayout)findViewById(R.id.list_endTime); show_endTime.setVisibility(View.GONE);
        show_calendar=(LinearLayout)findViewById(R.id.list_calendar);   show_calendar.setVisibility(View.VISIBLE);
        show_group=(LinearLayout)findViewById(R.id.list_group); show_group.setVisibility(View.GONE);


        btn_date.getDayOfMonth(); //일, 정수형
        btn_date.getMonth(); //월, 정수형
        btn_date.getYear(); //년, 정수형

        btn_dDay.setOnClickListener(btn_dDayClickListener);
        btn_allDay.setOnClickListener(btn_allDayClickListener);
        btn_calendar.setOnClickListener(btn_CalendarClickListener);

        /*int scheduleYear=btn_date.getYear();
        int scheduleMonth=btn_date.getMonth()+1;
        int scheduleDay=btn_date.getDayOfMonth();
        int startHour=btn_startTime.getHour();
        int startMinute=btn_startTime.getMinute();
        int endHour=btn_endTime.getHour();
        int endMinute=btn_endTime.getMinute();*/


        final String[] Group={"오픈소스","데베","컴그"};
        ArrayAdapter<String> adapter_group=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Group);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        btn_group.setAdapter(adapter_group);
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
                startActivity(intent1);
                finish();
                return true;

            case R.id.menu_bt3:

                Intent intent3 =new Intent(getApplicationContext(),theme.class);
                startActivity(intent3);
                finish();
                return true;

            case R.id.menu_bt4:

                Intent intent4 =new Intent(getApplicationContext(),setting.class);
                startActivity(intent4);
                finish();
                return true;

        }

        return false;

    }
    View.OnClickListener btn_myHomeClickListener=new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener btn_myGroupClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MyGroupActivity.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener btn_mySelfClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MySelfActivity.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener RegisterSClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"새로운 일정이 생성되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),MySelfActivity.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener CancelSClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();
            finish();
        }
    };
    View.OnClickListener btn_dDayClickListener=new View.OnClickListener(){
        public void onClick(View v){
            btn_allDay.setChecked(true);
            show_startTime.setVisibility(View.GONE);
            show_endTime.setVisibility(View.GONE);
            btn_calendar.setChecked(true);
            show_group.setVisibility(View.GONE);
            if(btn_dDay.isChecked()){ //디데이 on할 경우 - 개인(default), 그룹 일정 생성
                show_allDay.setVisibility(View.GONE);
                show_calendar.setVisibility(View.VISIBLE);
            }
            else{ //디데이 off할 경우 - 일반 일정 생성
                show_allDay.setVisibility(View.VISIBLE);
                show_calendar.setVisibility(View.GONE);
            }
        }
    };
    View.OnClickListener btn_allDayClickListener=new View.OnClickListener(){ // 디데이 off일 때
        public void onClick(View v){
            if(btn_allDay.isChecked()){ // 하루종일 on할 경우
                show_startTime.setVisibility(View.GONE);
                show_endTime.setVisibility(View.GONE);
            }
            else{ // 하루종일 on할 경우
                show_startTime.setVisibility(View.VISIBLE);
                show_endTime.setVisibility(View.VISIBLE);
            }
        }
    };
    View.OnClickListener btn_CalendarClickListener=new View.OnClickListener(){ // 디데이 on일 때
        public void onClick(View v){
            if(btn_calendar.isChecked()){ // 캘린더 종류 개인인 경우
                show_group.setVisibility(View.GONE);
            }
            else{ // 캘린더 종류 그룹인 경우
                show_group.setVisibility(View.VISIBLE);
            }
        }
    };
}

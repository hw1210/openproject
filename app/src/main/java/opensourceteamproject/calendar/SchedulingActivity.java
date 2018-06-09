package opensourceteamproject.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SchedulingActivity extends AppCompatActivity {
 //   String ipchange="172.16.29.64";
 String ipchange="192.168..0.2";
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
    EditText btn_title;

    int scheduleYear,scheduleMonth,scheduleDay,startHour,startMinute,endHour,endMinute;

    String title="";
    String dDay="0";
    String dateAndTime="";
    String allDay="";
    String group="";

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
        btn_dDay=(Switch)findViewById(R.id.select_dDay);    btn_dDay.setChecked(false);
        btn_title=(EditText)findViewById(R.id.select_title);
        btn_date=(DatePicker)findViewById(R.id.select_date);
        btn_allDay=(Switch)findViewById(R.id.select_allDay);    btn_allDay.setChecked(true);
        btn_startTime=(TimePicker) findViewById(R.id.select_startTime);
        btn_endTime=(TimePicker) findViewById(R.id.select_endTime);

        show_allDay=(LinearLayout)findViewById(R.id.list_allDay);   show_allDay.setVisibility(View.VISIBLE);
        show_startTime=(LinearLayout)findViewById(R.id.list_startTime); show_startTime.setVisibility(View.GONE);
        show_endTime=(LinearLayout)findViewById(R.id.list_endTime); show_endTime.setVisibility(View.GONE);
        show_calendar=(LinearLayout)findViewById(R.id.list_calendar);   show_calendar.setVisibility(View.GONE);
        show_group=(LinearLayout)findViewById(R.id.list_group); show_group.setVisibility(View.GONE);

        btn_dDay.setOnClickListener(btn_dDayClickListener);
        btn_allDay.setOnClickListener(btn_allDayClickListener);

        title=btn_title.getText().toString();

        scheduleYear=btn_date.getYear();
        scheduleMonth=btn_date.getMonth()+1;
        scheduleDay=btn_date.getDayOfMonth();
        startHour=btn_startTime.getHour();
        startMinute=btn_startTime.getMinute();
        endHour=btn_endTime.getHour();
        endMinute=btn_endTime.getMinute();

        dateAndTime=scheduleYear+"-"+scheduleMonth+"-"+scheduleDay+"-"+startHour+"-"+startMinute+"-"+endHour+"-"+endMinute;
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
            String result;
            CustomTask task=new CustomTask();
            try {
                result = task.execute(title,dDay,dateAndTime,allDay,group).get();
            }catch(Exception e){

            }
            Toast.makeText(getApplicationContext(),"새로운 일정이 생성되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener CancelSClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),SchedulingActivity.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener btn_dDayClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"개인 일정 생성",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),Scheduling_MySelf.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener btn_allDayClickListener=new View.OnClickListener(){
        public void onClick(View v){
            if(btn_allDay.isChecked()){ // 하루종일 on할 경우
                show_startTime.setVisibility(View.GONE);
                show_endTime.setVisibility(View.GONE);
                dDay = "1";
            }
            else{ // 하루종일 on할 경우
                show_startTime.setVisibility(View.VISIBLE);
                show_endTime.setVisibility(View.VISIBLE);
                dDay = "0";
                startHour=0;startMinute=0;endHour=0;endMinute=0;
            }
        }
    };
//////////////////////////////////////////////////////////////////////////////////////////
    class CustomTask extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/selectuserinfo.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sMsg="dDay="+strings[0]+"&"+"title="+strings[1]+"&"+"allDay="+strings[2]+"&"+"dateAndTime="+strings[3]+"&"+"group="+strings[4];
            /*
            PrintWriter pwr=new PrintWriter(osw);
            sMsg.append("upnum").append(" = ").append(strings[0]);

            pwr.write(sMsg.toString());
            */
                osw.write(sMsg);
                osw.flush();
                //jsp 통신 ok
                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStreamReader tmp=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    String str;
                    BufferedReader reader=new BufferedReader(tmp);
                    StringBuffer buffer=new StringBuffer();
                    //jsp에서 보낸 값 받기
                    while((str=reader.readLine())!=null){
                        buffer.append(str);
                    }
                    rMsg=buffer.toString();
                }
                else{
                    Log.i("통신결과",conn.getResponseCode()+"에러");

                }
            }
            catch(MalformedURLException e){e.printStackTrace();}
            catch(IOException e){e.printStackTrace();}
            return rMsg;
        }
    }
}

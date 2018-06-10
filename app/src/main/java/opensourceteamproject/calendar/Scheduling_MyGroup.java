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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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
import java.util.ArrayList;

public class Scheduling_MyGroup extends AppCompatActivity {
 //   String ipchange="172.16.29.64";
 String ipchange="192.168.0.2";
    FloatingActionButton btn_RegisterS,btn_CancelS;

    String phoneNum="";
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
    String[] giddata;
    int scheduleYear,scheduleMonth,scheduleDay,startHour,startMinute,endHour,endMinute;
    ArrayList<String> GroupData;
    ArrayList<String> GroupDatatemp=new ArrayList<String>();
    ArrayAdapter<String> adapter_group;
    String title="";
    String dDay="1";
    String dateAndTime="";
    String allDay="1";
    String group="";
    String groupstr="";
    int ponum;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduling);

        Intent rintent=new Intent(this.getIntent());
      phoneNum=rintent.getStringExtra("phoneNum");
        Toolbar toolbar=(Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);
        ///////////////////////////////////////
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
        btn_title=(EditText)findViewById(R.id.select_title);
        btn_date=(DatePicker)findViewById(R.id.select_date);
        btn_allDay=(Switch)findViewById(R.id.select_allDay);    btn_allDay.setChecked(true);
        btn_startTime=(TimePicker) findViewById(R.id.select_startTime);
        btn_endTime=(TimePicker) findViewById(R.id.select_endTime);
        btn_calendar=(ToggleButton)findViewById(R.id.select_calendar);  btn_calendar.setChecked(false);
        btn_group=(Spinner)findViewById(R.id.select_group);

        show_allDay=(LinearLayout)findViewById(R.id.list_allDay);   show_allDay.setVisibility(View.GONE);
        show_startTime=(LinearLayout)findViewById(R.id.list_startTime); show_startTime.setVisibility(View.GONE);
        show_endTime=(LinearLayout)findViewById(R.id.list_endTime); show_endTime.setVisibility(View.GONE);
        show_calendar=(LinearLayout)findViewById(R.id.list_calendar);   show_calendar.setVisibility(View.VISIBLE);
        show_group=(LinearLayout)findViewById(R.id.list_group); show_group.setVisibility(View.VISIBLE);

        btn_dDay.setOnClickListener(btn_dDayClickListener);
        btn_calendar.setOnClickListener(btn_CalendarClickListener);

        title=btn_title.getText().toString();

        scheduleYear=btn_date.getYear();
        scheduleMonth=btn_date.getMonth()+1;
        scheduleDay=btn_date.getDayOfMonth();
        startHour=0;startMinute=0;endHour=0;endMinute=0;

        dateAndTime=scheduleYear+"-"+scheduleMonth+"-"+scheduleDay+"-"+startHour+"-"+startMinute+"-"+endHour+"-"+endMinute;



        GroupData=new ArrayList<>();
        /*ArrayAdapter<String> adapter_group=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,GroupData);
        btn_group.setPrompt("");
        btn_group.setAdapter(adapter_group);
        btn_group.setOnItemSelectedListener(groupSelectedListener);
*/
        ///////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
        String result2;

        Scheduling_MyGroup.CustomTask2 task2=new CustomTask2();
        try {

            result2 = task2.execute(phoneNum).get();
            Toast.makeText(getApplicationContext(), "**" + result2, Toast.LENGTH_LONG).show();
            giddata = result2.split("/");

            String result3;
            String sam = "";
            for (int i = 0; i < giddata.length; i++) {

                Scheduling_MyGroup.CustomTask3 task3 = new CustomTask3();
                try {
                    result3 = task3.execute(giddata[i].toString()).get();
                    GroupData.add(result3.toString());

                } catch (Exception e) {

                }
                }
            }catch(Exception e){

            }

        ///////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
        ArrayAdapter<String> adapter_group=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,GroupData);
        btn_group.setPrompt("");
        btn_group.setAdapter(adapter_group);
        btn_group.setOnItemSelectedListener(groupSelectedListener);

    }

    AdapterView.OnItemSelectedListener groupSelectedListener=new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           Toast.makeText(getApplicationContext(),"**"+position,Toast.LENGTH_LONG).show();
           ponum=position;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(getApplicationContext(),"그룹을 선택해주세요.",Toast.LENGTH_SHORT).show();
        }
    };

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

    View.OnClickListener btn_myGroupClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MyGroupActivity.class);
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

    View.OnClickListener RegisterSClickListener=new View.OnClickListener(){
        public void onClick(View v){

/////////////////////////////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁ
            String result;
            Scheduling_MyGroup.CustomTask task=new CustomTask();
            String temp="";

            if(!giddata[ponum].toString().isEmpty())
                temp=giddata[ponum].toString();

            title=btn_title.getText().toString();
      //      Toast.makeText(getApplicationContext(),title+"d"+dDay+"d"+dateAndTime+"d"+allDay+"d"+temp,Toast.LENGTH_LONG).show();
            try {
                result = task.execute(title,dDay,dateAndTime,allDay,temp).get();
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }catch(Exception e){

            }

            Toast.makeText(getApplicationContext(),"새로운 일정이 생성되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),MyGroupActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener CancelSClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"취소되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),Scheduling_MyGroup.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener btn_dDayClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"일반 일정 생성",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),SchedulingActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener btn_CalendarClickListener=new View.OnClickListener(){ // 디데이 on일 때
        public void onClick(View v){
            Toast.makeText(getApplicationContext(),"개인 일정 생성",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),Scheduling_MySelf.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////
    class CustomTask extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/insertmygrouptodo.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sMsg="title="+strings[0]+"&"+"dday="+strings[1]+"&"+"dateandtime="+strings[2]+"&"+"allday="+strings[3]+"&"+"group="+strings[4];
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

    class CustomTask2 extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/selectgroupinfo.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sMsg="upnum="+strings[0];
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


    class CustomTask3 extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/selectmygrouplist.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sMsg="gid="+strings[0];
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

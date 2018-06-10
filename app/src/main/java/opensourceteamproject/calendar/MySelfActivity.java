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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MySelfActivity extends AppCompatActivity{
    Button btn_myGroup;
    FloatingActionButton btn_MakeS;
    Button btn_myHome;
    //   String ipchange="172.16.29.64";
    String ipchange="192.168.0.2";
    String[] ttname;
    String[] ttdate;
    String[] ttline;
    String[] ttdday;
    String result;
    String phoneNum="";
    //Data data_container;
    ArrayList<Data> instanceList = new ArrayList<>();
    DataAdapter_MySelf dataAdapter;
    ListView listView;

    String result2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myself);
        setTitle("Calendar");

        Intent rintent=new Intent(this.getIntent());
        phoneNum=rintent.getStringExtra("phoneNum");

        //
        MySelfActivity.CustomTask task=new MySelfActivity.CustomTask();
        try {
            result = task.execute(phoneNum).get();

        }catch(Exception e){

        }


        //
        MySelfActivity.CustomTask2 task2=new MySelfActivity.CustomTask2();
        try {
            result2 = task2.execute(result).get();
            Toast.makeText(getApplicationContext(),result2,Toast.LENGTH_LONG).show();

            ttline=result2.split("&");
            String a="";
            String b="";
            for(int i=0;i<ttline.length;i++){
                if(i%2==0) a=ttline[i]+"/";
                else b=ttline[i]+"/";
            }
            ttname=a.split("/");
            ttdate=b.split("/");

            for(int i=0;i<ttname.length;i++){
                Toast.makeText(getApplicationContext(),ttname[i]+"a"+ttdate[i],Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){

        }


/*
            giddata = result2.split("/");

            String result3;
            String sam = "";
            for (int i = 0; i < giddata.length; i++) {

                Scheduling_MyGroup.CustomTask3 task3 = new CustomTask3();
                try {
                    result3 = task3.execute(giddata[i].toString()).get();
                    GroupData.add(result3.toString());

                } catch (Exception e) {


*/
        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);

        btn_myGroup=(Button)findViewById(R.id.myGroup);
        btn_myGroup.setOnClickListener(btn_myGroupClickListener);
        btn_MakeS=(FloatingActionButton)findViewById(R.id.MakeS);
        btn_MakeS.setOnClickListener(MakeSClickListener);
        btn_myHome=(Button)findViewById(R.id.myHome);
        btn_myHome.setOnClickListener(btn_myHomeClickListener);

        btn_MakeS.setRippleColor(Color.rgb(93,181,164));

        initListView();
    }

    public void initListView() {
        Date d=new Date();
        String tempdate="";
        try{
            for(int i=0;i<ttdate.length;i++){
                tempdate=ttdate[i].substring(0,10)+"/";

            }

        }catch(NullPointerException e) {

        }
        try{
            for(int i=0;i<ttdate.length;i++) {
                ttdday = tempdate.split("/");
            }
        }catch(NullPointerException e){

         }
        long cal;
        try{
            for(int i=0;i<ttdate.length;i++) {
                String oTime = "";
                Date currentDate;
                cal=0;
                SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                Date currentTime = new Date();
                oTime = mSimpleDateFormat.format(currentTime);//현재
                try{
                    Date memDelStartDate = mSimpleDateFormat.parse(ttdday[i]);

                    currentDate=mSimpleDateFormat.parse(oTime);
                    int compare=currentDate.compareTo(memDelStartDate);
                    if(compare>0){cal=currentDate.getTime()-memDelStartDate.getTime(); cal=cal/(24*60*60*1000);}
                    if(compare<0){cal=memDelStartDate.getTime()-currentDate.getTime();cal=cal/(24*60*60*1000);}
                    else{cal=0;}
                    instanceList.add(new Data(String.valueOf(cal),ttname[i],ttdday[i]));
                }
                catch (Exception e){

                }
            }
        }catch(NullPointerException e){

        }
        dataAdapter = new DataAdapter_MySelf(this, instanceList);
        listView = (ListView) findViewById(R.id.list_mySelf);
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

    View.OnClickListener btn_myGroupClickListener=new android.view.View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MyGroupActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener MakeSClickListener=new View.OnClickListener(){
        public void onClick(View v){
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
                URL url=new URL("http://"+ipchange+":8084/dbconn/searchmy.jsp"); //보낼 jsp 경로
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
    //////////////////////////////////////////////////////////////////////////////////////////
    class CustomTask2 extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/searchtodo.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sMsg="uid="+strings[0];
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

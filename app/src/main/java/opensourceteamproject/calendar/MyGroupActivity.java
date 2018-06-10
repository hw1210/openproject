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
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyGroupActivity extends AppCompatActivity {
    Button btn_mySelf;
    FloatingActionButton btn_MakeS;
    Button btn_myHome;
    ToggleButton btn_calendar;
    //   String ipchange="172.16.29.64";
    String ipchange="192.168.0.2";

    String phoneNum="";
    Data data_container;
    ArrayList<Data> instanceList=new ArrayList<Data>();;
    Data_Group GroupData;
    ArrayList<Data_Group> instance;
    DataAdapter_MyGroup dataAdapter;
    ExpandableListView listView;
    String[] giddata;
    ArrayList<String> GroupDatatemp;
    //String[] ttname;
    //String[] ttdate;
    //String[] ttline;
    //String[] ttdday;
    ArrayList<String[]> tdatelist;
    ArrayList<String[]> tnamelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroup);
        Intent rintent=new Intent(this.getIntent());
        phoneNum=rintent.getStringExtra("phoneNum");
        Toast.makeText(getApplicationContext(),phoneNum,Toast.LENGTH_LONG).show();
        tdatelist=new ArrayList<String[]>();
        tnamelist=new ArrayList<String[]>();
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

        Intent rintent=new Intent(this.getIntent());
        phoneNum=rintent.getStringExtra("phoneNum");
        GroupDatatemp=new ArrayList<>();

        /*ArrayAdapter<String> adapter_group=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,GroupData);
        btn_group.setPrompt("");
        btn_group.setAdapter(adapter_group);
        btn_group.setOnItemSelectedListener(groupSelectedListener);
*/
        ///////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
        String result2;

        MyGroupActivity.CustomTask2 task2=new MyGroupActivity.CustomTask2();
        try {

            result2 = task2.execute(phoneNum).get();
            Toast.makeText(getApplicationContext(), "**" + result2, Toast.LENGTH_LONG).show();
            giddata = result2.split("/");

            String result3;
            String sam = "";
            for (int i = 0; i < giddata.length; i++) {

                MyGroupActivity.CustomTask3 task3 = new MyGroupActivity.CustomTask3();
                try {
                    result3 = task3.execute(giddata[i].toString()).get();
                    GroupDatatemp.add(result3.toString());
            Toast.makeText(getApplicationContext(),result3.toString(),Toast.LENGTH_LONG).show();


                } catch (Exception e) {

                }
            }
        }catch(Exception e){

        }
        String result;
        try {
            for (int j = 0; j < giddata.length; j++) {
                String[] ttname;
                String[] ttdate;
                String[] ttline;
                String[] ttdday;
                MyGroupActivity.CustomTask task = new MyGroupActivity.CustomTask();
                try {
                    result = task.execute(giddata[j].toString()).get();
                    //Toast.makeText(getApplicationContext(), "그룹명:"+GroupDatatemp.get(j).toString(), Toast.LENGTH_LONG).show();
                    instanceList = new ArrayList<>();
                    if (!result.isEmpty()) {
                        ttline = result.split("&");
                        String a = "";
                        String b = "";
                        for (int i = 0; i < ttline.length; i++) {
                            //Toast.makeText(getApplicationContext(),"line:"+ttline[i].toString(), Toast.LENGTH_LONG).show();
                            instanceList.add(new Data("", ttline[i], ""));
                            a = ttline[i].toString() + "/";
                            //   else b = ttline[i].toString() + "/";
                        }
                        instance.add(new Data_Group(GroupDatatemp.get(j), instanceList));
                        ttname = a.split("/");
                        tnamelist.add(ttname);

                        // ttdate = b.split("/");
                        //tdatelist.add(ttdate);
    /*
                        for(int i=0;i<tnamelist.get(i).length;i++){
                            for(int jj=0;jj<tnamelist.get(i).length;jj++){
                                if(jj%2==0)Toast.makeText(getApplicationContext(),"name:"+(tnamelist.get(i))[jj].toString(), Toast.LENGTH_LONG).show();
                                    if(jj%2!=0)Toast.makeText(getApplicationContext(),"date:"+(tnamelist.get(i))[jj].toString(), Toast.LENGTH_LONG).show();
                                // Toast.makeText(getApplicationContext(),"date:"+(tdatelist.get(i))[jj].toString(), Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getApplicationContext(),"next",Toast.LENGTH_LONG).show();
                        }*/
                    }
                } catch (Exception e) {

            }
////////////////name:에 번갈아토스트//////////////////////////////////////////////////////////홀수일때는 name 짝수일때는 date출력
            }
        }catch (NullPointerException e){

        }

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


    //////////////////////////////////////////////////////////////////////////////////////////
    class CustomTask extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/searchmygrouptodo.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                sMsg="gid="+strings[0];
            /*PrintWriter pwr=new PrintWriter(osw);
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


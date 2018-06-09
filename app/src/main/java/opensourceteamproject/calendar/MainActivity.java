package opensourceteamproject.calendar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build.VERSION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String ipchange="172.16.29.64";
    GridView monthView;
    Button btn_myGroup;
    Button btn_mySelf;
    FloatingActionButton btn_MakeS;
    MonthAdapter monthViewAdapter;
    TextView monthText;
    int curYear;
    int curMonth;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Calendar");

        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);


        monthView = (GridView) findViewById(R.id.monthView);
        monthViewAdapter = new MonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);
        btn_MakeS=(FloatingActionButton)findViewById(R.id.MakeS);
        btn_MakeS.setOnClickListener(MakeSClickListener);
        btn_myGroup=(Button)findViewById(R.id.myGroup);
        btn_myGroup.setOnClickListener(btn_myGroupClickListener);
        btn_mySelf=(Button)findViewById(R.id.mySelf);
        btn_mySelf.setOnClickListener(btn_mySelfClickListener);


        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();

                Log.d("MainActivity", "Selected : " + day);
            }
        });


        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        ImageButton monthPrevious = (ImageButton) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        ImageButton monthNext = (ImageButton) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });
        /*전화번호 접근 권한 허가 여부 및 작업*/

        String phoneNum=null;
        //if : version check
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int permissionResult=checkSelfPermission(Manifest.permission.READ_PHONE_STATE);

            //if : permission Denied
            if(permissionResult== PackageManager.PERMISSION_DENIED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)){

                    AlertDialog.Builder dialog= new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("권한이 필요합니다.")
                            .setMessage("단말기의 전화번호 접근권한이 필요합니다. 계속하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.READ_PHONE_STATE},PERMISSIONS_REQUEST_READ_PHONE_STATE);
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    moveTaskToBack(true);
                                    finish();
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            })
                            .create()
                            .show();

                }
                else{
                    ActivityCompat.requestPermissions(MainActivity.this,new String []{Manifest.permission.READ_PHONE_STATE},PERMISSIONS_REQUEST_READ_PHONE_STATE);
                }
            }//end if : permission Denied if
            else {
                TelephonyManager telmanager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                phoneNum= telmanager.getLine1Number();
                phoneNum = phoneNum.replace("+82", "0");
                ///////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ3
                String result;
                CustomTask task=new CustomTask();
                try {
                    result = task.execute(phoneNum).get();
                }catch(Exception e){

                }
                /////////////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
            }
        }//end if : version check
        else{
            TelephonyManager telmanager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            phoneNum= telmanager.getLine1Number();
            phoneNum = phoneNum.replace("+82", "0");
            ///////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ1
            String result;
            CustomTask task=new CustomTask();
            try {
                result = task.execute(phoneNum).get();
            }catch(Exception e){

            }
            /////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
        }
    }

    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String phoneNum=null;
        switch (requestCode){
            case PERMISSIONS_REQUEST_READ_PHONE_STATE:
                for(int i=0;i<permissions.length;i++){
                    String permission=permissions[i];
                    int grantResult=grantResults[i];
                    if(grantResults.length>0&&grantResult==PackageManager.PERMISSION_GRANTED&&permission.equals(Manifest.permission.READ_PHONE_STATE)){
                        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED) {
                            TelephonyManager telmanager=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                            phoneNum= telmanager.getLine1Number();
                            phoneNum = phoneNum.replace("+82", "0");
                            //////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ2
                            String result;
                            CustomTask task=new CustomTask();
                            try {
                                result = task.execute(phoneNum).get();
                            }catch(Exception e){

                            }
                            ////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
                        }
                    }
                    else{
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }break;
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater=getMenuInflater();
        mInflater.inflate(R.menu.menu,menu);
        return true;

    }

    View.OnClickListener btn_myGroupClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MyGroupActivity.class);
            startActivity(intent);
            finish();
        }
    };
    View.OnClickListener MakeSClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),SchedulingActivity.class);
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


    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

                case R.id.menu_bt1:

                    Intent intent1 =new Intent(MainActivity.this,Group_List.class);
                    startActivity(intent1);
                    finish();
                    return true;

                case R.id.menu_bt3:

                    Intent intent3 =new Intent(MainActivity.this,theme.class);
                    startActivity(intent3);
                    finish();
                    return true;

                case R.id.menu_bt4:

                    Intent intent4 =new Intent(MainActivity.this,setting.class);
                    startActivity(intent4);
                    finish();
                    return true;

        }

        return false;

    }
    /////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ4
    class CustomTask extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/insertuserinfo.jsp"); //보낼 jsp 경로
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
/////////////////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ

}
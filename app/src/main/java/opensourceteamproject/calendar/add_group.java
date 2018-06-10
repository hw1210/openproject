package opensourceteamproject.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class add_group extends AppCompatActivity {
//    String ipchange="172.16.29.64";
    String ipchange="192.168.0.2";
    Button btn_mySelf;
    Button btn_myGroup;
    Button btn_myHome;

    EditText memberNumber;
    EditText grname;
    String number;
    String gname;
    ArrayList<String> membersnum=new ArrayList<String>();//memberNameCopy
    ArrayAdapter<String> adapter;
    ListView GroupList;

    ArrayList<String> members=new ArrayList<>();
    String phoneNum="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        GroupList=(ListView)findViewById(R.id.input_member_list);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,membersnum);
        Intent rintent=new Intent(this.getIntent());
        phoneNum=rintent.getStringExtra("phoneNum");

        GroupList.setAdapter(adapter);

        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);
        grname=(EditText)findViewById(R.id.input_group_name);
        btn_myGroup=(Button)findViewById(R.id.myGroup);
        btn_myGroup.setOnClickListener(btn_myGroupClickListener);
        btn_myHome=(Button)findViewById(R.id.myHome);
        btn_myHome.setOnClickListener(btn_myHomeClickListener);
        btn_mySelf=(Button)findViewById(R.id.mySelf);
        btn_mySelf.setOnClickListener(btn_mySelfClickListener);
       members.add(phoneNum);
        membersnum.add("나");
/*        ArrayList<String> membersnumtemp=new ArrayList<String>();
        membersnumtemp.addAll(membersnum);
        membersnum.clear();
        membersnum.addAll(membersnumtemp);
        adapter.notifyDataSetChanged();
        memberNumber.setText(null);
*/
        Button button=(Button) findViewById(R.id.group_member_button); //번호 조회하여 추가

        memberNumber=(EditText)findViewById(R.id.input_member_number);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String data=R.id.group_input_member.getText().toString();
              //폰번호로 사용자 조회
              String result="";
              add_group.CustomTask task=new add_group.CustomTask();
              try {
                  number=memberNumber.getText().toString();
                  int mlen=membersnum.size();
                  int mflag=10;
                  for(int i=0;i<mlen;i++){
                      if(number.equals(members.get(i))) {
                          mflag=5;

                      }
                  }
                  if(mflag==10) {
                      result = task.execute(number).get();
                      if(!result.isEmpty()) {
                          ArrayList<String> membersnumtemp=new ArrayList<String>();
                          membersnumtemp.addAll(membersnum);
                          membersnumtemp.add(result);
                          membersnum.clear();
                          membersnum.addAll(membersnumtemp);
                          adapter.notifyDataSetChanged();
                          memberNumber.setText(null);
                          showMessage(result);
                      }
                  }
              }catch(Exception e){

              }
                memberNumber.setText(null);
            }
        });


        FloatingActionButton button1=(FloatingActionButton) findViewById(R.id.RegisterS);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ3
                String result2="";
                add_group.CustomTask2 task2=new add_group.CustomTask2();
                try {
                    gname=grname.getText().toString();
                    result2 = task2.execute(gname).get();

                }catch(Exception e){

                }
                /////////////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
///////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ3

                for(int i=0;i<members.size();i++) {
                    String result3;
                    add_group.CustomTask3 task3 = new add_group.CustomTask3();
                    try {
                        gname = grname.getText().toString();
                        String numtemp=members.get(i).toString();
                        Toast.makeText(getApplicationContext(),"+r3"+numtemp,Toast.LENGTH_LONG).show();
                        result3 = task3.execute(result2,numtemp).get();
                        Toast.makeText(getApplicationContext(),"+r3"+result3,Toast.LENGTH_LONG).show();
                    } catch (Exception e) {

                    }
                }
                /////////////////////////////////////////////////////////////////////////////ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ




                Toast.makeText(getApplicationContext(),"새로운 그룹이 생성되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),Group_List.class);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton button2=(FloatingActionButton) findViewById(R.id.CancelS);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"취소하였습니다.",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),Group_List.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showMessage(String result){
      if(!result.isEmpty()){
          AlertDialog.Builder builder=new AlertDialog.Builder(this);
          builder.setTitle("멤버 추가");
          builder.setMessage(result+" 을(를) 추가하시겠습니까?");
          builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

              members.add(number);
              memberNumber.setText(null);

              /*
              String result;
              add_group.CustomTask task=new add_group.CustomTask();
              try {
                  for(String s: members)
                  result = task.execute(s).get();
              }catch(Exception e){

              }
              */                                                                  //////////////////////////////추가하겠습니까?예
          }
      });
      builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
              memberNumber.setText(null);
              ArrayList<String> membersnumtemp=new ArrayList<String>();
              membersnumtemp.addAll(membersnum);
              membersnumtemp.remove((membersnum.size()-1));
              membersnum.clear();
              membersnum.addAll(membersnumtemp);
              adapter.notifyDataSetChanged();
          }
      });
      AlertDialog alertDialog=builder.create();
      alertDialog.show();
      }
      else memberNumber.setText(null);
    }

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
//옵션메뉴에서 메인메뉴 탭으로 이동
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
    class CustomTask extends AsyncTask<String,Void,String> {
        String sMsg,rMsg;

        @Override
        protected String doInBackground(String... strings) {//빨간줄 떠서 막음
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/selectuserinfo.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
           sMsg="upnum="+strings[0];
                //     sMsg="members="+strings[0];
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
                    int flag=10;
                    BufferedReader reader=new BufferedReader(tmp);
                    StringBuffer buffer=new StringBuffer();
                    //jsp에서 보낸 값 받기
                    while((str=reader.readLine())!=null){
                        buffer.append(str);
                        flag=5;
                    }
                    if(flag==5)rMsg=buffer.toString();
                    else rMsg="";

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
        String saMsg,raMsg;

        @Override
        protected String doInBackground(String... strings) {//빨간줄 떠서 막음
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/insertgroupinfo.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                saMsg="gname="+strings[0];
                //     sMsg="members="+strings[0];
            /*
            PrintWriter pwr=new PrintWriter(osw);
            sMsg.append("upnum").append(" = ").append(strings[0]);

            pwr.write(sMsg.toString());
            */
                osw.write(saMsg);
                osw.flush();
                //jsp 통신 ok
                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStreamReader tmp=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    String str;
                    int flag=10;
                    BufferedReader reader=new BufferedReader(tmp);
                    StringBuffer buffer=new StringBuffer();
                    //jsp에서 보낸 값 받기
                    while((str=reader.readLine())!=null){
                        buffer.append(str);
                        flag=5;
                    }
                    if(flag==5)raMsg=buffer.toString();
                    else raMsg="";

                }
                else{
                    Log.i("통신결과",conn.getResponseCode()+"에러");

                }
            }
            catch(MalformedURLException e){e.printStackTrace();}
            catch(IOException e){e.printStackTrace();}
            return raMsg;
        }
    }


    class CustomTask3 extends AsyncTask<String,Void,String> {
        String saMsg,raMsg;

        @Override
        protected String doInBackground(String... strings) {//빨간줄 떠서 막음
            try{
                // StringBuffer sMsg=new StringBuffer();
                URL url=new URL("http://"+ipchange+":8084/dbconn/insertusergroup.jsp"); //보낼 jsp 경로
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");

                OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                saMsg="gid="+strings[0]+"&"+"upnum="+strings[1];
                //     sMsg="members="+strings[0];
            /*
            PrintWriter pwr=new PrintWriter(osw);
            sMsg.append("upnum").append(" = ").append(strings[0]);

            pwr.write(sMsg.toString());
            */
                osw.write(saMsg);
                osw.flush();
                //jsp 통신 ok
                if(conn.getResponseCode()==conn.HTTP_OK){
                    InputStreamReader tmp=new InputStreamReader(conn.getInputStream(),"UTF-8");
                    String str;
                    int flag=10;
                    BufferedReader reader=new BufferedReader(tmp);
                    StringBuffer buffer=new StringBuffer();
                    //jsp에서 보낸 값 받기
                    while((str=reader.readLine())!=null){
                        buffer.append(str);
                        flag=5;
                    }
                    if(flag==5)raMsg=buffer.toString();
                    else raMsg="";

                }
                else{
                    Log.i("통신결과",conn.getResponseCode()+"에러");

                }
            }
            catch(MalformedURLException e){e.printStackTrace();}
            catch(IOException e){e.printStackTrace();}
            return raMsg;
        }
    }



}
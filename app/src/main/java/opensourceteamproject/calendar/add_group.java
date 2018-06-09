package opensourceteamproject.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class add_group extends AppCompatActivity {
    Button btn_mySelf;
    Button btn_myGroup;
    Button btn_myHome;

    EditText groupName=(EditText)findViewById(R.id.input_group_name);
    EditText memberNumber=(EditText)findViewById(R.id.input_member_number);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);


        Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);
        btn_myGroup=(Button)findViewById(R.id.myGroup);
        btn_myGroup.setOnClickListener(btn_myGroupClickListener);
        btn_myHome=(Button)findViewById(R.id.myHome);
        btn_myHome.setOnClickListener(btn_myHomeClickListener);
        btn_mySelf=(Button)findViewById(R.id.mySelf);
        btn_mySelf.setOnClickListener(btn_mySelfClickListener);

        Button button=(Button) findViewById(R.id.group_member_button); //번호 조회하여 추가

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String data=R.id.group_input_member.getText().toString();
                showMessage();
            }
        });

        int i=0;
        final String[] GroupData=null;
        ListView GroupList=(ListView)findViewById(R.id.input_member_list);
        ArrayAdapter<String> Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,GroupData);
        GroupList.setAdapter(Adapter);

        FloatingActionButton button1=(FloatingActionButton) findViewById(R.id.RegisterS);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void showMessage(){
      AlertDialog.Builder builder=new AlertDialog.Builder(this);
      builder.setTitle("멤버 추가");
      builder.setMessage("ㅇㅇㅇ을(를) 추가하시겠습니까?");

      builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
      });
      builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
      });
      AlertDialog dialog=builder.create();
      dialog.show();
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
//옵션메뉴에서 메인메뉴 탭으로 이동
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

}
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
import android.widget.TextView;

public class theme extends AppCompatActivity {
    Button btn_mySelf;
    Button btn_myGroup;
    Button btn_myHome;
    String phoneNum="";
    String a="original";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        Intent rintent=new Intent(this.getIntent());
        phoneNum=rintent.getStringExtra("phoneNum");

        final Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.rgb(93,181,164));
        toolbar.setTitleTextColor(Color.WHITE);
        btn_myGroup=(Button)findViewById(R.id.myGroup);
        btn_myGroup.setOnClickListener(btn_myGroupClickListener);
        btn_myHome=(Button)findViewById(R.id.myHome);
        btn_myHome.setOnClickListener(btn_myHomeClickListener);
        btn_mySelf=(Button)findViewById(R.id.mySelf);
        btn_mySelf.setOnClickListener(btn_mySelfClickListener);

        setSupportActionBar(toolbar);
        FloatingActionButton button=(FloatingActionButton) findViewById(R.id.redButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                toolbar.setBackgroundColor((Color.rgb(233,191,120)));
                a="Red";
            }
        });

        button=(FloatingActionButton) findViewById(R.id.blueButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setBackgroundColor(getResources().getColor(R.color.Blue));
                a="Blue";
            }
        });

        button=(FloatingActionButton) findViewById(R.id.blackButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setBackgroundColor(getResources().getColor(R.color.Black));
                a="Black";
            }
        });

        button=(FloatingActionButton) findViewById(R.id.greenButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toolbar.setBackgroundColor(getResources().getColor(R.color.Green));
                a="Green";
            }
        });

        Button reset=(Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                toolbar.setBackgroundColor(Color.rgb(93,181,164));
                a="Original";
                            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater=getMenuInflater();
        mInflater.inflate(R.menu.menu,menu);
        return true;

    }


    View.OnClickListener MakeSClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),SchedulingActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            intent.putExtra("a",a);
            startActivity(intent);
        }
    };


    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.menu_bt1:

                Intent intent1 =new Intent(getApplicationContext(),Group_List.class);
                intent1.putExtra("phoneNum",phoneNum);
                intent1.putExtra("a",a);
                startActivity(intent1);
                finish();
                return true;

            case R.id.menu_bt3:

                Intent intent3 =new Intent(getApplicationContext(),theme.class);
                intent3.putExtra("phoneNum",phoneNum);
                intent3.putExtra("a",a);
                startActivity(intent3);
                finish();
                return true;

            case R.id.menu_bt4:

                Intent intent4 =new Intent(getApplicationContext(),setting.class);
                intent4.putExtra("phoneNum",phoneNum);
                intent4.putExtra("a",a);
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
            intent.putExtra("a",a);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener btn_myGroupClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MyGroupActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            intent.putExtra("a",a);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener btn_mySelfClickListener=new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getApplicationContext(),MySelfActivity.class);
            intent.putExtra("phoneNum",phoneNum);
            intent.putExtra("a",a);
            startActivity(intent);
            finish();
        }
    };

}

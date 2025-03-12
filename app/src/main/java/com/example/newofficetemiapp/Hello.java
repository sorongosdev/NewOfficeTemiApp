package com.example.newofficetemiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newofficetemiapp.TemiRoboTemi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
// 테미 파이어베이스 호출하면 뜨는 JAVA 엑티비티 부분
public class Hello extends AppCompatActivity implements
        OnGoToLocationStatusChangedListener
{


    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    TextView actionText;
    Robot robot;
    final RoboTemi roboTemi = new RoboTemi();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // my layout
        setContentView(R.layout.hello);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        robot = Robot.getInstance();
        //소라,성훈:ExecutiveTeam, 영로,유신:EditorialTeam, 지윤,종찬:PlanningTeam
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboTemi.goTo("ExecutiveTeam");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboTemi.goTo("PlanningTeam");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboTemi.goTo("PlanningTeam");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboTemi.goTo("ExecutiveTeam");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboTemi.goTo("EditorialTeam");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roboTemi.goTo("EditorialTeam");
            }
        });
        button7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                roboTemi.goTo("MeetingRoom");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Listener 객체추가, this 추가
        robot.addOnGoToLocationStatusChangedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnGoToLocationStatusChangedListener(this);
    }


    @Override
    public void onGoToLocationStatusChanged(@NonNull String s, @NonNull String s1, int i, @NonNull String s2) {

            if(!s.equals("홈베이스")&&s1.equals(OnGoToLocationStatusChangedListener.COMPLETE)){
                roboTemi.speak(s+"에 도착했습니다");
                roboTemi.goTo("홈베이스");
            }
    }
}
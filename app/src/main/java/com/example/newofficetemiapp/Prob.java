package com.example.newofficetemiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newofficetemiapp.Temi.RoboTemi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import java.util.List;

public class Prob extends AppCompatActivity implements
        OnGoToLocationStatusChangedListener
{
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Button prob_start;
    Button prob_restart;
    Button meeting;
    Button menu;
    ImageView blink;
    TextView prob_stop_txt;
    Robot robot;
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    public String id;
    public String loc = "starting";
    public List<String> map_list;
    public int index;
//    public Map<String, Float> temi_list = new HashMap<>();
    boolean b =false;


    final RoboTemi roboTemi = new RoboTemi();
    // for sharing variables with other classes
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // my layout
        setContentView(R.layout.prob);

        blink = findViewById(R.id.blink); // 이미지 뷰
        Glide.with(this).load(R.drawable.blink).into(blink);

        context=this;
        //prob_start = findViewById(R.id.prob_start);
        //list of workable Temi

        robot = Robot.getInstance();
        id=robot.getSerialNumber();
        map_list = robot.getLocations();


        if(id.equals(TEMI1)) {
            roboTemi.goTo("prob11");
        }
        else if(id.equals(TEMI2)){
            roboTemi.goTo("prob21");
        }


        firebaseDatabase.getReference("location").addValueEventListener(new ValueEventListener() {
            //@SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object location = snapshot.getValue();
                // o = Objects.requireNonNull(myText);
                if(location!=null){
                    if(id.equals(TEMI2)){
                        if ("PlanningTeam".equals(location.toString()) || "MeetingRoom".equals(location.toString())) {
                            Intent intent = new Intent(getApplicationContext(), Send.class);
                            startActivity(intent);
                        }
                    }
                    else if(id.equals(TEMI1)){
                        if("ExecutiveTeam".equals(location.toString()) || "EditorialTeam".equals(location.toString())){
                            Intent intent = new Intent(getApplicationContext(), Send.class);
                            startActivity(intent);
                        }
                    }
                    else if("stop".equals(location.toString())){
                        roboTemi.stopMovement();
                        finish();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Fail to read value.", error.toException());
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

    // Patrol area
    @Override
    public void onGoToLocationStatusChanged(@NonNull String s, @NonNull String s1, int i, @NonNull String s2) {
        if(s1.equals(OnGoToLocationStatusChangedListener.COMPLETE)) {
            switch (s) {
                //Temi1 prob
                case "prob11":
                    roboTemi.goTo("prob12");
                    break;
                case "prob12":
                    roboTemi.goTo("prob11");
                    break;
                //Temi2 prob
                case "prob21":
                    roboTemi.goTo("prob22");
                    break;
                case "prob22":
                    roboTemi.goTo("prob21");
                    break;
            }
        }
        else if(s1.equals(OnGoToLocationStatusChangedListener.ABORT))
        {
            setContentView(R.layout.prob_stop);
            meeting = findViewById(R.id.meeting);
            menu = findViewById(R.id.menu);
            prob_restart = findViewById(R.id.prob_restart);
            //prob_stop_txt = findViewById(R.id.prob_stop_txt);
            //prob_stop_txt.setText(id);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Prob.this, menu.class);
                    startActivity(intent);
                }
            });

            meeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Prob.this, Meeting.class);
                    startActivity(intent);
                }
            });

            prob_restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(R.layout.prob);
                    roboTemi.goTo(s);
                }
            });
        }
        else if(s1.equals(OnGoToLocationStatusChangedListener.GOING)){
            setContentView(R.layout.prob);
            if(id.equals(TEMI1))
                databaseReference.child("LED1").setValue("Green");
            else if(id.equals(TEMI2))
                databaseReference.child("LED2").setValue("Green");

        }
    }
}
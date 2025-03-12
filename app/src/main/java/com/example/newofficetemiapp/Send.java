package com.example.newofficetemiapp;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

// 호출 이동시 화면 넣어야됨, 도착했을 때 화면 전환
public class Send extends AppCompatActivity  implements
        OnGoToLocationStatusChangedListener {


    ImageView imageView; // 이미지 넣을 때 필요한 코드
    Button Send_Btn;
    Robot robot;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    public String id;
    public String loc_temi1="temp";
    public String loc_temi2="temp";
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    final RoboTemi roboTemi = new RoboTemi();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        robot = Robot.getInstance();
        id=robot.getSerialNumber();
        imageView = findViewById(R.id.imageView); // 이미지 뷰
        Glide.with(this).load(R.drawable.gifimage).into(imageView); // 이미지 넣을 때 필요한 코드

        firebaseDatabase.getReference("location").addValueEventListener(new ValueEventListener() {
            //@SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object location = snapshot.getValue();
                // o = Objects.requireNonNull(myText);
                if(!location.equals("null")){
                    if(id.equals(TEMI2)){
                        if ("PlanningTeam".equals(location.toString()) || "MeetingRoom".equals(location.toString())) {
                            loc_temi2 = location.toString();
                            databaseReference.child("location").setValue("null");
                            roboTemi.goTo(loc_temi2);
                        }
                    }
                    else if(id.equals(TEMI1)){
                        if("ExecutiveTeam".equals(location.toString()) || "EditorialTeam".equals(location.toString())){
                            loc_temi1 = location.toString();
                            databaseReference.child("location").setValue("null");
                            roboTemi.goTo(loc_temi1);
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

        firebaseDatabase.getReference("Sender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object Sender = snapshot.getValue();
                if(!Sender.toString().equals("null")){
                    if(id.equals(TEMI2)){
                        if(loc_temi2.equals("PlanningTeam") || loc_temi2.equals("MeetingRoom")){
                            databaseReference.child("Sender_temi2").setValue(Sender.toString());
                            databaseReference.child("Sender").setValue("null");
                        }
                    }
                    else if(id.equals(TEMI1)){
                        if(loc_temi1.equals("EditorialTeam") || loc_temi1.equals("ExecutiveTeam")){
                            databaseReference.child("Sender_temi1").setValue(Sender.toString());
                            databaseReference.child("Sender").setValue("null");
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
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

        if(s1.equals(OnGoToLocationStatusChangedListener.COMPLETE)) {
            setContentView(R.layout.send);
            Send_Btn = findViewById(R.id.send_Btn);
            Send_Btn.setBackgroundResource(R.drawable.button);
            Send_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Send.this, Map_Click.class);
                    startActivity(intent);
                }
            });
        }
    }

}
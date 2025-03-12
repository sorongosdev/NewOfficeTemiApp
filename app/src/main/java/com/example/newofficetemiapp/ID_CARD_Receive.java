package com.example.newofficetemiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;


public class ID_CARD_Receive extends AppCompatActivity {

    private int toast_here = 0;
    public String Receiver_com="temp";
    public String Receiver_com2="temp";
    public String id;
    public String location;
    Robot robot;
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    final RoboTemi roboTemi = new RoboTemi();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card_receive);
        robot = Robot.getInstance();
        id=robot.getSerialNumber();
        //Intent intent = getIntent();
        //String division = intent.getStringExtra("division");

        firebaseDatabase.getReference("location_temi1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(id.equals(TEMI1)){
                    Object Location = snapshot.getValue();
                    location=Location.toString();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });

        firebaseDatabase.getReference("location_temi2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(id.equals(TEMI2)){
                    Object Location = snapshot.getValue();
                    location=Location.toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });


        firebaseDatabase.getReference("ID_CARD_NUMBER1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(id.equals(TEMI1)){
                    Object myText = snapshot.getValue();
                    if(location.equals("PlanningTeam")){
                        Receiver_com = "jiyun";
                        Receiver_com2 = "joungchan";
                    }
                    else if(location.equals("ExecutiveTeam")){
                        Receiver_com = "sora";
                        Receiver_com2 = "sunghoon";
                    }
                    else if(location.equals("EditorialTeam")){
                        Receiver_com = "youngro";
                        Receiver_com2 = "yushin";
                    }
                    if (Receiver_com.equals(myText.toString())||Receiver_com2.equals(myText.toString())){

                        Log.d("tag", "ID_CARD is" + myText);
                        Intent intent = new Intent(ID_CARD_Receive.this, Pull.class);
                        startActivity(intent);//액티비티 이동해주는 구문
                    }
                    else if ("null".equals(myText.toString()))
                        databaseReference.child("LED1").setValue("Yellow");

                    else {
                        Toast.makeText(getApplicationContext(), "등록되지 않는 사원증입니다. 등록 후 태그해주세요", Toast.LENGTH_LONG).show();
                        databaseReference.child("LED1").setValue("Red");
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if ("null".equals(myText.toString())) {

                                if (toast_here == 0) {
                                    Toast.makeText(getApplicationContext(), "사원증을 찍어주세요", Toast.LENGTH_SHORT).show();
                                }
                                toast_here = 1;
                                databaseReference.child("LED1").setValue("Yellow");
                            }
                        }
                    }, 2000);
                }

            }


            @Override
            public void onCancelled (@NonNull DatabaseError error){

            }
        });
        firebaseDatabase.getReference("ID_CARD_NUMBER2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(id.equals(TEMI2)){
                    Object myText = snapshot.getValue();
                    if(location.equals("PlanningTeam")){
                        Receiver_com = "jiyun";
                        Receiver_com2 = "joungchan";
                    }
                    else if(location.equals("ExecutiveTeam")){
                        Receiver_com = "sora";
                        Receiver_com2 = "sunghoon";
                    }
                    else if(location.equals("EditorialTeam")){
                        Receiver_com = "youngro";
                        Receiver_com2 = "yushin";
                    }
                    if (Receiver_com.equals(myText.toString())||Receiver_com2.equals(myText.toString())){

                        Log.d("tag", "ID_CARD is" + myText);
                        Intent intent = new Intent(ID_CARD_Receive.this, Pull.class);
                        startActivity(intent);//액티비티 이동해주는 구문
                    }
                    else if ("null".equals(myText.toString()))
                        databaseReference.child("LED2").setValue("Yellow");

                    else {
                        Toast.makeText(getApplicationContext(), "등록되지 않는 사원증입니다. 등록 후 태그해주세요", Toast.LENGTH_LONG).show();
                        databaseReference.child("LED2").setValue("Red");
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if ("null".equals(myText.toString())) {

                                if (toast_here == 0) {
                                    Toast.makeText(getApplicationContext(), "사원증을 찍어주세요", Toast.LENGTH_SHORT).show();
                                }
                                toast_here = 1;
                                databaseReference.child("LED2").setValue("Yellow");
                            }
                        }
                    }, 2000);

                }

            }


            @Override
            public void onCancelled (@NonNull DatabaseError error){

            }
        });


    }//dodnodnodndon
}
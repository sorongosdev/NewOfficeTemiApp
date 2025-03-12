package com.example.newofficetemiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.newofficetemiapp.Temi.RoboTemi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robotemi.sdk.Robot;

public class Pull extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Button button2;
    public String id;
    Robot robot;
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    final RoboTemi roboTemi = new RoboTemi();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        robot = Robot.getInstance();
        id=robot.getSerialNumber();
        if(id.equals(TEMI1)){
            databaseReference.child("MOTOR_LOCK1").setValue("Unlock");
            databaseReference.child("LED1").setValue("Green");

        }
        else if(id.equals(TEMI2)){
            databaseReference.child("MOTOR_LOCK2").setValue("Unlock");
            databaseReference.child("LED2").setValue("Green");
        }

        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pull.this , Done_delivering.class);
                startActivity(intent);//액티비티 이동해주는 구문
            }

        });
    }
}
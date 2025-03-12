package com.example.newofficetemiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;

public class Map_Click extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Button div_1;//
    private Button div_3;
    private Button div_4;
    Robot robot;
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    private String division;//
    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_click);
        robot = Robot.getInstance();
        id=robot.getSerialNumber();
        div_1 = findViewById(R.id.div_1);
        div_3 = findViewById(R.id.div_3);
        div_4 = findViewById(R.id.div_4);

        div_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                division = "PlanningTeam";
                if(id.equals(TEMI1))
                    databaseReference.child("location_temi1").setValue(division);
                else if(id.equals(TEMI2)){
                    databaseReference.child("location_temi2").setValue(division);
                }
                Intent intent = new Intent(Map_Click.this , ID_CARD_send.class);
                //intent.putExtra("division",division);
                startActivity(intent);//액티비티 이동해주는 구문
            }
        });



        div_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                division = "EditorialTeam";
                if(id.equals(TEMI1))
                    databaseReference.child("location_temi1").setValue(division);
                else if(id.equals(TEMI2)){
                    databaseReference.child("location_temi2").setValue(division);
                }
                Intent intent = new Intent(Map_Click.this , ID_CARD_send.class);
                intent.putExtra("division",division);
                startActivity(intent);//액티비티 이동해주는 구문

            }

        });

        div_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Map_Click.this , ID_CARD_send.class);
                division = "ExecutiveTeam";
                if(id.equals(TEMI1))
                    databaseReference.child("location_temi1").setValue(division);
                else if(id.equals(TEMI2)){
                    databaseReference.child("location_temi2").setValue(division);
                }
                intent.putExtra("division",division);
                startActivity(intent);//액티비티 이동해주는 구문

            }

        });
    }


}
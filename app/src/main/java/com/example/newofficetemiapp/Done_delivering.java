package com.example.newofficetemiapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robotemi.sdk.Robot;

public class Done_delivering extends AppCompatActivity {

    private Button btn;
    public String id;
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    Robot robot;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_delivering);
        robot = Robot.getInstance();
        id=robot.getSerialNumber();
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Done_delivering.this, Prob.class);
                if(id.equals(TEMI1)){
                    databaseReference.child("ID_CARD_NUMBER1").setValue("null");
                    databaseReference.child("Sender_temi1").setValue("null");
                    databaseReference.child("location_temi1").setValue("null");
                }
                else if(id.equals(TEMI2)){
                    databaseReference.child("ID_CARD_NUMBER2").setValue("null");
                    databaseReference.child("Sender_temi2").setValue("null");
                    databaseReference.child("location_temi2").setValue("null");
                }
                Intent intent = new Intent(getApplicationContext(), Prob.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);//액티비티 이동해주는 구문
            }
        });
    }
}
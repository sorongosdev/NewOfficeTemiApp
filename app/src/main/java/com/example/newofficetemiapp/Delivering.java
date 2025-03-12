package com.example.newofficetemiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.of_temi.temi.RoboTemi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import java.util.Objects;

public class Delivering extends AppCompatActivity implements OnGoToLocationStatusChangedListener
{

    private TextView go_div;
    ImageView imageView3;
    ImageView Sender_image;
    ImageView Receiver_image;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Robot robot;
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    public String id;
    final RoboTemi roboTemi = new RoboTemi();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivering);

        imageView3 = findViewById(R.id.imageView3); // 이미지 뷰
        Sender_image=findViewById(R.id.Sender_image);
        Receiver_image=findViewById(R.id.Receiver_image);

        Glide.with(this).load(R.drawable.delivering_gifimage).into(imageView3);

        go_div = findViewById(R.id.go_div);
        Intent intent = getIntent();
        String Sender = intent.getStringExtra("Sender");
        String division=intent.getStringExtra("division");
        go_div.setText(division);
        robot = Robot.getInstance();
        id=robot.getSerialNumber();

        firebaseDatabase.getReference("location_temi1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(id.equals(TEMI1)){
                    Object location = snapshot.getValue();
                    roboTemi.goTo(location.toString());
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
                    Object location = snapshot.getValue();
                    roboTemi.goTo(location.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });


        if (Objects.equals(Sender,"jiyun")) {
            Sender_image.setImageResource(R.drawable.jiyun);
        }
        else if(Objects.equals(Sender,"jongchan")){
            Sender_image.setImageResource(R.drawable.jongchan);
        }
        else if(Objects.equals(Sender,"sora")){
            Sender_image.setImageResource(R.drawable.sora);
        }
        else if(Objects.equals(Sender,"sunghoon")){
            Sender_image.setImageResource(R.drawable.sunghoon);
        }
        else if(Objects.equals(Sender,"youngro")){
            Sender_image.setImageResource(R.drawable.youngro);
        }
        else if(Objects.equals(Sender,"yusin")){
            Sender_image.setImageResource(R.drawable.yushin);
        }//보내는 사람 이미지 바꾸기



        if(Objects.equals(division,"PlanningTeam")){
            Receiver_image.setImageResource(R.drawable.jiyun2);
        }
        else if(Objects.equals(division,"ExecutiveTeam")){
            Receiver_image.setImageResource(R.drawable.sora2);
        }
        else if(Objects.equals(division,"EditorialTeam")){
            Receiver_image.setImageResource(R.drawable.youngro2);
        }//받는 부서 대표 사람으로 이미지 바꾸기


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
            roboTemi.speak(s + "에 서류가 도착했습니다");
            if(id.equals(TEMI1))
                databaseReference.child("ID_CARD_NUMBER1").setValue("null");
            else if(id.equals(TEMI2))
                databaseReference.child("ID_CARD_NUMBER2").setValue("null");
            Intent intent = new Intent(getApplicationContext(), ID_CARD_Receive.class);
            startActivity(intent);
        }
    }
}
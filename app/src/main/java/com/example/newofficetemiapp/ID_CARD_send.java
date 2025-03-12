package com.example.newofficetemiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;

public class ID_CARD_send extends AppCompatActivity {

    private TextView go_div;
    private Object Sender_temi1;
    private Object Sender_temi2;
    private Object Sender_ID;
    ImageView imageView2;
    private int toast_here = 0;// 여기서만 toast가 뜨도록 하기 위함
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    static final String TEMI1 = "00120485035";
    static final String TEMI2 = "00120474994";
    public String id;
    public Robot robot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card_send);


        imageView2 = findViewById(R.id.imageView2); // 이미지 뷰
        Glide.with(this).load(R.drawable.id_card_gifimage).into(imageView2);

        go_div = findViewById(R.id.go_div);
        Intent intent = getIntent();
        String division=intent.getStringExtra("division");

        //go_div.setText(division);//
        //TextView id_card_text = findViewById(R.id.id_card_number);

        robot = Robot.getInstance();
        id=robot.getSerialNumber();

      /*  firebaseDatabase.getReference("Sender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object Sender = snapshot.getValue();
                Sender_com=Sender.toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });

       */


        firebaseDatabase.getReference("Sender_temi1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object Sender = snapshot.getValue();
                Sender_temi1=Sender.toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });

        firebaseDatabase.getReference("Sender_temi2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object Sender = snapshot.getValue();
                Sender_temi2=Sender.toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });


        firebaseDatabase.getReference("ID_CARD_NUMBER1").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(id.equals(TEMI1)){
                    Object myText = snapshot.getValue();
                    Sender_ID=myText.toString();

                    if (Sender_ID.equals(Sender_temi1) ) {//무조건 스트링 받을 땐 equals쓰기

                        //id_card_text.setText("ID_CARD_NUMBER" + myText.toString());

                        Log.d("tag", "ID_CARD is" + myText);
                        Intent intent = new Intent(ID_CARD_send.this, Delivering.class);
                        intent.putExtra("division", division);
                        intent.putExtra("Sender_temi1", (String) Sender_temi1);
                        startActivity(intent);//액티비티 이동해주는 구문
//                    databaseReference.child("ID_CARD_NUMBER").setValue("null");
                        databaseReference.child("LED1").setValue("Green");
                        databaseReference.child("MOTOR_LOCK1").setValue("Lock");
                    }
                    else if( "null".equals(myText.toString())){
                        //id_card_text.setText("null");
                        if(toast_here==0){
                            Toast.makeText(getApplicationContext(),"사원증을 찍어주세요",Toast.LENGTH_SHORT).show();}
                        toast_here=1;
                        intent.putExtra("toast_here",1);//
                        databaseReference.child("LED1").setValue("Yellow");
                    }
                    else{
                        //id_card_text.setText("fuck");
                        Toast.makeText(getApplicationContext(),"등록되지 않는 사원증입니다. 등록 후 태그해주세요",Toast.LENGTH_LONG).show();
                        databaseReference.child("LED1").setValue("Red");
                    }

                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });


        firebaseDatabase.getReference("ID_CARD_NUMBER2").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(id.equals(TEMI2)){
                    Object myText = snapshot.getValue();
                    Sender_ID=myText.toString();
                    if (Sender_ID.equals(Sender_temi2) ) {//무조건 스트링 받을 땐 equals쓰기

                        //id_card_text.setText("ID_CARD_NUMBER" + myText.toString());

                        Log.d("tag", "ID_CARD is" + myText);
                        Intent intent = new Intent(ID_CARD_send.this, Delivering.class);
                        intent.putExtra("division", division);
                        intent.putExtra("Sender_temi1", (String) Sender_temi2);
                        startActivity(intent);//액티비티 이동해주는 구문
//                    databaseReference.child("ID_CARD_NUMBER").setValue("null");
                        databaseReference.child("LED2").setValue("Green");
                        databaseReference.child("MOTOR_LOCK2").setValue("Lock");
                    }

                    else if( "null".equals(myText.toString())){
                        //id_card_text.setText("null");
                        if(toast_here==0){
                            Toast.makeText(getApplicationContext(),"사원증을 찍어주세요",Toast.LENGTH_SHORT).show();}
                        toast_here=1;
                        intent.putExtra("toast_here",1);//
                        databaseReference.child("LED2").setValue("Yellow");
                    }
                    else{
                        //id_card_text.setText("fuck");
                        Toast.makeText(getApplicationContext(),"등록되지 않는 사원증입니다. 등록 후 태그해주세요",Toast.LENGTH_LONG).show();
                        databaseReference.child("LED2").setValue("Red");
                    }


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });


    }


}
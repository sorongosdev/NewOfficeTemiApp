package com.example.newofficetemiapp;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Meeting extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Button back2;

    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.meeting);
        back2 = findViewById(R.id.prob_restart2);
        TextView textView8 = (TextView)findViewById(R.id.time8);
        TextView textView9 = (TextView)findViewById(R.id.time9);
        TextView textView10 = (TextView)findViewById(R.id.time10);
        TextView textView11 = (TextView)findViewById(R.id.time11);
        TextView textView12 = (TextView)findViewById(R.id.time12);
        TextView textView13 = (TextView)findViewById(R.id.time13);
        TextView textView14 = (TextView)findViewById(R.id.time14);
        TextView textView15 = (TextView)findViewById(R.id.time15);
        TextView textView16 = (TextView)findViewById(R.id.time16);
        TextView textView17 = (TextView)findViewById(R.id.time17);
        TextView textView18 = (TextView)findViewById(R.id.time18);

        firebaseDatabase.getReference("time8").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case "0":
                        textView8.setText("");
                        break;
                    case "PlanningTeam":
                        textView8.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView8.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView8.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time9").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView9.setText("");
                        break;
                    case "PlanningTeam":
                        textView9.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView9.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView9.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time10").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView10.setText("");
                        break;
                    case "PlanningTeam":
                        textView10.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView10.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView10.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time11").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView11.setText("");
                        break;
                    case "PlanningTeam":
                        textView11.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView11.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView11.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time12").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView12.setText("");
                        break;
                    case "PlanningTeam":
                        textView12.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView12.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView12.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time13").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView13.setText("");
                        break;
                    case "PlanningTeam":
                        textView13.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView13.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView13.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time14").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView14.setText("");
                        break;
                    case "PlanningTeam":
                        textView14.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView14.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView14.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time15").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView15.setText("");
                        break;
                    case "PlanningTeam":
                        textView15.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView15.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView15.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time16").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView16.setText("");
                        break;
                    case "PlanningTeam":
                        textView16.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView16.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView16.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time17").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView17.setText("");
                        break;
                    case "PlanningTeam":
                        textView17.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView17.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView17.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        firebaseDatabase.getReference("time18").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                String s = myText.toString();
                switch(s){
                    case"0":
                        textView18.setText("");
                        break;
                    case "PlanningTeam":
                        textView18.setText("Planning Team");
                        break;
                    case "ExecutiveTeam":
                        textView18.setText("Executive Team");
                        break;
                    case "EditorialTeam":
                        textView18.setText("Editorial Team");
                        break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
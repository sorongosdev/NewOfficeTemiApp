package com.example.newofficetemiapp;
import android.os.Bundle;
import android.util.Log;import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Firebase extends  AppCompatActivity{
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase);
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switchLed);
        TextView distanceText = (TextView)findViewById(R.id.textDistance);
        TextView actionText = (TextView)findViewById(R.id.action1);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    databaseReference.child("led").setValue(true);
                    switchCompat.setText("LED ON");
                } else {
                    databaseReference.child("led").setValue(false);
                    switchCompat.setText("LED OFF");
                }
            }
        });
        firebaseDatabase.getReference("distance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                if(myText != null) {
                    distanceText.setText("Distance: " + myText.toString());
                    Log.d("tag", "distance is " + myText);
                }
                else{
                    distanceText.setText("fuck");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
        firebaseDatabase.getReference("action").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object myText = snapshot.getValue();
                if(myText != null){
                    actionText.setText("action number:" + myText.toString());
                    Log.d("tag", "action is "+myText);
                }
                else{
                    actionText.setText("fuck");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "Fail to read value.", error.toException());
            }
        });
    }

}



package com.example.newofficetemiapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

public class MainActivity extends AppCompatActivity implements
        OnRobotReadyListener,
        View.OnClickListener {


    Button button5; // 버튼 총 3개 추가하고, switch문에다가도 추가해줘야한다.
    Button button2;
    Button button1;
    Robot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button5 = findViewById(R.id.button5);
        robot = Robot.getInstance();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button5.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        robot.addOnRobotReadyListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnRobotReadyListener(this);
    }


    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                robot.onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Class exampleContext = null;
        switch (view.getId()) {
            case R.id.button1:
                exampleContext = Firebase.class; //원하는 Java파일과 연결
                break;
            case R.id.button2:
                exampleContext = Send.class;
                break;
            case R.id.button5:
                exampleContext = Prob.class;
                break;
        }
        Intent intent = new Intent(getApplicationContext(),exampleContext);
        startActivity(intent);
    }
}

package com.example.newofficetemiapp.ui.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.ui.delivery.DeliveryActivity;
import com.example.newofficetemiapp.ui.firebase.FirebaseActivity;
import com.example.newofficetemiapp.ui.patrol.PatrolActivity;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

public class MainActivity extends BaseActivity<MainViewModel> implements
        OnRobotReadyListener,
        View.OnClickListener {

    private Button firebaseButton;
    private Button deliveryButton;
    private Button patrolButton;
    private Robot robot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void setupViews() {
        firebaseButton = findViewById(R.id.button1);
        deliveryButton = findViewById(R.id.button2);
        patrolButton = findViewById(R.id.button5);
        robot = Robot.getInstance();

        firebaseButton.setOnClickListener(this);
        deliveryButton.setOnClickListener(this);
        patrolButton.setOnClickListener(this);
    }

    @Override
    protected void observeViewModel() {
        // 필요시 ViewModel 관찰 추가
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
        int viewId = view.getId();

        if (viewId == R.id.button1) {
            navigateToFirebase();
        } else if (viewId == R.id.button2) {
            navigateToDelivery();
        } else if (viewId == R.id.button5) {
            navigateToPatrol();
        }
    }

    private void navigateToFirebase() {
        Intent intent = new Intent(this, FirebaseActivity.class);
        startActivity(intent);
    }

    private void navigateToDelivery() {
        Intent intent = new Intent(this, DeliveryActivity.class);
        startActivity(intent);
    }

    private void navigateToPatrol() {
        Intent intent = new Intent(this, PatrolActivity.class);
        startActivity(intent);
    }
}
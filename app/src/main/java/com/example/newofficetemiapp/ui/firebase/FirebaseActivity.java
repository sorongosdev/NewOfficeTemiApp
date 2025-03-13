package com.example.newofficetemiapp.ui.firebase;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;

/**
 * Firebase 연동 화면
 * Firebase 데이터베이스 값을 실시간으로 표시하고 제어하는 화면
 */
public class FirebaseActivity extends BaseActivity<FirebaseViewModel> {

    private SwitchCompat ledSwitch;
    private TextView distanceTextView;
    private TextView actionTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.firebase;
    }

    @Override
    protected Class<FirebaseViewModel> getViewModelClass() {
        return FirebaseViewModel.class;
    }

    @Override
    protected void setupViews() {
        ledSwitch = findViewById(R.id.switchLed);
        distanceTextView = findViewById(R.id.textDistance);
        actionTextView = findViewById(R.id.action1);

        // LED 스위치 상태 변경 이벤트
        ledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setLedStatus(isChecked);
                ledSwitch.setText(isChecked ? "LED ON" : "LED OFF");
            }
        });
    }

    @Override
    protected void observeViewModel() {
        // 거리 값 관찰
        viewModel.getDistance().observe(this, distance -> {
            if (distance != null) {
                distanceTextView.setText("Distance: " + distance);
            } else {
                distanceTextView.setText("No data");
            }
        });

        // 액션 값 관찰
        viewModel.getAction().observe(this, action -> {
            if (action != null) {
                actionTextView.setText("action number: " + action);
            } else {
                actionTextView.setText("No data");
            }
        });

        // LED 상태 관찰
        viewModel.getLedStatus().observe(this, isLedOn -> {
            ledSwitch.setChecked(isLedOn);
        });
    }
}
package com.example.newofficetemiapp.ui.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.ui.meeting.MeetingActivity;
import com.example.newofficetemiapp.ui.menu.MenuActivity;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 순찰 화면
 * 테미가 지정된 경로를 순찰하는 화면
 */
public class PatrolActivity extends BaseActivity<PatrolViewModel> implements OnGoToLocationStatusChangedListener {

    private ImageView blinkImageView;
    private Button restartButton;
    private Button meetingButton;
    private Button menuButton;
    private TextView statusTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.prob;
    }

    @Override
    protected Class<PatrolViewModel> getViewModelClass() {
        return PatrolViewModel.class;
    }

    @Override
    protected void setupViews() {
        blinkImageView = findViewById(R.id.blink);

        // GIF 이미지 로드
        Glide.with(this).load(R.drawable.blink).into(blinkImageView);
    }

    @Override
    protected void observeViewModel() {
        // 내비게이션 상태 관찰
        viewModel.getNavigationStatus().observe(this, status -> {
            if (OnGoToLocationStatusChangedListener.ABORT.equals(status)) {
                // 순찰 중단 시 UI 변경
                showStopUI();
            } else if (OnGoToLocationStatusChangedListener.GOING.equals(status)) {
                // 이동 중 UI 변경
                showMovingUI();
            }
        });

        // 목적지 관찰
        viewModel.getCurrentDestination().observe(this, destination -> {
            // 필요시 UI 업데이트
        });
    }

    private void showStopUI() {
        setContentView(R.layout.prob_stop);
        restartButton = findViewById(R.id.prob_restart);
        meetingButton = findViewById(R.id.meeting);
        menuButton = findViewById(R.id.menu);

        // 재시작 버튼 클릭 이벤트
        restartButton.setOnClickListener(view -> {
            viewModel.restartPatrol();
            setContentView(R.layout.prob);
            setupViews();
        });

        // 회의 버튼 클릭 이벤트
        meetingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MeetingActivity.class);
            startActivity(intent);
        });

        // 메뉴 버튼 클릭 이벤트
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });
    }

    private void showMovingUI() {
        setContentView(R.layout.prob);
        setupViews();
        viewModel.updateLedColor("Green");
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.registerNavigationListener();
        viewModel.startPatrol();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.unregisterNavigationListener();
    }

    @Override
    public void onGoToLocationStatusChanged(@NonNull String location, @NonNull String status, int descriptionId, @NonNull String description) {
        viewModel.updateNavigationStatus(location, status);
    }
}
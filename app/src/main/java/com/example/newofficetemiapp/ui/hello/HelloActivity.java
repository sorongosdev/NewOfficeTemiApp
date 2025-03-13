package com.example.newofficetemiapp.ui.hello;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 인사 화면
 * 각 팀으로 인사하러 가는 기능 제공
 */
public class HelloActivity extends BaseActivity<HelloViewModel> implements OnGoToLocationStatusChangedListener {

    private Button executiveTeamButton;
    private Button planningTeamButton1;
    private Button planningTeamButton2;
    private Button executiveTeamButton2;
    private Button editorialTeamButton1;
    private Button editorialTeamButton2;
    private Button meetingRoomButton;

    @Override
    protected int getLayoutId() {
        return R.layout.hello;
    }

    @Override
    protected Class<HelloViewModel> getViewModelClass() {
        return HelloViewModel.class;
    }

    @Override
    protected void setupViews() {
        executiveTeamButton = findViewById(R.id.button1);
        planningTeamButton1 = findViewById(R.id.button2);
        planningTeamButton2 = findViewById(R.id.button3);
        executiveTeamButton2 = findViewById(R.id.button4);
        editorialTeamButton1 = findViewById(R.id.button5);
        editorialTeamButton2 = findViewById(R.id.button6);
        meetingRoomButton = findViewById(R.id.button7);

        // 버튼에 리스너 설정
        setupButtonWithLocation(executiveTeamButton, "ExecutiveTeam");
        setupButtonWithLocation(planningTeamButton1, "PlanningTeam");
        setupButtonWithLocation(planningTeamButton2, "PlanningTeam");
        setupButtonWithLocation(executiveTeamButton2, "ExecutiveTeam");
        setupButtonWithLocation(editorialTeamButton1, "EditorialTeam");
        setupButtonWithLocation(editorialTeamButton2, "EditorialTeam");
        setupButtonWithLocation(meetingRoomButton, "MeetingRoom");
    }

    // 버튼 설정 및 리스너 등록을 위한 헬퍼 메서드
    private void setupButtonWithLocation(Button button, final String location) {
        button.setOnClickListener(v -> viewModel.goToLocation(location));
    }

    @Override
    protected void observeViewModel() {
        // 내비게이션 상태 관찰
        viewModel.getNavigationStatus().observe(this, status -> {
            // 필요시 UI 업데이트
        });

        // 내비게이션 목적지 관찰
        viewModel.getCurrentDestination().observe(this, destination -> {
            // 필요시 UI 업데이트
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.registerNavigationListener();
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
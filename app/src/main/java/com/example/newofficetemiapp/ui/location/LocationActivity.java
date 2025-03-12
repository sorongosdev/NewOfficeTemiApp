package com.example.newofficetemiapp.ui.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.ui.delivery.CardSendActivity;

/**
 * 위치 선택 화면
 * 배달할 위치를 선택하는 화면
 */
public class LocationActivity extends BaseActivity<LocationViewModel> {
    private Button planningTeamButton;
    private Button editorialTeamButton;
    private Button executiveTeamButton;
    private Button meetingRoomButton;
    private TextView titleTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_click;
    }

    @Override
    protected Class<LocationViewModel> getViewModelClass() {
        return LocationViewModel.class;
    }

    @Override
    protected void setupViews() {
        planningTeamButton = findViewById(R.id.div_1);
        editorialTeamButton = findViewById(R.id.div_3);
        executiveTeamButton = findViewById(R.id.div_4);
        meetingRoomButton = findViewById(R.id.meeting_room);
        titleTextView = findViewById(R.id.mapTitle);

        // 제목 설정
        titleTextView.setText("배달할 위치를 선택하세요");

        // 기획팀 버튼 클릭 이벤트
        planningTeamButton.setOnClickListener(v -> {
            viewModel.setSelectedLocation("PlanningTeam");
            navigateToCardSend();
        });

        // 편집팀 버튼 클릭 이벤트
        editorialTeamButton.setOnClickListener(v -> {
            viewModel.setSelectedLocation("EditorialTeam");
            navigateToCardSend();
        });

        // 임원팀 버튼 클릭 이벤트
        executiveTeamButton.setOnClickListener(v -> {
            viewModel.setSelectedLocation("ExecutiveTeam");
            navigateToCardSend();
        });

        // 회의실 버튼 클릭 이벤트
        meetingRoomButton.setOnClickListener(v -> {
            viewModel.setSelectedLocation("MeetingRoom");
            navigateToCardSend();
        });
    }

    @Override
    protected void observeViewModel() {
        // 위치 리스트 관찰
        viewModel.getSavedLocations().observe(this, locations -> {
            // 저장된 위치가 있으면 UI 업데이트
        });

        // 로딩 상태 관찰
        viewModel.isLoading().observe(this, isLoading -> {
            // 로딩 상태에 따라 UI 업데이트
        });
    }

    private void navigateToCardSend() {
        Intent intent = new Intent(this, CardSendActivity.class);
        intent.putExtra("location", viewModel.getSelectedLocation().getValue());
        startActivity(intent);
    }
}
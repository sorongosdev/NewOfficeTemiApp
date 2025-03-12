package com.example.newofficetemiapp.ui.location;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseFragment;

/**
 * 위치 지도 프래그먼트
 * 사무실 지도를 표시하고 위치 선택 인터페이스 제공
 */
public class LocationMapFragment extends BaseFragment<LocationViewModel> {
    private ImageView mapImageView;
    private Button planningTeamButton;
    private Button editorialTeamButton;
    private Button executiveTeamButton;
    private Button meetingRoomButton;
    private RecyclerView locationsRecyclerView;
    private LocationSelectionListener locationSelectionListener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_location_map;
    }

    @Override
    protected Class<LocationViewModel> getViewModelClass() {
        return LocationViewModel.class;
    }

    @Override
    protected void setupViews(View view) {
        mapImageView = view.findViewById(R.id.mapImageView);
        planningTeamButton = view.findViewById(R.id.planningTeamButton);
        editorialTeamButton = view.findViewById(R.id.editorialTeamButton);
        executiveTeamButton = view.findViewById(R.id.executiveTeamButton);
        meetingRoomButton = view.findViewById(R.id.meetingRoomButton);
        locationsRecyclerView = view.findViewById(R.id.locationsRecyclerView);

        // RecyclerView 설정
        locationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        LocationsAdapter adapter = new LocationsAdapter(location -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected(location);
            }
        });
        locationsRecyclerView.setAdapter(adapter);

        // 기획팀 버튼 클릭 이벤트
        planningTeamButton.setOnClickListener(v -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected("PlanningTeam");
            }
        });

        // 편집팀 버튼 클릭 이벤트
        editorialTeamButton.setOnClickListener(v -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected("EditorialTeam");
            }
        });

        // 임원팀 버튼 클릭 이벤트
        executiveTeamButton.setOnClickListener(v -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected("ExecutiveTeam");
            }
        });

        // 회의실 버튼 클릭 이벤트
        meetingRoomButton.setOnClickListener(v -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected("MeetingRoom");
            }
        });
    }

    @Override
    protected void observeViewModel() {
        // 저장된 위치 목록 관찰
        viewModel.getSavedLocations().observe(getViewLifecycleOwner(), locations -> {
            ((LocationsAdapter) locationsRecyclerView.getAdapter()).submitList(locations);
        });
    }

    public void setLocationSelectionListener(LocationSelectionListener listener) {
        this.locationSelectionListener = listener;
    }

    /**
     * 위치 선택 인터페이스
     */
    public interface LocationSelectionListener {
        void onLocationSelected(String location);
    }

/**

package com.example.newofficetemiapp.ui.location;

import android.view.View;
import android.widget.Button;
import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseFragment;

/**
 * 위치 지도 프래그먼트
 * 사무실 지도를 표시하고 위치 선택 인터페이스 제공
 */
public class LocationMapFragment extends BaseFragment<LocationViewModel> {
    private Button planningTeamButton;
    private Button editorialTeamButton;
    private Button executiveTeamButton;
    private LocationSelectionListener locationSelectionListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_click; // 기존 맵 레이아웃 사용
    }

    @Override
    protected Class<LocationViewModel> getViewModelClass() {
        return LocationViewModel.class;
    }

    @Override
    protected void setupViews(View view) {
        planningTeamButton = view.findViewById(R.id.div_1);
        editorialTeamButton = view.findViewById(R.id.div_3);
        executiveTeamButton = view.findViewById(R.id.div_4);

        // 기획팀 버튼 클릭 이벤트
        planningTeamButton.setOnClickListener(v -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected("PlanningTeam");
            } else {
                viewModel.setSelectedLocation("PlanningTeam");
            }
        });

        // 편집팀 버튼 클릭 이벤트
        editorialTeamButton.setOnClickListener(v -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected("EditorialTeam");
            } else {
                viewModel.setSelectedLocation("EditorialTeam");
            }
        });

        // 임원팀 버튼 클릭 이벤트
        executiveTeamButton.setOnClickListener(v -> {
            if (locationSelectionListener != null) {
                locationSelectionListener.onLocationSelected("ExecutiveTeam");
            } else {
                viewModel.setSelectedLocation("ExecutiveTeam");
            }
        });
    }

    @Override
    protected void observeViewModel() {
        // 뷰모델 관찰 로직 (필요한 경우 추가)
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
}

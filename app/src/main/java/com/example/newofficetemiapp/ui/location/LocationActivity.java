package com.example.newofficetemiapp.ui.location;

import android.content.Intent;
import android.widget.Button;
import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.ui.delivery.CardSendActivity;

public class LocationActivity extends BaseActivity<LocationViewModel> {
    private Button planningTeamButton;
    private Button editorialTeamButton;
    private Button executiveTeamButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_click; // 기존 레이아웃 사용
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
    }

    @Override
    protected void observeViewModel() {
    }

    private void navigateToCardSend() {
        Intent intent = new Intent(this, CardSendActivity.class);
        intent.putExtra("location", viewModel.getSelectedLocation().getValue());
        startActivity(intent);
    }
}
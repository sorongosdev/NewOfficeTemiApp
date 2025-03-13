package com.example.newofficetemiapp.ui.call;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 호출 기능 화면
 * 특정 위치로 이동하는 기능 제공
 */
public class CallActivity extends BaseActivity<CallViewModel> implements OnGoToLocationStatusChangedListener {

    private Button gotoPosition1Button;
    private Button gotoPosition2Button;
    private Button gotoPosition3Button;
    private Button followButton;
    private Button backButton;
    private TextView actionTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.call;
    }

    @Override
    protected Class<CallViewModel> getViewModelClass() {
        return CallViewModel.class;
    }

    @Override
    protected void setupViews() {
        gotoPosition1Button = findViewById(R.id.goto_position1);
        gotoPosition2Button = findViewById(R.id.goto_position2);
        gotoPosition3Button = findViewById(R.id.goto_position3);
        followButton = findViewById(R.id.buttonFollow);
        backButton = findViewById(R.id.buttonBack);
        actionTextView = findViewById(R.id.action1);

        // 위치 1 이동 버튼
        gotoPosition1Button.setOnClickListener(view -> {
            viewModel.goToLocation("point1");
        });

        // 위치 2 이동 버튼
        gotoPosition2Button.setOnClickListener(view -> {
            viewModel.goToLocation("point2");
        });

        // 위치 3 이동 버튼
        gotoPosition3Button.setOnClickListener(view -> {
            viewModel.goToLocation("point3");
        });

        // 따라가기 버튼
        followButton.setOnClickListener(view -> {
            if ("따라가기".equals(followButton.getText().toString())) {
                viewModel.startFollowMe();
                followButton.setText("중지");
            } else if ("중지".equals(followButton.getText().toString())) {
                viewModel.stopMovement();
                followButton.setText("따라가기");
            }
        });

        // 뒤로가기 버튼
        backButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void observeViewModel() {
        // 액션 상태 관찰
        viewModel.getActionText().observe(this, actionText -> {
            if (actionText != null) {
                actionTextView.setText(actionText);
            }
        });

        // 내비게이션 목적지 관찰 - 메서드 이름 수정
        viewModel.getCurrentDestination().observe(this, destination -> {
            if (destination != null) {
                // 필요시 UI 업데이트
            }
        });

        // 내비게이션 상태 관찰
        viewModel.getNavigationStatus().observe(this, status -> {
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
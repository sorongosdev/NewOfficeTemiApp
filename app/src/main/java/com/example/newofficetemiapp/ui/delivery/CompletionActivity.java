package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.ui.main.MainActivity;

/**
 * 배달 완료 화면
 * 배달 완료 메시지 및 홈으로 돌아가는 버튼 제공
 */
public class CompletionActivity extends BaseActivity<DeliveryViewModel> {
    private Button homeButton;
    private TextView completionMessageTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_done_delivering;
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        homeButton = findViewById(R.id.btn);

        // 홈 버튼 클릭 이벤트
        homeButton.setOnClickListener(v -> {
            viewModel.resetDeliveryData();
            navigateToHome();
        });
    }

    @Override
    protected void observeViewModel() {
        // 현재 배달 관찰
        viewModel.getCurrentDelivery().observe(this, delivery -> {
            if (delivery != null) {
                String message = delivery.getTargetLocation() + "에 배달이 완료되었습니다.";
                completionMessageTextView.setText(message);
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
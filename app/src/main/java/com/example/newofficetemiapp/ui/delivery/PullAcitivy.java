package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.util.Constants;

/**
 * 서류 수령 화면
 * 사용자가 서류함에서 서류를 꺼내는 단계
 */
public class PullActivity extends BaseActivity<DeliveryViewModel> {
    private Button confirmButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pull;
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        confirmButton = findViewById(R.id.button2);

        // 인텐트에서 배달 ID 가져오기
        String deliveryId = getIntent().getStringExtra("deliveryId");
        if (deliveryId != null) {
            viewModel.setCurrentDelivery(deliveryId);
        }

        // 확인 버튼 클릭 이벤트
        confirmButton.setOnClickListener(v -> {
            navigateToCompletion();
        });
    }

    @Override
    protected void observeViewModel() {
        // 필요시 ViewModel 관찰 추가
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 모터 잠금 해제 & LED 초록색으로 변경
        viewModel.updateMotorLock(Constants.MOTOR_UNLOCK);
        viewModel.updateLedColor(Constants.LED_GREEN);
    }

    private void navigateToCompletion() {
        DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
        if (delivery != null) {
            // 배달 상태 업데이트
            viewModel.updateDeliveryStatus(delivery.getId(), DeliveryStatus.STATUS_COMPLETED);

            // 완료 화면으로 이동
            Intent intent = new Intent(this, CompletionActivity.class);
            intent.putExtra("deliveryId", delivery.getId());
            startActivity(intent);
        }
    }
}
package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.util.Constants;

/**
 * 카드 수신 화면
 * 수신자가 ID 카드를 태그하여 배달 수신 인증
 */
public class CardReceiveActivity extends BaseActivity<DeliveryViewModel> {
    private int toastShown = 0;
    private final Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card_receive;
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        // 인텐트에서 배달 ID 가져오기
        String deliveryId = getIntent().getStringExtra("deliveryId");
        if (deliveryId != null) {
            viewModel.setCurrentDelivery(deliveryId);
        }
    }

    @Override
    protected void observeViewModel() {
        // 카드 상태 관찰
        viewModel.getCardStatus().observe(this, status -> {
            switch (status) {
                case DeliveryViewModel.CARD_STATUS_VALID:
                    // 유효한 카드
                    navigateToPull();
                    break;

                case DeliveryViewModel.CARD_STATUS_EMPTY:
                    // 카드 대기 중
                    viewModel.updateLedColor(Constants.LED_YELLOW);

                    // 2초 후 안내 메시지 표시
                    handler.postDelayed(() -> {
                        if (toastShown == 0) {
                            Toast.makeText(this, "사원증을 찍어주세요", Toast.LENGTH_SHORT).show();
                            toastShown = 1;
                        }
                    }, 2000);
                    break;

                case DeliveryViewModel.CARD_STATUS_INVALID:
                    // 유효하지 않은 카드
                    Toast.makeText(this, "등록되지 않는 사원증입니다. 등록 후 태그해주세요", Toast.LENGTH_LONG).show();
                    viewModel.updateLedColor(Constants.LED_RED);
                    break;
            }
        });
    }

    private void navigateToPull() {
        DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
        if (delivery != null) {
            Intent intent = new Intent(this, PullActivity.class);
            intent.putExtra("deliveryId", delivery.getId());
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
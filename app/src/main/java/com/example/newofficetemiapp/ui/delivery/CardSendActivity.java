package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.util.Constants;

/**
 * 카드 전송 화면
 * 사용자가 ID 카드를 태그하여 배달 인증
 */
public class CardSendActivity extends BaseActivity<DeliveryViewModel> {
    private TextView divisionTextView;
    private ImageView cardGifImageView;
    private int toastShown = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card_send;
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        divisionTextView = findViewById(R.id.go_div);
        cardGifImageView = findViewById(R.id.imageView2);

        // GIF 이미지 로드
        Glide.with(this).load(R.drawable.id_card_gifimage).into(cardGifImageView);

        // 현재 배달의 목적지 표시
        DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
        if (delivery != null) {
            divisionTextView.setText(delivery.getTargetLocation());
        }
    }

    @Override
    protected void observeViewModel() {
        // 카드 상태 관찰
        viewModel.getCardStatus().observe(this, status -> {
            switch (status) {
                case DeliveryViewModel.CARD_STATUS_VALID:
                    // 유효한 카드
                    navigateToDelivering();
                    break;

                case DeliveryViewModel.CARD_STATUS_EMPTY:
                    // 카드 대기 중
                    if (toastShown == 0) {
                        Toast.makeText(this, "사원증을 찍어주세요", Toast.LENGTH_SHORT).show();
                        toastShown = 1;
                    }
                    viewModel.updateLedColor(Constants.LED_YELLOW);
                    break;

                case DeliveryViewModel.CARD_STATUS_INVALID:
                    // 유효하지 않은 카드
                    Toast.makeText(this, "등록되지 않는 사원증입니다. 등록 후 태그해주세요", Toast.LENGTH_LONG).show();
                    viewModel.updateLedColor(Constants.LED_RED);
                    break;
            }
        });
    }

    private void navigateToDelivering() {
        DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
        if (delivery != null) {
            Intent intent = new Intent(this, DeliveringActivity.class);
            intent.putExtra("deliveryId", delivery.getId());
            startActivity(intent);

            viewModel.updateLedColor(Constants.LED_GREEN);
            viewModel.updateMotorLock(Constants.MOTOR_LOCK);
        }
    }
}
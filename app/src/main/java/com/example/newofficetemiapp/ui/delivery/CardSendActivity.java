package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.ui.location.LocationActivity;
import com.example.newofficetemiapp.util.Constants;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 카드 전송 및 발송 화면
 * 사용자가 ID 카드를 태그하여 배달 인증
 * SendActivity와 통합
 */
public class CardSendActivity extends BaseActivity<DeliveryViewModel> implements OnGoToLocationStatusChangedListener {

    private TextView divisionTextView;
    private ImageView cardGifImageView;
    private ImageView gifImageView;
    private Button sendButton;
    private int toastShown = 0;
    private boolean isDeliveryPhase = false;

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

        // 발송 화면 UI 요소 (선택적 초기화)
        try {
            gifImageView = findViewById(R.id.imageView);
            sendButton = findViewById(R.id.send_Btn);

            // GIF 이미지 로드 (발송 화면)
            Glide.with(this).load(R.drawable.gifimage).into(gifImageView);
// 목적지 위치 관찰
            viewModel.getDestinationLocation().observe(this, location -> {
                if (location != null && !location.isEmpty()) {
                    viewModel.startNavigation(location);
                }
            });

            // 발송 버튼 숨김
            sendButton.setVisibility(View.GONE);
        } catch (Exception e) {
// 발송 화면 레이아웃이 아닌 경우 무시
            isDeliveryPhase = true;
        }

        // 카드 전송 화면 UI 초기화
        if (isDeliveryPhase) {
            // GIF 이미지 로드 (카드 전송 화면)
            Glide.with(this).load(R.drawable.id_card_gifimage).into(cardGifImageView);

// 현재 배달의 목적지 표시
            DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
            if (delivery != null) {
                divisionTextView.setText(delivery.getTargetLocation());
            }
        }
    }

    @Override
    protected void observeViewModel() {
        // 내비게이션 상태 관찰
        viewModel.getNavigationStatus().observe(this, status -> {
            if (OnGoToLocationStatusChangedListener.COMPLETE.equals(status)) {
                if (!isDeliveryPhase) {
                    // 발송 화면에서: 도착 후 UI 업데이트
                    showSendButton();
                }
            }
        });

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

        // 발신자 정보 관찰 (필요한 경우)
        viewModel.getSenderInfo().observe(this, sender -> {
            // 발신자 정보가 변경되면 UI 업데이트
        });
    }

    private void showSendButton() {
        if (sendButton != null) {
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setOnClickListener(v -> {
                navigateToLocationSelection();
            });
        }
    }

    private void navigateToLocationSelection() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
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
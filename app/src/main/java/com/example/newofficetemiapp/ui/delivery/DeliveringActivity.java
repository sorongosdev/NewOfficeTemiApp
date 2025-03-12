package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 배달 진행 화면
 * 테미가 목적지로 이동 중인 상태 표시
 */
public class DeliveringActivity extends BaseActivity<DeliveryViewModel> {
    private TextView divisionTextView;
    private ImageView deliveryGifImageView;
    private ImageView senderImageView;
    private ImageView receiverImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.delivering;
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        divisionTextView = findViewById(R.id.go_div);
        deliveryGifImageView = findViewById(R.id.imageView3);
        senderImageView = findViewById(R.id.Sender_image);
        receiverImageView = findViewById(R.id.Receiver_image);

        // GIF 이미지 로드
        Glide.with(this).load(R.drawable.delivering_gifimage).into(deliveryGifImageView);

        // 인텐트에서 배달 ID 가져오기
        String deliveryId = getIntent().getStringExtra("deliveryId");
        if (deliveryId != null) {
            viewModel.setCurrentDelivery(deliveryId);
        }

        // 배달 정보 표시
        DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
        if (delivery != null) {
            divisionTextView.setText(delivery.getTargetLocation());
            setSenderImage(delivery.getSenderId());
            setReceiverImage(delivery.getReceiverId());
        }
    }

    @Override
    protected void observeViewModel() {
        // 내비게이션 상태 관찰
        viewModel.getNavigationStatus().observe(this, status -> {
            if (OnGoToLocationStatusChangedListener.COMPLETE.equals(status)) {
                DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
                if (delivery != null) {
                    // 도착 메시지 재생
                    viewModel.speak(delivery.getTargetLocation() + "에 서류가 도착했습니다");

                    // ID 카드 초기화
                    viewModel.resetCardNumber();

                    // 카드 수신 화면으로 이동
                    Intent intent = new Intent(this, CardReceiveActivity.class);
                    intent.putExtra("deliveryId", delivery.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private void setSenderImage(String sender) {
        if (sender == null) return;

        if ("jiyun".equals(sender)) {
            senderImageView.setImageResource(R.drawable.jiyun);
        } else if ("jongchan".equals(sender)) {
            senderImageView.setImageResource(R.drawable.jongchan);
        } else if ("sora".equals(sender)) {
            senderImageView.setImageResource(R.drawable.sora);
        } else if ("sunghoon".equals(sender)) {
            senderImageView.setImageResource(R.drawable.sunghoon);
        } else if ("youngro".equals(sender)) {
            senderImageView.setImageResource(R.drawable.youngro);
        } else if ("yusin".equals(sender)) {
            senderImageView.setImageResource(R.drawable.yushin);
        }
    }

    private void setReceiverImage(String division) {
        if (division == null) return;

        if ("PlanningTeam".equals(division)) {
            receiverImageView.setImageResource(R.drawable.jiyun2);
        } else if ("ExecutiveTeam".equals(division)) {
            receiverImageView.setImageResource(R.drawable.sora2);
        } else if ("EditorialTeam".equals(division)) {
            receiverImageView.setImageResource(R.drawable.youngro2);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 목적지로 이동 시작
        DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
        if (delivery != null) {
            viewModel.startNavigation(delivery.getTargetLocation());
        }
    }
}

package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 배달 상태 화면
 * 배달의 세부 정보와 현재 상태를 표시
 */
public class DeliveryStatusActivity extends BaseActivity<DeliveryViewModel> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

    private TextView senderTextView;
    private TextView receiverTextView;
    private TextView routeTextView;
    private TextView statusTextView;
    private TextView timeTextView;
    private ImageView senderImageView;
    private ImageView receiverImageView;
    private Button actionButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_delivery_status;
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        senderTextView = findViewById(R.id.senderTextView);
        receiverTextView = findViewById(R.id.receiverTextView);
        routeTextView = findViewById(R.id.routeTextView);
        statusTextView = findViewById(R.id.statusTextView);
        timeTextView = findViewById(R.id.timeTextView);
        senderImageView = findViewById(R.id.senderImageView);
        receiverImageView = findViewById(R.id.receiverImageView);
        actionButton = findViewById(R.id.actionButton);

        // 인텐트에서 배달 ID 가져오기
        String deliveryId = getIntent().getStringExtra("deliveryId");
        if (deliveryId != null) {
            viewModel.setCurrentDelivery(deliveryId);
        }

        // 액션 버튼 클릭 이벤트
        actionButton.setOnClickListener(v -> {
            DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
            if (delivery != null) {
                switch (delivery.getStatus()) {
                    case DeliveryStatus.STATUS_PENDING:
                        // 대기 중이면 배달 시작
                        viewModel.updateDeliveryStatus(delivery.getId(), DeliveryStatus.STATUS_IN_PROGRESS);
                        viewModel.startNavigation(delivery.getTargetLocation());
                        break;

                    case DeliveryStatus.STATUS_IN_PROGRESS:
                        // 이미 진행 중이면 카드 전송 화면으로 이동
                        Intent intent = new Intent(this, CardSendActivity.class);
                        startActivity(intent);
                        break;

                    case DeliveryStatus.STATUS_COMPLETED:
                        // 완료된 경우 홈으로 돌아가기
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    protected void observeViewModel() {
        // 현재 배달 정보 관찰
        viewModel.getCurrentDelivery().observe(this, delivery -> {
            if (delivery != null) {
                updateDeliveryInfo(delivery);
            }
        });

        // 내비게이션 상태 관찰
        viewModel.getNavigationStatus().observe(this, status -> {
            if (OnGoToLocationStatusChangedListener.COMPLETE.equals(status)) {
                // 목적지 도착 시 카드 전송 화면으로 이동
                Intent intent = new Intent(this, CardSendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateDeliveryInfo(DeliveryStatus delivery) {
        senderTextView.setText(delivery.getSenderId());
        receiverTextView.setText(delivery.getReceiverId());
        routeTextView.setText(delivery.getSourceLocation() + " → " + delivery.getTargetLocation());
        timeTextView.setText(DATE_FORMAT.format(new Date(delivery.getTimestamp())));

        // 상태에 따른 UI 업데이트
        switch (delivery.getStatus()) {
            case DeliveryStatus.STATUS_PENDING:
                statusTextView.setText("배달 대기중");
                actionButton.setText("배달 시작");
                actionButton.setVisibility(View.VISIBLE);
                break;

            case DeliveryStatus.STATUS_IN_PROGRESS:
                statusTextView.setText("배달 중");
                actionButton.setText("전달하기");
                actionButton.setVisibility(View.VISIBLE);
                break;

            case DeliveryStatus.STATUS_COMPLETED:
                statusTextView.setText("배달 완료");
                actionButton.setText("돌아가기");
                actionButton.setVisibility(View.VISIBLE);
                break;

            case DeliveryStatus.STATUS_FAILED:
                statusTextView.setText("배달 실패");
                actionButton.setVisibility(View.GONE);
                break;
        }

        // 발신자 이미지 설정
        setSenderImage(delivery.getSenderId());

        // 수신자 이미지 설정
        setReceiverImage(delivery.getReceiverId());
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

    private void setReceiverImage(String receiver) {
        if (receiver == null) return;

        if ("jiyun".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.jiyun2);
        } else if ("jongchan".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.jongchan);
        } else if ("sora".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.sora2);
        } else if ("sunghoon".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.sunghoon);
        } else if ("youngro".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.youngro2);
        } else if ("yusin".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.yushin);
        }
    }
}
// 완료된 경우
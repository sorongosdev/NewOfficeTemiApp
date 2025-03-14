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

    private TextView goDiv; // go_div를 상태 텍스트뷰로 사용
    private ImageView senderImageView;
    private ImageView receiverImageView;
    private ImageView deliveryGifImage; // 배달 이미지로 사용

    @Override
    protected int getLayoutId() {
        return R.layout.delivering; // delivering.xml 사용
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        // delivering.xml의 컴포넌트들 사용
        goDiv = findViewById(R.id.go_div);
        senderImageView = findViewById(R.id.Sender_image);
        receiverImageView = findViewById(R.id.Receiver_image);
        deliveryGifImage = findViewById(R.id.imageView3);

        // 인텐트에서 배달 ID 가져오기
        String deliveryId = getIntent().getStringExtra("deliveryId");
        if (deliveryId != null) {
            viewModel.setCurrentDelivery(deliveryId);
            goDiv.setText("배달 ID: " + deliveryId);
        }

        // 액션 버튼은 별도로 없으므로 senderImageView를 클릭 가능하게 설정
        senderImageView.setOnClickListener(v -> {
            DeliveryStatus delivery = viewModel.getCurrentDelivery().getValue();
            if (delivery != null) {
                // 상태에 따른 작업 처리
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
        // 상태 텍스트 및 경로 정보를 go_div에 표시
        goDiv.setText(String.format(Locale.KOREA,
                "발신: %s → 수신: %s\n경로: %s → %s\n상태: %s\n시간: %s",
                delivery.getSenderId(),
                delivery.getReceiverId(),
                delivery.getSourceLocation(),
                delivery.getTargetLocation(),
                getStatusText(delivery.getStatus()),
                DATE_FORMAT.format(new Date(delivery.getTimestamp()))
        ));

        // 발신자 이미지 설정
        setSenderImage(delivery.getSenderId());

        // 수신자 이미지 설정
        setReceiverImage(delivery.getReceiverId());
    }

    private String getStatusText(String status) {
        switch (status) {
            case DeliveryStatus.STATUS_PENDING:
                return "배달 대기중";
            case DeliveryStatus.STATUS_IN_PROGRESS:
                return "배달 중";
            case DeliveryStatus.STATUS_COMPLETED:
                return "배달 완료";
            case DeliveryStatus.STATUS_FAILED:
                return "배달 실패";
            default:
                return "알 수 없음";
        }
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

        // delivering.xml에는 이미지 리소스가 다를 수 있으므로 기존 리소스를 사용
        if ("jiyun".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.jiyun);
        } else if ("jongchan".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.jongchan);
        } else if ("sora".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.sora);
        } else if ("sunghoon".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.sunghoon);
        } else if ("youngro".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.youngro);
        } else if ("yusin".equals(receiver)) {
            receiverImageView.setImageResource(R.drawable.yushin);
        }
    }
}
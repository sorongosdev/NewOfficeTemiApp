package com.example.newofficetemiapp.ui.delivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.data.model.DeliveryStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 배달 항목을 표시하기 위한 RecyclerView Adapter
 */
public class DeliveryAdapter extends ListAdapter<DeliveryStatus, DeliveryAdapter.DeliveryViewHolder> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
    private final OnDeliveryClickListener listener;

    public DeliveryAdapter(OnDeliveryClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<DeliveryStatus> DIFF_CALLBACK = new DiffUtil.ItemCallback<DeliveryStatus>() {
        @Override
        public boolean areItemsTheSame(@NonNull DeliveryStatus oldItem, @NonNull DeliveryStatus newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DeliveryStatus oldItem, @NonNull DeliveryStatus newItem) {
            return oldItem.getStatus().equals(newItem.getStatus()) &&
                    oldItem.getTimestamp() == newItem.getTimestamp();
        }
    };

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 대체 레이아웃으로 call.xml 사용 - 간단한 아이템 표시 가능한 레이아웃
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.call, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        DeliveryStatus delivery = getItem(position);
        holder.bind(delivery, listener);
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {
        private final TextView actionTextView; // call.xml의 action1 TextView 사용

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            actionTextView = itemView.findViewById(R.id.action1);
        }

        public void bind(DeliveryStatus delivery, OnDeliveryClickListener listener) {
            // call.xml에는 하나의 TextView만 있으므로 모든 정보를 하나의 문자열로 결합
            String deliveryInfo = "발신자: " + delivery.getSenderId() +
                    "\n수신자: " + delivery.getReceiverId() +
                    "\n경로: " + delivery.getSourceLocation() + " → " + delivery.getTargetLocation() +
                    "\n상태: " + getStatusText(delivery.getStatus()) +
                    "\n시간: " + DATE_FORMAT.format(new Date(delivery.getTimestamp()));

            actionTextView.setText(deliveryInfo);

            // 상태에 따라 색상 변경
            switch (delivery.getStatus()) {
                case DeliveryStatus.STATUS_PENDING:
                    actionTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_blue_dark));
                    break;
                case DeliveryStatus.STATUS_IN_PROGRESS:
                    actionTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_orange_dark));
                    break;
                case DeliveryStatus.STATUS_COMPLETED:
                    actionTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                case DeliveryStatus.STATUS_FAILED:
                    actionTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_red_dark));
                    break;
            }

            // 클릭 리스너 설정
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeliveryClick(delivery);
                }
            });
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
    }

    public interface OnDeliveryClickListener {
        void onDeliveryClick(DeliveryStatus delivery);
    }
}
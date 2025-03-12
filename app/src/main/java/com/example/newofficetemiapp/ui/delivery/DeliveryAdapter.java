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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        DeliveryStatus delivery = getItem(position);
        holder.bind(delivery, listener);
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {
        private final TextView senderTextView;
        private final TextView receiverTextView;
        private final TextView routeTextView;
        private final TextView statusTextView;
        private final TextView timeTextView;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            receiverTextView = itemView.findViewById(R.id.receiverTextView);
            routeTextView = itemView.findViewById(R.id.routeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }

        public void bind(DeliveryStatus delivery, OnDeliveryClickListener listener) {
            senderTextView.setText(delivery.getSenderId());
            receiverTextView.setText(delivery.getReceiverId());
            routeTextView.setText(delivery.getSourceLocation() + " → " + delivery.getTargetLocation());

            // 상태에 따라 색상 및 텍스트 변경
            switch (delivery.getStatus()) {
                case DeliveryStatus.STATUS_PENDING:
                    statusTextView.setText("배달 대기중");
                    statusTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_blue_dark));
                    break;
                case DeliveryStatus.STATUS_IN_PROGRESS:
                    statusTextView.setText("배달 중");
                    statusTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_orange_dark));
                    break;
                case DeliveryStatus.STATUS_COMPLETED:
                    statusTextView.setText("배달 완료");
                    statusTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                case DeliveryStatus.STATUS_FAILED:
                    statusTextView.setText("배달 실패");
                    statusTextView.setTextColor(itemView.getResources().getColor(android.R.color.holo_red_dark));
                    break;
            }

            // 시간 형식화
            timeTextView.setText(DATE_FORMAT.format(new Date(delivery.getTimestamp())));

            // 클릭 리스너 설정
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeliveryClick(delivery);
                }
            });
        }
    }

    public interface OnDeliveryClickListener {
        void onDeliveryClick(DeliveryStatus delivery);
    }
}
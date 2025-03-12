package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;
import com.example.newofficetemiapp.ui.location.LocationActivity;

/**
 * 배달 메인 화면
 * 배달 목록을 표시하고 새 배달 시작 버튼을 제공
 */
public class DeliveryActivity extends BaseActivity<DeliveryViewModel> {
    private RecyclerView deliveryRecyclerView;
    private DeliveryAdapter deliveryAdapter;
    private Button newDeliveryButton;
    private TextView emptyView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_delivery;
    }

    @Override
    protected Class<DeliveryViewModel> getViewModelClass() {
        return DeliveryViewModel.class;
    }

    @Override
    protected void setupViews() {
        deliveryRecyclerView = findViewById(R.id.deliveryRecyclerView);
        newDeliveryButton = findViewById(R.id.newDeliveryButton);
        emptyView = findViewById(R.id.emptyView);

        // RecyclerView 설정
        deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        deliveryAdapter = new DeliveryAdapter(delivery -> {
            // 배달 항목 클릭 시 상세 화면으로 이동
            Intent intent = new Intent(this, DeliveryStatusActivity.class);
            intent.putExtra("deliveryId", delivery.getId());
            startActivity(intent);
        });
        deliveryRecyclerView.setAdapter(deliveryAdapter);

        // 새 배달 버튼 클릭 이벤트
        newDeliveryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LocationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void observeViewModel() {
        // 배달 목록 관찰
        viewModel.getDeliveries().observe(this, deliveries -> {
            deliveryAdapter.submitList(deliveries);

            // 빈 상태 처리
            if (deliveries.isEmpty()) {
                deliveryRecyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                deliveryRecyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        });

        // 로딩 상태 관찰
        viewModel.isLoading().observe(this, isLoading -> {
            // 로딩 상태에 따라 UI 업데이트
        });

        // 에러 메시지 관찰
        viewModel.getErrorMessage().observe(this, errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                // 에러 메시지 표시
                showToast(errorMsg);
            }
        });
    }

    private void showToast(String message) {
        // 토스트 메시지 표시
    }
}
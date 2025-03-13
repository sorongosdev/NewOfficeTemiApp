package com.example.newofficetemiapp.ui.meeting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;

/**
 * 회의실 예약 화면
 * 회의실 예약 정보를 표시하는 화면
 */
public class MeetingActivity extends BaseActivity<MeetingViewModel> {

    private Button backButton;
    private TextView time8TextView;
    private TextView time9TextView;
    private TextView time10TextView;
    private TextView time11TextView;
    private TextView time12TextView;
    private TextView time13TextView;
    private TextView time14TextView;
    private TextView time15TextView;
    private TextView time16TextView;
    private TextView time17TextView;
    private TextView time18TextView;

    @Override
    protected int getLayoutId() {
        return R.layout.meeting;
    }

    @Override
    protected Class<MeetingViewModel> getViewModelClass() {
        return MeetingViewModel.class;
    }

    @Override
    protected void setupViews() {
        backButton = findViewById(R.id.prob_restart2);
        time8TextView = findViewById(R.id.time8);
        time9TextView = findViewById(R.id.time9);
        time10TextView = findViewById(R.id.time10);
        time11TextView = findViewById(R.id.time11);
        time12TextView = findViewById(R.id.time12);
        time13TextView = findViewById(R.id.time13);
        time14TextView = findViewById(R.id.time14);
        time15TextView = findViewById(R.id.time15);
        time16TextView = findViewById(R.id.time16);
        time17TextView = findViewById(R.id.time17);
        time18TextView = findViewById(R.id.time18);

        // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void observeViewModel() {
        // 8시 예약 관찰
        viewModel.getTime8Reservation().observe(this, reservation -> {
            updateReservationTextView(time8TextView, reservation);
        });

        // 9시 예약 관찰
        viewModel.getTime9Reservation().observe(this, reservation -> {
            updateReservationTextView(time9TextView, reservation);
        });

        // 10시 예약 관찰
        viewModel.getTime10Reservation().observe(this, reservation -> {
            updateReservationTextView(time10TextView, reservation);
        });

        // 11시 예약 관찰
        viewModel.getTime11Reservation().observe(this, reservation -> {
            updateReservationTextView(time11TextView, reservation);
        });

        // 12시 예약 관찰
        viewModel.getTime12Reservation().observe(this, reservation -> {
            updateReservationTextView(time12TextView, reservation);
        });

        // 13시 예약 관찰
        viewModel.getTime13Reservation().observe(this, reservation -> {
            updateReservationTextView(time13TextView, reservation);
        });

        // 14시 예약 관찰
        viewModel.getTime14Reservation().observe(this, reservation -> {
            updateReservationTextView(time14TextView, reservation);
        });

        // 15시 예약 관찰
        viewModel.getTime15Reservation().observe(this, reservation -> {
            updateReservationTextView(time15TextView, reservation);
        });

        // 16시 예약 관찰
        viewModel.getTime16Reservation().observe(this, reservation -> {
            updateReservationTextView(time16TextView, reservation);
        });

        // 17시 예약 관찰
        viewModel.getTime17Reservation().observe(this, reservation -> {
            updateReservationTextView(time17TextView, reservation);
        });

        // 18시 예약 관찰
        viewModel.getTime18Reservation().observe(this, reservation -> {
            updateReservationTextView(time18TextView, reservation);
        });
    }

    private void updateReservationTextView(TextView textView, String reservation) {
        switch (reservation) {
            case "0":
                textView.setText("");
                break;
            case "PlanningTeam":
                textView.setText("Planning Team");
                break;
            case "ExecutiveTeam":
                textView.setText("Executive Team");
                break;
            case "EditorialTeam":
                textView.setText("Editorial Team");
                break;
        }
    }
}
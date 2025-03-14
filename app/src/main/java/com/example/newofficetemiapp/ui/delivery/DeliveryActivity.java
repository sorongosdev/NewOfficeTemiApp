package com.example.newofficetemiapp.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.location.LocationActivity;

/**
 * 배달 메인 화면
 * 배달 목록을 표시하고 새 배달 시작 버튼을 제공
 */
public class DeliveryActivity extends AppCompatActivity {
    private Button newDeliveryButton;
    private Button button2;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 기존 activity_main.xml 사용

        setupViews();
        setupListeners();
    }

    private void setupViews() {
        // activity_main.xml의 실제 ID 사용
        textView1 = findViewById(R.id.textView1);
        textView1.setText("배달 서비스");

        // activity_main.xml의 버튼 ID로 변경
        newDeliveryButton = findViewById(R.id.button5); // 순찰 버튼 -> 새 배달 버튼으로 용도 변경
        newDeliveryButton.setText("새 배달");

        button2 = findViewById(R.id.button2); // 서류 버튼 -> 배달 목록 버튼으로 용도 변경
        button2.setText("배달 목록");
    }

    private void setupListeners() {
        // 새 배달 버튼 클릭 이벤트
        newDeliveryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LocationActivity.class);
            startActivity(intent);
            Toast.makeText(this, "배달 위치 선택 화면으로 이동합니다", Toast.LENGTH_SHORT).show();
        });

        // 배달 목록 버튼 클릭 이벤트
        button2.setOnClickListener(v -> {
            Toast.makeText(this, "배달 목록을 불러옵니다", Toast.LENGTH_SHORT).show();
            // 여기서 배달 목록 화면으로 이동하거나 목록을 표시하는 로직 추가
        });
    }
}
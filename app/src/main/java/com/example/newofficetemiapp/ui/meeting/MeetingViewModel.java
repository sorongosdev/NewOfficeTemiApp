package com.example.newofficetemiapp.ui.meeting;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.ui.base.BaseViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 회의실 예약 화면을 위한 ViewModel
 */
public class MeetingViewModel extends BaseViewModel {
    private static final String TAG = "MeetingViewModel";

    private final FirebaseDatabase firebaseDatabase;
    private final Map<String, MutableLiveData<String>> timeReservations = new HashMap<>();

    public MeetingViewModel() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        // 시간대별 LiveData 초기화
        for (int i = 8; i <= 18; i++) {
            timeReservations.put("time" + i, new MutableLiveData<>());
        }

        loadReservations();
    }

    private void loadReservations() {
        // 시간대별 예약 정보 로드 (8시부터 18시까지)
        for (int i = 8; i <= 18; i++) {
            final String timeKey = "time" + i;
            firebaseDatabase.getReference(timeKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Object value = snapshot.getValue();
                    if (value != null) {
                        timeReservations.get(timeKey).setValue(value.toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Failed to read " + timeKey, error.toException());
                    setErrorMessage(timeKey + " 데이터를 읽는데 실패했습니다.");
                }
            });
        }
    }

    // 시간대별 예약 정보 LiveData getter
    public LiveData<String> getTimeReservation(int hour) {
        String timeKey = "time" + hour;
        return timeReservations.getOrDefault(timeKey, new MutableLiveData<>());
    }

    // 기존 getter 메서드들 - 호환성 유지
    public LiveData<String> getTime8Reservation() {
        return timeReservations.get("time8");
    }

    public LiveData<String> getTime9Reservation() {
        return timeReservations.get("time9");
    }

    public LiveData<String> getTime10Reservation() {
        return timeReservations.get("time10");
    }

    public LiveData<String> getTime11Reservation() {
        return timeReservations.get("time11");
    }

    public LiveData<String> getTime12Reservation() {
        return timeReservations.get("time12");
    }

    public LiveData<String> getTime13Reservation() {
        return timeReservations.get("time13");
    }

    public LiveData<String> getTime14Reservation() {
        return timeReservations.get("time14");
    }

    public LiveData<String> getTime15Reservation() {
        return timeReservations.get("time15");
    }

    public LiveData<String> getTime16Reservation() {
        return timeReservations.get("time16");
    }

    public LiveData<String> getTime17Reservation() {
        return timeReservations.get("time17");
    }

    public LiveData<String> getTime18Reservation() {
        return timeReservations.get("time18");
    }
}
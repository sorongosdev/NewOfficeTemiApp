package com.example.newofficetemiapp.ui.firebase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.data.repository.FirebaseRepository;
import com.example.newofficetemiapp.ui.base.BaseViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Firebase 화면을 위한 ViewModel
 */
public class FirebaseViewModel extends BaseViewModel {
    private static final String TAG = "FirebaseViewModel";

    private final FirebaseRepository firebaseRepository;
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference databaseReference;

    private final MutableLiveData<String> distance = new MutableLiveData<>();
    private final MutableLiveData<String> action = new MutableLiveData<>();
    private final MutableLiveData<Boolean> ledStatus = new MutableLiveData<>(false);

    public FirebaseViewModel() {
        firebaseRepository = FirebaseRepository.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        initFirebaseListeners();
    }

    private void initFirebaseListeners() {
        // 거리 센서 값 리스너
        firebaseDatabase.getReference("distance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    distance.setValue(value.toString());
                    Log.d(TAG, "distance is " + value);
                } else {
                    distance.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
                setErrorMessage("거리 데이터를 읽는데 실패했습니다.");
            }
        });

        // 액션 값 리스너
        firebaseDatabase.getReference("action").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    action.setValue(value.toString());
                    Log.d(TAG, "action is " + value);
                } else {
                    action.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Fail to read value.", error.toException());
                setErrorMessage("액션 데이터를 읽는데 실패했습니다.");
            }
        });

        // LED 상태 리스너
        firebaseDatabase.getReference("led").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    boolean isLedOn = Boolean.parseBoolean(value.toString());
                    ledStatus.setValue(isLedOn);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read LED value.", error.toException());
            }
        });
    }

    public void setLedStatus(boolean isOn) {
        databaseReference.child("led").setValue(isOn);
        ledStatus.setValue(isOn);
    }

    public LiveData<String> getDistance() {
        return distance;
    }

    public LiveData<String> getAction() {
        return action;
    }

    public LiveData<Boolean> getLedStatus() {
        return ledStatus;
    }
}
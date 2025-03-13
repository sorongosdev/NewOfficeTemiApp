package com.example.newofficetemiapp.ui.patrol;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.data.repository.FirebaseRepository;
import com.example.newofficetemiapp.ui.base.NavBaseViewModel;
import com.example.newofficetemiapp.util.Constants;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import java.util.List;

/**
 * 순찰 화면을 위한 ViewModel
 * NavBaseViewModel 상속 적용
 */
public class PatrolViewModel extends NavBaseViewModel {
    private final FirebaseRepository firebaseRepository;

    private final MutableLiveData<List<String>> patrolPoints = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentPatrolIndex = new MutableLiveData<>(0);

    private final String temiId;
    private String lastLocation;

    public PatrolViewModel() {
        super();
        firebaseRepository = FirebaseRepository.getInstance();

        temiId = temiRepository.getTemiSerialNumber();
        loadPatrolPoints();
    }

    private void loadPatrolPoints() {
        if (Constants.TEMI1.equals(temiId)) {
            // TEMI1의 순찰 경로 설정
            List<String> points = List.of("prob11", "prob12");
            patrolPoints.setValue(points);
        } else if (Constants.TEMI2.equals(temiId)) {
            // TEMI2의 순찰 경로 설정
            List<String> points = List.of("prob21", "prob22");
            patrolPoints.setValue(points);
        }
    }

    public void startPatrol() {
        List<String> points = patrolPoints.getValue();
        if (points != null && !points.isEmpty()) {
            int index = currentPatrolIndex.getValue() != null ? currentPatrolIndex.getValue() : 0;
            String destination = points.get(index);
            goToLocation(destination);
        }
    }

    public void restartPatrol() {
        if (lastLocation != null) {
            goToLocation(lastLocation);
        } else {
            startPatrol();
        }
    }

    @Override
    public void onNavigationStatusChanged(String location, String status) {
        super.onNavigationStatusChanged(location, status);

        if (OnGoToLocationStatusChangedListener.COMPLETE.equals(status)) {
            // 목적지 도착 시 다음 목적지로 이동
            lastLocation = location;
            moveToNextPatrolPoint(location);
        }
    }

    private void moveToNextPatrolPoint(String currentLocation) {
        List<String> points = patrolPoints.getValue();
        if (points == null || points.isEmpty()) return;

        // 다음 순찰 지점 계산
        String nextLocation;
        if ("prob11".equals(currentLocation)) {
            nextLocation = "prob12";
        } else if ("prob12".equals(currentLocation)) {
            nextLocation = "prob11";
        } else if ("prob21".equals(currentLocation)) {
            nextLocation = "prob22";
        } else if ("prob22".equals(currentLocation)) {
            nextLocation = "prob21";
        } else {
            // 기본값
            int currentIndex = currentPatrolIndex.getValue() != null ? currentPatrolIndex.getValue() : 0;
            int nextIndex = (currentIndex + 1) % points.size();
            nextLocation = points.get(nextIndex);
            currentPatrolIndex.setValue(nextIndex);
        }

        // 다음 목적지로 이동
        goToLocation(nextLocation);
    }

    public LiveData<List<String>> getPatrolPoints() {
        return patrolPoints;
    }

    public void updateLedColor(String color) {
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setLED1(color);
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setLED2(color);
        }
    }
}
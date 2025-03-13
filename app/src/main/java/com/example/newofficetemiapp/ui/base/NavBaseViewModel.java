package com.example.newofficetemiapp.ui.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.data.repository.TemiRepository;
import com.example.newofficetemiapp.temi.TemiNavigationHelper;

/**
 * 네비게이션 기능이 필요한 ViewModel들의 기본 클래스
 * 중복 네비게이션 코드를 통합
 */
public abstract class NavBaseViewModel extends BaseViewModel implements TemiNavigationHelper.NavigationCallback {
    protected final TemiRepository temiRepository;
    protected final TemiNavigationHelper navigationHelper;

    private final MutableLiveData<String> navigationStatus = new MutableLiveData<>();
    private final MutableLiveData<String> currentDestination = new MutableLiveData<>();

    public NavBaseViewModel() {
        temiRepository = TemiRepository.getInstance();
        navigationHelper = TemiNavigationHelper.getInstance();
    }

    public void goToLocation(String location) {
        currentDestination.setValue(location);
        navigationHelper.goToLocation(location);
    }

    public void updateNavigationStatus(String location, String status) {
        navigationStatus.setValue(status);
        onNavigationStatusChanged(location, status);
    }

    public void registerNavigationListener() {
        navigationHelper.setNavigationCallback(this);
    }

    public void unregisterNavigationListener() {
        navigationHelper.setNavigationCallback(null);
    }

    public void speak(String text) {
        temiRepository.speak(text);
    }

    public void stopMovement() {
        navigationHelper.stopNavigation();
    }

    public void startNavigation(String destination) {
        if (destination != null && !destination.isEmpty()) {
            navigationHelper.goToLocation(destination);
        }
    }

    public LiveData<String> getNavigationStatus() {
        return navigationStatus;
    }

    public LiveData<String> getCurrentDestination() {
        return currentDestination;
    }

    // 필요시 자식 클래스에서 오버라이드
    @Override
    public void onNavigationStatusChanged(String location, String status) {
        // 기본 구현은 비어있음
    }
}
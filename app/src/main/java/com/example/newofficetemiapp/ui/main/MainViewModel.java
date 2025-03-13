package com.example.newofficetemiapp.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.data.repository.TemiRepository;
import com.example.newofficetemiapp.ui.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {
    private final TemiRepository temiRepository;
    private final MutableLiveData<String> temiSerialNumber = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRobotReady = new MutableLiveData<>(false);

    public MainViewModel() {
        temiRepository = TemiRepository.getInstance();
        initData();
    }

    private void initData() {
        // 테미 시리얼 번호 설정
        temiSerialNumber.setValue(temiRepository.getTemiSerialNumber());
    }

    public LiveData<String> getTemiSerialNumber() {
        return temiSerialNumber;
    }

    public LiveData<Boolean> isRobotReady() {
        return isRobotReady;
    }

    public void setRobotReady(boolean ready) {
        isRobotReady.setValue(ready);
    }

    public void checkBatteryStatus() {
        // 배터리 상태 확인 및 알림 등 로직 추가 가능
    }
}
package com.example.newofficetemiapp.ui.hello;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.ui.base.NavBaseViewModel;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 인사 화면을 위한 ViewModel
 * NavBaseViewModel 상속 적용
 */
public class HelloViewModel extends NavBaseViewModel {
    private final MutableLiveData<Boolean> isReturningHome = new MutableLiveData<>(false);

    public HelloViewModel() {
        super();
    }

    @Override
    public void onNavigationStatusChanged(String location, String status) {
        super.onNavigationStatusChanged(location, status);

        if (!location.equals("홈베이스") && OnGoToLocationStatusChangedListener.COMPLETE.equals(status)) {
            // 목적지 도착 후 인사
            speak(location + "에 도착했습니다");

            // 홈으로 돌아가기
            isReturningHome.setValue(true);
            goToLocation("홈베이스");
        }
    }

    public LiveData<Boolean> isReturningHome() {
        return isReturningHome;
    }
}
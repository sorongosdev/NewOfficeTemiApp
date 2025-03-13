package com.example.newofficetemiapp.ui.call;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.data.repository.FirebaseRepository;
import com.example.newofficetemiapp.ui.base.NavBaseViewModel;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

/**
 * 호출 화면을 위한 ViewModel
 * NavBaseViewModel 상속 적용
 */
public class CallViewModel extends NavBaseViewModel {
    private static final String TAG = "CallViewModel";

    private final FirebaseRepository firebaseRepository;
    private final MutableLiveData<String> actionText = new MutableLiveData<>("None");

    public CallViewModel() {
        super();
        firebaseRepository = FirebaseRepository.getInstance();

        // Firebase action 값 관찰
        firebaseRepository.getAction().observeForever(action -> {
            if (action != null) {
                actionText.setValue(action);
                Log.d(TAG, "action is " + action);

                // action 값에 따라 이동
                handleActionChange(action);
            }
        });
    }



    private void handleActionChange(String action) {
        if ("point1".equals(action)) {
            goToLocation("point1");
        } else if ("point2".equals(action)) {
            goToLocation("point2");
        } else if ("point3".equals(action)) {
            goToLocation("point3");
        } else if ("point4".equals(action)) {
            goToLocation("point4");
        }
    }

    public void startFollowMe() {
        temiRepository.followMe();
    }

    @Override
    public void onNavigationStatusChanged(String location, String status) {
        super.onNavigationStatusChanged(location, status);

        if (OnGoToLocationStatusChangedListener.COMPLETE.equals(status)) {
            speak(location + "에 도착했습니다");
            firebaseRepository.setAction("None");
            actionText.setValue("None");
        }
    }

    public LiveData<String> getActionText() {
        return actionText;
    }
}
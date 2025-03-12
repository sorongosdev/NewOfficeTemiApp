
// TemiNavigationHelper.java
package com.example.newofficetemiapp.temi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 테미 로봇 내비게이션 헬퍼 클래스
 */
public class TemiNavigationHelper implements OnGoToLocationStatusChangedListener, OnLocationsUpdatedListener {
    private static final String TAG = "TemiNavigationHelper";

    private static TemiNavigationHelper instance;
    private final Robot robot;
    private final MutableLiveData<String> currentNavigationStatus = new MutableLiveData<>();
    private final MutableLiveData<String> currentDestination = new MutableLiveData<>();
    private final MutableLiveData<List<String>> savedLocations = new MutableLiveData<>();
    private NavigationCallback navigationCallback;

    private TemiNavigationHelper() {
        robot = Robot.getInstance();
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addOnLocationsUpdatedListener(this);
        savedLocations.setValue(robot.getLocations());
    }

    public static synchronized TemiNavigationHelper getInstance() {
        if (instance == null) {
            instance = new TemiNavigationHelper();
        }
        return instance;
    }

    public void setNavigationCallback(NavigationCallback callback) {
        this.navigationCallback = callback;
    }

    public void goToLocation(String location) {
        if (robot.getLocations().contains(location)) {
            currentDestination.setValue(location);
            robot.goTo(location);
            speak("이동을 시작합니다.");
        } else {
            Log.e(TAG, "Location not found: " + location);
            speak("존재하지 않는 위치입니다.");
        }
    }

    public void stopNavigation() {
        robot.stopMovement();
        speak("이동을 중지합니다.");
        currentNavigationStatus.setValue(OnGoToLocationStatusChangedListener.ABORT);
    }

    public void speak(String text) {
        TtsRequest ttsRequest = TtsRequest.create(text.trim(), false);
        robot.speak(ttsRequest);
    }

    public boolean saveLocation(String locationName) {
        boolean result = robot.saveLocation(locationName);
        if (result) {
            speak(locationName + " 위치가 저장되었습니다.");
            savedLocations.setValue(robot.getLocations());
        } else {
            speak("위치 저장에 실패했습니다.");
        }
        return result;
    }

    public LiveData<String> getCurrentNavigationStatus() {
        return currentNavigationStatus;
    }

    public LiveData<String> getCurrentDestination() {
        return currentDestination;
    }

    public LiveData<List<String>> getSavedLocations() {
        return savedLocations;
    }

    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, @NotNull String status, int descriptionId, @NotNull String description) {
        currentNavigationStatus.setValue(status);

        if (navigationCallback != null) {
            navigationCallback.onNavigationStatusChanged(location, status);
        }

        switch (status) {
            case OnGoToLocationStatusChangedListener.START:
                Log.d(TAG, "Navigation started to: " + location);
                break;

            case OnGoToLocationStatusChangedListener.CALCULATING:
                Log.d(TAG, "Calculating path to: " + location);
                break;

            case OnGoToLocationStatusChangedListener.GOING:
                Log.d(TAG, "Going to: " + location);
                break;

            case OnGoToLocationStatusChangedListener.COMPLETE:
                Log.d(TAG, "Arrived at: " + location);
                speak(location + "에 도착했습니다.");
                break;

            case OnGoToLocationStatusChangedListener.ABORT:
                Log.d(TAG, "Navigation aborted to: " + location);
                break;
        }
    }

    @Override
    public void onLocationsUpdated(@NotNull List<String> locations) {
        savedLocations.setValue(locations);
    }

    public interface NavigationCallback {
        void onNavigationStatusChanged(String location, String status);
    }

    public void cleanup() {
        robot.removeOnGoToLocationStatusChangedListener(this);
        robot.removeOnLocationsUpdateListener(this);
    }
}

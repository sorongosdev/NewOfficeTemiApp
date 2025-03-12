package com.example.newofficetemiapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.data.model.Location;
import com.example.newofficetemiapp.temi.RoboTemiService;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import java.util.ArrayList;
import java.util.List;

public class TemiRepository {
    private static TemiRepository instance;
    private final Robot robot;
    private final RoboTemiService roboTemiService;
    private final MutableLiveData<List<Location>> locations = new MutableLiveData<>();
    private final MutableLiveData<String> currentLocation = new MutableLiveData<>();
    private final MutableLiveData<String> navigationStatus = new MutableLiveData<>();

    private TemiRepository() {
        robot = Robot.getInstance();
        roboTemiService = new RoboTemiService();
        loadLocations();
    }

    public static synchronized TemiRepository getInstance() {
        if (instance == null) {
            instance = new TemiRepository();
        }
        return instance;
    }

    public String getTemiSerialNumber() {
        return robot.getSerialNumber();
    }

    public void goToLocation(String locationName) {
        roboTemiService.goTo(locationName);
        navigationStatus.setValue(OnGoToLocationStatusChangedListener.START);
    }

    public void speak(String text) {
        roboTemiService.speak(text);
    }

    public void stopMovement() {
        roboTemiService.stopMovement();
        navigationStatus.setValue(OnGoToLocationStatusChangedListener.ABORT);
    }

    public void followMe() {
        roboTemiService.followMe();
    }

    public void saveLocation(String locationName) {
        boolean result = roboTemiService.saveLocation(locationName);
        if (result) {
            loadLocations();
        }
    }

    private void loadLocations() {
        List<String> temiLocations = robot.getLocations();
        List<Location> locationList = new ArrayList<>();

        for (String locationName : temiLocations) {
            Location location = new Location();
            location.setId(locationName);
            location.setName(locationName);
            location.setDescription("Temi saved location");
            locationList.add(location);
        }

        locations.setValue(locationList);
    }

    public LiveData<List<Location>> getLocations() {
        return locations;
    }

    public LiveData<String> getCurrentLocation() {
        return currentLocation;
    }

    public LiveData<String> getNavigationStatus() {
        return navigationStatus;
    }

    public void setNavigationStatus(String status) {
        navigationStatus.setValue(status);
    }

    public void setCurrentLocation(String location) {
        currentLocation.setValue(location);
    }
}
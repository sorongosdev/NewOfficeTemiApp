package com.example.newofficetemiapp.ui.location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.newofficetemiapp.data.repository.FirebaseRepository;
import com.example.newofficetemiapp.data.repository.TemiRepository;
import com.example.newofficetemiapp.temi.TemiNavigationHelper;
import com.example.newofficetemiapp.ui.base.BaseViewModel;
import com.example.newofficetemiapp.util.Constants;

import java.util.List;

/**
 * 위치 화면을 위한 ViewModel
 */
public class LocationViewModel extends BaseViewModel {
    private final TemiRepository temiRepository;
    private final FirebaseRepository firebaseRepository;
    private final TemiNavigationHelper navigationHelper;

    private final MutableLiveData<String> selectedLocation = new MutableLiveData<>();

    public LocationViewModel() {
        temiRepository = TemiRepository.getInstance();
        firebaseRepository = FirebaseRepository.getInstance();
        navigationHelper = TemiNavigationHelper.getInstance();
    }

    public void setSelectedLocation(String location) {
        selectedLocation.setValue(location);

        // Firebase에 위치 저장
        String temiId = temiRepository.getTemiSerialNumber();
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setLocationTemi1(location);
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setLocationTemi2(location);
        }
    }

    public LiveData<String> getSelectedLocation() {
        return selectedLocation;
    }

    public LiveData<List<String>> getSavedLocations() {
        return navigationHelper.getSavedLocations();
    }

    public void saveNewLocation(String locationName) {
        setLoading(true);
        boolean result = navigationHelper.saveLocation(locationName);
        setLoading(false);

        if (!result) {
            setErrorMessage("위치 저장에 실패했습니다.");
        }
    }

    public void goToLocation(String locationName) {
        navigationHelper.goToLocation(locationName);
    }
}
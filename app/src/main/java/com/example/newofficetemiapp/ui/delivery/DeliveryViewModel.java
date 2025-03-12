package com.example.newofficetemiapp.ui.delivery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.data.repository.FirebaseRepository;
import com.example.newofficetemiapp.data.repository.TemiRepository;
import com.example.newofficetemiapp.data.repository.UserRepository;
import com.example.newofficetemiapp.ui.base.BaseViewModel;
import com.example.newofficetemiapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 배달 화면을 위한 ViewModel
 */
public class DeliveryViewModel extends BaseViewModel {
    private final TemiRepository temiRepository;
    private final FirebaseRepository firebaseRepository;
    private final UserRepository userRepository;

    private final MutableLiveData<List<DeliveryStatus>> deliveries = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<DeliveryStatus> currentDelivery = new MutableLiveData<>();
    private final MutableLiveData<String> selectedLocation = new MutableLiveData<>();

    public DeliveryViewModel() {
        temiRepository = TemiRepository.getInstance();
        firebaseRepository = FirebaseRepository.getInstance();
        userRepository = UserRepository.getInstance();

        // 테스트용 데이터 로드
        loadDeliveries();
    }

    private void loadDeliveries() {
        setLoading(true);

        // TODO: 실제 배달 데이터 로드 구현
        // 현재는 테스트용 데이터만 생성

        List<DeliveryStatus> testDeliveries = new ArrayList<>();
        testDeliveries.add(new DeliveryStatus("1", "jiyun", "sora",
                "PlanningTeam", "ExecutiveTeam",
                DeliveryStatus.STATUS_COMPLETED, System.currentTimeMillis() - 3600000));

        testDeliveries.add(new DeliveryStatus("2", "youngro", "jongchan",
                "EditorialTeam", "PlanningTeam",
                DeliveryStatus.STATUS_IN_PROGRESS, System.currentTimeMillis() - 1800000));

        testDeliveries.add(new DeliveryStatus("3", "sunghoon", "yushin",
                "ExecutiveTeam", "EditorialTeam",
                DeliveryStatus.STATUS_PENDING, System.currentTimeMillis() - 900000));

        deliveries.setValue(testDeliveries);
        setLoading(false);
    }

    public LiveData<List<DeliveryStatus>> getDeliveries() {
        return deliveries;
    }

    public void createNewDelivery(String sender, String receiver, String source, String target) {
        DeliveryStatus newDelivery = new DeliveryStatus(
                String.valueOf(System.currentTimeMillis()),
                sender,
                receiver,
                source,
                target,
                DeliveryStatus.STATUS_PENDING,
                System.currentTimeMillis()
        );

        // 현재 배달 저장
        currentDelivery.setValue(newDelivery);

        // 배달 목록에 추가
        List<DeliveryStatus> currentList = deliveries.getValue();
        if (currentList != null) {
            currentList.add(newDelivery);
            deliveries.setValue(currentList);
        }

        // Firebase에 저장할 수 있음
        // saveDeliveryToFirebase(newDelivery);
    }

    public LiveData<DeliveryStatus> getCurrentDelivery() {
        return currentDelivery;
    }

    public void setCurrentDelivery(String deliveryId) {
        List<DeliveryStatus> currentList = deliveries.getValue();
        if (currentList != null) {
            for (DeliveryStatus delivery : currentList) {
                if (delivery.getId().equals(deliveryId)) {
                    currentDelivery.setValue(delivery);
                    break;
                }
            }
        }
    }

    public void setSelectedLocation(String location) {
        selectedLocation.setValue(location);
    }

    public LiveData<String> getSelectedLocation() {
        return selectedLocation;
    }

    public void updateDeliveryStatus(String deliveryId, String newStatus) {
        List<DeliveryStatus> currentList = deliveries.getValue();
        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                DeliveryStatus delivery = currentList.get(i);
                if (delivery.getId().equals(deliveryId)) {
                    delivery.setStatus(newStatus);
                    currentList.set(i, delivery);
                    deliveries.setValue(currentList);

                    // 현재 배달이 이 배달이라면 현재 배달도 업데이트
                    DeliveryStatus current = currentDelivery.getValue();
                    if (current != null && current.getId().equals(deliveryId)) {
                        currentDelivery.setValue(delivery);
                    }

                    break;
                }
            }
        }
    }

    public void startNavigation(String destination) {
        if (destination != null && !destination.isEmpty()) {
            temiRepository.goToLocation(destination);
        }
    }

    public void completeDelivery() {
        DeliveryStatus delivery = currentDelivery.getValue();
        if (delivery != null) {
            updateDeliveryStatus(delivery.getId(), DeliveryStatus.STATUS_COMPLETED);
        }
    }

    public String getTemiId() {
        return temiRepository.getTemiSerialNumber();
    }

    public void updateLedColor(String color) {
        String temiId = getTemiId();
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setLED1(color);
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setLED2(color);
        }
    }

    public void updateMotorLock(String lockStatus) {
        String temiId = getTemiId();
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setMotorLock1(lockStatus);
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setMotorLock2(lockStatus);
        }
    }
}
package com.example.newofficetemiapp.ui.delivery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.newofficetemiapp.data.model.DeliveryStatus;
import com.example.newofficetemiapp.data.repository.FirebaseRepository;
import com.example.newofficetemiapp.data.repository.TemiRepository;
import com.example.newofficetemiapp.data.repository.UserRepository;
import com.example.newofficetemiapp.temi.TemiNavigationHelper;
import com.example.newofficetemiapp.ui.base.NavBaseViewModel;
import com.example.newofficetemiapp.util.Constants;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 배달 화면을 위한 ViewModel
 * SendViewModel과 통합
 */
public class DeliveryViewModel extends NavBaseViewModel {
    // 카드 상태 상수
    public static final String CARD_STATUS_VALID = "valid";
    public static final String CARD_STATUS_INVALID = "invalid";
    public static final String CARD_STATUS_EMPTY = "empty";

    private final FirebaseRepository firebaseRepository;
    private final UserRepository userRepository;

    private final MutableLiveData<List<DeliveryStatus>> deliveries = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<DeliveryStatus> currentDelivery = new MutableLiveData<>();
    private final MutableLiveData<String> selectedLocation = new MutableLiveData<>();
    private final MutableLiveData<String> cardStatus = new MutableLiveData<>(CARD_STATUS_EMPTY);
    private final MutableLiveData<String> destinationLocation = new MutableLiveData<>();
    private final MutableLiveData<String> senderInfo = new MutableLiveData<>();

    private final String temiId;

    // Observer 객체들을 저장하기 위한 변수
    private Observer<String> locationObserver;
    private Observer<String> senderObserver;
    private Observer<String> cardObserver;

    public DeliveryViewModel() {
        super();
        firebaseRepository = FirebaseRepository.getInstance();
        userRepository = UserRepository.getInstance();

        temiId = temiRepository.getTemiSerialNumber();

        // 테스트용 데이터 로드
        loadDeliveries();

        // Firebase에서 카드 상태 관찰
        observeCardStatus();

        // 목적지 위치 로드
        loadDestinationFromFirebase();
    }

    private void loadDestinationFromFirebase() {
        if (Constants.TEMI1.equals(temiId)) {
            locationObserver = locationValue -> {
                if (locationValue != null &&
                        (locationValue.equals("ExecutiveTeam") ||
                                locationValue.equals("EditorialTeam"))) {
                    destinationLocation.setValue(locationValue);
                    // 위치 초기화
                    firebaseRepository.setLocation("null");
                }
            };
            firebaseRepository.getLocation().observeForever(locationObserver);
        } else if (Constants.TEMI2.equals(temiId)) {
            locationObserver = locationValue -> {
                if (locationValue != null &&
                        (locationValue.equals("PlanningTeam") ||
                                locationValue.equals("MeetingRoom"))) {
                    destinationLocation.setValue(locationValue);
                    // 위치 초기화
                    firebaseRepository.setLocation("null");
                }
            };
            firebaseRepository.getLocation().observeForever(locationObserver);
        }

        // 발신자 정보 로드
        loadSenderInfo();
    }

    private void loadSenderInfo() {
        if (Constants.TEMI1.equals(temiId)) {
            senderObserver = sender -> {
                if (sender != null && !sender.equals("null")) {
                    senderInfo.setValue(sender);
                }
            };
            firebaseRepository.getSenderTemi1().observeForever(senderObserver);
        } else if (Constants.TEMI2.equals(temiId)) {
            senderObserver = sender -> {
                if (sender != null && !sender.equals("null")) {
                    senderInfo.setValue(sender);
                }
            };
            firebaseRepository.getSenderTemi2().observeForever(senderObserver);
        }
    }

    private void observeCardStatus() {
        if (Constants.TEMI1.equals(temiId)) {
            cardObserver = cardId -> validateCardId(cardId);
            firebaseRepository.getIDCardNumber1().observeForever(cardObserver);
        } else if (Constants.TEMI2.equals(temiId)) {
            cardObserver = cardId -> validateCardId(cardId);
            firebaseRepository.getIDCardNumber2().observeForever(cardObserver);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        // Observer 제거하여 메모리 누수 방지
        if (locationObserver != null) {
            firebaseRepository.getLocation().removeObserver(locationObserver);
        }

        if (senderObserver != null) {
            if (Constants.TEMI1.equals(temiId)) {
                firebaseRepository.getSenderTemi1().removeObserver(senderObserver);
            } else if (Constants.TEMI2.equals(temiId)) {
                firebaseRepository.getSenderTemi2().removeObserver(senderObserver);
            }
        }

        if (cardObserver != null) {
            if (Constants.TEMI1.equals(temiId)) {
                firebaseRepository.getIDCardNumber1().removeObserver(cardObserver);
            } else if (Constants.TEMI2.equals(temiId)) {
                firebaseRepository.getIDCardNumber2().removeObserver(cardObserver);
            }
        }
    }

    private void validateCardId(String cardId) {
        DeliveryStatus delivery = currentDelivery.getValue();

        if (cardId == null || "null".equals(cardId)) {
            // 카드가 없음
            cardStatus.setValue(CARD_STATUS_EMPTY);
        } else if (delivery != null) {
            // 카드 ID 유효성 검사
            boolean isValid = false;

            // 발신자 검증 (발신자 카드로 보내는 경우)
            if (delivery.getSenderId().equals(cardId)) {
                isValid = true;
            }

            // 수신자 검증 (수신자 카드로 받는 경우)
            if (delivery.getReceiverId().equals(cardId)) {
                isValid = true;
            }

            // 팀 구성원 검증 (같은 팀 구성원의 카드)
            if (delivery.getTargetLocation().equals("PlanningTeam") &&
                    (cardId.equals("jiyun") || cardId.equals("jongchan"))) {
                isValid = true;
            } else if (delivery.getTargetLocation().equals("ExecutiveTeam") &&
                    (cardId.equals("sora") || cardId.equals("sunghoon"))) {
                isValid = true;
            } else if (delivery.getTargetLocation().equals("EditorialTeam") &&
                    (cardId.equals("youngro") || cardId.equals("yushin"))) {
                isValid = true;
            }

            if (isValid) {
                cardStatus.setValue(CARD_STATUS_VALID);
            } else {
                cardStatus.setValue(CARD_STATUS_INVALID);
            }
        } else {
            // 현재 배달이 없는 경우
            cardStatus.setValue(CARD_STATUS_INVALID);
        }
    }

    public void resetCardNumber() {
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setIdCardNumber1("null");
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setIdCardNumber2("null");
        }

        cardStatus.setValue(CARD_STATUS_EMPTY);
    }

    public LiveData<String> getCardStatus() {
        return cardStatus;
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

    @Override
    public void onNavigationStatusChanged(String location, String status) {
        super.onNavigationStatusChanged(location, status);
        // 필요한 추가 처리 구현
    }

    public void completeDelivery() {
        DeliveryStatus delivery = currentDelivery.getValue();
        if (delivery != null) {
            updateDeliveryStatus(delivery.getId(), DeliveryStatus.STATUS_COMPLETED);
        }
    }

    public void resetDeliveryData() {
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setIdCardNumber1("null");
            firebaseRepository.setSenderTemi1("null");
            firebaseRepository.setLocationTemi1("null");
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setIdCardNumber2("null");
            firebaseRepository.setSenderTemi2("null");
            firebaseRepository.setLocationTemi2("null");
        }
    }

    public String getTemiId() {
        return temiId;
    }

    public void updateLedColor(String color) {
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setLED1(color);
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setLED2(color);
        }
    }

    public void updateMotorLock(String lockStatus) {
        if (Constants.TEMI1.equals(temiId)) {
            firebaseRepository.setMotorLock1(lockStatus);
        } else if (Constants.TEMI2.equals(temiId)) {
            firebaseRepository.setMotorLock2(lockStatus);
        }
    }

    // 통합된 SendViewModel 메서드들
    public LiveData<String> getDestinationLocation() {
        return destinationLocation;
    }

    public LiveData<String> getSenderInfo() {
        return senderInfo;
    }
}
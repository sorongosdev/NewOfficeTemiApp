package com.example.newofficetemiapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRepository {
    private static FirebaseRepository instance;
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference databaseReference;

    private final MutableLiveData<String> idCardNumber1 = new MutableLiveData<>();
    private final MutableLiveData<String> idCardNumber2 = new MutableLiveData<>();
    private final MutableLiveData<String> locationTemi1 = new MutableLiveData<>();
    private final MutableLiveData<String> locationTemi2 = new MutableLiveData<>();
    private final MutableLiveData<String> senderTemi1 = new MutableLiveData<>();
    private final MutableLiveData<String> senderTemi2 = new MutableLiveData<>();
    private final MutableLiveData<String> action = new MutableLiveData<>();
    private final MutableLiveData<String> location = new MutableLiveData<>();

    private FirebaseRepository() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        // ID 카드 리스너 설정
        firebaseDatabase.getReference("ID_CARD_NUMBER1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    idCardNumber1.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });

        firebaseDatabase.getReference("ID_CARD_NUMBER2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    idCardNumber2.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });

        // 위치 리스너 설정
        firebaseDatabase.getReference("location_temi1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    locationTemi1.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });

        firebaseDatabase.getReference("location_temi2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    locationTemi2.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });

        // 발신자 리스너 설정
        firebaseDatabase.getReference("Sender_temi1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    senderTemi1.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });

        firebaseDatabase.getReference("Sender_temi2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    senderTemi2.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });

        // 액션 리스너 설정
        firebaseDatabase.getReference("action").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    action.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });

        // 위치 리스너 설정
        firebaseDatabase.getReference("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    location.setValue(value.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });
    }

    public static synchronized FirebaseRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseRepository();
        }
        return instance;
    }

    // 데이터 조회 메서드
    public LiveData<String> getIDCardNumber1() {
        return idCardNumber1;
    }

    public LiveData<String> getIDCardNumber2() {
        return idCardNumber2;
    }

    public LiveData<String> getLocationTemi1() {
        return locationTemi1;
    }

    public LiveData<String> getLocationTemi2() {
        return locationTemi2;
    }

    public LiveData<String> getSenderTemi1() {
        return senderTemi1;
    }

    public LiveData<String> getSenderTemi2() {
        return senderTemi2;
    }

    public LiveData<String> getAction() {
        return action;
    }

    public LiveData<String> getLocation() {
        return location;
    }

    // 데이터 설정 메서드
    public void setIdCardNumber1(String value) {
        databaseReference.child("ID_CARD_NUMBER1").setValue(value);
    }

    public void setIdCardNumber2(String value) {
        databaseReference.child("ID_CARD_NUMBER2").setValue(value);
    }

    public void setLocationTemi1(String value) {
        databaseReference.child("location_temi1").setValue(value);
    }

    public void setLocationTemi2(String value) {
        databaseReference.child("location_temi2").setValue(value);
    }

    public void setSenderTemi1(String value) {
        databaseReference.child("Sender_temi1").setValue(value);
    }

    public void setSenderTemi2(String value) {
        databaseReference.child("Sender_temi2").setValue(value);
    }

    public void setAction(String value) {
        databaseReference.child("action").setValue(value);
    }

    public void setLocation(String value) {
        databaseReference.child("location").setValue(value);
    }

    public void setLED1(String value) {
        databaseReference.child("LED1").setValue(value);
    }

    public void setLED2(String value) {
        databaseReference.child("LED2").setValue(value);
    }

    public void setMotorLock1(String value) {
        databaseReference.child("MOTOR_LOCK1").setValue(value);
    }

    public void setMotorLock2(String value) {
        databaseReference.child("MOTOR_LOCK2").setValue(value);
    }
}
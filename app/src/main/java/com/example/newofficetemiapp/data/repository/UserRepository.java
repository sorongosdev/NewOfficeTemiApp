// UserRepository.java
package com.example.newofficetemiapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 정보 관리 저장소
 */

public class UserRepository {
    private static UserRepository instance;
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference usersReference;
    private final Map<String, MutableLiveData<User>> userCache = new HashMap<>();
    private final MutableLiveData<List<User>> allUsers = new MutableLiveData<>(new ArrayList<>());

    private UserRepository() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("users");

        // 모든 사용자 목록 감시
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        users.add(user);

                        // 캐시 업데이트
                        MutableLiveData<User> cachedUser = userCache.get(user.getId());
                        if (cachedUser != null) {
                            cachedUser.setValue(user);
                        }
                    }
                }
                allUsers.setValue(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUserById(String userId) {
        if (!userCache.containsKey(userId)) {
            MutableLiveData<User> userData = new MutableLiveData<>();
            userCache.put(userId, userData);

            usersReference.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    userData.setValue(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // 에러 처리
                }
            });
        }

        return userCache.get(userId);
    }

    public void saveUser(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            String newId = usersReference.push().getKey();
            user.setId(newId);
        }

        usersReference.child(user.getId()).setValue(user);
    }

    public LiveData<List<User>> getUsersByTeam(String teamName) {
        MutableLiveData<List<User>> teamUsers = new MutableLiveData<>(new ArrayList<>());

        usersReference.orderByChild("teamName").equalTo(teamName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<User> users = new ArrayList<>();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                users.add(user);
                            }
                        }
                        teamUsers.setValue(users);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // 에러 처리
                    }
                });

        return teamUsers;
    }

    public void deleteUser(String userId) {
        usersReference.child(userId).removeValue();
        userCache.remove(userId);
    }
}

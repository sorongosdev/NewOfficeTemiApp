package com.example.newofficetemiapp.ui.base;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

/**
 * 모든 ViewModel의 기본 클래스
 * 공통 기능을 여기에 구현
 */
public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    protected void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    protected void setErrorMessage(String message) {
        errorMessage.setValue(message);
    }

    protected void clearError() {
        errorMessage.setValue(null);
    }
}
package com.example.newofficetemiapp.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseActivity<VM extends ViewModel> extends AppCompatActivity {
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        setupViews();
        observeViewModel();
    }

    protected abstract int getLayoutId();
    protected abstract Class<VM> getViewModelClass();
    protected abstract void setupViews();
    protected abstract void observeViewModel();
}
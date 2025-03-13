package com.example.newofficetemiapp.ui.menu;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.newofficetemiapp.R;
import com.example.newofficetemiapp.ui.base.BaseActivity;

/**
 * 식단 메뉴 화면
 * 웹뷰를 통해 식단 메뉴를 표시하는 화면
 */
public class MenuActivity extends BaseActivity<MenuViewModel> {

    private WebView webView;
    private Button backButton;

    @Override
    protected int getLayoutId() {
        return R.layout.memu;
    }

    @Override
    protected Class<MenuViewModel> getViewModelClass() {
        return MenuViewModel.class;
    }

    @Override
    protected void setupViews() {
        webView = findViewById(R.id.webView);
        backButton = findViewById(R.id.back1);

        // 웹뷰 설정
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());

        // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 메뉴 URL 로드
        loadMenuUrl();
    }

    @Override
    protected void observeViewModel() {
        // 메뉴 URL 관찰
        viewModel.getMenuUrl().observe(this, url -> {
            if (url != null && !url.isEmpty()) {
                webView.loadUrl(url);
            }
        });
    }

    private void loadMenuUrl() {
        viewModel.loadMenuUrl();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
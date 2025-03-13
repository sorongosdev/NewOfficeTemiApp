package com.example.newofficetemiapp.ui.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newofficetemiapp.ui.base.BaseViewModel;

/**
 * 메뉴 화면을 위한 ViewModel
 */
public class MenuViewModel extends BaseViewModel {
    private final MutableLiveData<String> menuUrl = new MutableLiveData<>();

    public MenuViewModel() {
        // 기본 메뉴 URL 설정
        menuUrl.setValue("https://www.hanyang.ac.kr/web/www/re15?p_p_id=foodView_WAR_foodportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=2&_foodView_WAR_foodportlet_sFoodDateDay=7&_foodView_WAR_foodportlet_sFoodDateYear=2021&_foodView_WAR_foodportlet_action=view&_foodView_WAR_foodportlet_sFoodDateMonth=11");
    }

    /**
     * 메뉴 URL을 로드합니다.
     * 필요시 동적으로 URL을 변경할 수 있습니다.
     */
    public void loadMenuUrl() {
        setLoading(true);

        // 필요시 URL을 동적으로 생성하거나 API에서 가져오는 로직 추가 가능

        setLoading(false);
    }

    public LiveData<String> getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String url) {
        menuUrl.setValue(url);
    }
}